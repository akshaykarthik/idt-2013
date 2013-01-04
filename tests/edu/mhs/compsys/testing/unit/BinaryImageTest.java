package edu.mhs.compsys.testing.unit;

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
		a = new BinaryImage("10000 01000 00100 00010 00001", " ");
		b = new BinaryImage("11111 00000 00000 00000 00000", " ");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFlip() {
		assertTrue(a.get(0, 0));
		a.flip(0, 0);
		assertFalse(a.get(0, 0));
	}
	
	@Test
	public void testInverse(){
		assertTrue(a.get(0, 0));
		assertFalse(a.get(0, 1));
		a = a.inverse();
		assertFalse(a.	get(0, 0));
		assertTrue(a.get(0, 1));
	}

	@Test
	public void testAnd(){
		BinaryImage c = a.and(b);
		assertEquals(c.get(0, 0), a.get(0, 0) && b.get(0, 0));
		assertEquals(c.get(0, 1), a.get(0, 1) && b.get(0, 1));
	}
	
	@Test
	public void testOr() {
		BinaryImage c = a.or(b);
		assertEquals(c.get(0, 0), a.get(0, 0) || b.get(0, 0));
		assertEquals(c.get(0, 1), a.get(0, 1) || b.get(0, 1));
	}

}
