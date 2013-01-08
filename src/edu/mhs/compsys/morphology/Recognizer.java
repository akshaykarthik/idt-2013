package edu.mhs.compsys.morphology;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.reporting.Report;

public class Recognizer {

	private Dataset data;
	private Report report;
	private ArrayList<BufferedImage> changes;

	public Recognizer(Dataset data) {
		this.data = data;
		this.report = new Report();
	}

	public void process() {
		for (int i = 0; i < data.length() - 1; i++) {
			changes.add(BinaryImageProcessor.toImage(BinaryImageProcessor
					.fromDiff(data.get(i), data.get(i + 1))));
		}

	}

	public Report getReport() {
		return report;
	}

	public ArrayList<BufferedImage> getChanges() {
		return changes;
	}
}
