/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

		_changes = new ArrayList<Change>();
		BufferedImage xButton = ImageProcessor.intArrayToBufferedImage(cfg
				.getColorOfX());

		boolean newXButtonFound = true;
		boolean sameXButtonFound = true;
		boolean xButtonLost = true;
		int imgxButtonCount = 0;
		int img2xButtonCount = 0;

		for (int x = 0; x < cfg.getImageWidth() - xButton.getWidth() - 1; x++)
		{
			for (int y = 0; y < cfg.getImageHeight() - xButton.getHeight() - 1; y++)
			{

				// checking for xButtons in img
				boolean fullXButtonFound = true;
				for (int xx = x; xx < x + xButton.getWidth(); xx++)
				{
					for (int yy = y; yy < y + xButton.getHeight(); yy++)
					{
						if (img.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
						{
							fullXButtonFound = false;
						}
					}
				}
				if (fullXButtonFound)
					imgxButtonCount++;
				// checking for xButtons in img2
				fullXButtonFound = true;
				for (int xx = x; xx < x + xButton.getWidth(); xx++)
				{
					for (int yy = y; yy < y + xButton.getHeight(); yy++)
					{
						if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
						{
							fullXButtonFound = false;
						}
					}
				}
				if (fullXButtonFound)
					imgxButtonCount++;

				// /////
				// /////
				// /////

				// xButton found in second image and not in first
				if (img2.getRGB(x, y) == xButton.getRGB(x, y) && img.getRGB(x, y) != xButton.getRGB(x, y))
				{
					for (int xx = x; xx < x + xButton.getWidth(); xx++)
					{
						for (int yy = y; yy < y + xButton.getHeight(); yy++)
						{
							if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
								newXButtonFound = false;
						}
					}
				}
				// xButton found in both images in the same place
				else if (img2.getRGB(x, y) == xButton.getRGB(x, y) && img.getRGB(x, y) == xButton.getRGB(x, y))
				{
					for (int xx = x; xx < x + xButton.getWidth(); xx++)
					{
						for (int yy = y; yy < y + xButton.getHeight(); yy++)
						{
							if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
								sameXButtonFound = false;
						}
					}
				}
				// xButton found in first but not second image
				else if (img2.getRGB(x, y) != xButton.getRGB(x, y) && img.getRGB(x, y) == xButton.getRGB(x, y))
				{
					for (int xx = x; xx < x + xButton.getWidth(); xx++)
					{
						for (int yy = y; yy < y + xButton.getHeight(); yy++)
						{
							if (img.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
								xButtonLost = false;
						}
					}
				}
			}
		}

		if (newXButtonFound && imgxButtonCount < img2xButtonCount)
		{
			// find bounds first
			_changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
					ClassificationType.WINDOW_OPEN));
		}
		else if (sameXButtonFound && imgxButtonCount == img2xButtonCount)
		{
			// check if window size has changed
			_changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
					ClassificationType.WINDOW_RESIZE));
		}
		else if (xButtonLost && imgxButtonCount > img2xButtonCount)
		{
			_changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
					ClassificationType.WINDOW_CLOSE));
		}

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
