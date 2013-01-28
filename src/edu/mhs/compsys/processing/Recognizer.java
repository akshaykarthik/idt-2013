package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
public class Recognizer {

	private Dataset data;
	private Report report;
	private ArrayList<StateTransition> changes;

	private ArrayList<BinaryImage> bindiffs;
	private ArrayList<BufferedImage> diffs;
	private ArrayList<IChangeProcessor> processors;
	private Config config;

	/**
	 * Creates a new Recognizer with the given files.
	 * 
	 * @param files
	 */
	public Recognizer(File[] files, Config cfg) {
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
	public Recognizer(File[] files, Config cfg, boolean debug) {
		try {
			data = new Dataset(files);
			report = new Report();
			config = cfg;

			processors = new ArrayList<IChangeProcessor>();
			processors.add(new DesktopTaskbarChangeProcessor());
			processors.add(new WindowStateProcessor());
			processors.add(new WindowMenuProcessor());
			processors.add(new WindowChangeProcessor());

			if (debug) {
				processors.add(new dummyChange());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the images and populates differences.
	 */
	public void process() {
		diffs = new ArrayList<BufferedImage>();
		changes = new ArrayList<StateTransition>();

		for (int i = 0; i < data.length() - 1; i++) {
			BinaryImage diff = BinaryImageProcessor.fromDiff(data.get(i),
					data.get(i + 1));

			bindiffs.add(diff);
			diffs.add(BinaryImageProcessor.toImage(diff));

			StateTransition c = new StateTransition("State_" + i, "State_" + i
					+ 1);
			for (IChangeProcessor proc : processors) {
				proc.initialize(config);
				// TODO: process
				// proc.process(data.get(i), data.get(i + 1), diff, changes,
				// data);
				for (Change ch : proc.getChanges()) {
					c.addChange(ch);
				}
			}
			changes.add(c);
		}
	}

	/**
	 * Getter method for this Recognizer's Report
	 * 
	 * @return The report object.
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * Return the change between the <code>index</code> and
	 * <code>index + 1</code>
	 * 
	 * @param index
	 * @return The <code>'index'</code>th change.
	 */
	public BufferedImage getChange(int index) {
		return BinaryImageProcessor.toImage(BinaryImageProcessor.fromDiff(
				data.get(index), data.get(index + 1)));
	}

	/**
	 * @return the raw Changes object.
	 */
	public ArrayList<StateTransition> getChanges() {
		return changes;
	}

	/**
	 * @return An ArrayList of BufferedImages that are the differences between
	 *         states.
	 */
	public ArrayList<BufferedImage> getDiff() {
		return diffs;
	}

	public ArrayList<BinaryImage> getBinDiff() {
		return bindiffs;
	}
}
