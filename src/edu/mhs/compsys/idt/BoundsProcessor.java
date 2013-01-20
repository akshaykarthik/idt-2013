package edu.mhs.compsys.idt;

import java.awt.Point;
import java.util.ArrayList;

public class BoundsProcessor {
	// is point in bounds
	public static boolean inBounds(int x, int y, Bounds b) {
		if (x >= b.getX() && x < b.getWidth() && y >= b.getY()
				&& y < b.getLength())
			return true;
		return false;
	}

	public static boolean inBounds(Point p, Bounds b) {
		if (p.getX() >= b.getX() && p.getX() < b.getWidth()
				&& p.getY() >= b.getY() && p.getY() < b.getLength())
			return true;
		return false;
	}

	// do the bounds intesect
	public static boolean intersect(Bounds a, Bounds b) {
		// if(a.getX() + a.getWidth())

		return false;
	}

	// all is b in a
	public static boolean inside(Bounds a, Bounds b) {
		if (b.getX() <= a.getX() && b.getY() <= a.getY()
				&& b.getWidth() < a.getWidth() && b.getLength() < a.getLength())
			return true;
		return false;
	}

	// smallest bounds that contains both Bounds objects
	public static Bounds union(Bounds a, Bounds b) {
		Point topLeft = new Point();
		Point botRight = new Point();

		if (inside(a, b))
			return a;
		if (inside(b, a))
			return b;
		else {
			topLeft = new Point(Math.min(a.getX(), b.getX()), Math.min(
					a.getY(), b.getY()));
			botRight = new Point(Math.max(a.getX() + a.getWidth(),
					b.getX() + b.getWidth()), Math.max(
					a.getY() + a.getLength(), b.getY() + b.getLength()));
			return new Bounds((int) topLeft.getX(), (int) topLeft.getY(),
					(int) botRight.getX(), (int) botRight.getY());
		}
	}

	public static Bounds union(ArrayList<Bounds> input) {
		return new Bounds();
	}

	// The region bound by bounds a and bounds b
	public static Bounds intersection(Bounds a, Bounds b) {
		return new Bounds();
	}

	public static ArrayList<Point> findCorners(ArrayList<Bounds> windows) {
		ArrayList<Point> corners = new ArrayList<Point>();
		for (int i = 0; i < windows.size(); i++)

			return corners;
	}

}
