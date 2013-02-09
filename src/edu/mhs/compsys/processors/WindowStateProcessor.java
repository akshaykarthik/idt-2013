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
				ClassificationType.TASKBAR_UPDATE))// IF THERE IS A TASKBAR
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
		boolean somethingHappened = false;
		int startX = 0, startY = 0, endX = 0, endY = 0;
		startY = cfg.getTaskBarHeight();

		// bounds of change to be returned
		Bounds bounds = new Bounds(-1, -1, -1, -1);

		int checkAreaStartX = 0;
		int checkAreaStartY = 0;
		int checkWidth = cfg.getImageWidth();
		int checkHeight = cfg.getImageHeight() - cfg.getTaskBarHeight();

		boolean[][] checked = new boolean[checkWidth][checkHeight];// stops
																	// overlaps
		for (int x = 0; x < checked.length; x++)
			for (int y = 0; y < checked[0].length; y++)
				checked[x][y] = false;

		for (int x = 0; x < checkWidth; x++)// total checking X area
		{
			for (int y = 0; y < checkHeight; y++)// total checking Y
													// area
			{
				if (diff.get(x + checkAreaStartX, y + checkAreaStartY) &&
						!checked[x][y])// if an unchecked part
				{
					checked[x][y] = true;
					somethingHappened = true;

					startX = x;
					startY = y;

					int width = 0;
					int height = 0;

					// checking for the current width of change
					boolean stopHeightCheck = false;
					for (int w = 0; w < checkWidth - x - 1; w++)
					{
						if (checked[w][y])
							width++;
						else
							stopHeightCheck = true;
					}
					// checking for the curent height of change
					boolean stopWidthCheck = false;
					for (int h = 0; h < checkHeight - y - 1; h++)
					{
						if (checked[x][h])
							height++;
						else
							stopWidthCheck = true;
					}
					endX = startX + width;
					endY = startY + height;
				}
			}
		}

		if (somethingHappened)
			_changes.add(new Change(new Bounds(startX, startY, endY - startY, endX - startX), ClassificationType.WINDOW_OPEN));

	}
	public ArrayList<Change> getPROChanges()
	{
		return _changes;
	}
}
