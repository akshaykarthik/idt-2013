package edu.mhs.compsys.idt;

/**
 * A container class that contains a single change, it is made up of a bounds
 * object and a classification type.
 */
/**
 * @author akr
 * 
 */
public class Change {

	private Bounds bounds;
	private ClassificationType type;

	/**
	 * @param bounds
	 * @param type
	 */
	public Change(Bounds bounds, ClassificationType type) {
		this.bounds = bounds;
		this.type = type;
	}

	/**
	 * @return the bounds
	 */
	public Bounds getBounds() {
		return bounds;
	}

	/**
	 * @param bounds
	 *            the bounds to set
	 */
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	/**
	 * @return the type
	 */
	public ClassificationType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ClassificationType type) {
		this.type = type;
	}

	/*
	 * Returns a visual representation of the change.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[type=%s @ bounds=%s]", type, bounds);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		return new Change(bounds, type);
	}

	/**
	 * @param b
	 *            The other bounds
	 * @return Whether or not the changes are the same.
	 */
	public boolean equals(Change b) {
		return type.equals(b.getType()) && bounds.equals(b.getBounds());
	}

	/**
	 * Returns whether or not the two changes are the same.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		return equals((Change) arg0);
	}
}
