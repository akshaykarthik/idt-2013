package edu.mhs.compsys.application;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DocumentationUI extends JFrame
{

	public DocumentationUI()
	{
		setTitle("Documentation");
		setResizable(false);
		setSize(400, 500);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{}
			public void keyTyped(KeyEvent e)
			{}
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					setVisible(false);
				}
			}
		});
	}

}
