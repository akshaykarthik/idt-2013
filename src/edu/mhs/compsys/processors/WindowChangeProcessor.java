/**
 * 
 */
package edu.mhs.compsys.processors;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.mhs.compsys.idt.Bounds;
import edu.mhs.compsys.idt.Change;
import edu.mhs.compsys.idt.ClassificationType;
import edu.mhs.compsys.idt.Dataset;
import edu.mhs.compsys.idt.StateTransition;
import edu.mhs.compsys.processing.BinaryImage;
import edu.mhs.compsys.processing.BinaryImageProcessor;
import edu.mhs.compsys.processing.ChangeBundle;
import edu.mhs.compsys.processing.IChangeProcessor;
import edu.mhs.compsys.utils.Config;

/**
 * This Processor will recognize changes in the windows and quantify those. It
 * will return WINDOW_APPLICATION_AREA_UPDATE, WINDOW_TITLE_BAR_CLICK, and
 * WINDOW_TITLE_CHANGE
 */
public class WindowChangeProcessor implements IChangeProcessor
{

	private ArrayList<Change>	_changes;
	private Config				cfg;

	/**
	 * Initialize the processor with the given config file.
	 * 
	 * @see edu.mhs.compsys.processing.IChangeProcessor#initialize(edu.mhs.compsys.utils.Config)
	 */
	@Override
	public void initialize(Config cfg)
	{
		this.cfg = cfg;
	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#process(java.awt.image.
	 *      BufferedImage, java.awt.image.BufferedImage,
	 *      edu.mhs.compsys.processing.BinaryImage, java.util.ArrayList,
	 *      edu.mhs.compsys.idt.Dataset)
	 */
	@Override
	public void process(BufferedImage img, BufferedImage img2,
			BinaryImage diff, ArrayList<StateTransition> changes, Dataset data,
			ArrayList<Bounds> previousStateWindows)
	{
		_changes = new ArrayList<Change>();
		if (previousStateWindows.size() > 0)
		{
			Bounds[] windowChange = new Bounds[previousStateWindows.size()];
			Bounds maxChange = new Bounds();
			int window = -1;
			for (int i = 0; i < windowChange.length; i++)
			{
				windowChange[i] = BinaryImageProcessor.boundsOfChange(
						diff,
						new Bounds(windowChange[i].getX(), windowChange[i]
								.getY(), windowChange[i].getHeight() - 1,
								windowChange[i].getWidth() - 1));
				if (maxChange.size() < windowChange[i].size())
				{
					maxChange = windowChange[i];
					window = i;
				}
			}
			// Find which area of the window it took place in. Top portion is
			// titlebar, then menubar, then application area.
			// if(maxChange.getY() > ) {
			// //application area.
			// }
			if (maxChange.getY() < 24)
			{ // This will be anything inside the
				// title
				// bar. Title bar click or title bar
				// change. Click will be the right
				// most
				// xx pixels.

				if (previousStateWindows.get(window).getWidth()
						- maxChange.getX() < 56)
					// 56 pixels approx. from edge to minimize button edge.
					_changes.add(new Change(maxChange,
							ClassificationType.WINDOW_TITLE_BAR_CLICK));
				else
					_changes.add(new Change(maxChange,
							ClassificationType.WINDOW_TITLE_CHANGE));
			}
			if (maxChange.getY() > 73)
				_changes.add(new Change(maxChange,
						ClassificationType.WINDOW_APPLICATION_AREA_UPDATE));
		}

	}

	/**
	 * @see edu.mhs.compsys.processing.IChangeProcessor#getChanges()
	 */
	@Override
	public Change[] getChanges()
	{
		// TODO Auto-generated method stub
		return _changes.toArray(new Change[0]);
	}

	public void proProcess(BufferedImage img1, BufferedImage img2, BinaryImage diff, ArrayList<ChangeBundle> prevChanges, ChangeBundle curChanges)

	{
		ArrayList<Bounds> previousStateWindows = new ArrayList<Bounds>();
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// XXX you need to make a method to fix populate and track the windows
		// because it wont be passed in, sorry.
		// I suggest making a loop with a DesktopChangeProcessor and a
		// WindowStateProcessos in an ArrayList so they will give you the
		// opens/closes/moves

		_changes = new ArrayList<Change>();

		if (previousStateWindows.size() > 0)
		{
			Bounds[] windowChange = new Bounds[previousStateWindows.size()];
			Bounds maxChange = new Bounds();
			int window = -1;
			for (int i = 0; i < windowChange.length; i++)
			{
				windowChange[i] = BinaryImageProcessor.boundsOfChange(
						diff,
						new Bounds(windowChange[i].getX(), windowChange[i]
								.getY(), windowChange[i].getHeight() - 1,
								windowChange[i].getWidth() - 1));
				if (maxChange.size() < windowChange[i].size())
				{
					maxChange = windowChange[i];
					window = i;
				}
			}
			if (maxChange.getY() < 24)
			{ // This will be anything inside the
				// title
				// bar. Title bar click or title bar
				// change. Click will be the right
				// most
				// xx pixels.

				if (previousStateWindows.get(window).getWidth()
						- maxChange.getX() < 56)
					// 56 pixels approx. from edge to minimize button edge.
					_changes.add(new Change(maxChange,
							ClassificationType.WINDOW_TITLE_BAR_CLICK));
				else
					_changes.add(new Change(maxChange,
							ClassificationType.WINDOW_TITLE_CHANGE));
			}
			if (maxChange.getY() > 73)
				_changes.add(new Change(maxChange,
						ClassificationType.WINDOW_APPLICATION_AREA_UPDATE));
		}

	}

	public ArrayList<Change> getPROChanges()
	{
		if (_changes == null)
		{
			System.out.println(this.getClass() + ".getPROChanges returned null");
			return new ArrayList<Change>();
		}
		return _changes;
	}

}
