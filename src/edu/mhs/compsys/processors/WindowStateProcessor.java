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
	public void process(BufferedImage img, BufferedImage img2, BinaryImage diff,
			ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows)
	{

		_changes = new ArrayList<Change>();

		boolean newWindow = false;
		int newWindowX = 0, newWindowY = 0, newWindowWidth = 0, newWindowHeight = 0;
		for (int x = 0; x < cfg.getImageWidth(); x++)
		{
			for (int y = 0; y < cfg.getImageHeight(); y++)
			{
				if (diff.get(x, y))
				{
					// start looking for corners and see if they line up
					// looking down
					int down = x;
					int height;
					while (diff.get(x, down))// just keeps looking until it
												// finds a corner vertically
					{
						down++;
					}
					if (down > x)
						down--;
					height = down - x;

					// looking across
					int across = y;
					int width;
					while (diff.get(across, y))// just keeps looking untill it
												// finds a corner horizontally
					{
						across++;
					}
					if (across > y)
						across--;
					width = across - y;
					// now that corners are found, chekc all positions for
					// continuity with a percentage of error margin
					int totalPixels = 0;
					int truePixels = 0;
					for (int windX = 0; windX < width; windX++)
					{
						for (int windY = 0; windY < height; windY++)
						{
							if (diff.get(windX + x, windY + y))
								truePixels++;
							totalPixels++;
						}
					}
					// this "if" is just a buffer zone in case not all of the
					// window has new colors. Also prevents finding icons or
					// task bar changes
					if ((double) (truePixels / totalPixels) > .9D && Math.min(width, height) > 250)
					{
						newWindow = true;
						newWindowX = x;
						newWindowY = y;
						newWindowWidth = width;
						newWindowHeight = height;
					}
				}
			}
		}

		if (newWindow)
		{
			Bounds newWindowBounds = new Bounds(newWindowX, newWindowY, newWindowHeight, newWindowWidth);
			_changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff, newWindowBounds), ClassificationType.WINDOW_OPEN));
		}

		// ClassificationType.WINDOW_OPEN)); // BufferedImage xButton =
		// ImageProcessor.intArrayToBufferedImage(cfg
		// .getColorOfX());
		// boolean newXButtonFound = true;
		// boolean sameXButtonFound = true;
		// boolean xButtonLost = true;
		// int imgxButtonCount = 0;
		// int img2xButtonCount = 0;
		// for (int x = 0; x < cfg.getImageWidth() - xButton.getWidth() - 1;
		// x++)
		// {
		// for (int y = 0; y < cfg.getImageHeight() - xButton.getHeight() - 1;
		// y++)
		// {
		//
		// // checking for xButtons in img
		// boolean fullXButtonFound = true;
		// for (int xx = x; xx < x + xButton.getWidth(); xx++)
		// {
		// for (int yy = y; yy < y + xButton.getHeight(); yy++)
		// {
		// if (img.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
		// {
		// fullXButtonFound = false;
		// }
		// }
		// }
		// if (fullXButtonFound)
		// imgxButtonCount++;
		// // checking for xButtons in img2
		// fullXButtonFound = true;
		// for (int xx = x; xx < x + xButton.getWidth(); xx++)
		// {
		// for (int yy = y; yy < y + xButton.getHeight(); yy++)
		// {
		// if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
		// {
		// fullXButtonFound = false;
		// }
		// }
		// }
		// if (fullXButtonFound)
		// imgxButtonCount++;
		//
		// // /////
		// // /////
		// // /////
		//
		// // xButton found in second image and not in first
		// if (img2.getRGB(x, y) == xButton.getRGB(0, 0) && img.getRGB(x, y) !=
		// xButton.getRGB(0, 0))
		// {
		// for (int xx = x; xx < x + xButton.getWidth(); xx++)
		// {
		// for (int yy = y; yy < y + xButton.getHeight(); yy++)
		// {
		// if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
		// newXButtonFound = false;
		// }
		// }
		// }
		// // xButton found in both images in the same place
		// else if (img2.getRGB(x, y) == xButton.getRGB(0, 0) && img.getRGB(x,
		// y) == xButton.getRGB(0, 0))
		// {
		// for (int xx = x; xx < x + xButton.getWidth(); xx++)
		// {
		// for (int yy = y; yy < y + xButton.getHeight(); yy++)
		// {
		// if (img2.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
		// sameXButtonFound = false;
		// }
		// }
		// }
		// // xButton found in first but not second image
		// else if (img2.getRGB(x, y) != xButton.getRGB(0, 0) && img.getRGB(x,
		// y) == xButton.getRGB(0, 0))
		// {
		// for (int xx = x; xx < x + xButton.getWidth(); xx++)
		// {
		// for (int yy = y; yy < y + xButton.getHeight(); yy++)
		// {
		// if (img.getRGB(xx, yy) != xButton.getRGB(xx - x, yy - y))
		// xButtonLost = false;
		// }
		// }
		// }
		// }
		// }
		// if (newXButtonFound && imgxButtonCount < img2xButtonCount)
		// {
		// // find bounds first
		// _changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
		// ClassificationType.WINDOW_OPEN));
		// }
		// else if (sameXButtonFound && imgxButtonCount == img2xButtonCount)
		// {
		// // check if window size has changed
		// _changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
		// ClassificationType.WINDOW_RESIZE));
		// }
		// else if (xButtonLost && imgxButtonCount > img2xButtonCount)
		// {
		// _changes.add(new Change(BinaryImageProcessor.boundsOfChange(diff),
		// ClassificationType.WINDOW_CLOSE));
		// }
		// System.out.println("counts here dawg: " + imgxButtonCount + " " +
		// img2xButtonCount);

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
