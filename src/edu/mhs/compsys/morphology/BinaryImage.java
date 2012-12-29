package edu.mhs.compsys.morphology;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * A class for easy operations on binary images. This class is simply a
 * datastore and is not an actual image. It can however be converted to
 * BufferedImage via toBufferedImage;
 * <p>
 * The representation is an arraylist of bitsets Each bitset is a row thus the
 * arraylist is the list of rows. Get (x, y) gets the x'th bit of the y'th row.
 * <p>
 * X,Y is 0,0 at the Top Left corner and increases as you go down and to the
 * left
 * 
 **/
public class BinaryImage {

	private int _height = 0;
	private int _width = 0;
	protected ArrayList<BitSet> _data;

	public BinaryImage(int height, int width) {
		this._height = height;
		this._width = width;
		_data = new ArrayList<BitSet>();
		for (int i = 0; i < height; i++) {
			_data.add(i, new BitSet(width));
			_data.get(i).clear();
		}
	}
	
	public boolean inBounds(int x, int y){
		return (0 < x && x < _width) && (0 < y && y < _height);
	}
	
	public boolean get(int x, int y) throws IndexOutOfBoundsException{
		return _data.get(y).get(x);
	}

	public void set(int x, int y, boolean value){
		_data.get(y).set(x, value);
	}
	
	public void flip(int x, int y){
		set(x, y, !get(x, y));
	}

	public BinaryImage inverse(){
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, !get(i, j));
			}
		}
		return ret;
	}
	
	public BinaryImage and(BinaryImage other){
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) && other.get(i, j));
			}
		}
		return ret;
	}
	
	public BinaryImage or(BinaryImage other){
		BinaryImage ret = new BinaryImage(_height, _width);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				ret.set(i, j, get(i, j) || other.get(i, j));
			}
		}
		return ret;
	}	
	
	public String toString(){
		String retval = "BinaryImage("+_height+","+_width+")\n";
		for (int i = 0; i < _height; i++) {
			retval += "\t";
			for (int j = 0; j < _width; j++) {
				retval += (get(i,j)?"1":"0");
			}
			retval += "\n";
		}
		return retval;
	}
	
	public static void main(String[] args){

		System.out.println("Test Binary Image");
		BinaryImage a = new BinaryImage(5, 5);
		a.flip(0, 0);
		a.flip(1, 1);
		a.flip(2, 2);
		a.flip(3, 3);
		a.flip(4, 4);
		
		BinaryImage b = new BinaryImage(5, 5);
		b.flip(0, 0);
		b.flip(0, 1);
		b.flip(0, 2);
		b.flip(0, 3);
		b.flip(0, 4);
		
		System.out.println("A:" + a.toString());
		System.out.println("b:" + b.toString());
		System.out.println("A inv:" + a.inverse().toString());
		System.out.println("A&&B:" + a.and(b).toString());
		System.out.println("A||B:" + a.or(b).toString());
		
		
	}
}
