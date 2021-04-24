package model;

public class BestPlayers {
	
	private String name;
	private long score;
	private String symbol;
	
	private BestPlayers next;
	private BestPlayers previous;
	
	public BestPlayers(String name, long score, String symbol) {
		this.name = name;
		this.score = score;
		this.symbol = symbol;
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
	
	public BestPlayers getNext() {
		return next;
	}

	public void setNext(BestPlayers next) {
		this.next = next;
	}

	public BestPlayers getPrevious() {
		return previous;
	}

	public void setPrevious(BestPlayers previous) {
		this.previous = previous;
	}

	public String toString() {
		String message = "";
		
		message = "Nick: "+name+" Ficha: "+symbol+" Puntaje: "+score;
		
		return message;
	}
}
