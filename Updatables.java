package Interface;

import java.awt.Graphics;

/**
 * Any object that should be updated should implement this
 * Note: This is in a package called Interface
 * @author Matt
 *
 */
public interface Updatable {

	/**
	 * This update is run several times in a second
	 */
	public void update();

	/**
	 * This update runs once every second
	 */
	public void fixedUpdate();

	/**
	 * draws the object on screen. Leave this method blank if the object should not be drawn
	 * @param g The graphics to drawn on
	 */
	public void draw(Graphics g);

}
