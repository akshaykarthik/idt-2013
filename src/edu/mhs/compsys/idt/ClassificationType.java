package edu.mhs.compsys.idt;

/**
 * Classifies all changes. Each set of changes between two states comprises of
 * one or many of these. These are all of the possible changes between two
 * states. This combined with the <code>Bounds</code> class should give enough
 * information about each change.
 */
public enum ClassificationType {
	/**
	 * Identify the window uniquely (against other windows). Quantify location
	 * and rectangular bounds of window. Classify as ‘window open’
	 */
	WINDOW_OPEN("window", "window open"),

	/**
	 * Identify the window uniquely (against other windows). Quantify location
	 * and rectangular bounds of window. Classify as ‘window close’
	 */
	WINDOW_CLOSE("window", "window close"),

	/**
	 * Identify which unique window has moved/resized. Quantify location and
	 * rectangular bounds of window. Classify as ‘window move’
	 */
	WINDOW_MOVE("window", "window move"),

	/**
	 * Identify which unique window has moved/resized. Quantify location and
	 * rectangular bounds of window. Classify as ‘window resize’
	 */
	WINDOW_RESIZE("window", "window resize"),

	/**
	 * Identify in which unique window the menu opened. Quantify location and
	 * rectangular bounds of menu selected. Quantify location and rectangular
	 * bounds of menu area. Classify as ‘window menu open’.
	 */
	WINDOW_MENU_OPEN("menu", "window menu open"),

	/**
	 * Identify in which unique window the menu opened. Quantify location and
	 * rectangular bounds of menu selected. Quantify location and rectangular
	 * bounds of menu area. Classify as ‘window menu close’.
	 */
	WINDOW_MENU_CLOSE("menu", "window menu close"),

	/**
	 * Identify in which unique window containing menu. Identify in which unique
	 * menu the item was selected. Quantify location and rectangular bounds of
	 * menu item selected. Classify as ‘window menu item selected’.
	 */
	WINDOW_MENU_ITEM_SELECTED("menu", "window menu item selected"),

	/**
	 * Identify which unique window this update occurs in. Quantify location and
	 * rectangular bounds of application update. Classify as ‘window application
	 * area update’.
	 */
	WINDOW_APPLICATION_AREA_UPDATE("application",
			"window application area update"),

	/**
	 * Identify which unique window this click occurs in. Quantify location and
	 * rectangular bounds of item clicked. Classify as ‘window application area
	 * update’
	 */
	WINDOW_TITLE_BAR_CLICK("title", "window application area update"),

	/**
	 * Identify which unique window this change occurs in. Quantify location and
	 * rectangular bounds of area changed. Classify as ‘window application area
	 * update’
	 */
	WINDOW_TITLE_CHANGE("title", "window application area update"),

	/**
	 * Identify that the change occurred outside of a window. Quantify location
	 * and rectangular bounds of area changed. Classify as ‘desktop icon change’
	 */
	DESKTOP_ICON_CHANGE("desktop", "desktop icon change"),

	/**
	 * Identify that an event occurred in the taskbar. Quantify location and
	 * rectangular bounds of the update. Classify as ‘taskbar update’.
	 */
	TASKBAR_UPDATE("taskbar", "taskbar update");

	private String description;

	private String type;

	private ClassificationType(String type, String description) {
		this.description = description;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		return String.format("[%s]:%s", type, description);
	}
}
