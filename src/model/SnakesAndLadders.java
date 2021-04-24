package model;

import java.util.Random;

public class SnakesAndLadders {
	
	private Node root;
	private BestPlayers firstPlayer;
	
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
	
	public void addPlayer(String name, long score, String symbol) {
		
		BestPlayers newPlayer = new BestPlayers(name,score,symbol);
		
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
	
	private void addPlayer(BestPlayers current, BestPlayers newPlayer) {
		
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
	
	private String toStringScores(BestPlayers player) {
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
		
		if(searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && snakes != 0 && searched.getSnake() == ' ' && searched.getLadder() == 0){
			char letter = (char)('A'+searched.getCol());
			searched.setSnake(letter);
			char letterSnake = (char)('A'+searched.getRow());
			searched.setSnake(letterSnake);
			addSettingSnake(snakes - 1);
		}else {
			addSettingSnake(snakes);
		}
	}
	
	public void addSettingLadders(int ladders) {
		
		Random azarRow = new Random();
		Random azarCol = new Random();
		
		int selectedRow = (int)(azarRow.nextDouble() * matrixRows);
		int selectedCol = (int)(azarCol.nextDouble() * matrixCols);
		
		Node searched = searchNode(selectedRow, selectedCol);
		
		if(searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && ladders != 0 && searched.getSnake() == ' ' && searched.getLadder() == 0){
			int letter = (ladders);
			searched.setLadder(letter);
			int letterLadder = (ladders);
			searched.setLadder(letterLadder);
			addSettingLadders(ladders - 1);
		}else {
			addSettingSnake(ladders);
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
}
