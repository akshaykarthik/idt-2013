package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processors.DesktopTaskbarChangeProcessor;
import edu.mhs.compsys.processors.WindowChangeProcessor;
import edu.mhs.compsys.processors.WindowMenuProcessor;
import edu.mhs.compsys.processors.WindowStateProcessor;
import edu.mhs.compsys.reporting.Report;
import edu.mhs.compsys.testing.dummyChange;
import edu.mhs.compsys.utils.Config;

/**
 * The class that does all of the work. This class encapsulates all change
 * recognition. It contains generates ArrayList of state transitions in each
 * processing step. It also creates a final report in the string format;
 * 
 */
public class Recognizer
{

	private Dataset						data;
	private Report						report;
	private ArrayList<StateTransition>	changes;
	private ArrayList<ChangeBundle>		changeBundles;
	private ArrayList<BinaryImage>		bindiffs;
	private ArrayList<BufferedImage>	diffs;
	private ArrayList<IChangeProcessor>	processors;
	private Config						config;

	/**
	 * Creates a new Recognizer with the given files.
	 * 
	 * @param files
	 */
	public Recognizer(File[] files, Config cfg)
	{
		this(files, cfg, false);
	}

	/**
	 * Creates a new Recognizer with the given files. If debug is true, also
	 * loads dummychanges
	 * 
	 * @param files
	 * @param cfg
	 * @param debug
	 */
	public Recognizer(File[] files, Config cfg, boolean debug)
	{
		try
		{
			data = new Dataset(files);
			report = new Report();
			config = cfg;

			processors = new ArrayList<IChangeProcessor>();

			processors.add(new DesktopTaskbarChangeProcessor());// DONE (like your mom)
			processors.add(new WindowStateProcessor());
			processors.add(new WindowChangeProcessor());
			processors.add(new WindowMenuProcessor());

			if (debug)
			{
				processors.add(new dummyChange());
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Processes the images and populates differences.
	 */
	public void process()
	{
		bindiffs = new ArrayList<BinaryImage>();
		diffs = new ArrayList<BufferedImage>();
		changes = new ArrayList<StateTransition>();

		for (int i = 0; i < data.length() - 1; i++)
		{
			final BufferedImage data1 = data.get(i);
			final BufferedImage data2 = data.get(i + 1);
			final BinaryImage diff = BinaryImageProcessor
					.fromDiff(data1, data2);
			bindiffs.add(diff);
			diffs.add(BinaryImageProcessor.toImage(diff));

			final StateTransition c = new StateTransition("St_" + i, "St_" + i
					+ 1);

			for (final IChangeProcessor proc : processors)
			{
				Thread th = new Thread(new Runnable()
				{
					public void run()
					{
						proc.initialize(config);
						proc.process(data1, data2, diff, changes, data,
								new ArrayList<Bounds>());
						for (Change ch : proc.getChanges())
						{
							c.addChange(ch);
						}

					}
				});
				th.setName((i) + " " + (i + 1) + " "
						+ proc.getClass().getName());
				th.start();
			}
			changes.add(c);
		}
	}
	public void proprocess()
	{
		changeBundles = new ArrayList<ChangeBundle>();
		bindiffs = new ArrayList<BinaryImage>();
		diffs = new ArrayList<BufferedImage>();

		for (int i = 0; i < data.length() - 1; i++)
		{
			final BufferedImage img1 = data.get(i);
			final BufferedImage img2 = data.get(i + 1);
			final BinaryImage diff = BinaryImageProcessor
					.fromDiff(img1, img2);
			bindiffs.add(diff);
			diffs.add(BinaryImageProcessor.toImage(diff));

			final ChangeBundle newCB = new ChangeBundle();

			for (final IChangeProcessor proc : processors)
			{
				Thread th = new Thread(new Runnable()
				{
					public void run()
					{
						proc.initialize(config);
						proc.proProcess(img1, img2, diff, changeBundles);


						newCB.addChanges(proc.getPROChanges());

					}
				});
				th.setName("PROTek_THREAD " + (i) + " " + (i + 1) + " "
						+ proc.getClass().getName());
				th.start();
			}
			changeBundles.add(newCB);

		}
	}
	/**
	 * Getter method for this Recognizer's Report
	 * 
	 * @return The report object.
	 */
	public Report getReport()
	{
		return report;
	}
	public ChangeBundle getChangeBundle(int index)
	{
		System.out.println();
		return changeBundles.get(index);
	}

	/**
	 * Return the change between the <code>index</code> and
	 * <code>index + 1</code>
	 * 
	 * @param index
	 * @return The <code>'index'</code>th change.
	 */
	public BufferedImage getChange(int index)
	{
		return BinaryImageProcessor.toImage(BinaryImageProcessor.fromDiff(
				data.get(index), data.get(index + 1)));
	}

	/**
	 * @return the raw Changes object.
	 */
	public ArrayList<StateTransition> getChanges()
	{
		return changes;
	}

	/**
	 * @return An ArrayList of BufferedImages that are the differences between
	 *         states.
	 */
	public ArrayList<BufferedImage> getDiff()
	{
		return diffs;
	}

	public ArrayList<BinaryImage> getBinDiff()
	{
		return bindiffs;
	}
}
