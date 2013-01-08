package edu.mhs.compsys.application;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RunMe// don't actually run me, this is just a fancy wan of doing
					// stuff
{
	public static void main(String[] arguments)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				startUp();
			}
		});
	}
	private static void startUp()
	{
		JFrame jf = new JFrame("IDT 2013 | MHS");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsPanel gp = new GraphicsPanel();
		jf.add(gp);
		jf.setVisible(true);
		jf.pack();
		jf.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - jf.getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - jf.getHeight() / 2);

		//jf.setJMenuBar(gp.menu);
	}

}
