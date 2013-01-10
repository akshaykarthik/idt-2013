package edu.mhs.compsys.processing;

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
/**
 * @author akr
 * 
 */
/**
 * @author akr
 * 
 */
public class BinaryImage {

	private int _height = 0;
	private int _width = 0;
	protected boolean[][] _data;

	/**
	 * Initializes a new BinaryImage with all values set to false;
	 * 
	 * @param height
	 * @param width
	 */
	public BinaryImage(int height, int width) {
		this._height = height;
		this._width = width;
		_data = new boolean[height][width];
		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				_data[i][j] = false;
	}

	/**
	 * see <code>BinaryImage(String, String)</code>
	 * 
	 * Initializes a new BinaryImage from the given string. '1's are
	 * <code>true</code> and '0's are <code>false</code>. the sep is assumed to
	 * be '\n'
	 */
	public BinaryImage(String str) {
		this(str, "\n");
	}

	/**
	 * Initializes a new BinaryImage from the given string. <code>'1's</code>
	 * are <code>true</code> and <code>'0's</code> are <code>false</code>.
	 * <code>sep</code> determines a new pixel row.
	 * 
	 * @param str
	 * @param sep
	 */
	public BinaryImage(String str, String sep) {
		String[] columns = str.split(sep);
		this._height = columns.length;
		this._width = columns[0].length();

		_data = new boolean[_height][_width];
		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				set(i, j, (columns[i].charAt(j) == '1'));
	}

	/**
	 * Returns a clone of the BinaryImage.
	 * 
	 * @see java.lang.Object#clone()
	 */
	public BinaryImage clone() {
		BinaryImage b = new BinaryImage(_height, _width);

		for (int i = 0; i < _data.length; i++)
			for (int j = 0; j < _data[0].length; j++)
				b.set(i, j, get(i, j));
		return b;
	}

	/**
	 * @return height of image
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * @return width of image
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * Returns whether or not the given coordinates are within the bounds of the
	 * image.
	 * 
	 * @param x
	 * @param y
	 * @return True if the coordinate are inside the image, false otherwise.
	 */
	public boolean inBounds(int x, int y) {
		return (0 <= x && x < _height) && (0 <= y && y < _width);
	}

	/**
	 * Returns the value of the pixel at the given index.
	 * 
	 * @param x
	 * @param y
	 * @return The value of the pixel
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public boolean get(int x, int y) throws ArrayIndexOutOfBoundsException {
		return _data[x][y];
	}

	/**
	 * Gets the value at the given coordinates. If the coordinates are invalid,
	 * it returns false.
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public boolean safeGet(int x, int y) {
		if (inBounds(x, y))
			return _data[x][y];
		return false;
	}

	/**
	 * Sets the value at the given coordinates. Throws exception if coordinates
	 * are invalid.
	 * 
	 * @param x
	 * @param y
	 * @param value
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void set(int x, int y, boolean value)
			throws ArrayIndexOutOfBoundsException {
		_data[x][y] = value;
	}

	/**
	 * Sets the value at the given coordinates. If the coordinates are invalid,
	 * it does nothing.
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public void safeSet(int x, int y, boolean value) {
		if (inBounds(x, y))
			set(x, y, value);
	}

	/**
	 * Returns a new BinaryImage of the size <h(height), w(width)> that is taken
	 * from the current image at coordinated (x,y). Throws exception if
	 * dimensions are not within the current image;
	 * 
	 * @param x
	 * @param y
	 * @param h
	 * @param w
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public BinaryImage slice(int x, int y, int h, int w)
			throws ArrayIndexOutOfBoundsException {
		BinaryImage ret = new BinaryImage(h, w);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				ret.set(i, j, get(x + i, y + j));
			}
		}
		return ret;
	}

	/**
	 * Flips the value at the given coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	public void flip(int x, int y) {
		set(x, y, !get(x, y));
	}

	/**
	 * Inverts the image, setting each <code>true</code> pixel to
	 * <code>false</code> and each <code>false</code> pixel to <code>true</code>
	 * .
	 * 
	 * @return
	 */
	public BinaryImage inverse() {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, !get(i, j));
			}
		}
		return ret;
	}

	/**
	 * Returns the logical and of the two BinaryImages. Each pixel in the second
	 * image are only true if they are true in this or the <code>'other'</code>
	 * images.
	 * 
	 * @param other
	 *            BinaryImage to OR
	 * @return
	 */
	public BinaryImage or(BinaryImage other) {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) || other.get(i, j));
			}
		}
		return ret;
	}

	/**
	 * Returns the logical and of the two BinaryImages. Each pixel in the second
	 * image are only true if they are true in both this and
	 * <code>'other'</code> images.
	 * 
	 * @param other
	 *            BinaryImage to AND
	 * @return
	 */
	public BinaryImage and(BinaryImage other) {
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) && other.get(i, j));
			}
		}
		return ret;
	}

	/**
	 * Returns true if both Images have the same pixels, and the same size.
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals(BinaryImage other) {
		if (this.getHeight() != other.getHeight()
				|| this.getWidth() != other.getWidth())
			return false;
		for (int i = 0; i < _height; i++)
			for (int j = 0; j < _width; j++)
				if (get(i, j) != other.get(i, j))
					return false;
		return true;
	}

	/**
	 * Returns a string representation of the image with each <code>"1"</code>
	 * being <code>true</code> and each <code>"0"</code> being
	 * <code>false</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString("1", "0");
	}

	/**
	 * Returns a string representation of the image with each <code>one</code>
	 * being <code>true</code> and each <code>zero</code> being
	 * <code>false</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString(String one, String zero) {
		StringBuilder ret = new StringBuilder();
		ret.append(String.format("BinaryImage(%s,%s)\n \t  ", _width, _height));
		for (int i = 0; i < _width; i++)
			ret.append(i);
		ret.append("\n");

		for (int i = 0; i < _height; i++) {
			ret.append("\t" + i + " ");
			for (int j = 0; j < _width; j++) {
				ret.append(get(i, j) ? one : zero);
			}
			ret.append("\n");
		}

		return ret.toString();

	}

}
