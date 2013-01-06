package edu.mhs.compsys.application;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class DocumentationUI extends JFrame
{

	public DocumentationUI()
	{
		setTitle("Documentation");
		setResizable(false);
		setSize(400, 500);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(this.HIDE_ON_CLOSE);
	}

}
