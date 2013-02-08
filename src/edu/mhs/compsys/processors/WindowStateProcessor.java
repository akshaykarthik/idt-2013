/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
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
}
