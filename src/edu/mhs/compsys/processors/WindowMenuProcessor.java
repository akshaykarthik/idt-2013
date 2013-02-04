/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in the window-menus and quantify those.
 * It will recognize WINDOW_MENU_OPEN, WINDOW_MENU_CLOSE, and
 * WINDOW_MENU_ITEM_SELECTED,
 * 
 */
public class WindowMenuProcessor implements IChangeProcessor
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

		int minX = 1280, minY = 1024, maxX = 0, maxY = 0;
		
		for (int boundNum = previousStateWindows.size() - 1; boundNum >= 0; boundNum++)//go through the windows from the previous change
		{
			for (int windX = previousStateWindows.get(boundNum).getX(); windX < previousStateWindows.get(boundNum).getWidth() + previousStateWindows.get(boundNum).getX(); windX++)
			{
				//checkHeight is the ammout of the top of the window to check for changes to be window changes
				int checkHeight = Math.min(150, previousStateWindows.get(boundNum).getHeight());
				for (int windY = previousStateWindows.get(boundNum).getY(); windY < checkHeight; windY++)
				{
					if (diff.get(windX, windY))
					{
						minX = Math.min(minX, windX);
						minY = Math.min(minY, windY);
						maxX = Math.max(maxX, windX);
						maxY = Math.max(maxY, windY);
					}
				}
			}
		}

		 _changes.add(new Change(new Bounds(minX, minY, maxX-minX, maxY-minY),		 ClassificationType.WINDOW_MENU_CLOSE));

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
