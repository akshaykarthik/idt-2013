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

	public Config() {
		try {
			Properties serv = new Properties();
			serv.load(new FileReader("Config.ini"));
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

}
