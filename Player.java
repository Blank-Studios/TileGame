package Core;

import java.util.ArrayList;

/**
 * Defines a player
 * @author Matt
 *
 */
public class Player {

	int money; //The amount of money a player has
	static int id = 1; //An id given to new player objects. This is incremented every time a new player is made
	int playerID; //The player's id

	boolean isAI; //Determines if thie player is an AI

	private ArrayList<Integer> allies;

	/**
	 * Create a player with more starting options
	 * @param money The player's money
	 * @param isAI Is this player an AI?
	 */
	public Player(int money, boolean isAI){
		playerID = id;
		this.money = money;
		this.isAI = isAI;
		id++;
		
		setUp();
	}

	/**
	 * Create a new player without specifying: 
	 *  -If player is an AI (This constructor assumes that the player is *human*)
	 * @param money The amount of money for the player to initially have
	 */
	public Player(int money){
		playerID = id;
		this.money = money;
		isAI = false;
		id++;
		
		setUp();
	}

	/**
	 * Create a new player without specifying: 
	 *  -Player's money
	 *  @param isAI should thie player be an AI?
	 */
	public Player(boolean isAI){
		playerID = id;
		money = 0;
		this.isAI = isAI;
		id++;
		
		setUp();
	}

	/**
	 * Create a new player without specifying: 
	 *  -Player's money
	 *  -If player is an AI (This constructor assumes that the player is *human*)
	 */
	public Player(){
		playerID = id;
		money = 0;
		isAI = false;
		id++;
		
		setUp();
	}
	
	/**
	 * Sets up the player
	 */
	private void setUp(){
		allies = new ArrayList<Integer>();
	}

	/**
	 * Get the ID of the player
	 * @return id The int id of the player
	 */
	public int getID(){
		return playerID;
	}

	/**
	 * Get the player's money
	 * @return money The player's money
	 */
	public int getMoney(){
		return money;
	}

	/**
	 * Adds money to the player
	 * @param amount The amount to add
	 */
	public void addMoney(int amount){
		money += amount;
	}

	/**
	 * Subtracts money from the player
	 * @param amount The amount to take
	 */
	public void subtractMoney(int amount){
		money -= amount;
	}

	/**
	 * Add a player as an ally/friendly
	 * @param allyID Player to add
	 */
	public void addAlly(int allyID){
		if(!allies.contains(allyID))
			allies.add(allyID);
	}

	/**
	 * Removes a player from being an ally/frinedly
	 * @param allyID Player to remove
	 */
	public void removeAlly(int allyID){
		if(allies.contains(allyID))
			allies.remove(allyID);
	}

	/**
	 * Is the player an AI or human
	 * @return isAI Whether this player is an AI or human
	 */
	public boolean isAI(){
		return isAI;
	}

	/**
	 * Sets whether this player is an AI
	 * @param set Should this player be an AI?
	 */
	public void setIsAI(boolean set){
		isAI = set;
	}

	/**
	 * Determines if the given player is an ally
	 * @param playerID The player in question of being an ally
	 * @return boolean Is the player given an ally?
	 */
	public boolean isAlly(int playerID){
		return allies.contains(playerID);
	}

}
