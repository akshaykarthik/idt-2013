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

		int smallImgX = smallImg.getWidth();
		int smallImgY = smallImg.getHeight();
		int bigImgX = bigImg.getWidth();
		int bigImgY = bigImg.getHeight();

		ArrayList<Point> locs = new ArrayList<Point>();
		for (int y = 0; y < bigImgY - smallImgY; y++) {
			for (int x = 0; x < bigImgX - smallImgX; x++) {
				
				if ((bigImg.getRGB(x, y) == smallImg.getRGB(0, 0))) {
					nestedLoop: for (int smallY = 0; smallY < smallImgY; smallY++) {
						for (int smallX = 0; smallX < smallImgX; smallX++) {

							if (smallImg.getRGB(smallX, smallY) == bigImg
									.getRGB(smallX + x, smallY + y))
								count++;
							else
								break nestedLoop;
						}
					}
					if (count == smallImgX * smallImgY)
						locs.add(new Point(x, y));
				}
			}
		}

		return locs;
	}

}
