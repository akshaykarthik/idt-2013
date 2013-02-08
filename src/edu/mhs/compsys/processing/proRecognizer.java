package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.processors.DesktopTaskbarChangeProcessor;
import edu.mhs.compsys.processors.WindowChangeProcessor;
import edu.mhs.compsys.processors.WindowMenuProcessor;
import edu.mhs.compsys.utils.Config;

public class proRecognizer
{
	private Config						cfg;
	private ArrayList<ChangeBundle>		allChanges;
	private ArrayList<BinaryImage>		binImages;
	private ArrayList<BufferedImage>	images;
	private ArrayList<BufferedImage>	binBufferedImages;
	private ArrayList<IChangeProcessor>	processors;

	public proRecognizer(File[] files, Config incfg)
	{
		cfg = incfg;
		allChanges = new ArrayList<ChangeBundle>();
		binImages = new ArrayList<BinaryImage>();
		images = new ArrayList<BufferedImage>();
		binBufferedImages = new ArrayList<BufferedImage>();
		processors = new ArrayList<IChangeProcessor>();
		// loading images
		for (int i = 0; i < files.length; i++)
		{
			try
			{
				images.add(ImageIO.read(files[i]));
			}
			catch (IOException e)
			{
				System.out.println("proRecognizer didnt load the images good >:(");
			}
		}

		processors.add(new DesktopTaskbarChangeProcessor());
		processors.add(new WindowChangeProcessor());
		processors.add(new WindowMenuProcessor());
		processors.add(new WindowChangeProcessor());

	}
	public void process()
	{
		for (int i = 0; i < images.size() - 1; i++)
		{
			final int ii = 0 + i;

			final BufferedImage img1 = images.get(i);
			final BufferedImage img2 = images.get(i + 1);

			final BinaryImage bin = getDiff(img1, img2);
			binImages.add(bin);
			binBufferedImages.add(BinaryImageProcessor.toImage(bin));

			for (int p = 0; p < processors.size(); i++)
			{
				final int index = 0 + p;

				Thread th = new Thread(new Runnable()
				{
					public void run()
					{
						processors.get(index).initialize(cfg);
						processors.get(index).proProcess(img1, img2, bin, allChanges);
						for (int c = 0; c < processors.get(index).getPROChanges().size(); c++)
						{
							allChanges.get(ii).addChanges(processors.get(index).getPROChanges());
						}
					}
				});
				th.setName((i) + " " + (i + 1) + " "
						+ processors.get(index).getClass().getName());
				th.start();
			}

		}
	}
	public ArrayList<ChangeBundle> getAllChangeBundles()
	{
		return allChanges;
	}
	public ArrayList<Change> getChanges(int index)
	{
		return allChanges.get(index).getChanges();
	}
public BufferedImage getChangeImage(int index)
{
	return binBufferedImages.get(index);
}
	private BinaryImage getDiff(BufferedImage img1, BufferedImage img2)
	{
		BinaryImage r = new BinaryImage(img1.getHeight(), img1.getWidth());
		for (int x = 0; x < r.getWidth(); x++)
			for (int y = 0; y < r.getHeight(); y++)
				r.set(x, y, !(img1.getRGB(x, y) == img2.getRGB(x, y)));
		return r;
	}
}
