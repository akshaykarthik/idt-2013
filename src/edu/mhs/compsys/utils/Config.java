package edu.mhs.compsys.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private int imageHeight;
	private int imageWidth;
	private int taskBarHeight;
	private int xButtonHeight;
	private int xButtonWidth;

	/**
	 * Initializes new Config Class with default config file of Config.ini If
	 * Config.ini was deleted, loads default values.
	 */
	public Config() {
		this("Config.ini");
	}

	/**
	 * Initializes new Config Class with config file name of fileName If file is
	 * not found, loads default values.
	 * 
	 * @param fileName
	 *            The name of the config file.
	 */
	public Config(String fileName) {
		try {
			Properties serv = new Properties();
			serv.load(new FileReader(fileName));
			this.imageHeight = Integer
					.parseInt(serv.getProperty("ImageHeight"));
			this.imageWidth = Integer.parseInt(serv.getProperty("ImageWidth"));
			this.taskBarHeight = Integer.parseInt(serv
					.getProperty("TaskBarHeight"));
			this.xButtonHeight = Integer.parseInt(serv
					.getProperty("XButtonHeight"));
			this.xButtonWidth = Integer.parseInt(serv
					.getProperty("XButtonWidth"));

		} catch (FileNotFoundException e) {
			this.imageHeight = 1024;
			this.imageWidth = 1280;
			this.taskBarHeight = 44;
			this.xButtonHeight = 14;
			this.xButtonWidth = 16;

		} catch (IOException e) {
			System.out.println("Could Not Find File: Config.ini");
		}

	}

	/**
	 * Returns the height of the image to be loaded.
	 * @return int imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * Returns the width of the image to be loaded.
	 * @return int imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * Returns the height of the taskbar in the loaded image.
	 * @return int taskBarHeight
	 */
	public int getTaskBarHeight() {
		return taskBarHeight;
	}

	/**
	 * Returns the height of the X button in the loaded image.
	 * @return int xButtonHeight
	 */
	public int getXButtonHeight() {
		return xButtonHeight;
	}

	/**
	 * Returns the width of the X button in the loaded image.
	 * @return int xButtonWidth
	 */
	public int getXButtonWidth() {
		return xButtonWidth;
	}

}
