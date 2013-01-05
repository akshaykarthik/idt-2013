package edu.mhs.compsys.idt;

public class Bounds {

	private int x;
	private int y;
	private int l;
	private int w;

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

	public Bounds(int x, int y, int l, int w) {
		super();
		this.x = x;
		this.y = y;
		this.l = l;
		this.w = w;
	}

	/*
	 * Returns a visual representation of bounds of change 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[x=%s, y=%s, l=%s, w=%s]", x, y, l, w);
	}

}
