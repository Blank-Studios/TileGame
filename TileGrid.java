package Core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import Interface.Updatable;
import Units.SampleUnit;
import Units.Unit;


public class TileGrid implements ActionListener
{

	private int row; //number of rows
	private int col; //number of columns

	ArrayList<GameTile> highlightedGameTiles;

	static GameTile[][] map; //two-dimensional array containing GameTiles
	Updatable[][] units; //two-dimensional array containing all of the updatable objects

	private GameTile selectedTile; //tracks the selected GameTile

	public boolean attacking = false; //is the player attacking
	public boolean moving = false; //is the player moving

	private Unit selectedUnit; //tracks the selected Unit

	private Color enemyBGColor = Color.RED; //The color for the BG of a nearby enemy
	private Color allyBGColor = Color.BLUE; //The color for the BG of a nearby ally
	private Color moveBGColor = Color.GREEN; //The color for the BG of a nearby valid move to GameTile

	private int currentPlayerTurn; //The current turn
	private ArrayList<Player> players; //The players of the game

	private boolean processMouseEvents = true; //Should the grid should process mouse events

	private UnitOptionHud unitOptions;

	/**
	 * Create a grid with the given amount of rows and columns
	 * @param row The number of rows for the Grid to have
	 * @param col The number of columns for the Grid to have
	 */
	public TileGrid(int row, int col)
	{
		setUp(); //Set up the grid
		/*
		 * set global variables to constructor parameters
		 */
		this.row = row; 
		this.col = col;

		/*
		 * Get ready to create game tiles
		 */
		int x = 0;
		int y = 0;

		//Create a GameTile two-dimensional array tracking all of the tiles in the grid and updatables
		map = new GameTile[row][col];
		units = new Updatable[row][col];

		//Loop through and create new GameTiles at every location in the GameTile Two-Dimensional array
		for (int r = 0; r < row; r++)
		{
			for (int c = 0; c < col; c++)
			{
				map[r][c] = new GameTile(x, y, r, c);
				x += GameTile.getSize();
			}

			x = 0;
			y += GameTile.getSize();
		}

		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		units[0][0] = new SampleUnit(map[0][0], 1);
		units[1][1] = new SampleUnit(map[1][1], 1);

		units[2][2] = new SampleUnit(map[2][2], 2);
		units[3][3] = new SampleUnit(map[3][3], 3);
		players.get(0).addAlly(players.get(1).getID());


		startNextPlayerTurn();
	}

	/**
	 * Starts the next player's turn
	 */
	public void startNextPlayerTurn(){
		Player player = getNextPlayer();

		ArrayList<Unit> resetUnits = getUnits(player);
		for(Unit u : resetUnits)
			u.reset();

		if(player.isAI){
			processMouseEvents = false; //Human player's should not be able to move unless it is their turn
			runAI(player);
		}
		else
			processMouseEvents = true;
	}

	/**
	 * Runs the Player AI
	 * @param player The player AI to run
	 */
	public void runAI(Player player){
		//TODO
		ArrayList<Unit> aiUnits = getUnits(player); //retrieves the units for the player
		/**
		 * This is a key method for playing this game and MUST be implemented
		 */
	}

	/**
	 * Get the units for a given player
	 * @param player The player whose units are to be retrieved
	 * @return ArrayList The collection of all the units under the player's control
	 */
	public ArrayList<Unit> getUnits(Player player){
		ArrayList<Unit> playerUnits = new ArrayList<Unit>();

		for(int i = 0; i < units.length; i++){
			for(int j = 0; j < units[i].length; j++){
				if(units[i][j] != null && ((Unit)units[i][j]).getOwner() == player.getID())
					playerUnits.add((Unit)units[i][j]);
			}

		}

		return playerUnits;
	}

	/**
	 * Get the next player's turn
	 * @return Player The next player to play a turn
	 */
	public Player getNextPlayer(){
		currentPlayerTurn++;

		if(currentPlayerTurn >= players.size() || currentPlayerTurn < 0)
			currentPlayerTurn = 0;

		return players.get(currentPlayerTurn);
	}

	/**
	 * Determines if a given player is alive
	 * @param player The Player in question
	 * @return boolean Does the player have any units on the map?
	 */
	public boolean isAlive(Player player){
		for(int i = 0; i < units.length; i++){
			for(int j = 0; j < units[i].length; j++){
				if(((Unit)(units[i][j])).getOwner() == player.getID())
					return true;
			}
		}

		return false;
	}

	/**
	 * Sets up the TileGrid
	 */
	private void setUp(){
		highlightedGameTiles = new ArrayList<GameTile>();
		players = new ArrayList<Player>();
		unitOptions = new UnitOptionHud(this);
		currentPlayerTurn = -1;
	}

	/**
	 * Get the number of rows in the grid
	 * @return row The number of rows in the grid
	 */
	public int getNumberOfRows(){
		return row;
	}

	/**
	 * Get the number of columns in the grid
	 * @return col The number of columns in the grid
	 */
	public int getNumberOfCols(){
		return col;
	}

	/**
	 * Call this method to conduct a battle, with atk vs def.
	 * @param atk - offensive Unit
	 * @param def - defensive Unit
	 */
	public static void battle(Unit atk, Unit def)
	{
		int[] aStat = atk.getAtts();
		int[] dStat = def.getAtts();
		if (atk.getAtkAtt() == 1)
		{
			if (aStat[1] > dStat[2])
			{
				dStat[0] = 0; //atk.getWep().getDmg() - dStat[0];
			}
		}
		if (atk.getAtkAtt() == 3)
		{
			if (aStat[3] > dStat[4])
			{

			}
		}

	}

	/**
	 * Updates the grid
	 */
	public void update(){

		for(int i = 0; i < units.length; i++){
			for(int j = 0; j < units[i].length; j++){
				if(units[i][j] != null)
					units[i][j].update();
			}
		}
	}

	/**
	 * Update the grid in a fixed one second interval
	 */
	public void fixedUpdate(){
		for(int i = 0; i < units.length; i++){
			for(int j = 0; j < units[i].length; j++){
				if(units[i][j] != null)
					units[i][j].fixedUpdate();
			}
		}
	}

	/**
	 * The width of the grid
	 * @return int The size of a game tile * the number of columns in the grid
	 */
	public int getGridWidth(){
		return GameTile.getSize() * getNumberOfCols();
	}

	/**
	 * The height of the grid
	 * @return int The size of a game tile * the number of rows in the grid
	 */
	public int getGridHeight(){
		return GameTile.getSize() * getNumberOfRows();
	}

	/**
	 * draws the graphics of the grid's units and tiles
	 * @param g The graphics to use when drawing
	 */
	public void draw(Graphics g){

		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++)
			{
				if(map[i][j] != null)
					map[i][j].draw(g);
			}
		}

		for(int i = 0; i < units.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(units[i][j] != null)
					units[i][j].draw(g);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {


	}

	/**
	 * Get a Tile from the grid from a user's mouse click
	 * @param x The X coordinate of the mouse click
	 * @param y The Y coordinate of the mouse click
	 * @return GameTile The tile that intersects the x and y coordinates of the mouse
	 */
	public GameTile getMouseClickedTile(int x, int y){
		for(int r = 0; r < getNumberOfRows(); r++){
			for(int c = 0; c < getNumberOfCols(); c++){
				if(map[r][c].withinBounds(x, y)){
					return map[r][c];
				}
			}
		}

		return null;
	}

	/**
	 * Take a mouse event from a mouse listener from a different class
	 * @param e The mouse event to take
	 */
	public void takeMouseEvent(MouseEvent e){

		if(processMouseEvents){
			int mouseY = e.getY() - 32; //Subtract the height of the title 'banner' of the JFrame from the mouse click from mouse's Y coordinate
			int mouseX = e.getX(); //mouses X coordinate

			GameTile clicked = getMouseClickedTile(mouseX, mouseY);

			if(selectedTile != null && clicked == selectedTile)
			{
				clear();
			}
			else if(!(attacking || moving) || ((moving || attacking) && !isAMove(clicked)))
			{	
				clear();

				selectedTile = clicked;
				selectedTile.select();

				selectedUnit = getUnit(selectedTile);

				if(selectedUnit != null)
					showUnitOptions(selectedUnit);
			}
			else if(moving && isAMove(clicked)){
				moveUnitTo(clicked);
				clear();
			}
			else if(attacking && isAMove(clicked)){
				battle(selectedUnit, getUnit(clicked));
				clear();
			}
		}
	}

	/**
	 * Initiates a unit's movement
	 * @param tile The tile the unit is to move to
	 */
	public void moveUnitTo(GameTile tile){
		//selectedUnit.setIsMoving(true);
		selectedUnit.setMovePath(createMovePath(selectedTile, tile));
		units[selectedTile.getRow()][selectedTile.getCol()] = null;
		units[tile.getRow()][tile.getCol()] = selectedUnit;
	}

	/**
	 * Initiates unit move options
	 */
	public void move(){
		moving = true;
		highlightWalkablePath(selectedUnit, selectedTile);
	}

	/**
	 * Creates a move path for the unit to get to a tile
	 * @param from The tile the unit is in currently
	 * @param to The tile the unit will be when at the destination
	 * @return ArrayList<GameTile> An array list of GameTiles in the order the unit is to move between them
	 */
	public ArrayList<GameTile> createMovePath(GameTile from, GameTile to){
		ArrayList<GameTile> path = new ArrayList<GameTile>();

		int startR = from.getRow();
		int startC = from.getCol();
		int endR = to.getRow();
		int endC = to.getCol();

		while(!(startR == endR && startC == endC)){

			startR = from.getGameTileInDirection(from.getDirectionTowardGameTile(to)).getRow();
			startC = from.getGameTileInDirection(from.getDirectionTowardGameTile(to)).getCol();

			from = getGridTileAt(startR, startC);

			if(withinGrid(from.getRow(), from.getCol()))
				path.add(map[startR][startC]);
		}

		return path;
	}

	/**
	 * Verifies that the user has made a move
	 * @param tile The GameTile that is in question of being a move
	 * @return
	 */
	public boolean isAMove(GameTile tile){
		if(moving && tile.getBGColor() == moveBGColor)
			return true;
		if(attacking && tile.getBGColor() == enemyBGColor)
			return true;

		return false;
	}

	/**
	 * Clears game data for refresh of variables
	 * 
	 */
	public void clear(){
		if(selectedTile != null)
			selectedTile.deSelect();
		selectedTile = null;
		selectedUnit = null;
		moving = false;
		attacking = false;

		unitOptions.setVisible(false);

		for(int i = 0; i < highlightedGameTiles.size(); i++)
			highlightedGameTiles.get(i).deSelect();

		highlightedGameTiles.clear();
	}

	/**
	 * Shows the options that are available for the given unit
	 * @param unit The unit
	 */
	public void showUnitOptions(Unit unit){
		System.out.println("Moving: " + (moving));

		unitOptions.setOptions(unit);
		unitOptions.setVisible(true);
	}

	/**
	 * Highlights the grid's GameTile's that are within in the unit's move range; The tiles that are within range are added to highlightedGameTiles
	 * @param unit The unit that is selected
	 * @param gameTile The gameTile that is selected
	 */
	public void highlightWalkablePath(Unit unit, GameTile gameTile){

		int movePoints = unit.getMovePoints();

		int x = gameTile.getCol();
		int y = gameTile.getRow();

		for (int yChange = -movePoints; yChange <= movePoints; yChange++){

			for (int xChange = -movePoints; xChange <= movePoints; xChange++){

				int row = y + yChange;
				int col = x + xChange;

				if(withinGrid(row, col) && !(col == x && row == y) && !hasUnit(map[row][col]) && unit.validMove(map[row][col])){
					map[row][col].setBGColor(moveBGColor);
					highlightedGameTiles.add(map[row][col]);
				}
			}
		}
	}

	public void highlightTargets(Unit unit, GameTile tile){
		int range = unit.getAttackRange();

		int x = tile.getCol();
		int y = tile.getRow();

		for (int yChange = -range; yChange <= range; yChange++){

			for (int xChange = -range; xChange <= range; xChange++){

				int row = y + yChange;
				int col = x + xChange;

				if(withinGrid(row, col) && !(col == x && row == y) && hasUnit(map[row][col])){
					if(!getPlayer(unit).isAlly(((Unit)units[row][col]).getOwner()))
						map[row][col].setBGColor(enemyBGColor);
					else
						map[row][col].setBGColor(allyBGColor);
					highlightedGameTiles.add(map[row][col]);
				}
			}
		}

	}

	/**
	 * Get A player that controls the specified unit
	 * @param unit The unit to use
	 * @return Player The player who owns the unit
	 */
	public Player getPlayer(Unit unit){
		for(int i = 0; i < players.size(); i++){
			if(unit.getOwner() == players.get(i).getID())
				return players.get(i);
		}

		return null; //This should never be reached.
	}

	/**
	 * Validates whether the given r and c values are within the map[][] indexes
	 * @param r The row to check
	 * @param c The column to check
	 * @return boolean whether the r and c values are both within the indexes of map[][]
	 */
	public boolean withinGrid(int r, int c){
		return (r >= 0 && r < getNumberOfRows()) && (c >= 0 && c < getNumberOfCols());
	}

	/**
	 * Determine whether the user has selected a unit
	 * @return boolean does the currently selected GameTile have a unit
	 */
	public boolean hasUnit(GameTile tile){
		if(tile != null)
			return (units[tile.getRow()][tile.getCol()] != null);

		return false;
	}

	/**
	 * Gets the unit associated with the given tile - if there is one
	 * @param tile The tile in question
	 * @return Unit The unit that is associated with this tile; however, it may return null if there is no unit associated with the tile
	 */
	public Unit getUnit(GameTile tile){
		if (hasUnit(tile))
			return (Unit) units[tile.getRow()][tile.getCol()];

		return null;
	}

	/**
	 * Initiate attack options
	 */
	public void attack(){
		attacking = true;
		highlightTargets(selectedUnit, selectedTile);
	}

	/**
	 * Ends the current turn and starts the next
	 */
	public void endTurn(){
		clear();
		startNextPlayerTurn();
	}

	/**
	 * Get the current player
	 * @return Player The player who is currently taking their turn
	 */
	public Player getCurrentPlayer(){
		return players.get(currentPlayerTurn);
	}

	/**
	 * Gets the GameTile at the given row and column
	 * @param row The row to get
	 * @param col The column to get
	 * @return GameTile The GameTile that is at the given row and column location
	 */
	public static GameTile getGridTileAt(int row, int col){
		return map[row][col];
	}

}
