package edu.mhs.compsys.morphology;

import java.awt.image.BufferedImage;

/**
 * @author akr
 * 
 */
public class BinaryImageProcessor {

	/**
	 * From Wikipedia: Dilation is one of the basic operations in mathematical
	 * morphology. Originally developed for binary images, it has been expanded
	 * first to grayscale images, and then to complete lattices. The dilation
	 * operation usually uses a structuring element for probing and expanding
	 * the shapes contained in the input image
	 * 
	 * @param input
	 *            binary image that needs to be dilated
	 * @return A binary image that has been dilated
	 */
	public static BinaryImage dilate(BinaryImage input) {
		return dilate(input, false);
	}

	/**
	 * From Wikipedia: Dilation is one of the basic operations in mathematical
	 * morphology. Originally developed for binary images, it has been expanded
	 * first to grayscale images, and then to complete lattices. The dilation
	 * operation usually uses a structuring element for probing and expanding
	 * the shapes contained in the input image
	 * 
	 * @param input
	 *            binary image that needs to be dilated
	 * @return A binary image that has been dilated
	 */
	public static BinaryImage dilate(BinaryImage input, boolean box) {
		int width = input.getWidth();
		int height = input.getHeight();
		BinaryImage ret = new BinaryImage(width, height);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {
					if (input.get(i, j)) {
						if (input.inBounds(i + 1, j))
							ret.set(i + 1, j, true);

						if (input.inBounds(i - 1, j))
							ret.set(i - 1, j, true);

						if (input.inBounds(i, j + 1))
							ret.set(i, j + 1, true);

						if (input.inBounds(i, j - 1))
							ret.set(i, j - 1, true);
						if (box) {
							if (input.inBounds(i + 1, j + 1))
								ret.set(i + 1, j + 1, true);

							if (input.inBounds(i - 1, j + 1))
								ret.set(i - 1, j + 1, true);

							if (input.inBounds(i - 1, j + 1))
								ret.set(i - 1, j + 1, true);

							if (input.inBounds(i - 1, j - 1))
								ret.set(i - 1, j - 1, true);
						}
						ret.set(i, j, true);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}

	/**
	 * From wikipedia: Erosion is one of two fundamental operations (the other
	 * being dilation) in Morphological image processing from which all other
	 * morphological operations are based. It was originally defined for binary
	 * images, later being extended to grayscale images, and subsequently to
	 * complete lattices.
	 * 
	 * @param input
	 *            binaryImage to be eroded.
	 * @return A new binary image that is the eroded version of the given image
	 */
	public static BinaryImage erode(BinaryImage input) {
		return erode(input, false);
	}

	/**
	 * From wikipedia: Erosion is one of two fundamental operations (the other
	 * being dilation) in Morphological image processing from which all other
	 * morphological operations are based. It was originally defined for binary
	 * images, later being extended to grayscale images, and subsequently to
	 * complete lattices.
	 * 
	 * @param input
	 *            binaryImage to be eroded.
	 * @return A new binary image that is the eroded version of the given image
	 */
	public static BinaryImage erode(BinaryImage input, boolean box) {
		int width = input.getWidth();
		int height = input.getHeight();
		BinaryImage ret = new BinaryImage(height, width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				try {
					if (input.get(i, j)) {
						if (input.get(i + 1, j) && input.get(i - 1, j)
								&& input.get(i, j + 1) && input.get(i, j - 1))
							if (box && input.get(i + 1, j + 1)
									&& input.get(i + 1, j - 1)
									&& input.get(i - 1, j + 1)
									&& input.get(i - 1, j - 1))
								ret.set(i, j, true);
							else
								ret.set(i, j, true);

					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}

	/**
	 * A morphological operation that involves first dilating the input then
	 * eroding it. It results in a BinaryImage that has had small 'blemishes'
	 * removed.
	 * 
	 * @param input
	 * @return
	 */
	public static BinaryImage open(BinaryImage input) {
		return open(input, false);
	}

	/**
	 * A morphological operation that involves first dilating the input then
	 * eroding it. It results in a BinaryImage that has had small 'blemishes'
	 * removed.
	 * 
	 * @param input
	 * @return
	 */
	public static BinaryImage open(BinaryImage input, boolean box) {
		return input.or(erode(dilate(input, box), box));
	}

	/**
	 * A morphological operation that involves first eroding then dilating the
	 * input. This results in an images that has its 'holes' filled up.
	 * 
	 * @param input
	 * @return
	 */
	public static BinaryImage close(BinaryImage input) {
		return close(input, false);
	}

	/**
	 * A morphological operation that involves first eroding then dilating the
	 * input. This results in an images that has its 'holes' filled up.
	 * 
	 * @param input
	 * @return
	 */
	public static BinaryImage close(BinaryImage input, boolean box) {
		return input.or(dilate(erode(input, box), box));
	}

	/**
	 * This function takes two BufferedImages and returns a BinaryImage. Each
	 * pixel that is true has been changed. Each pixel that is false is the same
	 * in both images.
	 * 
	 * @param in1
	 *            The first BufferedImage
	 * @param in2
	 *            The second BufferedImage
	 * @return The difference between the two images.
	 */
	public static BinaryImage fromDiff(BufferedImage in1, BufferedImage in2) {
		BinaryImage ret = new BinaryImage(in1.getHeight(), in1.getWidth());
		for (int i = 0; i < in1.getHeight(); i++) {
			for (int j = 0; j < in1.getWidth(); j++) {
				if (in1.getRGB(j, i) == in2.getRGB(j, i))
					ret.set(i, j, true);
			}
		}
		return ret;
	}

	/**
	 * This method takes a binaryImage and converts it into a BufferedImage to
	 * save or print or show on a GUI.
	 * 
	 * @param input
	 *            BinaryImage
	 * @return BufferedImage of the BinaryImage
	 */
	public static BufferedImage toImage(BinaryImage input) {
		BufferedImage ret = new BufferedImage(input.getWidth(),
				input.getHeight(), BufferedImage.TYPE_INT_RGB);
		int white = 0xffffff;
		int black = 0x000000;

		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				ret.setRGB(i, j, input.get(j, i) ? black : white);
			}
		}
		return ret;
	}
}
