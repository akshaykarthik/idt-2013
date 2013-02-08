package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.utils.Config;

/**
 * An interface to describe change processors. Mostly used so that testing will
 * be easier.
 */
public interface IChangeProcessor
{

	/**
	 * Creates a new ChangeProcessor that uses the given config file.
	 * 
	 * @param cfg
	 */
	void initialize(Config cfg);

	/**
	 * @param img
	 *            First Image
	 * @param img2
	 *            Second Image
	 * @param diff
	 *            ImageDifference(BinaryImage)
	 * @param changes
	 *            Past changes
	 * @param data
	 *            Other Images.
	 * @param previousStateWindows
	 *            ArrayList of bounds of the windows in the previous state
	 */
	void process(BufferedImage img, BufferedImage img2, BinaryImage diff,
			ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows);

	Change[] getChanges();
	
	void proProcess(BufferedImage img, BufferedImage img2, BinaryImage diff, ArrayList<ChangeBundle> prevChanges);
}
