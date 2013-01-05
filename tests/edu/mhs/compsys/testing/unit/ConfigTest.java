package edu.mhs.compsys.testing.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mhs.compsys.utils.Config;

public class ConfigTest {

	private Config cfg;

	@Before
	public void setUp() throws Exception {
		cfg = new Config();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetImageHeight() {
		assertEquals(cfg.getImageHeight(), 1024);
	}

	@Test
	public void testGetImageWidth() {
		assertEquals(cfg.getImageWidth(), 1280);
	}

	@Test
	public void testGetTaskBarHeight() {
		assertEquals(cfg.getTaskBarHeight(), 44);
	}

	@Test
	public void testGetXButtonHeight() {
		assertEquals(cfg.getXButtonHeight(), 14);
	}

	@Test
	public void testGetXButtonWidth() {
		assertEquals(cfg.getXButtonWidth(), 16);
	}

	@Test
	public void testGetDateWidth() {
		assertEquals(cfg.getDateWidth(), 200);
	}

}
