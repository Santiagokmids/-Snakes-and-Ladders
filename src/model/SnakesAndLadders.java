package model;

import java.util.Random;

public class SnakesAndLadders {
	
	private Node root;
	private Player firstPlayer;
	
	private int matrixRows;
	private int matrixCols;
	
	public SnakesAndLadders(int matrixRows, int matrixCols) {
		this.matrixRows = matrixRows;
		this.matrixCols = matrixCols;
		createNewMatrix();
	}
	
	private void createNewMatrix() {
		root = new Node(0,0);
		createNewRow(0,0,root);
		asingPosition(0,matrixRows-1,0);
	}
	
	private void createNewRow(int i, int j, Node currentRootRow) {
		
		createNewCol(i,j+1,currentRootRow,currentRootRow.getUp());
		
		if(i+1 < matrixRows) {
			
			Node downRootRow = new Node(i+1,j);
			downRootRow.setUp(currentRootRow);
			currentRootRow.setDown(downRootRow);
			createNewRow(i+1,j,downRootRow);
		}
	}
	
	private void createNewCol(int i, int j, Node previous, Node rowPrevious) {
		
		if(j < matrixCols) {
			
			Node current = new Node(i,j);
			current.setPrevious(previous);
			previous.setNext(current);
			
			if(rowPrevious != null) {
				
				rowPrevious = rowPrevious.getNext();
				current.setUp(rowPrevious);
				rowPrevious.setDown(current);
			}
			createNewCol(i, 1+j, current, rowPrevious);
		}
	}
	
	public void addPlayer(String name, long score, String symbol, int movement) {
		
		Player newPlayer = new Player(name,score,symbol,movement);
		
		if(firstPlayer == null) {
			firstPlayer = newPlayer;
		}else {
			addPlayer(firstPlayer,newPlayer);
		}
	}
	
	private void asingPosition(int position, int i,int j) {
		
		Node newNode = searchNode(i,j);
		
		if(newNode != null) {
			asingNext(newNode,position+1);
		}
	}
	
	private void asingNext(Node newNode, int position) {
		
		newNode.setPosition(position);
		
		if(newNode.getNext() != null) {
			asingNext(newNode.getNext(),position+1);
		}else if(newNode.getUp() != null){
			asingPrev(newNode.getUp(),position+1);
		}
	}
	
	private void asingPrev(Node newNode, int position) {
		
		newNode.setPosition(position);
		
		if(newNode.getPrevious() != null) {
			
			asingPrev(newNode.getPrevious(),position+1);
		}else  if(newNode.getUp() != null){
			asingNext(newNode.getUp(),position+1);
		}
	}
	
	private void addPlayer(Player current, Player newPlayer) {
		
		if(newPlayer.getScore() <= current.getScore()){
			if(current.getNext() == null) {
				current.setNext(newPlayer);
			}else {
				addPlayer(current.getNext(),newPlayer);
			}
		}else {
			if(current.getPrevious() == null) {
				current.setPrevious(newPlayer);
			}else {
				addPlayer(current.getPrevious(),newPlayer);
			}
		}
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int getMatrixRows() {
		return matrixRows;
	}

	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}

	public int getMatrixCols() {
		return matrixCols;
	}

	public void setMatrixCols(int matrixCols) {
		this.matrixCols = matrixCols;
	}
	
	public String toString() {
		String msg;
		msg = toStringRow(root);
		return msg;
	}

	private String toStringRow(Node firstRow) {
		String msg = "";
		if(firstRow!=null) {
			msg = toStringCol(firstRow) + "\n";
			msg += toStringRow(firstRow.getDown());
		}
		return msg;
	}

	private String toStringCol(Node current) {
		String msg = "";
		if(current!=null) {
			msg = current.toString();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}
	
	public String toStringScoreTable() {
		String message;
		
		if(firstPlayer != null) {
			message = toStringScores(firstPlayer);
		}else {
			message = "---No hay ningun puntaje todavia---";
		}
	
		return message;
	}
	
	private String toStringScores(Player player) {
		String message = "";
		
		if(player != null) {
			message = player.toString();
			
			if(player.getNext() != null) {
				message += "\n" + toStringScores(player.getNext());
			}
		}
		
		return message;
	}
	
	public String toString2() {
		String msg = "";
		
		return msg;
	}
	
	public void addSettingSnake(int snakes) {
		
		Random azarRow = new Random();
		Random azarCol = new Random();
		
		int selectedRow = (int)(azarRow.nextDouble() * getMatrixRows());
		int selectedCol = (int)(azarCol.nextDouble() * getMatrixCols());
		
		Node searched = searchNode(selectedRow, selectedCol);
		
		if(searched.getNext() != null && (searched.getPrevious() != null || searched.getUp() != null)){
			char letter = (char)('A'+searched.getCol());
			searched.setSnake(letter);
			char letterSnake = (char)('A'+searched.getRow());
			searched.setSnake(letterSnake);
			
		}else {
			addSettingSnake(snakes - 1);
		}
	}
	
	public void addSettingLadders(int ladders) {
		
		Random azarRow = new Random();
		Random azarCol = new Random();
		
		int selectedRow = (int)(azarRow.nextDouble() * getMatrixRows());
		int selectedCol = (int)(azarCol.nextDouble() * getMatrixCols());
		
		Node searched = searchNode(selectedRow, selectedCol);
		
		if(searched.getNext() != null && (searched.getPrevious() != null || searched.getUp() != null)){
			char letter = (char)('A'+searched.getCol());
			searched.setSnake(letter);
			char letterLadder = (char)('A'+searched.getRow());
			searched.setLadder(letterLadder);
			
		}else {
			addSettingLadders(ladders - 1);
		}
	}
	
	public void addSettingPlayers(int players, String[] symbols) {
		if(players == 1) {
			Player player = new Player(null, 0, symbols[players-1], 0);
		}else {
			addSettingPlayers(players - 1, symbols);
		}
	}
	
	public Node searchNode(int row, int col) {
		return searchNode(root, row, col);
	}
	
	private Node searchNode(Node current, int row, int col) {
		
		Node searched = null;
		
		if(current == null || (current.getRow() == row && current.getCol() == col)) {
			searched = current;
		}
		else {
			if(current.getRow() < row) {
				if(current.getDown() != null) {
					searched = searchNode(current.getDown(), row, col);
				}
			}else if(current.getCol() < col) {
				
				if(current.getNext() != null) {
					searched = searchNode(current.getNext(), row, col);
				}
			}
		}

		return searched;
	}
	
	public boolean splitString(String settings) {
		
		String setting[] = settings.split("");
		boolean verify = true;
		
		try {
			
			int row = Integer.parseInt(setting[0]);
			int col = Integer.parseInt(setting[2]);
			int snakes = Integer.parseInt(setting[4]);
			int ladders = Integer.parseInt(setting[6]);
			int players = Integer.parseInt(setting[8]);

			setMatrixRows(row);
			setMatrixCols(col);
			
			addSettingSnake(snakes);
			addSettingLadders(ladders);
			addSettingPlayers(players, setting);
			
		}catch(NumberFormatException nfe){
			System.out.println("Ingreso un valor INVALIDO");
			verify = false;
		}
		return verify;
	}
}
