package model;

public class Player {
	
	private Player next;
	
	private String symbol;
	private int movement;
	private int position;
	
	public Player(String symbol) {
		this.symbol = symbol;
		movement = 0;
		position = 0;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
}
