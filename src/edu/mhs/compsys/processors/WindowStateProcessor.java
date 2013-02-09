/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.BoundsProcessor;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
import edu.mhs.compsys.processing.ChangeBundle;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.processing.ImageProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in windows and quantify those. This
 * will quantify WINDOW_OPEN, WINDOW_CLOSE, WINDOW_RESIZE, WINDOW_MOVE
 */
public class WindowStateProcessor implements IChangeProcessor
{

	private ArrayList<Change>	_changes;
	private Config				cfg;

	/**
	 * Initialize the processor with the given config file.
	 * 
	 * @see edu.mhs.compsys.processing.IChangeProcessor#initialize(edu.mhs.compsys.utils.Config)
	 */
	@Override
	public void initialize(Config cfg)
	{
		this.cfg = cfg;
	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#process(java.awt.image.
	 *      BufferedImage, java.awt.image.BufferedImage,
	 *      edu.mhs.compsys.processing.BinaryImage, java.util.ArrayList,
	 *      edu.mhs.compsys.idt.Dataset)
	 */
	@Override
	public void process(BufferedImage img, BufferedImage img2,
			BinaryImage diff, ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows)
	{

		DesktopTaskbarChangeProcessor dTbChange = new DesktopTaskbarChangeProcessor();
		dTbChange.process(img, img2, diff, changes, data, previousStateWindows);
		_changes = new ArrayList<Change>();
		BufferedImage xButton = ImageProcessor.intArrayToBufferedImage(cfg
				.getColorOfX());
		if (Arrays.asList(dTbChange.getChanges()).contains(
				ClassificationType.TASKBAR_UPDATE_OPEN) || Arrays.asList(dTbChange.getChanges()).contains(
				ClassificationType.TASKBAR_UPDATE_CLOSE))// IF THERE IS A
															// TASKBAR
			// UPDATE AND A DESKTOP ICON
			// UPDATE
			for (int i = 0; i < changes.size(); i++)
			{
				if (Arrays.asList(dTbChange.getChanges()).contains(
						ClassificationType.DESKTOP_ICON_CHANGE))
					_changes.add(new Change(BinaryImageProcessor
							.boundsOfChange(diff),
							ClassificationType.WINDOW_OPEN));

				else if (changes.get(changes.size() - 1).getChange(i).getType()
						.toString()
						.equals(ClassificationType.WINDOW_TITLE_BAR_CLICK))
					_changes.add(new Change(BinaryImageProcessor
							.boundsOfChange(diff),
							ClassificationType.WINDOW_CLOSE));
			}

		// If the window has 1 corner remaining the same or the entire image is
		// a change, it's a window resize. LARGE IF STATEMENT
		Bounds difBounds = BinaryImageProcessor.boundsOfChange(diff);
		for (int i = 0; i < previousStateWindows.size(); i++)
		{
			Bounds window = previousStateWindows.get(i);
			if ((window.getTopLeft().equals(difBounds.getTopLeft())
					|| window.getTopRight().equals(difBounds.getTopRight())
					|| window.getBotLeft().equals(difBounds.getBotLeft()) || window
					.getBotRight().equals(difBounds.getBotRight()))
					&& (BoundsProcessor.inside(window, difBounds) || BoundsProcessor
							.inside(difBounds, window)))
				_changes.add(new Change(difBounds,
						ClassificationType.WINDOW_RESIZE));
			if (difBounds.getX() == 0
					&& difBounds.getY() == 0
					&& difBounds.getWidth() == cfg.getImageWidth() - 1
					&& difBounds.getHeight() == cfg.getImageHeight()
							- cfg.getTaskBarHeight() - 1)
				_changes.add(new Change(difBounds,
						ClassificationType.WINDOW_RESIZE));
			if (difBounds.size() == window.size() && !difBounds.getTopLeft().equals(window.getTopLeft()))
				_changes.add(new Change(difBounds, ClassificationType.WINDOW_MOVE));
		}

		/*
		 * ArrayList<Point> windowCorners = ImageProcessor.findIn(img2,
		 * xButton);
		 * 
		 * for (Point p : windowCorners) { _changes.add(new Change(new Bounds(0,
		 * 0, p.x, p.y), ClassificationType.WINDOW_OPEN)); }
		 */

	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#getChanges()
	 */
	@Override
	public Change[] getChanges()
	{
		// TODO Auto-generated method stub
		return _changes.toArray(new Change[0]);
	}
	public void proProcess(BufferedImage img1, BufferedImage img2, BinaryImage diff, ArrayList<ChangeBundle> prevChanges)
	{
		_changes = new ArrayList<Change>();

		int checkWidth = cfg.getImageWidth();
		int checkHeight = cfg.getImageHeight() - cfg.getTaskBarHeight();

		ArrayList<Bounds> areas = new ArrayList<Bounds>();

		for (int x = 0; x < checkWidth; x++)// total checking X area
		{
			for (int y = 0; y < checkHeight; y++)// total checking Y
													// area
			{
				if (diff.get(x, y) && notContained(x, y, areas))
				{
					int minx = x, maxx = x, miny = y, maxy = y;
					boolean L = true, R = true, U = true, D = true;
					while (L || R || U || D)
					{
						if (L)
						{
							L = false;
							for (int i = miny; i <= maxy; i++)
								if (diff.get(minx, i))
									L = true;
							if (L)
								minx--;
						}
						if (R)
						{
							R = false;
							for (int i = miny; i < maxy; i++)
								if (diff.get(maxx, i))
									R = true;
							if (R)
								maxx++;
						}
						if (U)
						{
							U = false;
							for (int i = minx; i < maxx; i++)
								if (diff.get(i, miny))
									U = true;
							if (U)
								miny--;
						}
						if (D)
						{
							D = false;
							for (int i = minx; i < maxx; i++)
								if (diff.get(i, maxy))
									D = true;
							if (D)
								maxy++;
						}
					}
					boolean addNew = true;
					Bounds newB = new Bounds(minx, miny, maxy - miny, maxx - minx);
					for (int i = 0; i < areas.size(); i++)
					{
						if (newB.overlaps(areas.get(i)))
						{
							areas.get(i).merge(newB);
							addNew = false;
						}
					}
					if (addNew)
						areas.add(newB);
				}
			}
		}

		Bounds biggestBounds = new Bounds(-1, -1, -1, -1);
		for (int i = 0; i < areas.size(); i++)
		{
			if (areas.get(i).getHeight() * areas.get(i).getWidth() > biggestBounds.getWidth() * biggestBounds.getHeight())
				biggestBounds = areas.get(i);

		}
		int minDimension = Math.min(biggestBounds.getWidth(), biggestBounds.getHeight());
		boolean addedIcon = false;
		if (!boundsInPrevWindow(biggestBounds, prevChanges))
		{
			if (minDimension > 300)
			{
				if (taskbarOpen(prevChanges) && taskbarChangesExist(prevChanges) && !boundsInPrevWindow(biggestBounds, prevChanges))
				{
					_changes.add(new Change(biggestBounds, ClassificationType.WINDOW_OPEN));
					if (pastIconSelect(prevChanges))
					{
						addedIcon = true;
						_changes.add(new Change(pastIcon(prevChanges), ClassificationType.DESKTOP_ICON_CHANGE));
					}
				}

			}
			if (!addedIcon && minDimension > 40 && minDimension < 300 && !boundsInPrevWindow(biggestBounds, prevChanges))
			{
				_changes.add(new Change(biggestBounds, ClassificationType.DESKTOP_ICON_CHANGE));
			}
		}
		else
		// in previous window
		{
			if (!(taskbarOpen(prevChanges)) && taskbarChangesExist(prevChanges))
			{
				_changes.add(new Change(biggestBounds, ClassificationType.WINDOW_CLOSE));
				// or resize
			}
		}

	}
	private boolean taskbarChangesExist(ArrayList<ChangeBundle> c)
	{
		boolean ret = false;
		for (int i = 0; i < c.size(); i++)
		{
			for (int j = 0; j < c.get(i).size(); j++)
			{
				if (c.get(i).get(j).getType().equals(ClassificationType.TASKBAR_UPDATE_CLOSE) || c.get(i).get(j).getType().equals(ClassificationType.TASKBAR_UPDATE_OPEN))
					ret = true;
			}
		}
		return ret;
	}
	private boolean boundsInPrevWindow(Bounds b, ArrayList<ChangeBundle> c)
	{
//DREW
		ArrayList<Bounds> windows = new ArrayList<Bounds>();

		for (int i = 0; i < c.size(); i++)
		{
			for (int j = 0; j < c.get(i).size(); j++)
			{
				if (c.get(i).get(j).getType().equals(ClassificationType.WINDOW_OPEN) || c.get(i).get(j).getType().equals(ClassificationType.WINDOW_RESIZE) || c.get(i).get(j).getType().equals(ClassificationType.WINDOW_MOVE))
				{
					windows.add(c.get(i).get(j).getBounds());
				}
				else if (c.get(i).get(j).getType().equals(ClassificationType.WINDOW_CLOSE))
				{
					for (int w = windows.size() - 1; w >= 0; w--)
					{
						if (windows.get(w).overlaps(c.get(i).get(j).getBounds()))
							windows.remove(w);
					}
				}
			}
		}

		boolean ret = false;
		for (int i = 0; i < windows.size(); i++)
			if (b.overlaps(windows.get(i)))
				ret = true;

		return ret;
		// boolean windowAlreadyThere = false;
		// for (int i = 0; i < c.size(); i++)
		// {
		// for (int j = 0; j < c.get(i).size(); j++)
		// {
		// if ((c.get(i).get(j).getType().equals(ClassificationType.WINDOW_OPEN)
		// || c.get(i).get(j).getType().equals(ClassificationType.WINDOW_RESIZE)
		// || c.get(i).get(j).getType().equals(ClassificationType.WINDOW_MOVE))
		// &&
		// b.overlaps(c.get(i).get(j).getBounds()))
		// {
		// windowAlreadyThere = true;
		// }
		// }
		// }
		// return windowAlreadyThere;
	}
	private Bounds pastIcon(ArrayList<ChangeBundle> c)
	{
		Bounds latest = new Bounds();
		boolean found = false;
		for (int i = 0; i < c.size(); i++)
		{
			for (int j = 0; j < c.get(i).size(); j++)
			{
				if (c.get(i).get(j).getType().equals(ClassificationType.DESKTOP_ICON_CHANGE))
				{
					if (!found)
					{
						latest = c.get(i).get(j).getBounds();
						found = true;
					}

				}
			}
		}
		return latest;
	}

	private boolean pastIconSelect(ArrayList<ChangeBundle> c)
	{
		int numSelects = 0;
		for (int i = 0; i < c.size(); i++)
		{
			for (int j = 0; j < c.get(i).size(); j++)
			{
				if (c.get(i).get(j).getType().equals(ClassificationType.DESKTOP_ICON_CHANGE))
					numSelects++;
			}
		}
		return (numSelects % 2) == 0;
	}

	private boolean taskbarOpen(ArrayList<ChangeBundle> c)
	{
		int latestOpen = -1;
		int latestClose = -1;
		for (int i = 0; i < c.size(); i++)
		{
			for (int j = 0; j < c.get(i).size(); j++)
			{
				if (c.get(i).get(j).getType() == ClassificationType.TASKBAR_UPDATE_OPEN)
					latestOpen = i;
				else if (c.get(i).get(j).getType() == ClassificationType.TASKBAR_UPDATE_CLOSE)
					latestClose = i;

			}
		}
		return latestOpen > latestClose;
	}
	private boolean notContained(int x, int y, ArrayList<Bounds> bnds)
	{
		boolean ret = true;
		for (int i = 0; i < bnds.size(); i++)
		{
			if (bnds.get(i).contains(x, y))
				ret = false;
		}
		return ret;
	}

	public ArrayList<Change> getPROChanges()
	{
		if (_changes == null)
		{
			System.out.println(this.getClass() + ".getPROChanges returned null");
			return new ArrayList<Change>();
		}
		return _changes;
	}
}
