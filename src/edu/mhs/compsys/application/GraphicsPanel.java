package edu.mhs.compsys.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements ActionListener
{
	private static final long	serialVersionUID	= 1L;

	private JFileChooser		jfc;
	private File[]				files;
	private ImageIcon[]			pics, changePics;
	private boolean				drawImages			= false;
	public JMenuBar				menu				= new JMenuBar();

	// public to allow the RunMe class to access it
	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsPanel gp = new GraphicsPanel();
		jf.add(gp);
		jf.setVisible(true);
		jf.pack();
		jf.setJMenuBar(gp.menu);
}
	public GraphicsPanel()
	{
		jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setMultiSelectionEnabled(true);
		this.setFocusable(true);
		// in line KeyListener
		// lets the user close with the Esc key
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
					System.out.println("closed");
				}
			}
		});
		loadUI();
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(1000, 500);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString("Esc - Close", 10, this.getHeight() - 10);
		if (drawImages)
		{

		}
	}
	private void loadUI()
	{
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
	private void loadImages(File[] f)
	{
		pics = new ImageIcon[f.length];

		for (int i = 0; i < files.length; i++)
		{
			ImageIcon ii = new ImageIcon(files[i].getPath());
			pics[i] = ii;
		}
		drawImages = true;
		repaint(); // once images are loaded, they are paintd to the screen
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

}
