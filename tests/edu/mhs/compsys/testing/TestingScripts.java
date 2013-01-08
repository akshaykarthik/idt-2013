package edu.mhs.compsys.testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import edu.mhs.compsys.morphology.BinaryImage;
import edu.mhs.compsys.morphology.BinaryImageProcessor;

public class TestingScripts {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String f1 = "./tests/edu/mhs/compsys/testing/system/SCENARIO_1/1_A.png";
		String f2 = "./tests/edu/mhs/compsys/testing/system/SCENARIO_1/1_B.png";

		File ff1 = new File(f1);
		File ff2 = new File(f2);
		BufferedImage imageA = ImageIO.read(ff1);
		BufferedImage imageB = ImageIO.read(ff2);

		BinaryImage diff = BinaryImageProcessor.fromDiff(imageA, imageB);
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("filename.txt"));
			out.println("test");
			out.println(f1);
			out.println(f2);
			out.println(diff);
		} finally {
			if (out != null)
				out.close();
		}

		ImageIO.write(
				BinaryImageProcessor.toImage(BinaryImageProcessor.open(diff)),
				"png", new File("AB_Opened.png"));

		ImageIO.write(
				BinaryImageProcessor.toImage(BinaryImageProcessor.dilate(diff)),
				"png", new File("AB_Dilate.png"));
		ImageIO.write(
				BinaryImageProcessor.toImage(BinaryImageProcessor.erode(diff)),
				"png", new File("AB_Erode.png"));
		ImageIO.write(
				BinaryImageProcessor.toImage(BinaryImageProcessor.close(diff)),
				"png", new File("AB_Closed.png"));
		ImageIO.write(BinaryImageProcessor.toImage(diff), "png", new File(
				"AB.png"));

		System.out.println("finished");
	}
}
