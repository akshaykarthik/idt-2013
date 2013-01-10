package edu.mhs.compsys.idt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Dataset
{
	private ArrayList<BufferedImage> _data;

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
	 * @param len
	 *            the length of the image
	 * 
	 * @param the
	 *            width of the image
	 * @throws IOException
	 */
	public Dataset(String folder, String type, int length, int width) throws IOException
	{
		File ifolder = new File(folder);
		File[] listOfFiles = ifolder.listFiles();
		String[] paths = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++)
		{
			paths[i] = listOfFiles[i].getPath();
		}
		Arrays.sort(paths);
		for (String filepath : paths)
			this._data.add(ImageIO.read(new File(filepath)));
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
	 * @throws IOException
	 */
	public Dataset(String folder, String type) throws IOException
	{
		this(folder, type, 1280, 1024);
	}

	/**
	 * Constructs a dataset given a folder. It reads all "png" images in that
	 * folder with the specific image dimensions of (1280x1024)
	 * 
	 * @param folder
	 * @throws IOException
	 */
	public Dataset(String folder) throws IOException
	{
		this(folder, "png");
	}

	/**
	 * Construct a dataset using the given images.
	 * 
	 * The images are passed in as paths;
	 * 
	 * 
	 * @param bufferedImages
	 * @throws IOException
	 */
	public Dataset(String[] bufferedImages) throws IOException
	{
		for (String file : bufferedImages)
			this._data.add(ImageIO.read(new File(file)));
	}
	
	/**
	 * Construct a Dataset using the given images.
	 * Loaded directly from file object.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public Dataset(File[] file) throws IOException
	{
		for(File fileName : file)
			this._data.add(ImageIO.read(fileName));
	}
	
	/**
	 * @return Returns the size of the dataset, returns -1 if dataset is not
	 *         initialized
	 */
	public int length()
	{
		return ((_data != null) ? _data.size() : -1);
	}

	/**
	 * @param index
	 * @return
	 */
	public BufferedImage get(int index)
	{
		return (index < length()) ? _data.get(index) : null;
	}

}
