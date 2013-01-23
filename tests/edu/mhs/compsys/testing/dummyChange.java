package edu.mhs.compsys.testing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

public class dummyChange implements IChangeProcessor {

	private Config cfg;
	private ArrayList<Change> ichanges;

	@Override
	public void initialize(Config cfg) {
		this.cfg = cfg;

	}

	@Override
	public void process(BufferedImage img, BufferedImage img2,
			BinaryImage diff, ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows) {
		ichanges = new ArrayList<Change>();
		int boundsX = (int) (Math.random() * cfg.getImageWidth());
		int boundsY = (int) (Math.random() * cfg.getImageHeight());
		int boundsL = (int) (Math.random() * (cfg.getImageHeight() - boundsY - cfg
				.getTaskBarHeight()));
		int boundsW = (int) (Math.random() * (cfg.getImageWidth() - boundsX));
		Bounds b = new Bounds(boundsX, boundsY, boundsL, boundsW);
		ClassificationType c = ClassificationType.values()[new Random()
				.nextInt(ClassificationType.values().length)];
		ichanges.add(new Change(b, c));

	}

	@Override
	public Change[] getChanges() {
		return ichanges.toArray(new Change[ichanges.size()]);
	}

}
