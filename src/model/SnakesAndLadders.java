package model;

import java.util.Random;

public class SnakesAndLadders {

	private Node root;
	private BestPlayers firstPlayer;

	private int matrixRows;
	private int matrixCols;

	private static int verify = 0;
	private static int numberPlayer = 0;
	private static int numberPlayerVerify = 0;

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

	public void addPlayer(String name, int row, int col, int snakes, int ladders, int players, long score, String symbol, String otherPlayers) {

		BestPlayers newPlayer = new BestPlayers(name,row,col,snakes,ladders,players,score,symbol,otherPlayers);

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

	public void addSettingSnake(int snakes, int i) {

		int selectedRow = (int)Math.floor(Math.random()*matrixRows);
		int selectedCol = (int)Math.floor(Math.random()*matrixCols);

		Node searched = searchNode(selectedRow, selectedCol);

		if(searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && snakes > 0 && searched.getLadder() == 0 && searched.getSnake() == ' '){

			char letter = (char)('A'+i);
			searched.setSnake(letter);

			addSecondSnake(searched,selectedRow);

			addSettingSnake(snakes - 1, i+1);
		}else if(snakes > 0){
			addSettingSnake(snakes,i);
		}
	}

	public void addSecondSnake(Node ladder, int row) {

		int selectedRow = (int)Math.floor(Math.random()*(matrixRows-row));
		int selectedCol = (int)Math.floor(Math.random()*matrixCols);

		Node searched = searchNode(selectedRow, selectedCol);

		if(searched.getRow() != row && searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && searched.getSnake() == ' ' && searched.getLadder() == 0){

			searched.setSnake(ladder.getSnake());
		}else {
			addSecondSnake(ladder,row);
		}
	}

	public void addSettingLadders(int ladders) {

		int selectedRow = (int)Math.floor(Math.random()*matrixRows);
		int selectedCol = (int)Math.floor(Math.random()*matrixCols);

		Node searched = searchNode(selectedRow, selectedCol);

		if(searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && ladders != 0 && searched.getSnake() == ' ' && searched.getLadder() == 0){
			int letter = (ladders);
			searched.setLadder(letter);

			addSecondladders(searched,selectedRow);

			addSettingLadders(ladders - 1);
		}else if(ladders > 0){
			addSettingLadders(ladders);
		}
	}

	private void addSecondladders(Node ladder, int row) {

		int selectedRow = (int)Math.floor(Math.random()*(matrixRows-row));
		int selectedCol = (int)Math.floor(Math.random()*matrixCols);

		Node searched = searchNode(selectedRow, selectedCol);

		if(searched.getRow() != row && searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && searched.getSnake() == ' ' && searched.getLadder() == 0){

			searched.setLadder(ladder.getLadder());
		}else {
			addSecondladders(ladder,row);
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
		boolean stop = true;
		
		if(setting.length >= 8) {
			
			try {

				int row = Integer.parseInt(setting[0]);
				int col = Integer.parseInt(setting[2]);
				int snakes = Integer.parseInt(setting[4]);
				int ladders = Integer.parseInt(setting[6]);
				int players = Integer.parseInt(setting[8]);

				if(snakes+ladders < ((row*col)-2)/2) {
					matrixRows = row;
					matrixCols = col;
					createNewMatrix();
					
					numberPlayer = players;

					if(players <= 9) {
						int index = 9;

						if(players == ((settings.length() - 1) - index) && setting.length > 10 && setting[9].equals(" ")) {
							verifySymbols(setting, settings.length()-1);
							
							if(verify == 0) {
								index = 10;
								addSettingPlayers(getFirst().getFirst(),setting, index);
								
							}else {
								stop = false;
							}
							
						}else if(setting.length == 9) {
							addSymbols(players);
							
						}else {
							stop = false;
						}

						int cont = 1;

						insertValuePlayer(getFirst().getFirst(),players,cont);

						addSettingSnake(snakes,0);
						addSettingLadders(ladders);

					}else {
						stop = false;
					}
				}else {
					stop = false;
				}
			}catch(NumberFormatException nfe){
				stop = false;
			}
		}else {
			stop = false;
		}
		return stop;
	}
	
	private void verifySymbols(String[] setting,int players) {
		if(players >= 10 && setting[players].equals(setting[players - 1])) {
			verify++;
		}
	}

	private void insertValuePlayer(Player current,int i,int cont) {

		if(current != null && cont <= i) {
			current.setPosition(cont);
			insertValuePlayer(current.getNext(), i, cont++);
		}
	}

	private void addSettingPlayers(Player first,String[] settings,int index) {

		if(index == 10 && settings.length > index) {
			getFirst().setFirst(new Player(settings[index]));
			
			if(settings.length > 11) {
				getFirst().getFirst().setNext(new Player(settings[index + 1]));
				addSettingPlayers(getFirst().getFirst().getNext(), settings, index + 2);
			}

		}else if(index == 10) {
			getFirst().setFirst(new Player(settings[index]));
		}
		else{
			if(first.getNext() == null && index <= settings.length - 1) {
				first.setNext(new Player(settings[index]));
				addSettingPlayers(first.getNext(),settings, index + 1);
			}
		}
	}

	public void addSymbols(int index) {

		selectSymbols(getFirst(),getFirst().getFirst(),index);
	}

	private String selectSymbols(Node first,Player player,int index) {
		String symbol = "";

		Random azarSymbols = new Random();
		int selectedSymbol = (int)(azarSymbols.nextDouble() * 9);

		if(index > 0) {

			switch(selectedSymbol) {
			case 1:
				verify = 0;
				symbol = "*";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 2:
				verify = 0;
				symbol = "!";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 3:
				verify = 0;
				symbol = "O";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 4:
				verify = 0;
				symbol = "X";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 5:
				verify = 0;
				symbol = "%";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 6:
				verify = 0;
				symbol = "$";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 7:
				verify = 0;
				symbol = "#";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 8:
				verify = 0;
				symbol = "+";
				searchSymbols(first.getFirst(),symbol, 9);
				break;

			case 9:
				verify = 0;
				symbol = "&";
				searchSymbols(first.getFirst(),symbol, 9);
				break;
			}

			if(verify == 0) {
				asignSymbol(player, index, symbol);
				
			}else {
				selectSymbols(first,getFirst().getFirst().getNext(),index);
			}
		}
		return symbol;
	}
	
	private void asignSymbol(Player player, int index, String symbol) {
		
		if(getFirst().getFirst() == null) {
			Player current = new Player(symbol);
			getFirst().setFirst(current);
			selectSymbols(getFirst(),getFirst().getFirst(), index-1);
			
		}else {
			if(index > 0 ) {
				searchNext(player, symbol);
				selectSymbols(getFirst(),player.getNext(), index-1);
			}
		}
	}

	public void searchNext(Player player, String symbol) {
		if(player.getNext() == null) {
			player.setNext(new Player(symbol));
		}
	}

	private void searchSymbols(Player player,String symbol, int index) {
		
		if(player != null && player.getSymbol().equals(symbol) && index > 0) {
			verify++;
			searchSymbols(player.getNext(),symbol, index - 1);
		}
	}
	
	public String moveplayer() {
		
		String message = "";
		
		if(numberPlayer == 0) {
			numberPlayer = numberPlayerVerify;
		}else {
			Player player = findPlayer(numberPlayer);
			
			message = movePlayer(player);
			numberPlayer--;
		}
		
		return message;
	}

	public String movePlayer(Player current) {

		String message = "";

		int die = random();
		
		if(current != null) {
			message = "El jugador "+current.getSymbol()+" ha lanzado el dado y obtuvo el puntaje "+die;
		}

		move(current,die);

		return message;
	}

	private void move(Player current, int die) {
		
		Node node = searchNode(current);
		
		Player player = move(current);
		
		if(player != null && node != null) {
			movePlayerNode(player, (node.getPosition()+die));
		}
	}
	
	private void movePlayerNode(Player player, int position) {
		movePlayerNode(getFirst(),player,position);
	}
	
	private void movePlayerNode(Node current, Player player, int position) {
		
		if(current.getPosition() == position) {
			if(current.getFirst() == null) {
				current.setFirst(player);
			}else {
				moveInOrder(current,current.getFirst(),player);
			}
		}
	}
	
	private void moveInOrder(Node node, Player current, Player player) {
		
		if(current == null) {
			node.setFirst(player);
		}else {
			if(current.getPosition() < player.getPosition()) {
				if(current.getNext() != null) {
					moveInOrder(node,current.getNext(),player);
				}else {
					current.setNext(player);
				}
			}else {
				node.setFirst(player);
				player.setNext(current);
			}
		}
	}
	
	private Node searchNode(Player current) {

		Node baseNode = getFirst();
		Node searched = null;

		if(baseNode != null) {
			searched = nextNode(current,baseNode);
		}

		return searched;
	}

	private Node nextNode(Player current, Node baseNode) {

		Player player = searchPlayer(current,baseNode);
		Node searched = null;
		
		if(player != null) {
			searched = baseNode;
		}else if(baseNode.getNext() != null && player == null) {
			searched = nextNode(current,baseNode.getNext());
		}else if(baseNode.getUp() != null && player == null){
			searched = prevNode(current,baseNode.getUp());
		}
		
		return searched;
	}

	private Node prevNode(Player current, Node baseNode) {

		Player player = searchPlayer(current,baseNode);
		Node searched = null;
		
		if(player != null) {
			searched = baseNode;
		}else if(baseNode.getPrevious() != null && player == null) {
			searched = prevNode(current,baseNode.getPrevious());
		}else  if(baseNode.getUp() != null && player == null){
			searched = nextNode(current,baseNode.getUp());
		}
		
		return searched;
	}

	private Player move(Player current) {

		Node baseNode = getFirst();
		Player player = null;

		if(baseNode != null) {
			player = next(current,baseNode);
		}

		return player;
	}

	private Player next(Player current, Node baseNode) {

		Player player = searchPlayer(current,baseNode);

		if(baseNode.getNext() != null && player == null) {
			player = next(current,baseNode.getNext());
		}else if(baseNode.getUp() != null && player == null){
			player = prev(current,baseNode.getUp());
		}
		
		return player;
	}

	private Player prev(Player current, Node baseNode) {

		Player player = searchPlayer(current,baseNode);

		if(baseNode.getPrevious() != null && player == null) {
			player = prev(current,baseNode.getPrevious());
		}else  if(baseNode.getUp() != null && player == null){
			player = next(current,baseNode.getUp());
		}
		
		return player;
	}

	private Player searchPlayer(Player current, Node baseNode) {

		Player player = null;

		if(baseNode.getFirst() != null && baseNode.getFirst() == current) {
			
			player = current;
			
			if(baseNode.getFirst().getNext() == null) {
				baseNode.setFirst(null);
			}else {
				baseNode.setFirst(baseNode.getFirst().getNext());
			}
			
		}

		return player;
	}
	
	private Player findPlayer(int position) {
		
		Player player = null;
		
		player = findNext(getFirst(),position);
				
		return player;
	}
	
	private Player findNext(Node newNode, int position) {
		
		Player player = findPlayerToMove(position,newNode);

		if(newNode.getNext() != null && player == null) {
			player = findNext(newNode.getNext(),position);
		}else if(newNode.getUp() != null && player == null){
			player = findPrev(newNode.getUp(),position);
		}
		
		return player;
	}
	
	private Player findPrev(Node newNode, int position) {

		Player player = findPlayerToMove(position,newNode);

		if(newNode.getPrevious() != null && player == null) {
			player = findPrev(newNode.getPrevious(),position);
		}else  if(newNode.getUp() != null && player == null){
			player = findNext(newNode.getUp(),position);
		}
		
		return player;
	}
	
	private Player findPlayerToMove(int current, Node baseNode) {
		
		Player player = null;

		if(baseNode.getFirst() != null && baseNode.getFirst().getPosition() == current) {
			player = baseNode.getFirst();
		}
		return player;
	}

	public int random() {

		int random = (int) Math.floor(Math.random()*6+1);

		return random;
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
		String message;
		message = toStringRow(root);
		return message;
	}

	private String toStringRow(Node firstRow) {
		String message = "";
		if(firstRow!=null) {
			message = toStringCol(firstRow) + "\n";
			message += toStringRow(firstRow.getDown());
		}
		return message;
	}

	private String toStringCol(Node current) {
		String message = "";
		if(current!=null) {
			message = current.toString();
			message += toStringCol(current.getNext());
		}
		return message;
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
		String message = "";
		
		message = toStringRow2(root);
		
		return message;
	}
	
	private String toStringRow2(Node firstRow) {
		String message = "";
		if(firstRow!=null) {
			message = toStringCol2(firstRow) + "\n";
			message += toStringRow2(firstRow.getDown());
		}
		return message;
	}

	private String toStringCol2(Node current) {
		String message = "";
		if(current!=null) {
			message = current.toString2();
			message += toStringCol2(current.getNext());
		}
		return message;
	}

	private Node getFirst() {
		return searchNode(matrixRows - 1, 0);
	}
}
