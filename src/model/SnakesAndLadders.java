package model;

public class SnakesAndLadders {
	
	private Node root;
	
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
	
	public String toString2() {
		String msg = "";
		
		return msg;
	}
}
