package edu.mhs.compsys.testing.unit;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mhs.compsys.morphology.BinaryImage;
import edu.mhs.compsys.morphology.BinaryImageProcessor;

public class BinaryImageProcessorTest {

	BinaryImage a;
	BinaryImage b;
	@Before
	public void setUp() throws Exception {
		a = new BinaryImage("11111 1000" +
				"1 10001 10001 11111", " ");
		b = new BinaryImage("00000 01110 01110 01110 00000", " ");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}

}
