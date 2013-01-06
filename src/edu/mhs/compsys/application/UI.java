package edu.mhs.compsys.application;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.mhs.compsys.utils.Config;

public class UI extends JFrame implements ActionListener
{
	private int				resX, resY;
	private Config			cfg;
	private JFileChooser	jfc;
	File[]					files;
	ImageIcon[]				pics, changePics;

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
	/**
	 * initializer method to start up the JFrame creates needed objects and sets
	 * basic settings loads up a kKeyListener
	 */
	public void init()
	{
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("IDT 2013 | MHS");

		cfg = new Config();
		jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setMultiSelectionEnabled(true);
		// this just finds the URL to the class file for some reason
		// ya know, if you need it
		// String path = getClass().getResource("UI.class").toString();
		// path = path.substring(6, path.length() - 8);
		// for (int i = path.length() - 1; i > 0; i--)
		// {
		// if (path.charAt(i) == '/')
		// {
		// path = path.substring(0, i) + "\\" + path.substring(i + 1);
		// i--;
		// }
		// }

		setDefaults();
		loadUI();
		loadConfig();
		setSize(resX, resY);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);

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
	private void setDefaults()
	{
		resX = 1000;
		resY = 500;
	}
	private void loadConfig()
	{

	}
	/**
	 * Loads up the UI including drop down menus, buttons, and other window
	 * properties
	 */
	private void loadUI()
	{
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu file = new JMenu("File");// file drop down menu
		menu.add(file);
		{
			JMenuItem open = new JMenuItem("Open Images");
			open.addActionListener(this);
			file.add(open);
		}
		JMenu help = new JMenu("Help"); // help menu
		menu.add(help);
		{
			JMenuItem helpinfo = new JMenuItem("Help Information");
			helpinfo.addActionListener(this);
			help.add(helpinfo);

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
	/**
	 * will return the files if they have been loaded if null if not
	 * 
	 * @return
	 */
	public File[] getFiles()
	{
		if (files != null)
			return files;
		else
			return null;
	}
	public void actionPerformed(ActionEvent e)
	{
		String name = e.getActionCommand();

		if (name.equals("Help Information"))
		{
			HelpUI hui = new HelpUI();
			hui.setVisible(true);
		}
		else if (name.equals("About"))
		{
			AboutUI aui = new AboutUI();
			aui.setVisible(true);
		}
		else if (name.equals("Documentation"))
		{
			DocumentationUI dui = new DocumentationUI();
			dui.setVisible(true);
		}
		else if (name.equals("Testing"))
		{
			TestingUI tui = new TestingUI();
			tui.setVisible(true);
		}
		else if (name.equals("Open Images"))
		{
			int rv = jfc.showOpenDialog(this);
			if (rv == JFileChooser.APPROVE_OPTION)
			{
				files = jfc.getSelectedFiles();
				loadImages(files);
			}
		}
	}
	private void loadImages(File[] f)
	{
		pics = new ImageIcon[f.length];
		for (int i = 0; i < files.length; i++)
		{
			ImageIcon ii = new ImageIcon(files[i].getPath());
			pics[i] = ii;

		}

	}
	/**
	 * Just an easy way to print stuff
	 * 
	 * @param o
	 *            is the object being printed
	 */
	public void print(Object o)
	{
		System.out.println(o);
	}
}
