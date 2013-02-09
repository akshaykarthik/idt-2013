package edu.mhs.compsys.idt;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is a container for the Transition between two states. It consists
 * of two <code>strings</code>, the name of the two states, and an
 * <code>ArrayList</code> of <code>Changes</code>.
 */
public class StateTransition {
	private String state1;
	private String state2;
	private ArrayList<Change> changes;

	/**
	 * Creates a new {@link StateTransition} with <code>s1</code> as State1 and
	 * <code>s2</code> as State2;
	 * 
	 * @param s1
	 * @param s2
	 */
	public StateTransition(String s1, String s2) {
		state1 = s1;
		state2 = s2;
		changes = new ArrayList<Change>();
	}

	/**
	 * @return the state1
	 */
	public String getState1() {
		return state1;
	}

	/**
	 * @return the state2
	 */
	public String getState2() {
		return state2;
	}

	/**
	 * @return the changes
	 */
	public ArrayList<Change> getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public void setChanges(ArrayList<Change> changes) {
		this.changes = changes;
	}

	public void addChanges(Change[] in) {
		this.changes.addAll(Arrays.asList(in));
	}

	public void addChanges(ArrayList<Change> in) {
		this.changes.addAll(in);
	}
	

	/**
	 * Adds a change to the current stateTransition
	 * 
	 * @param in
	 */
	public void addChange(Change in) {
		this.changes.add(in);
	}

	/**
	 * @param i
	 */
	public Change getChange(int i) {
		return this.changes.get(i);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
