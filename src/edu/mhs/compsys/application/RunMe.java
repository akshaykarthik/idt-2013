package edu.mhs.compsys.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RunMe
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
		jf.setJMenuBar(gp.menu);
	}

}
