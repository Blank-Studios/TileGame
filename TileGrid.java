package Core;
import java.awt.Color;
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

	/*
	 * User interaction buttons
	 */
	private JButton move;
	private JButton attack;
	private JButton spells;
	private JButton inventory;
	private JButton close;
	private JButton info;

	private JFrame optionFrame; //JFrame for the option menu

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

		units[0][0] = new SampleUnit(0,0);
	}
	
	/**
	 * Sets up the TileGrid
	 */
	private void setUp(){
			highlightedGameTiles = new ArrayList<GameTile>();
		
		  	move = new JButton("Move");
	        attack = new JButton("Attack");
	        spells = new JButton("Spells");
	        inventory = new JButton("Inventory");
	        info = new JButton("Info");
	        close = new JButton("Close");
	        
	        move.addActionListener(this);
	        attack.addActionListener(this);
	        spells.addActionListener(this);
	        inventory.addActionListener(this);
	        info.addActionListener(this);
	        close.addActionListener(this);
	        
	        optionFrame = new JFrame("Option Menu");
	        optionFrame.add(move);
	        optionFrame.getContentPane().setPreferredSize(move.getPreferredSize());
	        optionFrame.pack();
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
		if(e.getSource() == move && selectedUnit.canMove() && !selectedUnit.isMoving()){
			moving = true;
			highlightWalkablePath(selectedUnit, selectedTile);
		}
		
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
		int mouseY = e.getY() - 32; //Subtract the height of the title 'banner' of the JFrame from the mouse click from mouse's Y coordinate
		int mouseX = e.getX(); //mouses X coordinate
		
		GameTile clicked = getMouseClickedTile(mouseX, mouseY);
		
		if(selectedTile != null && clicked == selectedTile)
		{
			clear();
		}
		else if(!(attacking || moving) || (moving && !isAMove(clicked)))
		{	
			clear();
			
			selectedTile = clicked;
			selectedTile.select();
			
			selectedUnit = getUnit(selectedTile);
			
			if(selectedUnit != null)
				showUnitOptions(selectedUnit);
		}
		else if(moving && isAMove(clicked)){
			move(clicked);
			clear();
		}
		else if(attacking){
			
		}
	
	}
	
	/**
	 * Initiates a unit's movement
	 * @param tile The tile the unit is to move to
	 */
	public void move(GameTile tile){
		//selectedUnit.setIsMoving(true);
		selectedUnit.setMovePath(createMovePath(selectedTile, tile));
		units[selectedTile.getRow()][selectedTile.getCol()] = null;
		units[tile.getRow()][tile.getCol()] = selectedUnit;
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
		return highlightedGameTiles.contains(tile);
	}
	
	/**
	 * Clears game data for refresh of varaibles
	 * 
	 */
	public void clear(){
		if(selectedTile != null)
			selectedTile.deSelect();
		selectedTile = null;
		selectedUnit = null;
		moving = false;
		attacking = false;
		
		optionFrame.setVisible(false);
		
		for(int i = 0; i < highlightedGameTiles.size(); i++)
			highlightedGameTiles.get(i).deSelect();
		
		highlightedGameTiles.clear();
	}
	
	/**
	 * Shows the options that are available for the given unit
	 * @param unit The unit
	 */
	public void showUnitOptions(Unit unit){
		optionFrame.setVisible(true);
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
		
		System.out.println("X: " + x + " Y: " + y);

		for (int yChange = -movePoints; yChange <= movePoints; yChange++){

			for (int xChange = -movePoints; xChange <= movePoints; xChange++){
				
				int row = y + yChange;
				int col = x + xChange;
				
				if(withinGrid(row, col) && !(col == x && row == y)){
					map[row][col].setBGColor(getBGColorForTile(map[row][col], unit));
					highlightedGameTiles.add(map[row][col]);
				}
			}
		}
	}
	
	/**
	 * Gets the Background color a tile should be when a user has a unit selected
	 * @param tile The Tile to get a background color
	 * @param unit The Unit itself
	 * @return Color The color that the tile should be
	 */
	public Color getBGColorForTile(GameTile tile, Unit unit){
		if(unit.validMove(tile)){
			return moveBGColor;
		}
		else if(unit.isAlly(unit.getOwner())){
			return allyBGColor;
		}
		return enemyBGColor;
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
	 * Gets the GameTile at the given row and column
	 * @param row The row to get
	 * @param col The column to get
	 * @return GameTile The GameTile that is at the given row and column location
	 */
	public static GameTile getGridTileAt(int row, int col){
		return map[row][col];
	}

}
