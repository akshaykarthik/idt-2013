package edu.mhs.compsys.processing;

import java.util.ArrayList;

import edu.mhs.compsys.idt.Change;

public class ChangeBundle
{
	private ArrayList<Change>	changes;

	public ChangeBundle()
	{
		changes = new ArrayList<Change>();
	}
	public ArrayList<Change> getChanges()
	{
		return changes;
	}
	public Change get(int index)
	{
		return changes.get(index);
	}
	public void addChanges(ArrayList<Change> newChanges)
	{
		changes.addAll(newChanges);
	}
	public int size()
	{
		return changes.size();
	}

}
