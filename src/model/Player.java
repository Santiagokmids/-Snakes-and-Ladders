package model;

public class Player {
	
	private String name;
	private long score;
	private String symbol;
	private int movement;
	
	private Player next;
	private Player previous;
	
	public Player(String name, long score, String symbol, int movement) {
		this.name = name;
		this.score = score;
		this.symbol = symbol;
		this.movement = movement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
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

	public Player getPrevious() {
		return previous;
	}

	public void setPrevious(Player previous) {
		this.previous = previous;
	}

	public String toString() {
		String message = "";
		
		message = "Nick: "+name+" Ficha: "+symbol+" Puntaje: "+score;
		
		return message;
	}
}
