package edu.mhs.compsys.morphology;

/**
 * A class for easy operations on binary images. This class is simply a
 * datastore and is not an actual image. It can however be converted to
 * BufferedImage via toBufferedImage;
 * <p>
 * The representation is simply a 2d array of booleans.
 * <p>
 * X,Y is 0,0 at the Top Left corner and increases as you go down and to the
 * left
 * 
 **/
public class BinaryImage {

	private int _height = 0;
	private int _width = 0;
	protected boolean[][] _data;

	public BinaryImage(int height, int width) {
		this._height = height;
		this._width = width;
		_data = new boolean[height][width];
		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				_data[i][j] = false;
	}

	public BinaryImage(String str, String sep) {
		String[] columns = str.split(sep);
		this._height = columns.length;
		this._width = columns[0].length();

		_data = new boolean[_height][_width];
		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				set(i, j, (columns[i].charAt(j) == '1'));
	}

	public BinaryImage(String str) {
		this(str, "\n");
	}

	public BinaryImage clone() {
		BinaryImage b = new BinaryImage(_height, _width);

		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				b.set(i, j, get(i, j));
		return b;
	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}

	public boolean inBounds(int x, int y) {
		return (0 <= x && x < _height) && (0 <= y && y < _width);
	}

	public boolean get(int x, int y) throws IndexOutOfBoundsException {
		return _data[x][y];
	}

	public void set(int x, int y, boolean value) {
		_data[x][y] = value;
	}

	public void flip(int x, int y) {
		set(x, y, !get(x, y));
	}

	public BinaryImage inverse() {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, !get(i, j));
			}
		}
		return ret;
	}

	public BinaryImage and(BinaryImage other) {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) && other.get(i, j));
			}
		}
		return ret;
	}

	public BinaryImage or(BinaryImage other) {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) || other.get(i, j));
			}
		}
		return ret;
	}

	public boolean equals(BinaryImage other) {
		for (int i = 0; i < _height; i++)
			for (int j = 0; j < _width; j++)
				if (get(i, j) != other.get(i, j))
					return false;
		return true;
	}

	public String toString() {
		return toString("1", "0");
	}

	public String toString(String one, String zero) {

		String retval = String.format("BinaryImage(%s,%s)\n \t  ", _width,
				_height);
		for (int i = 0; i < _width; i++)
			retval += i;
		retval += "\n";

		for (int i = 0; i < _height; i++) {
			retval += "\t" + i + " ";
			for (int j = 0; j < _width; j++) {
				retval += (get(i, j) ? one : zero);
			}
			retval += "\n";
		}

		return retval;

	}

}
