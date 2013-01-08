package edu.mhs.compsys.morphology;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.utils.Config;

public class ImageProcessor {
	Config cfg = new Config();

	/**
	 * Finds a smaller image inside a given larger image.
	 * 
	 * @param bigImg
	 * @param smallImg
	 * @return ArrayList of Point objects starting in the top left corner of the
	 *         smaller image for each instance of the smaller image.
	 */
	public ArrayList<Point> findIn(BufferedImage bigImg, BufferedImage smallImg) {
		int count = 0;
		ArrayList<Point> locs = new ArrayList<Point>();
		for (int y = 0; y < smallImg.getHeight(); y++) {
			for (int x = 0; x < smallImg.getWidth(); x++) {
				if ((bigImg.getRGB(x, y) == smallImg.getRGB(0, 0))
						&& smallImg.getWidth() + x <= cfg.getImageWidth()
						&& smallImg.getHeight() + y <= cfg.getImageHeight()) {
					for (int smallY = 0; smallY < smallImg.getHeight(); smallY++) {
						for (int smallX = 0; smallX < smallImg.getWidth(); smallX++) {

							if (smallImg.getRGB(smallX, smallY) == bigImg
									.getRGB(smallX + x, smallY + y))
								count++;
						}
					}
					if (count == smallImg.getWidth() * smallImg.getHeight())
						locs.add(new Point(x, y));
				}
			}
		}

		return locs;
	}

}
