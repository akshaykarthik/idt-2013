package edu.mhs.compsys.processing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.BoundsProcessor;

/**
 * A utility class that provides methods for working with BinaryImages.
 * 
 */
public class BinaryImageProcessor {

	public static final int WHITE = 0xffffff;
	public static final int BLACK = 0x000000;
	public static final int AWHITE = 0xffffffff;
	public static final int ABLACK = 0xff000000;

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

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {
					if (input.get(i, j)) {
						ret.safeSet(i + 1, j, true);
						ret.safeSet(i - 1, j, true);
						ret.safeSet(i, j + 1, true);
						ret.safeSet(i, j - 1, true);
						if (box) {
							ret.safeSet(i + 1, j + 1, true);
							ret.safeSet(i + 1, j - 1, true);
							ret.safeSet(i - 1, j + 1, true);
							ret.safeSet(i - 1, j - 1, true);
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
		BinaryImage ret = new BinaryImage(height, width);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (input.safeGet(i, j)) {
					if (input.safeGet(i + 1, j) && input.safeGet(i - 1, j)
							&& input.safeGet(i, j + 1)
							&& input.safeGet(i, j - 1))
						if (box && input.safeGet(i + 1, j + 1)
								&& input.safeGet(i + 1, j - 1)
								&& input.safeGet(i - 1, j + 1)
								&& input.safeGet(i - 1, j - 1))
							ret.set(i, j, true);
						else
							ret.set(i, j, true);

				}

			}
		}
		return ret;
	}

	/**
	 * Does <code>iterations</code> of repeated erosions, uses <code>'t'</code>
	 * as structure.
	 * 
	 * @param input
	 * @param iterations
	 * @return The result of <code>iterations</code> of erosion.
	 */
	public static BinaryImage repeatedErode(BinaryImage input, int iterations) {
		return repeatedErode(input, false, iterations);
	}

	/**
	 * Does <code>iterations</code> of repeated erosions, uses <code>box</code>
	 * as structure.
	 * 
	 * @param input
	 * @param iterations
	 * @return The result of <code>iterations</code> of erosion.
	 */
	public static BinaryImage repeatedErode(BinaryImage input, boolean box,
			int iterations) {
		if (iterations == 1)
			return erode(input, box);
		else
			return erode(repeatedErode(input, box, iterations - 1), box);
	}

	/**
	 * Does <code>iterations</code> of repeated dilations, uses <code>'t'</code>
	 * as structure.
	 * 
	 * @param input
	 * @param iterations
	 * @return The result of <code>iterations</code> of dilation.
	 */
	public static BinaryImage repeatedDilate(BinaryImage input, int iterations) {
		return repeatedDilate(input, false, iterations);
	}

	/**
	 * Does <code>iterations</code> of repeated dilations, uses
	 * <code>'box'</code> as structure.
	 * 
	 * @param input
	 * @param box
	 * @param iterations
	 * @return The result of <code>iterations</code> of dilation.
	 */
	public static BinaryImage repeatedDilate(BinaryImage input, boolean box,
			int iterations) {
		if (iterations == 1)
			return dilate(input, box);
		else
			return dilate(repeatedErode(input, box, iterations - 1), box);
	}

	/**
	 * A morphological operation that involves first dilating the input then
	 * eroding it. It results in a BinaryImage that has had small 'blemishes'
	 * removed.
	 * 
	 * @param input
	 * @return The image opened with a structure of 't'.
	 */
	public static BinaryImage open(BinaryImage input) {
		return open(input, false);
	}

	/**
	 * A morphological operation that involves first eroding then dilating the
	 * input. It results in a BinaryImage that has had small 'blemishes'
	 * removed.
	 * 
	 * @param input
	 * @return The image opened with the given structure.
	 */
	public static BinaryImage open(BinaryImage input, boolean box) {
		return input.or(dilate(erode(input, box), box));
	}

	/**
	 * A morphological operation that involves first eroding then dilating the
	 * input. This results in an images that has its 'holes' filled up.
	 * 
	 * @param input
	 * @return The image closed with a structure of 't'.
	 */
	public static BinaryImage close(BinaryImage input) {
		return close(input, false);
	}

	/**
	 * A morphological operation that involves first dilating the input then
	 * eroding it. This results in an images that has its 'holes' filled up.
	 * 
	 * @param input
	 * @return The image closed with a given structure.
	 */
	public static BinaryImage close(BinaryImage input, boolean box) {
		return input.or(erode(dilate(input, box), box));
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
				if (in1.getRGB(j, i) != in2.getRGB(j, i))
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
		return toImage(input, WHITE, BLACK);
	}

	/**
	 * This method takes a binaryImage and converts it into a BufferedImage to
	 * save or print or show on a GUI.
	 * 
	 * @param input
	 *            BinaryImage
	 * @return BufferedImage of the BinaryImage
	 */
	public static BufferedImage toImage(BinaryImage input, int white, int black) {
		BufferedImage ret = new BufferedImage(input.getWidth(),
				input.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				ret.setRGB(i, j, input.get(j, i) ? white : black);
			}
		}
		return ret;
	}

	/**
	 * Calculates the minimum bounding box of all changes within the whole image
	 * 
	 * @param input
	 *            BinaryImage to be processed
	 * @return A single Bounds object that returns the bounds of change.
	 */
	public static Bounds boundsOfChange(BinaryImage input) {
		return boundsOfChange(input,
				new Bounds(0, 0, input.getWidth(), input.getHeight()));
	}

	/**
	 * Calculates the minimum bounding box of all changes within the given
	 * Boundaries of the image.
	 * 
	 * @param input
	 *            BinaryImage to be processed.
	 * @param boundaries
	 *            The boundary to search within.
	 * @return A single Bounds object that returns the bounds of change.
	 */
	public static Bounds boundsOfChange(BinaryImage input, Bounds boundaries) {
		return boundsOfChange(input, boundaries, new ArrayList<Bounds>());
	}

	/**
	 * Calculates the minimum bounding box of all changes within the given
	 * Boundaries of the image.
	 * 
	 * @param input
	 *            BinaryImage to be processed.
	 * @param boundaries
	 *            The boundary to search within.
	 * @param windows
	 *            The boundaries to ignore.
	 * @return A single Bounds object that returns the bounds of change.
	 */
	public static Bounds boundsOfChange(BinaryImage input, Bounds boundaries,
			ArrayList<Bounds> windows) {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		int l = Integer.MIN_VALUE;
		int w = Integer.MIN_VALUE;

		final int b_x = boundaries.getX();
		final int b_y = boundaries.getY();
		final int b_w = boundaries.getHeight();
		final int b_l = boundaries.getWidth();

		for (int i = b_x; i < b_x + b_l; i++) {
			for (int j = b_y; j < b_y + b_w; j++) {
				if (input.safeGet(i, j)) {
					boolean set = true;
					for (Bounds b : windows) {
						if (BoundsProcessor.inBounds(new Point(i, j), b)) {
							set = false;
							break;
						}
					}

					if (set) {
						x = Math.min(x, i);
						y = Math.min(y, j);
						l = Math.max(l, i);
						w = Math.max(w, j);
					}
				}
			}
		}

		if (x == b_x || y == b_y || l == b_l || w == b_w) {
			return new Bounds();
		}

		return new Bounds(x, y, l, w);
	}
}
