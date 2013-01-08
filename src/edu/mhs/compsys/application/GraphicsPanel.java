package edu.mhs.compsys.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	JFrame						jframe;
	private JMenuBar			menu				= new JMenuBar();
	private JButton				next, prev;
	private File[]				files;
	private ImageIcon[]			images, imageChanges;
	private boolean				drawImages			= false, notAllImages;
	private int					imageNum			= -1;
	private int					resX				= 1000, resY = 500;

	public static void main(String[] args)
	{
		JFrame jf = new JFrame("IDT 2013 | MHS");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsPanel gp = new GraphicsPanel();
		gp.loadJFrame(jf);
		jf.add(gp);
		jf.setVisible(true);
		jf.pack();
		jf.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - jf.getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - jf.getHeight() / 2);
		jf.setJMenuBar(gp.menu);

	}

	/**
	 * Constructor - Initializes the JFileChooser
	 */
	public GraphicsPanel()
	{
		jfc = new JFileChooser();
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
				}
			}
		});
		loadUI();

	}
	public void loadJFrame(JFrame jf)
	{
		jframe = jf;
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(resX, resY);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Calibri", 0, 20));
		g.setColor(Color.black);
		g.drawString("Esc - Close", 10, this.getHeight() - 10);
		if (drawImages)
		{

			resX = jframe.getWidth();
			int imgWidth = (resX - 40) / 2;

			int imgXSize = (int) (imgWidth);
			int imgYSize = (int) ((1024.0 / 1280.0) * imgWidth);
			if (images != null && images.length > 2 && images.length - 1 > imageNum)
			{
				g.drawImage(images[imageNum].getImage(), 10, 10, imgXSize, imgYSize, null);
				g.drawImage(images[imageNum + 1].getImage(), 10 + (imgWidth + 10), 10, imgXSize, imgYSize, null);
			}
		}
		else
		{
			if (!notAllImages)
				g.drawString("You have not loaded the images yet.", 250, 200);
			else
			{
				g.setColor(Color.red);
				g.drawString("Something loaded was not an image. Please try again.", 250, 200);
				g.setColor(Color.black);
			}
			g.drawString("Click \"File > Open Images\" to load the images into the applicaiton.", 250, 220);
			g.drawString("For more help click \"Help > Help Information\" to open a help window.", 250, 240);
		}
	}
	/**
	 * Loads up the menu strip buttons and assigns action listeners to the
	 * options
	 */
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
	/**
	 * This takes in the files from the "Open" window. Files will only be loaded
	 * if they end with .png (not case sensative) and an error message will be
	 * displayed in-window alerting the user if there is anything wrong with the
	 * loaded files
	 * 
	 * @param f
	 *            the loaded files obtained through the
	 *            JFileChooser.getSelectedFiles()
	 */
	private void loadImages(File[] f)
	{
		images = new ImageIcon[f.length];
		boolean haveImages = true;
		for (int i = 0; i < files.length; i++)
		{
			ImageIcon ii = new ImageIcon(files[i].getPath());
			images[i] = ii;
			if (!files[i].getName().toUpperCase().endsWith(".PNG"))
				haveImages = false;
		}
		if (haveImages)
			drawImages = true;
		if (files.length > 0 && !haveImages)
		{
			files = null;
			notAllImages = true;
			drawImages = false;
		}
		if (drawImages)
		{
			imageNum = 0;
			notAllImages = false;
			next = new JButton("Next");
			prev = new JButton("Prev");
			add(prev);
			add(next);
			resX = 1000;
			resY = 850;
			jframe.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - jframe.getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - jframe.getHeight() / 2);
			setSize(resX, resY);
			jframe.setSize(resX, resY);

			repaint();
		}
		// TODO: this is where the processing will be
		// analyzer.analyze(images);
		// analyzer.getChangeImages(imageChanges);
		// analyzer.getChange
		repaint(); // once images are loaded, they are painted to the screen
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
		else if (name.equals("Next"))
		{
			imageNum++;
			if (imageNum >= images.length)
				imageNum = 0;
		}
		else if (name.equals("Prev."))
		{
			imageNum--;
			if (imageNum == -1 && images.length > 2)
				imageNum = images.length - 2;
			else
				imageNum = images.length - 1;
		}
	}
}
