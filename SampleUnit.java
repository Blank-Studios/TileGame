package Units;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.w3c.dom.css.Rect;

/**
 * Example of how updating and drawing should be done for units
 * @author Matt
 *
 */
public class SampleUnit extends Unit {
	
	Color sampleUnitColor = Color.red; //colors may not be used with some units - 
									//for this example unit, a rectangle is used; however,
									//if using an image, this may not be used
	
	public SampleUnit(int x, int y){
		super(x, y); //create unit an x and y location on the grid
		movePoints = 3; //set move its move points
		movePointsReset = movePoints; //make sure the reset value is the same
		attackRange = 4; //give it an attack range
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Color defaultColor = g.getColor();
		g2.setColor(sampleUnitColor);
		g2.fillRect(x, y, 25, 25);
		g.setColor(defaultColor);
	}

}
