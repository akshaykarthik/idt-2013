/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in the windows and quantify those. It
 * will return WINDOW_APPLICATION_AREA_UPDATE, WINDOW_TITLE_BAR_CLICK, and
 * WINDOW_TITLE_CHANGE
 */
public class WindowChangeProcessor implements IChangeProcessor {

	private ArrayList<Change> _changes;
	private Config cfg;

	/**
	 * Initialize the processor with the given config file.
	 * 
	 * @see edu.mhs.compsys.processing.IChangeProcessor#initialize(edu.mhs.compsys.utils.Config)
	 */
	@Override
	public void initialize(Config cfg) {
		this.cfg = cfg;
	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#process(java.awt.image.
	 *      BufferedImage, java.awt.image.BufferedImage,
	 *      edu.mhs.compsys.processing.BinaryImage, java.util.ArrayList,
	 *      edu.mhs.compsys.idt.Dataset)
	 */
	@Override
	public void process(BufferedImage img, BufferedImage img2,
			BinaryImage diff, ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows) {
		_changes = new ArrayList<Change>();
		Bounds[] windowChange = new Bounds[previousStateWindows.size()];
		Bounds maxChange = new Bounds();
		for (int i = 0; i<windowChange.length; i++){
			windowChange[i] = BinaryImageProcessor.boundsOfChange(diff,new Bounds(windowChange[i].getX(), windowChange[i].getY(), windowChange[i].getLength() - 1, windowChange[i].getWidth() - 1));
			if(maxChange.getLength()* maxChange.getWidth() < windowChange[i].getLength() * windowChange[i].getWidth())
				maxChange = windowChange[i];
		}
			//Whichever one has more change is the one the change took place in.
	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#getChanges()
	 */
	@Override
	public Change[] getChanges() {
		// TODO Auto-generated method stub
		return (Change[]) _changes.toArray();
	}
}
