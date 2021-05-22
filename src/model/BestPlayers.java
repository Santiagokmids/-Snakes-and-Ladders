package model;

/**
 * This class contains methods, attributes,  and relations of a snakes and ladders.
 * @version 1
 * @author Santiago Trochez Velasco, https://github.com/Santiagokmids <br>
 * @author Luis Miguel Ossa Arias, https://github.com/Itsumohitoride <br>
 * Based on the linked matrix of https://github.com/seyerman
 */

import java.io.Serializable;

public class BestPlayers implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int row;
	private int col;
	private int snakes;
	private int ladders;
	private int players;
	
	private long score;
	private char symbol;
	private String otherPlayers;
	
	private BestPlayers next;
	private BestPlayers previous;
	
	/** 
	 *Name: BestPlayers.
	 *Constructor of BestPlayers <br> 
	 *<b> post: </b> Start the class BestPlayers.
	 *@param name. Name of the winning player.
	 *@param row. Amount of rows of the matrix of the game that the player win.
	 *@param col. Amount of columns of the matrix of the game that the player win.
	 *@param snakes. Amount of snakes of the matrix of the game that the player win.
	 *@param ladders. Amount of ladders of the matrix of the game that the player win.
	 *@param players. Amount of players of the matrix of the game that the player win.
	 *@param score. Score of the winning player.
	 *@param symbol. Symbol that is a letter. This letter mean the symbol of the winning player.
	 *@param otherPlayers. String that represent the symbols of the others players in the game.
   */
	public BestPlayers(String name, int row, int col, int snakes, int ladders, int players, long score, char symbol, String otherPlayers) {
		this.name = name;
		this.row = row;
		this.col = col;
		this.snakes = snakes;
		this.ladders = ladders;
		this.players = players;
		this.score = score;
		this.symbol = symbol;
		this.otherPlayers = otherPlayers;
	}
	
	/** 
     *Name: getName.
     *Get the name of the winning player.<br> 
     *<b> post: </b> The name of the winning player.
     *@return String name. This is the name of the winning player.
    */
	public String getName() {
		return name;
	}
	
	/** 
     *Name: setName.
     *Change the name of the winning player.<br> 
     *<b> post: </b> New name of the winning player.
     *@param name. This is the new name of the winning player.
    */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 
     *Name: getScore.
     *Get the scores of the winning player.<br> 
     *<b> post: </b> The score of the winning player.
     *@return long score. This is the score of the winning player.
    */
	public long getScore() {
		return score;
	}
	
	/** 
     *Name: setScore.
     *Change the score of the winning player.<br> 
     *<b> post: </b> New score of the winning player.
     *@param score. This is the new score of the winning player.
    */
	public void setScore(long score) {
		this.score = score;
	}
	
	/** 
     *Name: getSymbol.
     *Get the symbol of the winning player.<br> 
     *<b> post: </b> The symbol of the winning player.
     *@return char symbol. This is the symbol of the winning player.
    */
	public char getSymbol() {
		return symbol;
	}
	
	/** 
     *Name: setSymbol.
     *Change the symbol of the winning player.<br> 
     *<b> post: </b> New symbol of the winning player.
     *@param symbol. This is the new symbol of the winning player.
    */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	/** 
     *Name: getNext.
     *Get the next player to the winning player.<br> 
     *<b> post: </b> The next player to the winning player.
     *@return BestPlayers next. This is the next player to the winning player.
    */
	public BestPlayers getNext() {
		return next;
	}
	
	/** 
     *Name: setNext.
     *Change the next player to the winning player.<br> 
     *<b> post: </b> New next player to the winning player.
     *@param next. This is the next player to the winning player.
    */
	public void setNext(BestPlayers next) {
		this.next = next;
	}
	
	/** 
     *Name: getPrevious.
     *Get the previous player to the winning player.<br> 
     *<b> post: </b> The previous player to the winning player.
     *@return BestPlayers previous. This is the previous player to the winning player.
    */
	public BestPlayers getPrevious() {
		return previous;
	}
	
	/** 
     *Name: setPrevious.
     *Change the previous player to the winning player.<br> 
     *<b> post: </b> New previous player to the winning player.
     *@param previous. This is the previous player to the winning player.
    */
	public void setPrevious(BestPlayers previous) {
		this.previous = previous;
	}
	
	/** 
     *Name: getRow.
     *Get the rows of the matrix in game of the player to the winning player.<br> 
     *<b> post: </b> The rows of the matrix in game of the player to the winning player.
     *@return int row. This are the rows of the matrix in game of the winning player.
    */
	public int getRow() {
		return row;
	}
	
	/** 
     *Name: getCol.
     *Get the columns of the matrix in game of the player to the winning player.<br> 
     *<b> post: </b> The columns of the matrix in game of the player to the winning player.
     *@return int col. This are the columns of the matrix in game of the winning player.
    */
	public int getCol() {
		return col;
	}
	
	/** 
     *Name: getSnakes.
     *Get the snakes of the matrix in game of the player to the winning player.<br> 
     *<b> post: </b> The snakes of the matrix in game of the player to the winning player.
     *@return int snakes. This are the snakes of the matrix in game of the winning player.
    */
	public int getSnakes() {
		return snakes;
	}
	
	/** 
     *Name: getLadders.
     *Get the ladders of the matrix in game of the player to the winning player.<br> 
     *<b> post: </b> The ladders of the matrix in game of the player to the winning player.
     *@return int ladders. This are the ladders of the matrix in game of the winning player.
    */
	public int getLadders() {
		return ladders;
	}

	/** 
     *Name: getPlayers.
     *Get the number of players in game of the player to the winning player.<br> 
     *<b> post: </b> The number of players in game of the player to the winning player.
     *@return int players. This is the number of players in game of the winning player.
    */
	public int getPlayers() {
		return players;
	}

	/** 
     *Name: getOtherPlayers.
     *Get the symbol of players in game of the player to the winning player.<br> 
     *<b> post: </b> The symbol of players in game of the player to the winning player.
     *@return String otherPlayers. This are the symbol of players in game of the winning player.
    */
	public String getOtherPlayers() {
		return otherPlayers;
	}
	
	/** 
     *Name: toString.
     *Show the best players of the game.<br> 
     *<b> post: </b> Information the best players of the game.
     *@return String message. This is the message where is the information the best players of the game.
   */
	public String toString() {
		String message = "";
		
		message = "Nick: "+name+" | Ficha: "+symbol+" | Puntaje: "+score+"\n";
		
		return message;
	}
}
