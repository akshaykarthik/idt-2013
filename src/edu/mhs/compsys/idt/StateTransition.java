package edu.mhs.compsys.idt;

import java.util.ArrayList;

public class StateTransition {
	private String state1;
	private String state2;
	private ArrayList<Change> changes;
	
	public StateTransition(String s1, String s2){
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
	 * @param state1
	 *            the state1 to set
	 */
	public void setState1(String state1) {
		this.state1 = state1;
	}

	/**
	 * @return the state2
	 */
	public String getState2() {
		return state2;
	}

	/**
	 * @param state2
	 *            the state2 to set
	 */
	public void setState2(String state2) {
		this.state2 = state2;
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

	/**
	 * Adds a change to the current stateTransition
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
