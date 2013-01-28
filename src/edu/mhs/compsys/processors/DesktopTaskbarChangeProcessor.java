/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in the desktop and taskbar and quantify
 * those. It will quantify TASKBAR_UPDATE and DESKTOP_ICON_CHANGE.
 */
public class DesktopTaskbarChangeProcessor implements IChangeProcessor {

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

		// Taskbar Change
		Bounds taskChange = BinaryImageProcessor.boundsOfChange(
				diff,
				new Bounds(0, cfg.getImageHeight() - cfg.getTaskBarHeight(),
						cfg.getTaskBarHeight() - 1, cfg.getImageWidth()
								- cfg.getDateWidth() - 1));

		if (taskChange.getX() > -1) {
			_changes.add(new Change(taskChange,
					ClassificationType.TASKBAR_UPDATE));
		}

		// Desktop Change
		Bounds desktopChange = BinaryImageProcessor.boundsOfChange(diff,
				new Bounds(0, 0, cfg.getImageHeight() - cfg.getTaskBarHeight(),
						cfg.getImageWidth()), previousStateWindows);

		if (desktopChange.getX() > -1) {
			_changes.add(new Change(desktopChange,
					ClassificationType.DESKTOP_ICON_CHANGE));

		}

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
