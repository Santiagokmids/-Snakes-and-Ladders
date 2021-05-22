package model;

/**
 * This class contains methods, attributes,  and relations of a snakes and ladders.
 * @version 1
 * @author Santiago Trochez Velasco, https://github.com/Santiagokmids <br>
 * @author Luis Miguel Ossa Arias, https://github.com/Itsumohitoride <br>
 * Based on the linked matrix of https://github.com/seyerman
 */

public class Player {
	
	private Player next;
	
	private char symbol;
	private int movement;
	private int position;
	
	/** 
	 *<b>name:</b>  Player.
	 *Constructor of Player <br> 
	 *<b> post: </b> Start the class Player.
	 *@param c. Character of a player.
   */
	public Player(char c) {
		this.setSymbol(c);
		movement = 0;
		position = 0;
	}
	
	/** 
     *<b>name:</b>  getMovement.
     *Get the amount of movements of a player.<br> 
     *<b> post: </b> The amount of movements of a player.
     *@return int movement. This is the amount of movements of a player.
    */
	public int getMovement() {
		return movement;
	}
	
	/** 
     *<b>name:</b>  setMovement.
     *Change the amount of movements of a player.<br> 
     *<b> post: </b> New amount of movements of a player.
     *@param movement. This is the new amount of movements of a player.
    */
	public void setMovement(int movement) {
		this.movement = movement;
	}
	
	/** 
     *<b>name:</b>  getNext.
     *Get the next player to the player.<br> 
     *<b> post: </b> The next player to the player.
     *@return Player next. This is the next player to the player.
    */
	public Player getNext() {
		return next;
	}
	
	/** 
     *<b>name:</b>  setNext.
     *Change the next player to the player.<br> 
     *<b> post: </b> New next player to the player.
     *@param next. This is the new next player to the player.
    */
	public void setNext(Player next) {
		this.next = next;
	}
	
	/** 
     *<b>name:</b>  getPosition.
     *Get the position of a player.<br> 
     *<b> post: </b> The position of a player.
     *@return int position. This is the position of a player.
    */
	public int getPosition() {
		return position;
	}
	
	/** 
     *<b>name:</b>  setPosition.
     *Change the position of a player.<br> 
     *<b> post: </b> New position of a player.
     *@param position. This is the new position of a player.
    */
	public void setPosition(int position) {
		this.position = position;
	}

	/** 
     *<b>name:</b>  getSymbol.
     *Get the symbol of a player.<br> 
     *<b> post: </b> The symbol of a player.
     *@return char symbol. This is the symbol of a player.
    */
	public char getSymbol() {
		return symbol;
	}

	/** 
     *<b>name:</b>  setSymbol.
     *Change the symbol of a player.<br> 
     *<b> post: </b> New symbol of a player.
     *@param symbol. This is the new symbol of a player.
    */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}
