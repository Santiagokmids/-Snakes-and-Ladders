package model;

import java.util.Random;

public class SnakesAndLadders {

	private Node root;
	private BestPlayers firstPlayer;

	private int matrixRows;
	private int matrixCols;

	private static int verify = 0;
	private static int numberPlayer;
	private static int numberPlayerVerify;
	private static Player currentPlayer;

	public SnakesAndLadders(int matrixRows, int matrixCols) {
		this.matrixRows = matrixRows;
		this.matrixCols = matrixCols;
		createNewMatrix();
	}

	private void createNewMatrix() {

		numberPlayer = 0;
		numberPlayerVerify = 0;
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

		String setting[] = settings.split(" ");
		boolean stop = true;

		if(setting.length >= 4) {

			try {

				int row = Integer.parseInt(setting[0]);
				int col = Integer.parseInt(setting[1]);
				int snakes = Integer.parseInt(setting[2]);
				int ladders = Integer.parseInt(setting[3]);
				int players = Integer.parseInt(setting[4]);

				if(snakes+ladders < ((row*col)-2)/2) {
					matrixRows = row;
					matrixCols = col;
					createNewMatrix();

					numberPlayer = players;

					if(players <= 9) {
						int index = 0;

						if(setting.length - 1 > 4) {

							if(players == (setting[5].length()) && !setting[5].equals(" ")) {
								verifySymbols(players);

								if(verify == 0) {
									index = players;
									int cont = 0;

									addSettingPlayers(getFirst().getFirst(),setting[5], index, cont);
									verify = 0;
									verifySymbols(getFirst().getFirst(),players);

									if(verify != 0) {
										stop = false;
									}

								}else {
									stop = false;
								}
							}else {
								stop = false;
							}

						}else if(setting.length - 1 == 4) {
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

	private void verifySymbols(int players) {
		if(players >= 10) {
			verify++;
		}
	}

	private void insertValuePlayer(Player current,int i,int cont) {

		if(current != null && cont <= i) {

			current.setPosition(cont);
			insertValuePlayer(current.getNext(), i, cont+1);
		}
	}

	private void addSettingPlayers(Player first,String players,int index,int cont) {
		if(index == players.length() && cont == 0) {
			getFirst().setFirst(new Player(players.charAt(cont)));

			if(players.length() > 1) {
				getFirst().getFirst().setNext(new Player(players.charAt(cont+1)));
				addSettingPlayers(getFirst().getFirst().getNext(), players, index,cont + 2);
			}
		}
		else{
			if(first.getNext() == null && index > cont) {
				first.setNext(new Player(players.charAt(cont)));
				addSettingPlayers(first.getNext(),players, index ,cont + 1);
			}
		}
	}

	private void verifySymbols(Player player,int players) {

		if(player.getNext() != null && players > 0) {
			searchSymbols(getFirst().getFirst(), player.getNext());
			verifySymbols(player.getNext(),players-1);
		}

	}

	public void addSymbols(int index) {

		selectSymbols(getFirst(),getFirst().getFirst(),index);
	}

	private void selectSymbols(Node first,Player player,int index) {
		char symbol = ' ';

		Random azarSymbols = new Random();
		int selectedSymbol = (int)(azarSymbols.nextDouble() * 10);
		Player current;

		if(index > 0) {

			switch(selectedSymbol) {
			case 1:
				verify = 0;
				symbol = '*';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 2:
				verify = 0;
				symbol = '!';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 3:
				verify = 0;
				symbol = 'O';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 4:
				verify = 0;
				symbol = 'X';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 5:
				verify = 0;
				symbol = '%';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 6:
				verify = 0;
				symbol = '$';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 7:
				verify = 0;
				symbol = '#';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 8:
				verify = 0;
				symbol = '+';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;

			case 9:
				verify = 0;
				symbol = '&';
				current = new Player(symbol);
				searchSymbolsAzar(first.getFirst(),current);
				break;
			}

			if(verify == 0 && symbol != (' ')) {
				asignSymbol(player, index, symbol);

			}else if(index > 0){
				selectSymbols(first,player,index);
			}
		}
	}

	private void asignSymbol(Player player, int index, char symbol) {

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

	public void searchNext(Player player, char symbol) {
		if(player.getNext() == null) {
			player.setNext(new Player(symbol));
		}
	}

	private void searchSymbols(Player player,Player current) {

		if(current != null && player != null && player.getSymbol() == (current.getSymbol()) && player.getNext() != current.getNext()) {
			verify++;

		}else if(player != null && player.getNext() != null) {
			searchSymbols(player.getNext(),current);
		}

	}
	private void searchSymbolsAzar(Player player,Player current) {

		if(current != null && player != null && player.getSymbol() == (current.getSymbol())) {
			verify++;

		}else if(player != null && player.getNext() != null) {
			searchSymbolsAzar(player.getNext(),current);
		}
	}

	public String moveplayer() {

		String message = "";

		if(numberPlayer == numberPlayerVerify) {
			numberPlayerVerify = 0;
			message = moveplayer();
		}else {
			numberPlayerVerify++;

			Player player = findPlayer(numberPlayerVerify);

			if(player != null) {
				message = movePlayer(player);
				player.setMovement(player.getMovement()+1);
			}else {
				message = moveplayer();
			}
		}
		return message;
	}

	public String movePlayer(Player current) {

		String message = "";

		int die = random();

		boolean verify = move(current,die);

		message = "El jugador "+current.getSymbol()+" ha lanzado el dado y obtuvo el puntaje "+die+"\n";

		if(verify) {
			message = "\nEl jugador "+current.getSymbol()+" ha ganado el juego, con "+current.getMovement()+" movimientos\n";
		}

		return message;
	}

	private boolean move(Player current, int die) {

		boolean verify = false;

		Node node = searchNode(current);

		if(current != null && node != null) {
			if((node.getPosition()+die) >= (matrixCols*matrixRows)) {
				verify = true;
			}else {
				movePlayerNode(currentPlayer, (node.getPosition()+die));
			}
		}
		return verify;
	}

	private void movePlayerNode(Player player, int position) {
		movePlayerNode(getFirst(),player,position);
	}

	private void movePlayerNode(Node current, Player player, int position) {
		if(current.getPosition() == position) {
			if(current.getFirst() == null) {
				if(current.getSnake() != ' ' || current.getLadder() != 0) {
					findSnakesAndLadders(current,player);
				}else {
					current.setFirst(player);
				}
			}else {
				moveInOrder(current,current.getFirst(),player);
			}

		}else if(current.getNext() != null){
			movePlayerNode(current.getNext(),player,position);
		}else if(current.getNext() == null) {
			movePlayerNodeUpPrev(current,player,position);
		}
	}

	private void movePlayerPrev(Node current, Player player, int position) {
		if(current.getPosition() == position) {
			if(current.getFirst() == null) {
				if(current.getSnake() != ' ' || current.getLadder() != 0) {
					findSnakesAndLadders(current,player);
				}else {
					current.setFirst(player);
				}
			}else {
				moveInOrder(current,current.getFirst(),player);
			}
		}else if(current.getPrevious() != null){
			movePlayerPrev(current.getPrevious(),player,position);
		}else if(current.getPrevious() == null) {
			movePlayerNodeUp(current,player,position);
		}
	}

	private void movePlayerNodeUp(Node current, Player player, int position) {

		if(current.getUp() != null) {
			movePlayerNode(current.getUp(),player,position);
		}
	}

	private void movePlayerNodeUpPrev(Node current, Player player, int position) {

		if(current.getUp() != null) {
			movePlayerPrev(current.getUp(),player,position);
		}
	}

	private void moveInOrder(Node node, Player current, Player player) {

		if(current.getPosition() < player.getPosition()) {
			if(current.getNext() != null) {
				moveInOrder(node,current.getNext(),player);
			}else {
				current.setNext(player);
			}
		}else {
			node.setFirst(player);
			node.getFirst().setNext(current);
		}
	}

	private void findSnakesAndLadders(Node current, Player player) {
		nextSnakesAndLadders(getFirst(),current,player);
	}

	private void nextSnakesAndLadders(Node current, Node node, Player player) {

		if(node.getSnake() != ' ' && node.getSnake() == current.getSnake() && current.getPosition() <= node.getPosition()) {
			if(current.getFirst() == null) {
				current.setFirst(player);
			}else {
				moveInOrder(current,current.getFirst(),player);
			}
		}else if(node.getLadder() == current.getLadder() && current.getPosition() >= node.getPosition()) {

			boolean verify = searchLadderNext(current,node);

			if(current.getPosition() == node.getPosition()) {
				if(verify) {
					if(current.getNext() != null) {
						nextSnakesAndLadders(current.getNext(),node,player);
					}else if(current.getUp() != null) {
						prevSanakesAndLadders(current.getUp(),node,player);
					}
				}else {
					if(current.getFirst() == null) {
						current.setFirst(player);
					}else {
						moveInOrder(current, current.getFirst(), player);
					}
				}
			}else {
				if(current.getFirst() == null) {
					current.setFirst(player);
				}else {
					moveInOrder(current, current.getFirst(), player);
				}
			}

		}else if(current.getNext() != null){
			nextSnakesAndLadders(current.getNext(),node,player);
		}else if(current.getUp() != null) {
			prevSanakesAndLadders(current.getUp(),node,player);
		}
	}

	private boolean searchLadderNext(Node current, Node node) {

		boolean verify = false;

		if(node.getLadder() != 0 && node.getLadder() == current.getLadder() && current.getPosition() > node.getPosition()) {
			verify = true;
		}else if(current.getNext() != null){
			verify = searchLadderNext(current.getNext(),node);
		}else if(current.getUp() != null) {
			verify = searchLadderPrev(current.getUp(),node);
		}

		return verify;
	}

	private boolean searchLadderPrev(Node current, Node node) {

		boolean verify = false;

		if(node.getLadder() != 0 && node.getLadder() == current.getLadder() && current.getPosition() > node.getPosition()) {
			verify = true;
		}else if(current.getPrevious() != null){
			verify = searchLadderPrev(current.getPrevious(),node);
		}else if(current.getUp() != null) {
			verify = searchLadderNext(current.getUp(),node);
		}

		return verify;
	}

	private void prevSanakesAndLadders(Node current, Node node, Player player) {

		if(node.getSnake() != ' ' && node.getSnake() == current.getSnake() && current.getPosition() <= node.getPosition()) {
			if(current.getFirst() == null) {
				current.setFirst(player);
			}else {
				moveInOrder(current,current.getFirst(),player);
			}
		}else if(node.getLadder() == current.getLadder() && current.getPosition() >= node.getPosition()) {

			boolean verify = searchLadderNext(current,node);

			if(current.getPosition() == node.getPosition()) {
				if(verify) {
					if(current.getNext() != null) {
						nextSnakesAndLadders(current.getNext(),node,player);
					}else if(current.getUp() != null) {
						prevSanakesAndLadders(current.getUp(),node,player);
					}
				}else {
					if(current.getFirst() == null) {
						current.setFirst(player);
					}else {
						moveInOrder(current, current.getFirst(), player);
					}
				}
			}else {
				if(current.getFirst() == null) {
					current.setFirst(player);
				}else {
					moveInOrder(current, current.getFirst(), player);
				}
			}
		}else if(current.getPrevious() != null){
			prevSanakesAndLadders(current.getPrevious(),node,player);
		}else if(current.getUp() != null) {
			nextSnakesAndLadders(current.getUp(),node,player);
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

	private Player searchPlayer(Player current, Node baseNode) {

		Player player = null;

		if(baseNode.getFirst() != null && baseNode.getFirst() == current) {

			player = new Player(current.getSymbol());
			currentPlayer = player;
			currentPlayer.setMovement(current.getMovement());
			currentPlayer.setPosition(current.getPosition());

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

		if(baseNode.getFirst() != null) {

			player = findPlayerCurrent(current,baseNode.getFirst());
		}
		return player;
	}

	private Player findPlayerCurrent(int position, Player current) {

		Player player = null;

		if(current.getPosition() == position) {
			player = current;
		}else if(current.getNext() != null){
			findPlayerCurrent(position,current.getNext());
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
