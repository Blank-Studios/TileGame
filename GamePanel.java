package Core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * 
 * @author Matt
 *
 *GamePanel - Draws the grid and other objects
 *
 */
public class GamePanel extends JPanel{

	/**
	 * A Generated SerialVersion ID
	 */
	private static final long serialVersionUID = 1L;

	private TileGrid grid; //Grid of tiles

	private BasicStroke gridLineWidth = new BasicStroke(3); //Grid Line width
	private Color gridLineColor = Color.blue; //Color of Gridline

	private boolean showingGridLines = true;

	public GamePanel(){
		grid = new TileGrid(10, 10); //create grid of a certain size
		setPreferredSize(new Dimension(grid.getGridWidth(), grid.getGridHeight())); //sets this panel's preferred size
	}

	/**
	 * All the drawing takes place here
	 */
	public void paintComponent(Graphics g){
		
		grid.draw(g); //draw the grid and its objects

		if(showingGridLines)
			drawGridLines(g); //if should show gridlines, draw them

	}

	/**
	 * Draws the gridlines on the graphics passed
	 * @param g the graphics used to draw
	 */
	private void drawGridLines(Graphics g){
		Graphics2D g2 = (Graphics2D) g; //make it 2D graphics
		g2.setStroke(gridLineWidth); //draw the line at a certain width
		Color defaultColor = g.getColor(); //get the default color before changing to gridline color
		g2.setColor(gridLineColor); //draw the lines with the given color
		for (int x = 0; x <= grid.getGridWidth(); x += GameTile.getSize()){ //as long as x is less than or equal to the width of the panel
			for (int y = 0; y <= grid.getGridHeight(); y += GameTile.getSize()){ //as long as y is less than or equal to the height of the panel
				g2.drawLine(x, y, grid.getGridWidth(), y); //draw the line from the current x and y to the edge of the width panel at the same y
				g2.drawLine(x, y, x, grid.getGridHeight()); //draw the line from the current x and y to the edge of the height of the panel at the same x
			}
		}
		g.setColor(defaultColor); //set the color back to what it was before drawing the lines
	}
	
	/**
	 * get the dimension size of this panel
	 */
	public Dimension getSize(){
		return new Dimension(grid.getGridWidth(), grid.getGridHeight()); //have the size of the panel be equal to the size of the grid
	}
	
	/**
	 * Sends the MouseEvent to be used by the grid
	 * @param e The mouse event to use
	 */
	public void sendMouseEvent(MouseEvent e){
		grid.takeMouseEvent(e); //send the mouse even to the grid
	}
	
	public void update(){
		grid.update();
	}

}
