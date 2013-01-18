package edu.mhs.compsys.idt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class encapsulates the list of files to be analyzed. This class simply
 * holds an array of Files which can be converted to BufferedImage and then
 * ImageIcon.
 * 
 */
public class Dataset {
	private File[] _data;

	/**
	 * Constructs a Dataset given a folder. It reads all "png" images in that
	 * folder with the specific image dimensions of (1280x1024)
	 * 
	 * @param folder
	 * @throws IOException
	 */
	public Dataset(String folder) {
		this(folder, "png");
	}

	/**
	 * Constructs a dataset given a folder by adding all images in that folder.
	 * 
	 * It accepts all images of the given filetype whose size is (1280x1024)
	 * 
	 * @param folder
	 *            The folder to search for images
	 * @param type
	 *            The file type of the image
	 */
	public Dataset(String folder, String type) {
		this(folder, type, 1280, 1024);
	}

	/**
	 * Constructs a dataset given a folder by adding all images in that folder.
	 * 
	 * It accepts all images of the given filetype whose size is (length x
	 * width)
	 * 
	 * @param folder
	 *            The folder to search for images
	 * 
	 * @param type
	 *            The file type of the image
	 * 
	 * @param length
	 *            the length of the image
	 * 
	 * @param width
	 *            width of the image
	 * @throws IOException
	 */
	public Dataset(String folder, String type, int length, int width) {
		_data = (new File(folder)).listFiles();

	}

	/**
	 * Construct a Dataset using the given images. Loaded directly from file
	 * object.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public Dataset(File[] file) throws IOException {
		_data = file;
	}

	/**
	 * @return Returns the size of the dataset, returns -1 if dataset is not
	 *         initialized
	 */
	public int length() {
		return _data.length;
	}

	/**
	 * @param index
	 * @return The image at the index, null otherwise
	 */
	public BufferedImage get(int index) {
		try {
			// returns BufferedImage or null if index is greater than
			// data.length
			return (index < _data.length) ? ImageIO.read(_data[index]) : null;
		} catch (IOException e) {
			// returns null when the file isn't an image or there is no such
			// file the disk
			return null;
		}
	}
}