package edu.mhs.compsys.idt;

/**
 * A container class that contains a single change, it is made up of a bounds
 * object and a classification type.
 */
public class Change {

	private Bounds bounds;
	private ClassificationType type;

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

	/**
	 * @param bounds
	 * @param type
	 */
	public Change(Bounds bounds, ClassificationType type) {
		this.bounds = bounds;
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

}
