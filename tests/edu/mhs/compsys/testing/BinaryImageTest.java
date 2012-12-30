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

		assertTrue(a.get(0, 0));
		a.flip(0, 0);
		assertFalse(a.get(0, 0));

		reset();
		assertTrue(a.get(0, 0));
		assertFalse(a.get(0, 1));
		a = a.inverse();
		assertFalse(a.get(0, 0));
		assertTrue(a.get(0, 1));

		reset();
		BinaryImage c = a.and(b);
		assertEquals(c.get(0, 0), a.get(0, 0) && b.get(0, 0));
		assertEquals(c.get(0, 1), a.get(0, 1) && b.get(0, 1));

		reset();
		c = a.or(b);
		assertEquals(c.get(0, 0), a.get(0, 0) || b.get(0, 0));
		assertEquals(c.get(0, 1), a.get(0, 1) || b.get(0, 1));
	}

	public void reset() {
		a = new BinaryImage("10000 01000 00100 00010 00001", " ");
		b = new BinaryImage("11111 00000 00000 00000 00000", " ");
	}
}
