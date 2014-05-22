package Core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private TileGrid grid;

	private BasicStroke gridLineWidth = new BasicStroke(3);
	private Color gridLineColor = Color.blue;

	private boolean showingGridLines = true;

	public GamePanel(){
		grid = new TileGrid(10, 10);
		setPreferredSize(new Dimension(grid.getGridSize(), grid.getGridSize()));
	}

	public void paintComponent(Graphics g){
		grid.draw(g);

		if(showingGridLines)
			drawGridLines(g);

	}

	private void drawGridLines(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(gridLineWidth);
		Color defaultColor = g.getColor();
		g2.setColor(gridLineColor);
		for (int x = 0; x <= grid.getGridSize(); x += GameTile.getSize()){
			for (int y = 0; y <= grid.getGridSize(); y += GameTile.getSize()){
				g2.drawLine(x, y, grid.getGridSize(), y);
				g2.drawLine(x, y, x, grid.getGridSize());
			}
		}
		g.setColor(defaultColor);
	}
	
	public Dimension getSize(){
		return new Dimension(grid.getGridSize(), grid.getGridSize());
	}
	
	public void sendMouseEvent(MouseEvent e){
		grid.takeMouseEvent(e);
	}
	

}
