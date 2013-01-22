package edu.mhs.compsys.idt;

import java.awt.Point;

/**
 * This class encapsulates the bounds of a change. It contains a description of
 * a box of change defined by <code>Bound{x, y, l, w};</code>
 */
public class Bounds {

	private int x;
	private int y;
	private int l;
	private int w;

	/**
	 * Creates a new <code>Bounds</code> object with all values set to
	 * <code>-1</code>.
	 */
	public Bounds() {
		this(-1, -1, -1, -1);
	}

	/**
	 * Creates a new <code>Bounds</code> object with the given parameters.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate.
	 * @param y
	 *            The <code>y</code> coordinate.
	 * @param l
	 *            The <code>length</code> of the change rectangle.
	 * @param w
	 *            The <code>width</code> of the change rectangle.
	 */
	public Bounds(int x, int y, int l, int w) {
		this.x = x;
		this.y = y;
		this.l = l;
		this.w = w;
	}

	/**
	 * @return the x coordinate of the change
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x coordinate to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y coordinate of the change
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y coordinate to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the length of the change
	 */
	public int getLength() {
		return l;
	}

	/**
	 * @param l
	 *            the length to set
	 */
	public void setLength(int l) {
		this.l = l;
	}

	/**
	 * @return the width of the change
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * @param w
	 *            the width to set
	 */
	public void setWidth(int w) {
		this.w = w;
	}

	public Point getBotRight() {
		return new Point(x + w, y + l);
	}

	/**
	 * Returns a visual representation of bounds of change The format is
	 * <code>[x={x}, y={y}, l={l}, w={w}]</code>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("[x=%s, y=%s, l=%s, w=%s]", x, y, l, w);
	}

	/**
	 * Returns equal if all coordinates are the same.
	 * 
	 * @param other
	 * @return <code>true</code> if equal, <code>false</code> otherwise.
	 */
	public boolean equals(Bounds other) {
		return (x == other.x && y == other.y && l == other.l && w == other.w);
	}
}
