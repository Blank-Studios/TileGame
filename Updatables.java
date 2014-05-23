package Interface;

import java.awt.Graphics;

/**
 * Any object that should be drawn/update should implement this
 * Note: This is in a package called Interface
 * @author Matt
 *
 */
public interface Updatable {

	public void update();
	public void draw(Graphics g);
	
}
