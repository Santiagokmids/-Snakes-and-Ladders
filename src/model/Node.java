package model;

public class Node {
	
	private int row;
	private int col;
	private char snake;
	private char ladder;
	
	private Node next;
	private Node previous;
	private Node up;
	private Node down;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
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

	public char getLadder() {
		return ladder;
	}

	public void setLadder(char ladder) {
		this.ladder = ladder;
	}

	public String toString() {
		return "[("+row+","+col+")]";
	}
}
