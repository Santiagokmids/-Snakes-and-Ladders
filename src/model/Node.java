package model;

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
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
		position = 0;
		snake = ' ';
		ladder = 0;
		setFirst(null);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}
	
	public char getSnake() {
		return snake;
	}

	public void setSnake(char snake) {
		this.snake = snake;
	}

	public int getLadder() {
		return ladder;
	}

	public void setLadder(int ladder) {
		this.ladder = ladder;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String toString() {
		
		String message = "";
		
		if(snake != ' ') {
			message = "["+position+snake+"]";
		}else if(ladder != 0) {
			message = "["+position+ladder+"]";
		}else if(first != null){
			message = "["+position+getSymbols(first)+"]";
		}else {
			message = "["+position+"]";
		}
		return message;
	}
	
	public String toString2() {
		
		String message = "";
		
		if(snake != ' ') {
			message = "["+snake+"]";
		}else if(ladder != 0) {
			message = "["+ladder+"]";
		}else if(first != null){
			message = "["+getSymbols(first)+"]";
		}else {
			message = "[  ]";
		}
		
		return message;
	}

	public Player getFirst() {
		return first;
	}
	
	private String getSymbols(Player player) {

		if(player != null) {
			symbols += player.getSymbol();
			getSymbols(player.getNext());
		}
		return symbols;
	}

	public void setFirst(Player first) {
		this.first = first;
	}

}
