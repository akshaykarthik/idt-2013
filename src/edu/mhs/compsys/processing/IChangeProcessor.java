package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;

/**
 * An interface to describe change processors. Mostly used so that testing will
 * be easier.
 */
public interface IChangeProcessor {

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
	 */
	void process(BufferedImage img, BufferedImage img2, BinaryImage diff,
			ArrayList<StateTransition> changes, Dataset data);

	Change[] getChanges();

}
