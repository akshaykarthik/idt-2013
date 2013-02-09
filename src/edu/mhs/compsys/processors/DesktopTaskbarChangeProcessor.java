/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
import edu.mhs.compsys.processing.ChangeBundle;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in the desktop and taskbar and quantify
 * those. It will quantify TASKBAR_UPDATE and DESKTOP_ICON_CHANGE.
 */
public class DesktopTaskbarChangeProcessor implements IChangeProcessor
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
		_changes = new ArrayList<Change>();

		// Taskbar Change
		Bounds taskbarBounds = new Bounds(0, cfg.getImageHeight()
				- cfg.getTaskBarHeight() - 1, cfg.getTaskBarHeight(),
				cfg.getImageWidth() - cfg.getDateWidth());

		Bounds taskChange = BinaryImageProcessor.boundsOfChange(diff,
				taskbarBounds);

		if (taskChange.getX() > -1)
		{
			_changes.add(new Change(taskChange,
					ClassificationType.TASKBAR_UPDATE_OPEN));
		}

		// Desktop Change

		Bounds desktopBounds = new Bounds(0, 0, cfg.getImageHeight()
				- cfg.getTaskBarHeight() - 1, cfg.getImageWidth() - 1);
		Bounds desktopChange = BinaryImageProcessor.boundsOfChange(diff,
				desktopBounds, previousStateWindows);

		if (desktopChange.getX() < Integer.MAX_VALUE)
		{
			_changes.add(new Change(desktopChange,
					ClassificationType.DESKTOP_ICON_CHANGE));

		}

		// Bounds deskChange = BinaryImageProcessor.boundsOfChange(diff,
		// new Bounds(0, 0, cfg.getImageWidth() - 1, cfg.getImageHeight()
		// - cfg.getTaskBarHeight() - 1));
		// boolean notInAnyWindows = true;
		// for(int i = 0; i < previousStateWindows.size(); i++){
		// if(BoundsProcessor.intersect(previousStateWindows.get(i),
		// deskChange)){
		// notInAnyWindows = false;
		// break;
		// }
		// }
		// if(notInAnyWindows)
		// _changes.add(new Change(deskChange,
		// ClassificationType.DESKTOP_ICON_CHANGE));

	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#getChanges()
	 */
	@Override
	public Change[] getChanges()
	{
		return _changes.toArray(new Change[0]);
	}

	public void proProcess(BufferedImage img1, BufferedImage img2, BinaryImage diff, ArrayList<ChangeBundle> prevChanges)
	{
		_changes = new ArrayList<Change>();

		boolean somethingHappened = false;
		int startX = 0, startY = 0, endX = 0, endY = 0;
		startY = cfg.getTaskBarHeight();
		Bounds bounds = new Bounds(0, startY, cfg.getImageWidth(), cfg.getTaskBarHeight());

		boolean[][] checked = new boolean[cfg.getImageWidth()][cfg.getImageHeight()];
		for (int x = 0; x < checked.length; x++)
			for (int y = 0; y < checked[0].length; y++)
				checked[x][y] = false;

		int taskBarStartY = cfg.getImageHeight() - cfg.getTaskBarHeight();

		for (int x = 0; x < cfg.getImageWidth() - cfg.getDateWidth(); x++)
		{
			for (int y = 0; y < cfg.getTaskBarHeight(); y++)
			{
				if (diff.get(x, taskBarStartY + y) &&
						!checked[x][y])
				{
					checked[x][y] = true;
					somethingHappened = true;

					startX = x;
					startY = y;

					int width = 0;
					int height = 0;
					for (int w = 0; w < cfg.getImageWidth() - x - 1; w++)
					{
						if (checked[w][y])
							width++;
						else
							w = cfg.getImageWidth();
					}

					for (int h = 0; h < cfg.getImageHeight() - y - 1; h++)
					{
						if (checked[x][h])
							height++;
						else
							h = cfg.getImageHeight();
					}
					endX = startX + width;
					endY = startY + height;
				}
			}
		}

		if (somethingHappened)
			if (previousTaskbarChange(prevChanges))
				_changes.add(new Change(new Bounds(startX, cfg.getImageHeight() - startY, endY - startY, endX - startX), ClassificationType.TASKBAR_UPDATE_CLOSE));
			else
				_changes.add(new Change(new Bounds(startX, cfg.getImageHeight() - startY, endY - startY, endX - startX), ClassificationType.TASKBAR_UPDATE_OPEN));
	}
	private boolean previousTaskbarChange(ArrayList<ChangeBundle> c)
	{
		return false;
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
