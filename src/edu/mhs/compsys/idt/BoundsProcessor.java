package edu.mhs.compsys.idt;

import java.awt.Point;
import java.util.ArrayList;

public class BoundsProcessor {
	// is point in bounds
	public static boolean inBounds(int x, int y, Bounds b) {
		return false;
	}

	public static boolean inBounds(Point p, Bounds b) {
		return false;
	}

	// do the bounds intesect
	public static boolean intersect(Bounds a, Bounds b) {
		return false;
	}

	// all is b in a
	public static boolean inside(Bounds a, Bounds b) {
		return false;
	}

	// smallest bounds that contains both Bounds objects
	public static Bounds union(Bounds a, Bounds b) {
		return new Bounds();
	}
	
	public static Bounds union(ArrayList<Bounds> input){
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
