package edu.mhs.compsys.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.ImageAreaIdentifier;
import edu.mhs.compsys.processing.Recognizer;
import edu.mhs.compsys.utils.Config;

public class GraphicsPanel extends JPanel implements ActionListener
{
	private static final long	serialVersionUID		= 1L;

	private final int			ERROR_NOT_AN_IMAGE		= 1;
	private final int			ERROR_WRONG_IMAGE_SIZE	= 2;
	private final int			ERROR_TOO_FEW_IMAGES	= 3;
	private int					errorCode				= 0;

	private JFileChooser		jfc;
	private JFrame				jframe;
	private JMenuBar			menu					= new JMenuBar();
	private JButton				next, prev, helpButton;

	private File[]				files;
	private ImageIcon[]			images;
	private boolean				drawImages				= false, notAllImages;
	private int					imageNum				= -1;
	private int					resX					= 1000, resY = 500;
	private Recognizer			rec;
	private Config				config;

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
		jf.setFocusable(true);

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
					System.exit(0);
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					prev();
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					next();
			}
		});

		config = new Config();
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
		System.out.println("Memory Usage: " + (double) ((int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 100000)) / 10 + " MB");
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Calibri", 0, 20));
		g.setColor(Color.black);
		g.drawString("Esc - Close", 10, this.getHeight() - 10);

		if (drawImages)
		{
			helpButton.setVisible(false);
			helpButton.setFocusable(false);
			resX = jframe.getWidth();
			int imgWidth = (resX - 40) / 2;

			int imgXSize = (int) (imgWidth);
			int imgYSize = (int) ((1024.0 / 1280.0) * imgWidth);
			if (images != null && images.length > 1 && images.length - 1 > imageNum)
			{
				// Image 1 --- quadrant: 2
				g.drawString(files[imageNum].getName() + "        " + (imageNum + 1) + " of " + images.length, 10, 50);
				g.drawImage(new ImageIcon(files[imageNum].getPath()).getImage(), 10, 55, imgXSize, imgYSize, null);

				// Image 2 --- quadrant: 1
				g.drawString(files[imageNum + 1].getName() + "        " + (imageNum + 2) + " of " + images.length, 10 + (imgWidth + 10), 50);
				g.drawImage(new ImageIcon(files[imageNum + 1].getPath()).getImage(), 10 + (imgWidth + 10), 55, imgXSize, imgYSize, null);

				// Change image --- quadrant: 3
				// put this back in
				g.drawImage(rec.getChange(imageNum), 10, 55 + 10 + imgYSize, imgXSize, imgYSize, null);
				// g.drawImage(ImageAreaIdentifier.getAreas(rec.getBinDiff().get(imageNum)),
				// 10, 55 + 10 + imgYSize, imgXSize, imgYSize, null);
				// imgYSize + 10, null);

				// Change Strings --- quadrant: 4
				ArrayList<String> changeStrings = new ArrayList<String>();
				ArrayList<StateTransition> changes = rec.getChanges();
				try
				{
					StateTransition st = changes.get(imageNum);

					for (Change ml : st.getChanges())
					{
						changeStrings.add(ml.toString());
					}
				}
				catch (Exception ex)
				{
					changeStrings.add("No changes loaded!");
				}

				for (int i = 0; i < changeStrings.size(); i++)
				{
					g.drawString(changeStrings.get(i), 20 + imgWidth, 89 + imgYSize + (i * 21));
				}

			}
		}

		else
		{
			if (!notAllImages)
				g.drawString("You have not loaded the images yet.", 250, 200);
			else if (errorCode == this.ERROR_NOT_AN_IMAGE)
			{
				g.setColor(Color.red);
				g.drawString("Something loaded was not an image. Please try again.", 250, 200);
				g.setColor(Color.black);
				addHelpButton();
			}
			else if (errorCode == this.ERROR_WRONG_IMAGE_SIZE)
			{
				g.setColor(Color.red);
				g.drawString("One of the images loaded was not the correct size. Please try again.", 250, 200);
				g.setColor(Color.black);
				addHelpButton();
			}
			else if (errorCode == this.ERROR_TOO_FEW_IMAGES)
			{
				g.setColor(Color.red);
				g.drawString("More images are needed. Please load all of the images to be tested.", 250, 200);
				g.setColor(Color.black);
				addHelpButton();
			}
			g.drawString("Click \"File > Open Images\" to load the images into the applicaiton.", 250, 220);
			g.drawString("For more help click \"Help > Help Information\" to open a help window.", 250, 240);
		}
	}
	private void addHelpButton()
	{
		helpButton.setVisible(true);
	}
	/**
	 * Loads up the menu strip buttons and assigns action listeners to the
	 * options
	 */
	private void loadUI()
	{
		helpButton = new JButton("Help");
		helpButton.addActionListener(this);
		helpButton.setVisible(false);
		this.setLayout(new FlowLayout(1));
		add(helpButton);
		JMenu file = new JMenu("File");// file drop down menu
		menu.add(file);
		{
			JMenuItem open = new JMenuItem("Open Images");
			open.addActionListener(this);
			file.add(open);
		}
		JMenu helpMenu = new JMenu("Help"); // help menu
		menu.add(helpMenu);
		{
			JMenuItem helpinfo = new JMenuItem("Help Information");
			helpinfo.addActionListener(this);
			helpMenu.add(helpinfo);

			JMenuItem about = new JMenuItem("About");
			about.addActionListener(this);
			helpMenu.add(about);

			JMenuItem testing = new JMenuItem("Testing");
			testing.addActionListener(this);
			helpMenu.add(testing);

			JMenuItem doc = new JMenuItem("Documentation");
			doc.addActionListener(this);
			helpMenu.add(doc);
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
			// ImageIcon ii = new ImageIcon(files[i].getPath());
			// images[i] = ii;
			if (!files[i].getName().toUpperCase().endsWith(".PNG"))
			{
				errorCode = ERROR_NOT_AN_IMAGE;
				haveImages = false;
			}
			else if (config.getImageWidth() != new ImageIcon(files[i].toString()).getImage().getWidth(null)
					|| config.getImageHeight() != new ImageIcon(files[i].toString()).getImage().getHeight(null))
			{
				errorCode = ERROR_WRONG_IMAGE_SIZE;
				haveImages = false;
			}
		}

		if (files.length < 2 && haveImages)
		{
			errorCode = ERROR_TOO_FEW_IMAGES;
			drawImages = false;
			haveImages = false;
		}
		if (files.length > 0 && !haveImages)
		{
			files = null;
			notAllImages = true;
			drawImages = false;
		}
		if (haveImages && !drawImages)
		{
			imageNum = 0;
			notAllImages = false;
			next = new JButton("Next");
			next.addActionListener(this);
			next.setFocusable(false);
			prev = new JButton("Prev.");
			prev.addActionListener(this);
			prev.setFocusable(false);
			add(prev);
			add(next);
			resX = 850;
			resY = 800;
			setSize(resX, resY);
			jframe.setSize(resX, resY);
			jframe.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - jframe.getWidth() / 2,
					Toolkit.getDefaultToolkit().getScreenSize().height / 2 - jframe.getHeight() / 2);
		}
		if (haveImages)
			drawImages = true;
		if (haveImages && drawImages)
			rec = new Recognizer(files, config);
		repaint();
	}
	private void next()
	{
		if (drawImages)
		{
			imageNum++;
			if (imageNum == images.length - 1)
				imageNum = 0;
			repaint();
		}
	}
	private void prev()
	{
		if (drawImages)
		{
			imageNum--;
			if (imageNum == -1)
				imageNum = images.length - 2;
			repaint();
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		String name = e.getActionCommand();

		if (name.equals("Help Information") || name.equals("Help"))
		{
			HelpUI hui = new HelpUI();
			hui.setFocusable(false);
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
				repaint();
				files = jfc.getSelectedFiles();
				loadImages(files);
			}
		}
		else if (name.equals("Next"))
		{
			next();
		}
		else if (name.equals("Prev."))
		{
			prev();
		}
	}
}
