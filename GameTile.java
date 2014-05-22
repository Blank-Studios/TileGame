package Core;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Units.MagicUnit;
import Units.Unit;


public class GameTile
{

    private int type = 0;
    private int x;
    private int y;
    private static int size = 50;
    private Unit unit = null;
    private Color defaultColor = Color.white;
    private Color selectedColor = Color.yellow;
    private boolean selected;
    
    /**
     * Default constructor. Empty 100x100 pixel tile with no bonus values. 
     */
    public GameTile(int posX, int posY)
    {
    	x = posX;
    	y = posY;
    	
    }
    
    /**
     * Constructor of an empty 100x100 pixel tile with specified bonus value.
     * @param type bonus value code
     */
    public GameTile(int type)
    {
        this.type = type;
    }
    
    /**
     * Constructor of a 100x100 pixel tile with no bonus value and a unit placed in it.
     * @param unit Unit placed in the tile
     */
    public GameTile(Unit unit)
    {
        this.unit = unit;
    }
    
    /**
     * Adds a unit to the tile (used to move Units)
     * @param unit to be added
     */
    public void addUnit(Unit unit)
    {
        this.unit = unit;
    }
    
    /**
     * Opens the menu to do anything for selected tile
     */
    public void getInfo()
    {
      
    }
    
    /**
     * Checks to see if the tile is empty.
     * @return whether or not the tile is empty
     */
    private boolean isEmpty()
    {
        return (unit == null);
    }
    
    /**
     * sets the bonus value.
     * @param type the type to set the bonus value to
     */
    public void setType(int type)
    {
        this.type = type;
    }
    
    public void draw(Graphics g){
    	if(selected)
    		g.setColor(selectedColor);
    	else
    		g.setColor(defaultColor);
    	
    	g.fillRect(x, y, size, size);
    }
    
    public static int getSize(){
    	return size;
    }
    
    public boolean withinBounds(int xPos, int yPos)
    {
    	return (xPos >= x && xPos < x + size && yPos >= y && yPos < y + size);
    }
    
    public void setSelected(boolean bool){
    	selected = bool;
    }

   
}
