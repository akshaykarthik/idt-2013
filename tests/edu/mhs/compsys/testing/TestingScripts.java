package edu.mhs.compsys.testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;

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

		ImageIO.write(BinaryImageProcessor.toImage(diff), "png", new File(
				"AB.png"));

		System.out.println("finished");
	}
}
