package Core;
import java.awt.Color;
import java.awt.Graphics;
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
    private Color bgColor = defaultColor;
    private int row;
    private int col;
    
    /**
     * Default constructor. Empty 100x100 pixel tile with no bonus values. 
     */
    public GameTile(int posX, int posY, int row, int col)
    {
    	x = posX;
    	y = posY;
    	this.row = row;
    	this.col = col;
    	
    }
    
    public void setBGColor(Color color){
    	bgColor = color;
    }
    
    public int getRow(){
    	return row;
    }
    
    public int getCol(){
    	return col;
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
    public boolean isEmpty()
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
    
    /**
     * Draws the tile
     * @param g The graphics to use
     */
    public void draw(Graphics g){
    	
    	g.setColor(bgColor);
    	
    	g.fillRect(x, y, size, size);
    }
    
    /**
     * Used to select the tile - uses setBGColor with a set value for color
     */
    public void select(){
    	setBGColor(selectedColor);
    }
    
    /**
     * Used to de-select the tile - uses setBGColor with a set value for color
     */
    public void deSelect(){
    	setBGColor(defaultColor);
    }
    
    /**
     * gets the size of the tile (which is a square)
     * @return size The size of the tile
     */
    public static int getSize(){
    	return size;
    }
    
    /**
     * get the current background color for the tile
     * @return bgColor The current background color
     */
    public Color getBGColor(){
    	return bgColor;
    }
    
    /**
     * Determines whether the given xPos or Ypos are within the bounds of this tile
     * @param xPos The x coordinate in question
     * @param yPos The y coordinate in question
     * @return boolean if the x and y coordinates are within the tile's bounds
     */
    public boolean withinBounds(int xPos, int yPos)
    {
    	return (xPos >= x && xPos < x + size && yPos >= y && yPos < y + size);
    }
    
    /**
     * Gets the closest direction to the given target
     * @param target The GameTile that is used to get the direction
     * @return int The closest degree angle to get to the target
     */
    public int getDirectionTowardGameTile(GameTile target){
		int dx = target.getCol() - getCol();
		int dy = target.getRow() - getRow();

		int angle = (int) Math.toDegrees(Math.atan2(dy, dx));

		//adjust angle to make North = 0 degrees
		int compassAngle =  angle + 90;

		// wrap negative angles
		if (compassAngle < 0)
			compassAngle += 360;
		// round to nearest multiple of 45
		return (compassAngle / 45) * 45;
	}
	
    /**
     * Gets the GameTile that is in the direction
     * @param direction The direction to get the tile
     * @return GameTile The GameTile that was in the direction
     */
	public GameTile getGameTileInDirection(int direction){
		
        int adjustedDirection = (direction + 45 / 2) % 360;
        if (adjustedDirection < 0)
            adjustedDirection += 360;

        adjustedDirection = (adjustedDirection / 45) * 45;
        int dc = 0;
        int dr = 0;
        if (adjustedDirection == Unit.EAST)
            dc = 1;
        else if (adjustedDirection == Unit.SOUTHEAST)
        {
            dc = 1;
            dr = 1;
        }
        else if (adjustedDirection == Unit.SOUTH)
            dr = 1;
        else if (adjustedDirection == Unit.SOUTHWEST)
        {
            dc = -1;
            dr = 1;
        }
        else if (adjustedDirection == Unit.WEST)
            dc = -1;
        else if (adjustedDirection == Unit.NORTHWEST)
        {
            dc = -1;
            dr = -1;
        }
        else if (adjustedDirection == Unit.NORTH)
            dr = -1;
        else if (adjustedDirection == Unit.NORTHEAST)
        {
            dc = 1;
            dr = -1;
        }
        
        return TileGrid.getGridTileAt(getRow() + dr, getCol() + dc);
	}
	
	/**
	 * Get the X coordinate of the start of this tile
	 * @return x The x coordinate starting position
	 */
	public int getXPos(){
		return x;
	}
	
	/**
	 * Get the Y coordinate of the start of this tile
	 * @return y The y coordinate starting position
	 */
	public int getYPos(){
		return y;
	}
   
}
