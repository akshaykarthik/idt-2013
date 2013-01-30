package edu.mhs.compsys.testing.unit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;

public class BinaryImageProcessorTest {

	BinaryImage a;
	BinaryImage b;

	@Before
	public void setUp() throws Exception {
		a = new BinaryImage("11111 10001 10001 10001 11111", " ");
		b = new BinaryImage("00000 01110 01110 01110 00000", " ");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testErosion() {
		BinaryImage al = BinaryImageProcessor.erode(b, false);
		BinaryImage bl = new BinaryImage("01110 11111 11111 11111 01110", " ");
		assertEquals(al, bl);
	}

	@Test
	public void testErosionBox() {
		BinaryImage bl = BinaryImageProcessor.erode(b, true);
		BinaryImage al = new BinaryImage("11111 11111 11111 11111 11111", " ");
		assertEquals(al, bl);
	}

	@Test
	public void testBoundsOfChange() {
		System.out.println(b);
		Bounds al = BinaryImageProcessor.boundsOfChange(b);
		System.out.println(al);
		Bounds bl = new Bounds(1, 1, 3, 3);
		assertEquals(al, bl);
	}

}
