package edu.mhs.compsys.morphology;

public class BinaryImageProcessor {
	public static BinaryImage dilate(BinaryImage input) {
		int width = input.getWidth();
		int height = input.getHeight();
		BinaryImage ret = new BinaryImage(width, height);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {
					if (input.get(i, j)) {
						if (input.inBounds(i + 1, j))
							ret.set(i + 1, j, true);

						if (input.inBounds(i - 1, j))
							ret.set(i - 1, j, true);

						if (input.inBounds(i, j + 1))
							ret.set(i, j + 1, true);

						if (input.inBounds(i, j - 1))
							ret.set(i, j - 1, true);

						ret.set(i, j, true);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}

	public static BinaryImage erode(BinaryImage input) {
		int width = input.getWidth();
		int height = input.getHeight();
		BinaryImage ret = new BinaryImage(height, width);
		System.out.println("" + width + " " + height);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				try {
					if (input.get(i, j)) {

						if (input.get(i + 1, j) && input.get(i - 1, j)
								&& input.get(i, j + 1) && input.get(i, j - 1))
							ret.set(i, j, true);

					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}
}
