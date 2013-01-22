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

	// do the bounds intersect
	public static boolean intersect(Bounds a, Bounds b) {
		Point aRight = a.getBotRight();
		Point bRight = b.getBotRight();
		if ((int) aRight.getX() < b.getX())
			return false;
		if (a.getX() > (int) bRight.getX())
			return false;
		if ((int) aRight.getY() < b.getY())
			return false;
		if (a.getY() > bRight.getY())
			return false;

		return true;
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
			botRight = new Point((int)Math.max(a.getBotRight().getX(),
					(int) b.getBotRight().getX()), Math.max((int)
					a.getBotRight().getY(), (int) b.getBotRight().getY()));
			return new Bounds((int) topLeft.getX(), (int) topLeft.getY(),
					(int) botRight.getX(), (int) botRight.getY());
		}
	}

	public static Bounds union(ArrayList<Bounds> input) {
		Bounds area = input.get(0);
		for(int i = 1; i < input.size(); i++){
			area = union(area, input.get(i));			
		}
		return area;
	}

	// The region bound by bounds a and bounds b
	public static Bounds intersection(Bounds a, Bounds b) {
		if (!intersect(a,b))
			return new Bounds();
		
		int left = Math.max(a.getX(), b.getX());
		int top = Math.max(a.getY(), b.getY());
		int right = Math.min((int)a.getBotRight().getX(), (int)b.getBotRight().getX());
		int bot = Math.min((int)a.getBotRight().getY(), (int)b.getBotRight().getY());
		
		return new Bounds(left, top, right - left, bot-top);
	}

	public static ArrayList<Point> findCorners(ArrayList<Bounds> windows) {
		ArrayList<Point> corners = new ArrayList<Point>();
		for (int i = 0; i < windows.size(); i++)
			//If it's inside the region, don't add it
			//If add corners of intersection + corners of window otherwise.
			return corners;
	}

}
