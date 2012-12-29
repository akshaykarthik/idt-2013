package edu.mhs.compsys.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mhs.compsys.morphology.BinaryImage;

public class BinaryImageTest {


	BinaryImage a;
	BinaryImage b;
	@Before
	public void setUp() throws Exception {
		reset();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		reset();
		assertFalse(a.get(0, 0));
		a.flip(0, 0);
		assertTrue(a.get(0, 0));
		
		reset();
		a = a.inverse();
		assertFalse(a.get(0, 0));
		assertTrue(a.get(0, 1));
		
		reset();
		System.out.println("A:" + a.toString());
		System.out.println("b:" + b.toString());
		System.out.println("A inv:" + a.inverse().toString());
		System.out.println("A&&B:" + a.and(b).toString());
		System.out.println("A||B:" + a.or(b).toString());
	}

	public void reset(){
		a = new BinaryImage(5, 5);
		a.flip(1, 1);
		a.flip(2, 2);
		a.flip(3, 3);
		a.flip(4, 4);
		
		b = new BinaryImage(5, 5);
		b.flip(0, 0);
		b.flip(0, 1);
		b.flip(0, 2);
		b.flip(0, 3);
		b.flip(0, 4);
	}
}
