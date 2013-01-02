package edu.mhs.compsys.morphology;

public class BinaryImageProcessor {

	public static BinaryImage dilate(BinaryImage input) {
		return dilate(input, false);
	}

	public static BinaryImage dilate(BinaryImage input, boolean box) {
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
						if (box) {
							if (input.inBounds(i + 1, j + 1))
								ret.set(i + 1, j + 1, true);

							if (input.inBounds(i - 1, j + 1))
								ret.set(i - 1, j + 1, true);

							if (input.inBounds(i - 1, j + 1))
								ret.set(i - 1, j + 1, true);

							if (input.inBounds(i - 1, j - 1))
								ret.set(i - 1, j - 1, true);
						}
						ret.set(i, j, true);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}

	public static BinaryImage dilate(BinaryImage input, int iterations) {
		BinaryImage dilated = dilate(input);
		for (int i = 0; i < iterations - 1; i++) {
			dilated = dilate(dilated);
		}
		return dilated;
	}

	public static BinaryImage erode(BinaryImage input) {
		return erode(input, false);
	}

	public static BinaryImage erode(BinaryImage input, boolean box) {
		int width = input.getWidth();
		int height = input.getHeight();
		BinaryImage ret = new BinaryImage(height, width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				try {
					if (input.get(i, j)) {
						if (input.get(i + 1, j) && input.get(i - 1, j)
								&& input.get(i, j + 1) && input.get(i, j - 1))
							if (box && input.get(i + 1, j + 1)
									&& input.get(i + 1, j - 1)
									&& input.get(i - 1, j + 1)
									&& input.get(i - 1, j - 1))
								ret.set(i, j, true);
							else
								ret.set(i, j, true);

					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					continue;
				}
			}
		}
		return ret;
	}

	public static BinaryImage erode(BinaryImage input, int iterations) {
		BinaryImage eroded = erode(input);
		for (int i = 0; i < iterations - 1; i++) {
			eroded = erode(eroded);
		}
		return eroded;
	}


	public static BinaryImage open(BinaryImage input, int iterations) {
		return input.or(erode(dilate(input)));
	}

	public static BinaryImage close(BinaryImage input){
		return close(input, 1);
	}
	
	public static BinaryImage close(BinaryImage input, int iterations) {
		BinaryImage ret = input.clone();
		for(int i = 0; i < iterations; i++){
			BinaryImage b = erode(dilate(input, i + 1), i + 1);
			System.out.println("i:" + i + " : " + b);
			ret = ret.or(b);
		}
		return ret;
	}

}
