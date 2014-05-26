package Units;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Core.GameTile;
import Core.Player;

/**
 * Example of how updating and drawing should be done for units
 * Example includes:
 *  -All possible super constructors
 *  -A way of using fixed update
 *  -A way of using draw
 *  
 * @author Matt
 *
 */
public class SampleUnit extends Unit {

	/*colors may not be used with some units - 
	 *for this example unit, a rectangle is used; however,
	 *if using an image, this may not be used
	 */
	Color sampleUnitColor1 = Color.red; 
	Color sampleUnitColor2 = Color.orange;
	Color sampleUnitColor3 = Color.pink; 

	/**
	 * Constructs a unit at the given Tile
	 * @param tile The tile to place the unit
	 * @param playerOwner The player that controls the unit initially
	 */
	public SampleUnit(GameTile tile, int playerOwner){
		super(tile, playerOwner); //create unit an x and y location on the grid
		attackRange = 4; //give it an attack range
		setAttackPoints(1); //Give this unit 1 attack per turn
		setMovePoints(3); //Give this unit 3 moves per turn
	}

	/**
	 * Constructs a unit at the given row and column
	 * @param row The row the unit is in
	 * @param column The column the unit is in
	 * @param playerOwner The player that controls the unit initially
	 */
	public SampleUnit(int row, int column, int playerOwner){
		super(row, column, playerOwner); //create unit an x and y location on the grid
		attackRange = 4; //give it an attack range
		setAttackPoints(1); //Give this unit 1 attack per turn
		setMovePoints(3); //Give this unit 3 moves per turn
	}

	/**
	 * Constructs a unit at the given Tile
	 * @param tile The tile to place the unit
	 * @param player The player that controls the unit initially
	 */
	public SampleUnit(GameTile tile, Player player){
		super(tile, player); //create unit an x and y location on the grid
		attackRange = 4; //give it an attack range
		setAttackPoints(1); //Give this unit 1 attack per turn
		setMovePoints(3); //Give this unit 3 moves per turn
	}

	/**
	 * Constructs a unit at the given row and column
	 * @param row The row the unit is in
	 * @param column The column the unit is in
	 * @param player The player that controls the unit initially
	 */
	public SampleUnit(int row, int column, Player player){
		super(row, column, player); //create unit an x and y location on the grid
		attackRange = 4; //give it an attack range
		setAttackPoints(1); //Give this unit 1 attack per turn
		setMovePoints(3); //Give this unit 3 moves per turn
	}

	/**
	 * This method should be override if something should be done once every second 
	 * Examples could include unit animation while being idle, lights flickering, etc.
	 * 
	 * This example alternates the color of the drawn rectangles once every second
	 * Remember: This is only an example and there may be some minor issues with how the this unit looks on screen - this is not meant to be
	 * a perfect unit, but instead a way of understanding fixedUpdate
	 */
	public void fixedUpdate(){
		if(!isMoving()){ //this should only run if the unit is not moving - when it is idle
			if(sampleUnitColor1 == Color.gray)
				sampleUnitColor1 = Color.red;
			else
				sampleUnitColor1 = Color.gray;

			if(sampleUnitColor2 ==  Color.cyan)
				sampleUnitColor2 = Color.orange;
			else
				sampleUnitColor2 = Color.cyan;

			if(sampleUnitColor3 == Color.magenta)
				sampleUnitColor3 = Color.pink;
			else
				sampleUnitColor3 = Color.magenta;
		}
		else{//unit is moving
			if(owner == 1){
				sampleUnitColor1 = Color.red;
			}

			if(owner == 2){
				sampleUnitColor2 = Color.orange;
			}

			if(owner == 3){
				sampleUnitColor3 = Color.pink;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Color defaultColor = g.getColor();
		if (owner == 1)
			g2.setColor(sampleUnitColor1);
		else if(owner == 2)
			g2.setColor(sampleUnitColor2);
		else if(owner == 3)
			g2.setColor(sampleUnitColor3);

		g2.fillRect(x, y, 25, 25);
		g.setColor(defaultColor);
	}

}
