package model;

/**
 * This class contains methods, attributes,  and relations of a snakes and ladders.
 * @version 1
 * @author Santiago Trochez Velasco, https://github.com/Santiagokmids <br>
 * @author Luis Miguel Ossa Arias, https://github.com/Itsumohitoride <br>
 * Based on the linked matrix of https://github.com/seyerman
 */

public class Node {

	private int row;
	private int col;
	private int position;
	private char snake;
	private int ladder;
	private Player first;

	private Node next;
	private Node previous;
	private Node up;
	private Node down;

	private String symbols = "";
	
	/**
	 * <b>name:</b> Node. <br> 
	 * Create an object node. <br>
	 * <b>post:</b> An object node has been created. 
	 * @param row is the position in the rows. 
	 * @param col is the position in the col.
	 */
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
		position = 0;
		snake = ' ';
		ladder = 0;
		setFirst(null);
	}
	
	/**
	 * <b>name:</b> getRow. <br>
	 * Is the row position of the node. <br>
	 * <b>post:</b> Get the position of the row of the node. <br>
	 * @return <code>int</code> specifying row is the row position of the node.
	 */

	public int getRow() {
		return row;
	}
	
	/**
	 * <b>name:</b> getCol. <br>
	 * Is the column position of the node. <br>
	 * <b>post:</b> Get the position of the column of the node. <br>
	 * @return <code>int</code> specifying col is the column position of the node.
	 */

	public int getCol() {
		return col;
	}
	
	/**
	 * <b>name:</b> getNext. <br>
	 * Get the next node. <br>
	 * <b>post:</b> The next node has been obtained. 
	 * @return <code>Node</code> specifying next is the next node to the node.
	 */

	public Node getNext() {
		return next;
	}
	
	/**
	 * <b>name:</b> setNext. <br>
	 * Set the next node. <br>
	 * <b>post:</b> The next node has been changed. 
	 * @param next is the new next node.
	 */

	public void setNext(Node next) {
		this.next = next;
	}
	
	/**
	 * <b>name:</b> getPrevious. <br>
	 * Get the previous node. <br>
	 * <b>post:</b> The previous node has been obtained. 
	 * @return <code>Node</code> specifying previous is the previous node to the node.
	 */

	public Node getPrevious() {
		return previous;
	}
	
	/**
	 * <b>name:</b> setPrevious. <br>
	 * Set the previous node. <br>
	 * <b>post:</b> The previous node has been changed. 
	 * @param previous is the new previous node.
	 */

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	/**
	 * <b>name:</b> getUp. <br>
	 * Get the up node. <br>
	 * <b>post:</b> The up node has been obtained. 
	 * @return <code>Node</code> specifying up is the up node to the node.
	 */

	public Node getUp() {
		return up;
	}
	
	/**
	 * <b>name:</b> setUp. <br>
	 * Set the up node. <br>
	 * <b>post:</b> <br>
	 * @param up is the new up node.
	 */

	public void setUp(Node up) {
		this.up = up;
	}
	
	/**
	 * <b>name:</b> getDown. <br>
	 * Get the down node. <br>
	 * <b>post:</b> The down node has been obtained. <br>
	 * @return <code>Node</code> specifying down is the down node to the node.
	 */

	public Node getDown() {
		return down;
	}
	
	/**
	 * <b>name:</b> setDown. <br>
	 * Set the down node. <br>
	 * <b>post:</b> The down node has been changed. <br>
	 * @param down is the new down node.
	 */

	public void setDown(Node down) {
		this.down = down;
	}
	
	/**
	 * <b>name:</b> getSnake. <br>
	 * Get the snake in the node. <br>
	 * <b>post:</b> The snake in the node has been obtained. <br> 
	 * @return <code>char</code> specifying snake is the snake in the node.
	 */

	public char getSnake() {
		return snake;
	}
	
	/**
	 * <b>name:</b> setSnake. <br>
	 * Set the snake in the node.
	 * @param snake is the new snake in the node.
	 */

	public void setSnake(char snake) {
		this.snake = snake;
	}
	
	/**
	 * <b>name:</b> getLadder. <br>
	 * Get the ladder in the node. <br>
	 * <b>post:</b> The ladder in the node has been obtained. <br>
	 * @return <code>int</code> specifying ladder is the ladder in the node.
	 */

	public int getLadder() {
		return ladder;
	}
	
	/**
	 * <b>name:</b> setLadder. <br>
	 * Set the ladder in the node. <br>
	 * <b>post:</b> The ladder in the node has been changed. <br>
	 * @param ladder is the new ladder in the node.
	 */

	public void setLadder(int ladder) {
		this.ladder = ladder;
	}
	
	/**
	 * <b>name:</b> getPosition. <br>
	 * Get the position of the node. <br>
	 * <b>post:</b> The position of the node has been obtained. <br>
	 * @return <code>int</code> specifying position is the position of the node of the gid.
	 */

	public int getPosition() {
		return position;
	}
	
	/**
	 * <b>name:</b> setPosition. <br>
	 * Set the position of the node.
	 * <b>post:</b> The position of the node has been changed. <br>
	 * @param position is the new position to the node.
	 */

	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * <b>name:</b> toString. <br>
	 * Convert to string the node. <br> 
	 * <b>post:</b> The string of the node with the snake or ladder and the position has been obtained.
	 * @return <code>String</code> specifying message is the node with his specification.
	 */

	public String toString() {

		String message = "";
		symbols = "";

		if(snake != ' ') {

			if(snake != ' ' && first != null) {
				message = "["+position+snake+getSymbols(first)+"]";
			}else {
				message = "["+position+snake+"]";
			}

		}else if(ladder != 0) {

			if(ladder != 0 && first != null) {
				message = "["+position+ladder+getSymbols(first)+"]";
			}else {
				message = "["+position+ladder+"]";
			}

		}else  if(first != null){
			message = "["+position+getSymbols(first)+"]";
		}else {
			message = "["+position+"]";
		}

		return message;
	}
	
	/**
	 * <b>name:</b> toString2. <br>
	 * Convert to string the node. <br> 
	 * <b>post:</b> The string of the node with the snake or ladder has been obtained.
	 * @return <code>String</code> specifying message is the node with his specification.
	 */

	public String toString2() {

		String message = "";
		symbols = "";

		if(snake != ' ') {

			if(snake != ' ' && first != null) {
				message = "["+snake+getSymbols(first)+"]";
				
			}else {
				message = "["+snake+"]";
			}

		}else if(ladder != 0) {

			if(ladder != 0 && first != null) {
				message = "["+ladder+getSymbols(first)+"]";
			}else {
				message = "["+ladder+"]";
			}

		}else  if(first != null){
			message = "["+getSymbols(first)+"]";
		}else {
			message = "[ ]";
		}

		return message;
	}
	
	/**
	 * <b>name:</b> getFirst. <br>
	 * Get the first player in the node. <br>
	 * <b>post:</b> The first player of the node has been added. <br>
	 * @return <code>Player</code> specifying first is the first player in the node.
	 */

	public Player getFirst() {
		return first;
	}
	
	/**
	 * <b>name:</b> getSymbols. <br>
	 * Get the symbol of the players. <br>
	 * <b>post:</b> The symbols of the players has been obtained. <br>
	 * @param player is the player first player.
	 * @return <code>String</code> specifying symbols are the symbols of the players in the node.
	 */

	private String getSymbols(Player player) {

		if(player != null) {
			symbols += player.getSymbol();
			getSymbols(player.getNext());
		}
		return symbols;
	}
	
	/**
	 * <b>name:</b> setFirst. <br>
	 * Set the first player in the node. <br>
	 * <b>post:</b> the first player of the node has been added. <br>
	 * @param first is the new first player in the node.
	 */

	public void setFirst(Player first) {
		this.first = first;
	}
}
