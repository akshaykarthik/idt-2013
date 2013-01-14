package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.reporting.Report;

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
	private ArrayList<BufferedImage>	diffs;
	private ArrayList<IChangeProcessor>	processors;

	public Recognizer(File[] files)
	{
		data = new Dataset(files);
		this.report = new Report();
	}

	public void process()
	{
		diffs = new ArrayList<BufferedImage>();
		processors = new ArrayList<IChangeProcessor>();
		changes = new ArrayList<StateTransition>();

		for (int i = 0; i < data.length() - 1; i++)
		{
			BinaryImage diff = BinaryImageProcessor.fromDiff(data.get(i),
					data.get(i + 1));
			diffs.add(BinaryImageProcessor.toImage(diff));
			StateTransition c = new StateTransition("State_" + i, "State_" + i
					+ 1);
			for (IChangeProcessor proc : processors)
			{
				proc.process(data.get(i), data.get(i + 1), diff, changes, data);
				for (Change ch : proc.getChanges())
				{
					c.addChange(ch);
				}
			}
			changes.add(c);
		}
	}
	public Report getReport()
	{
		return report;
	}
	public BufferedImage getChange(int index)
	{
		return BinaryImageProcessor.toImage(BinaryImageProcessor.fromDiff(data.get(index),
				data.get(index + 1)));
	}
	public ArrayList<StateTransition> getChanges()
	{
		return changes;
	}

	public ArrayList<BufferedImage> getDiff()
	{
		return diffs;
	}
}
