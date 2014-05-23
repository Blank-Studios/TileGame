package Units;

import java.awt.Graphics;
import java.util.ArrayList;

import Core.GameTile;
import Interface.Updatable;


@SuppressWarnings("unused")
public abstract class Unit implements Updatable
{
	/**
	 * Variables used for direction
	 */
	public static final int NORTH = 0;
	public static final int NORTHEAST = 45;
	public static final int EAST = 90;
	public static final int SOUTHEAST = 135;
	public static final int SOUTH = 180;
	public static final int SOUTHWEST = 225;
	public static final int WEST = 270;
	public static final int NORTHWEST = 315;
	
    private int[] attributes = new int[7];
    
    private int level;
    
    private int atkAtt;
    
    private boolean fly;
    
    private Weapons[] inventory = new Weapons[5];
    
    private Weapons equip;
    
    protected int x; //The unit's x location
    
    protected int y; //The unit's y location
    
    protected int movePoints; //Unit's move points
    
    protected int movePointsReset; //The unit's move points when reset
    
    protected int attackRange; //Unit's attack range
    
    protected int moveSpeed = 1; //Unit's move speed
    
    protected boolean enabled; //Is the unit enabled
    
    private ArrayList<GameTile> movePath; //The move path that the unit is taking to a destination - used with 'pathfinding'
    
    //The Unit's name
    private String name;
    
    protected int owner; //The unit's owner
    
    /**
     * Constructs a unit at the given x and y pixel location
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Unit(int x, int y){
    	this.x = x;
    	this.y = y;
    	setUp();
    }
    
    /**
     * Sets up the variables
     */
    public void setUp(){
    	movePath = new ArrayList<GameTile>();
    	
    }
    
    /**
     * Get the owner of the unit
     * @return owner The int representation of the owner of this unit
     */
    public int getOwner(){
    	return owner;
    }
    
    /**
     * Set the owner of the unit
     * @param newOwner The new owner
     */
    public void setOwner(int newOwner){
    	owner = newOwner;
    }
    
    public Weapons[] getInv()
    {
        return inventory;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public int[] getAtts()
    {
        return attributes;
    }
    
    public void setAtts(int[] atts)
    {
        attributes = atts;
    }
    
    public int getAtkAtt()
    {
        return atkAtt;
    }
    
    public Weapons getWep()
    {
        return equip;
    }
    
    /**
     * Get whether or not the unit is enabled
     * @return enabled is the unit enabled
     */
    public boolean isEnabled(){
    	return enabled;
    }
    
    /**
     * Set whether or not the unit is enabled
     * @param enabled Should the unit be enabled?
     */
    public void setEnabled(boolean enabled){
    	this.enabled = enabled;
    }
    
    /**
     * Determines whether the unit can move or not
     * @return boolean Is MovePoints for this unit greater than 0?
     */
    public boolean canMove(){
    	return movePoints > 0;
    }
    
    /**
     * Set the unit's name
     * @param name The name for this unit
     */
    public void setName(String name){
    	this.name = name;
    }
    
    /**
     * Get the unit's name
     * @return name The unit's name
     */
    public String getName(){
    	return name;
    }
    
    /**
     * Get the unit's move points
     * @return moveRange The move points of the unit
     */
    public int getMovePoints(){
    	return movePoints;
    }
    
    /**
     * Get the unit's attack range
     * @return attackRange The total range the unit can attack
     */
    public int getAttackRange(){
    	return attackRange;
    }
    
    /**
     * Get the unit's moveSpeed
     * @return moveSpeed the Speed of the unit
     */
    public int getMoveSpeed(){
    	return moveSpeed;
    }
    
    /**
     * Set the unit's move speed to the given parameter
     * @param speed The new move speed
     */
    public void setMoveSpeed(int speed){
    	moveSpeed = speed;
    }
    
    /**
     * Sets the move points of the unit to the given parameter
     * @param range The new move points the unit will have
     */
    public void setMovePoints(int movePoints){
    	this.movePoints = movePoints;
    }
    
    /**
     * Sets the attack range of the unit to the given parameter
     * @param range The new attack range
     */
    public void setAttackRange(int range){
    	attackRange = range;
    }
    
    /**
     * Checks to see if the unit can make a valid move
     * @param tile The tile to check
     * @return boolean Whether the tile given is empty
     */
    public boolean validMove(GameTile tile){
    	return true;
    }
    
    /**
     * Resets the unit's movePoints back to full
     */
    public void resetMovePoints(){
    	movePoints = movePointsReset;
    }
    
    /**
     * Checks to see if the owner is an ally
     * @param owner The player to check
     * @return boolean Whether the owner given is an ally
     */
    public boolean isAlly(int owner){
    	return this.owner == owner;
    }
    
    /**
     * Used by the GamePanel to update the various units
    */
    public void update(){
    	if(movePath.size() > 0){
    		move();
    	}
    }
    
    /**
     * Determines whether this unit is currently moving
     * @return boolean Is the unit's move path greater than 0? If it is, it must be moving
     */
    public boolean isMoving(){
    	return movePath.size() > 0;
    }
    
    /**
     * Get the x coordinate starting position for this unit
     * @return x The starting x coordinate for this unit
     */
    public int getXPos(){
    	return x;
    }
    
    /**
     * Get the y coordinate starting position for this unit
     * @return y The starting y coordinate for this unit
     */
    public int getYPos(){
    	return y;
    }
    
    /**
     * Set the x coordinate for this unit
     * @param xPos The new x Coordinate
     */
    public void setXPos(int xPos){
    	x = xPos;
    }
    
    /**
     * Set the y coordinate for this unit
     * @param yPos The new y coordinate
     */
    public void setYPos(int yPos){
    	y = yPos;
    }
    
    /**
     * Move the unit towards its destination
     */
    public void move(){

		int targetX = movePath.get(0).getXPos();
		int targetY = movePath.get(0).getYPos();

		if(getXPos() < targetX - moveSpeed){
			setXPos(getXPos() + moveSpeed);
		}else if(getXPos() > targetX + moveSpeed){
			setXPos(getXPos() - moveSpeed);
		}else{
			setXPos(targetX);
		}
		
		if(getYPos() < targetY - moveSpeed){
			setYPos(getYPos() + moveSpeed);
		}else if(getYPos() > targetY + moveSpeed){
			setYPos(getYPos() - moveSpeed);
		}else{
			setYPos(targetY);
		}
		
		if (targetX == getXPos() && targetY == getYPos()){
			movePath.remove(0);
			movePoints--;
		}
    }
    
    /**
     * Set the unit's move path
     * @param path The move path to take
     */
    public void setMovePath(ArrayList<GameTile> path){
    	for(int i = 0; i < path.size(); i++)
    		movePath.add(path.get(i));
    }
    
    /**
     * used by the GamePanel to draw the various units
     */
    public abstract void draw(Graphics g);
    
}
