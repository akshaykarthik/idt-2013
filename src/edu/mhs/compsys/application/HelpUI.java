package edu.mhs.compsys.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class HelpUI extends JFrame
{
	private static final long	serialVersionUID	= 1920201415316862146L;
	ArrayList<String>			helps;

	public HelpUI()
	{
		setTitle("Help");
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
		helps = new ArrayList<String>();
		try
		{
			InputStream in;
			in = HelpUI.class.getResourceAsStream("HelpText.txt"); //new FileInputStream("./src/edu/mhs/compsys/application/HelpText.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = "";
			while ((line = br.readLine()) != null)
				helps.add(line + "");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}
	public void paint(Graphics g)
	{
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Calibri", 0, 20));

		g.setColor(Color.BLACK);

		for (int i = 0; i < helps.size(); i++)
		{
			g.drawString(helps.get(i), 50, 100 + (21 * i));
		}

	}
}
