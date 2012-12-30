package edu.mhs.compsys.morphology;

public class BinaryImageProcessor {
	public static BinaryImage T_STRUCTURE = new BinaryImage(
			"110\n" +
			"111\n" +
			"010");
	public static BinaryImage dilate(BinaryImage structure){
		return new BinaryImage(3, 3);
	}
}
