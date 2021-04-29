package model;

public class Player {
	
	private Player next;
	
	private char symbol;
	private int movement;
	private int position;
	
	public Player(char c) {
		this.setSymbol(c);
		movement = 0;
		position = 0;
	}

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	public Player getNext() {
		return next;
	}

	public void setNext(Player next) {
		this.next = next;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}



	public char getSymbol() {
		return symbol;
	}



	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}
