package edu.mhs.compsys.idt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class UI extends JFrame implements ActionListener
{
	int	resX, resY;

	public static void main(String[] pirates)
	{
		UI ui = new UI();
		ui.init();
	}

	/**
	 * Constructor yo, does nothing right nao
	 */
	public UI()
	{

	}
	public void init()
	{
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("IDT 2013 | MHS");

		String path = getClass().getResource("UI.class").toString();
		path = path.substring(6, path.length() - 8);
		for (int i = path.length() - 1; i > 0; i--)
		{
			if (path.charAt(i) == '/')
			{
				path = path.substring(0, i) + "\\" + path.substring(i + 1);
				i--;
			}
		}

		setDefaults();
		loadUI();
		loadConfig();
		setSize(resX, resY);

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
					System.exit(0);
				}
			}
		});
	}
	public void setDefaults()
	{
		resX = 800;
		resY = 350;
	}
	public void loadConfig()
	{

	}
	/**
	 * Loads up the UI including drop down menus, buttons, and other window
	 * properties
	 */
	public void loadUI()
	{
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu file = new JMenu("File");// file drop down menu
		menu.add(file);
		{
			JMenuItem open1 = new JMenuItem("Open First Image");
			open1.addActionListener(this);
			file.add(open1);

			JMenuItem open2 = new JMenuItem("Open Second Image");
			open2.addActionListener(this);
			file.add(open2);
		}
		JMenu help = new JMenu("Help"); // help menu
		menu.add(help);
		{
			JMenuItem about = new JMenuItem("About");
			about.addActionListener(this);
			help.add(about);

			JMenuItem testing = new JMenuItem("Testing");
			testing.addActionListener(this);
			help.add(testing);

			JMenuItem doc = new JMenuItem("Documentation");
			doc.addActionListener(this);
			help.add(doc);
		}

	}

	public void actionPerformed(ActionEvent e)
	{
		String name = e.getActionCommand();
		if (name.equals("About"))
		{
			print("about stuff here just for now");
		}
	}
	public void print(Object o)
	{
		System.out.println(o);
	}
}
