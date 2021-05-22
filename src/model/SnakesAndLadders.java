package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class SnakesAndLadders{

	public final static String SAVE_PATH_FILE_PEOPLE = "data/scores.sal";
	
	private Node root;
	private BestPlayers firstPlayer;

	private int matrixRows;
	private int matrixCols;
	private int snakes;
	private int ladders;
	private int players;
	private String symbols;
	
	private static int verify = 0;
	private static int numberPlayer;
	private static int numberPlayerVerify;
	private static Player currentPlayer;

	public SnakesAndLadders(int matrixRows, int matrixCols) {
		this.matrixRows = matrixRows;
		this.matrixCols = matrixCols;
		snakes = 0;
		ladders= 0;
		players = 0;
		symbols = "";
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

	public void addPlayer(String name, int row, int col, int snakes, int ladders, int players, long score, char symbol, String otherPlayers) throws IOException, ClassNotFoundException {
		
		loadData();
		
		BestPlayers newPlayer = new BestPlayers(name,row,col,snakes,ladders,players,score,symbol,otherPlayers);
		
		if(firstPlayer == null) {
			firstPlayer = newPlayer;
		}else {
			addPlayer(firstPlayer,newPlayer);
		}
		saveData();
	}
	
	public String searchInOrder() throws ClassNotFoundException, IOException {
		loadData();
		return searchInOrder(firstPlayer);
	}
	
	public String searchInOrder(BestPlayers player) {
		
		String message = "";
		
		if(player != null) {
			message += searchInOrder(player.getPrevious());
			message += player.toString();
			message += searchInOrder(player.getNext());
		}
		
		return message;
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
			if(current.getPrevious() == null) {
				current.setPrevious(newPlayer);
			}else {
				addPlayer(current.getPrevious(),newPlayer);
			}
		}else {
			if(current.getNext() == null) {
				current.setNext(newPlayer);
			}else {
				addPlayer(current.getNext(),newPlayer);
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
					setSnakes(snakes);
					setPlayers(players);
					setLadders(ladders);

					numberPlayer = players;

					if(players <= 9) {
						int index = 0;

						if(setting.length - 1 > 4) {

							if(players == (setting[5].length()) && !setting[5].equals(" ")) {
								verifySymbols(players);

								if(verify == 0) {
									index = players;
									int cont = 0;
									
									setSymbols(setting[5]);

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
							String play = "";
							asignPlayers(getFirst().getFirst(), players,play);

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
	
	private void asignPlayers(Player player,int play,String players) {
		
		if(player != null && play >= 0) {
			players += player.getSymbol();
			if(player.getNext() != null) {
				asignPlayers(player.getNext(), play--,players);
			}
		}
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
				
				player.setMovement(player.getMovement()+1);
				message = movePlayer(player);
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
			currentPlayer = current;
			message = "\nEl jugador "+current.getSymbol()+" ha ganado el juego, con "+current.getMovement()+" movimientos\n";
		}

		return message;
	}
	
	/** 
     *Name: asignName.
     *Assigns the name of one player with your score<br> 
     *<b> post: </b> Winning player with their respective score has been added to the scores table.
     *@param name. Name of the winning player.
     *@throws IOException <br>
     *		thrown if...
     *	1. A local file that was no longer available is being read.
     *	2. Any process closed the stream while a stream is being used to read data.
     *  3. The disk space was no longer available while trying to write to a file.
     * @throws ClassNotFoundException <br>
     * 		thrown if the path of file wasn't found. <br>   	
    */
	public void asignName(String name) throws IOException, ClassNotFoundException {
		Long score = (long) (currentPlayer.getMovement() * (matrixCols * matrixRows)); 
		addPlayer(name, matrixRows, matrixCols, snakes, ladders, players, score, currentPlayer.getSymbol(), symbols);
	}

	/** 
     *Name: move.
     *Found a player to move depending on the number of dice.<br> 
     *<b> post: </b> The player has been found and will be send to move in the game.
     *@param current. Player current that is wanted for can move.
     *@param die. Number of the dice that has been rolled and determines the positions that the player must move .
     *@return boolean verify. This is a value depending if the player has been found. If is found the value is true, if not is false.
    */
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
	
	/** 
     *Name: movePlayer.
     *Send a player found to search their position.<br> 
     *<b> post: </b> The position of the player has been found.
     *@param player. Player that is wanted for can move.
     *@param position. Position when the player will be wanted.
    */
	private void movePlayerNode(Player player, int position) {
		movePlayerNode(getFirst(),player,position);
	}
	
	/** 
     *Name: movePlayerNode.
     *Search the node where the player should is.<br> 
     *<b> post: </b> The position where the player should is has been found.
     *@param current. Position where the player will be move.
     *@param player. Player that is wanted for can move.
     *@param position. Position when the player will be wanted.
    */
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

	/** 
     *Name: movePlayerPrev.
     *Search the node where the player should is but to left.<br> 
     *<b> post: </b> The position where the player should is has been found or has been wanted to rigth or up.
     *@param current. Position where the player will be move.
     *@param player. Player that is wanted for can move.
     *@param position. Position when the player will be wanted.
    */
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
	

	/** 
     *Name: movePlayerNodeUp.
     *Search the node where the player should is but to up.<br> 
     *<b> post: </b> The position where the player should is has been found to up.
     *@param current. Position where the player will be move.
     *@param player. Player that is wanted for can move.
     *@param position. Position when the player will be wanted.
    */
	private void movePlayerNodeUp(Node current, Player player, int position) {

		if(current.getUp() != null) {
			movePlayerNode(current.getUp(),player,position);
		}
	}
	
	/** 
     *Name: movePlayerNodeUp.
     *Search the node where the player should is but up to left.<br> 
     *<b> post: </b> The position where the player should is has been found up to left.
     *@param current. Position where the player will be move.
     *@param player. Player that is wanted for can move.
     *@param position. Position when the player will be wanted.
    */
	private void movePlayerNodeUpPrev(Node current, Player player, int position) {

		if(current.getUp() != null) {
			movePlayerPrev(current.getUp(),player,position);
		}
	}
	
	/** 
     *Name: moveInOrder.
     *Search the player in the lower position of the node for move.<br> 
     *<b> post: </b> The player of the lower position has been found.
     *@param node. Node where the player will be wanted to move.
     *@param current. Player that is compared with other player for take the player with the lower position.
     *@param player. Player that is compared with other player (current) for take the player with the lower position.
    */
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
	
	/** 
     *Name: findSnakesAndLadders.
     *Search the snakes and ladders with the method nextSnakesAndLadders.<br> 
     *<b> post: </b> The method has send to search the snakes and ladders.
     *@param current. Node where will be wanted the snakes and ladders.
     *@param player. Player that is moved depending if in the node there a snake or ladder.
    */
	private void findSnakesAndLadders(Node current, Player player) {
		nextSnakesAndLadders(getFirst(),current,player);
	}

	/** 
     *Name: nextSnakesAndLadders.
     *Search the snakes and ladders to the right.<br> 
     *<b> post: </b> A snake or ladder has been found, then, the player will be moved there. If not, will be wanted to up or left.
     *@param current. Node that will be compared with the other node for search the snakes and ladders.
     *@param node. Node where will be wanted the snakes and ladders.
     *@param player. Player that is moved depending if in the node there a snake or ladder.
    */
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
	
	/** 
     *Name: searchLadderNext.
     *Search the ladders to the right.<br> 
     *<b> post: </b> A ladder has been found.
     *@param current. Node that will be compared with the other node for search the ladders.
     *@param node. Node where will be wanted the ladders.
     *@return boolean verify. Value that mean if was or not found the ladder the to right. If is found, the value is true, if not is false.
    */
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
	
	/** 
     *Name: searchLadderPrev.
     *Search the ladders to the left.<br> 
     *<b> post: </b> A ladder has been found.
     *@param current. Node that will be compared with the other node for search the ladders.
     *@param node. Node where will be wanted the ladders.
     *@return boolean verify. Value that mean if was or not found the ladder the to left. If is found, the value is true, if not is false.
    */
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
	
	/** 
     *Name: prevSanakesAndLadders.
     *Search the snakes and ladders to the left.<br> 
     *<b> post: </b> A snake or ladder has been found, then, the player will be moved there. If not, will be wanted to up.
     *@param current. Node that will be compared with the other node for search the snakes and ladders.
     *@param node. Node where will be wanted the snakes and ladders.
     *@param player. Player that is moved depending if in the node there a snake or ladder.
    */
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
	
	/** 
     *Name: searchNode.
     *Search a node.<br> 
     *<b> post: </b> A node has been found.
     *@param current. Player that will be wanted for their node.
     *@return node searched. Node that was found.
    */
	private Node searchNode(Player current) {

		Node baseNode = getFirst();
		Node searched = null;

		if(baseNode != null) {
			searched = nextNode(current,baseNode);
		}

		return searched;
	}
	
	/** 
     *Name: nextNode.
     *Search a node to right.<br> 
     *<b> post: </b> A node has been found or has been wanted to left or up.
     *@param current. Player that will be wanted for their node.
     *@param baseNode. Node that will be compared with the node of the player.
     *@return node searched. Node that was found.
    */
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
	
	/** 
     *Name: prevNode.
     *Search a node to left.<br> 
     *<b> post: </b> A node has been found or has been wanted to up.
     *@param current. Player that will be wanted for their node.
     *@param baseNode. Node that will be compared with the node of the player.
     *@return node searched. Node that was found.
    */
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
	
	/** 
     *Name: searchPlayer.
     *Search a player by a node.<br> 
     *<b> post: </b> A player has been found.
     *@param current. Player that will be wanted for their node.
     *@param baseNode. Node that will be compared with the node of the player.
     *@return Player player. Player that was found by the node.
    */
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
	
	/** 
     *Name: findPlayer.
     *Search a player by a position.<br> 
     *<b> post: </b> A player has been found.
     *@param position. Integer that will be compared with the position of the player.
     *@return Player player. Player that was found by the position.
    */
	private Player findPlayer(int position) {

		Player player = null;

		player = findNext(getFirst(),position);

		return player;
	}
	
	/** 
     *Name: findNext.
     *Search a the next player on a position by a position.<br> 
     *<b> post: </b> A player has been found.
     *@param position. Integer that will be compared with the position of the player.
     *@param newNode. Node where will be wanted a player.
     *@return Player player. Next player that was found by the position on a node.
    */
	private Player findNext(Node newNode, int position) {

		Player player = findPlayerToMove(position,newNode);

		if(newNode.getNext() != null && player == null) {
			player = findNext(newNode.getNext(),position);
		}else if(newNode.getUp() != null && player == null){
			player = findPrev(newNode.getUp(),position);
		}

		return player;
	}
	
	/** 
     *Name: findNext.
     *Search a the previous player on a position by a position.<br> 
     *<b> post: </b> A player has been found.
     *@param position. Integer that will be compared with the position of the player.
     *@param newNode. Node where will be wanted a player.
     *@return Player player. Previous player that was found by the position on a node.
    */
	private Player findPrev(Node newNode, int position) {

		Player player = findPlayerToMove(position,newNode);

		if(newNode.getPrevious() != null && player == null) {
			player = findPrev(newNode.getPrevious(),position);
		}else  if(newNode.getUp() != null && player == null){
			player = findNext(newNode.getUp(),position);
		}

		return player;
	}
	
	/** 
     *Name: findPlayerToMove.
     *Search a player that will be moved.<br> 
     *<b> post: </b> A player has been found.
     *@param current. Integer that will be compared with the position of the player.
     *@param baseNode. Node where will be wanted a player.
     *@return Player player. Player that was found by the position on a node.
    */
	private Player findPlayerToMove(int current, Node baseNode) {

		Player player = null;
		
		if(baseNode.getFirst() != null) {

			player = findPlayerCurrent(current,baseNode.getFirst());
		}
		return player;
	}
	
	/** 
     *Name: findPlayerCurrent.
     *Search a player current with a position.<br> 
     *<b> post: </b> A player has been found.
     *@param position. Integer that will be compared with the position of the player.
     *@param current. Player that will be compared with a position.
     *@return Player player. Player that was found by the position in the game.
    */
	private Player findPlayerCurrent(int position, Player current) {

		Player player = null;

		if(current.getPosition() == position) {
			player = current;
		}else if(current.getNext() != null){
			findPlayerCurrent(position,current.getNext());
		}

		return player;
	}
	
	/** 
     *Name: random.
     *Choose a number between 1 and 6 for the dice.<br> 
     *<b> post: </b> A number has been assigns.
     *@return int random. Chosen number.
    */
	public int random() {

		int random = (int) Math.floor(Math.random()*6+1);

		return random;
	}
	
	/** 
     *Name: getPlayer.
     *Get the value of the node root with scores.<br> 
     *<b> post: </b> Root with the scores.
     *@return Node root. This is the node that have the scores.
    */
	public Node getRoot() {
		return root;
	}
	
	/** 
     *Name: setRoot.
     *Change the root of the node for the scores.<br> 
     *<b> post: </b> New root of the nodes.
     *@param root. Node that will be the root of the nodes.
    */
	public void setRoot(Node root) {
		this.root = root;
	}
	
	/** 
     *Name: getMatrixRows.
     *Get the rows of the matrix.<br> 
     *<b> post: </b> Values of the rows of the matrix.
     *@return int matrixRows. This is the rows of the matrix.
    */
	public int getMatrixRows() {
		return matrixRows;
	}
	
	/** 
     *Name: setMatrixRows.
     *Change the rows of the matrix.<br> 
     *<b> post: </b> New rows of the matrix.
     *@param matrixRows. Rows that will be new rows of the matrix.
    */
	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}
	
	/** 
     *Name: getMatrixCols.
     *Get the columns of the matrix.<br> 
     *<b> post: </b> Values of the columns of the matrix.
     *@return int getMatrixCols. This is the columns of the matrix.
    */
	public int getMatrixCols() {
		return matrixCols;
	}

	/** 
     *Name: setMatrixCols.
     *Change the columns of the matrix.<br> 
     *<b> post: </b> New columns of the matrix.
     *@param matrixCols. Columns that will be new columns of the matrix.
    */
	public void setMatrixCols(int matrixCols) {
		this.matrixCols = matrixCols;
	}
	
	/** 
     *Name: firstPlayer.
     *Get the first player of the root of nodes.<br> 
     *<b> post: </b> First player of the root.
     *@return BestPlayers firstPlayer. This is first player of the root.
    */
	public BestPlayers getFirstPlayer() {
		return firstPlayer;
	}
	
	/** 
     *Name: setFirstPlayer.
     *Change the first player of the root.<br> 
     *<b> post: </b> New first player of the root.
     *@param firstPlayer. First player of the root.
    */
	public void setFirstPlayer(BestPlayers firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	
	/** 
     *Name: getSnakes.
     *Get the snakes that there in game.<br> 
     *<b> post: </b> Snakes in the game.
     *@return int snakes. This are the snakes of the game.
    */
	public int getSnakes() {
		return snakes;
	}
	
	/** 
     *Name: setSnakes.
     *Change the snakes of the game.<br> 
     *<b> post: </b> New snakes in the game.
     *@param snakes. New snakes for the game.
    */
	public void setSnakes(int snakes) {
		this.snakes = snakes;
	}
	
	/** 
     *Name: getLadders.
     *Get the ladders that there in game.<br> 
     *<b> post: </b> Ladders in the game.
     *@return int ladders. This are the ladders of the game.
    */
	public int getLadders() {
		return ladders;
	}
	
	/** 
     *Name: setLadders.
     *Change the ladders of the game.<br> 
     *<b> post: </b> New ladders in the game.
     *@param ladders. New ladders for the game.
    */
	public void setLadders(int ladders) {
		this.ladders = ladders;
	}
	
	/** 
     *Name: getPlayers.
     *Get the players that there in game.<br> 
     *<b> post: </b> Players in the game.
     *@return int players. This are the players of the game.
    */
	public int getPlayers() {
		return players;
	}
	
	/** 
     *Name: setPlayers.
     *Change the players of the game.<br> 
     *<b> post: </b> New players in the game.
     *@param players. New players for the game.
    */
	public void setPlayers(int players) {
		this.players = players;
	}
	
	/** 
     *Name: getSymbols.
     *Get the symbols that there in game.<br> 
     *<b> post: </b> Symbols in the game.
     *@return String symbols. This are the symbols of the game.
    */
	public String getSymbols() {
		return symbols;
	}

	/** 
     *Name: setSymbols.
     *Change the symbols of the game.<br> 
     *<b> post: </b> New symbols in the game.
     *@param symbols. New symbols for the game.
    */
	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}
	
	/** 
     *Name: getVerify.
     *Get the value of verify.<br> 
     *<b> post: </b> Value of verify.
     *@return int verify. This is the value of verify.
    */
	public static int getVerify() {
		return verify;
	}
	
	/** 
     *Name: setVerify.
     *Change the value of verify.<br> 
     *<b> post: </b> New value of verify.
     *@param verify. New the value of verify.
    */
	public static void setVerify(int verify) {
		SnakesAndLadders.verify = verify;
	}
	
	/** 
     *Name: getNumberPlayer.
     *Get the number of Players in the game.<br> 
     *<b> post: </b> The number of Players in the game.
     *@return int numberPlayer. This is the number of Players in the game.
    */
	public static int getNumberPlayer() {
		return numberPlayer;
	}
	
	/** 
     *Name: setVerify.
     *Change the number of Players in the game.<br> 
     *<b> post: </b> New number of Players in the game.
     *@param numberPlayer. New number of Players in the game.
    */
	public static void setNumberPlayer(int numberPlayer) {
		SnakesAndLadders.numberPlayer = numberPlayer;
	}
	
	/** 
     *Name: getNumberPlayerVerify.
     *Get the value for verify the number of Players in the game.<br> 
     *<b> post: </b> The value for verify the number of Players in the game.
     *@return int numberPlayerVerify. This is the value for verify the number of Players in the game.
    */
	public static int getNumberPlayerVerify() {
		return numberPlayerVerify;
	}
	
	/** 
     *Name: setNumberPlayerVerify.
     *Change the value for verify the number of Players in the game.<br> 
     *<b> post: </b> New value for verify the number of Players in the game.
     *@param numberPlayerVerify. New the value for verify the number of Players in the game.
    */
	public static void setNumberPlayerVerify(int numberPlayerVerify) {
		SnakesAndLadders.numberPlayerVerify = numberPlayerVerify;
	}
	
	/** 
      *Name: toString.
      *Show the rows of the matrix.<br> 
      *<b> post: </b> Information of the rows of the matrix.
      *@return String message. This is the message where is the information of rows of the matrix.
    */
	public String toString() {
		String message;
		message = toStringRow(root);
		return message;
	}
	
	/** 
     *Name: toStringRow.
     *Show the rows and columns of the matrix.<br> 
     *<b> post: </b> Information of the rows and columns of the matrix.
     *@return String message. This is the message where is the informations of the rows and columns of the matrix.
   */
	private String toStringRow(Node firstRow) {
		String message = "";
		if(firstRow!=null) {
			message = toStringCol(firstRow) + "\n";
			message += toStringRow(firstRow.getDown());
		}
		return message;
	}
	
	/** 
     *Name: toStringCol.
     *Show the columns of the matrix.<br> 
     *<b> post: </b> Information of the columns of the matrix.
     *@return String message. This is the message where is the information of columns of the matrix.
   */
	private String toStringCol(Node current) {
		String message = "";
		if(current!=null) {
			message = current.toString();
			message += toStringCol(current.getNext());
		}
		return message;
	}
	
	/** 
     *Name: toStringScoreTable.
     *Show the score table of the game.<br> 
     *<b> post: </b> Score table of the players of the game.
     *@return String message. This is the message where is the score table of the game.
   */
	public String toStringScoreTable() throws ClassNotFoundException, IOException {
		String message;
		loadData();
		
		if(firstPlayer != null) {
			message = toStringScores(firstPlayer);
		}else {
			message = "---No hay ningun puntaje todavia---";
		}

		return message;
	}
	
	/** 
     *Name: toStringScores.
     *Show the scores the game.<br> 
     *<b> post: </b> Scores the players of the game.
     *@return String message. This is the message where is the scores of the players in game.
   */
	private String toStringScores(BestPlayers player) {
		String message = "";

		if(player != null) {
			message += player.toString();
			
			if(player.getPrevious() != null) {
				message += "\n" + toStringScores(player.getPrevious());
			}

			if(player.getNext() != null) {
				message += "\n" + toStringScores(player.getNext());
			}
			
		}

		return message;
	}
	
	/** 
     *Name: toString2.
     *Show the root of nodes.<br> 
     *<b> post: </b> Information of root of the nodes.
     *@return String message. This is the message where is the information of root of the nodes.
   */
	public String toString2() {
		String message = "";

		message = toStringRow2(root);

		return message;
	}
	
	/** 
     *Name: toStringRow2.
     *Show the nodes that is down of the rows.<br> 
     *<b> post: </b> The nodes that is down of the rows.
     *@return String message. This is the message where is the nodes that is down f the rows.
   */
	private String toStringRow2(Node firstRow) {
		String message = "";
		if(firstRow!=null) {
			message = toStringCol2(firstRow) + "\n";
			message += toStringRow2(firstRow.getDown());
		}
		return message;
	}
	
	/** 
     *Name: toStringCol2.
     *Show the nodes that is down of the columns.<br> 
     *<b> post: </b> The nodes that is down of the columns.
     *@return String message. This is the message where is the nodes that is down f the columns.
   */
	private String toStringCol2(Node current) {
		String message = "";
		if(current!=null) {
			message = current.toString2();
			message += toStringCol2(current.getNext());
		}
		return message;
	}
	
	/** 
     *Name: getFirst.
     *Get the first node of the nodes.<br> 
     *<b> post: </b> The first node.
     *@return Node searchNode. This is the message where is the first node.
   */
	private Node getFirst() {
		return searchNode(matrixRows - 1, 0);
	}
	
	/** 
     *Name: loadData.
     *Load the data of all winning players.<br> 
     *<b> post: </b> The data has been loaded.
     *@throws IOException <br>
     *		thrown if...
     *	1. A local file that was no longer available is being read.
     *	2. Any process closed the stream while a stream is being used to read data.
     *  3. The disk space was no longer available while trying to write to a file.
     * @throws ClassNotFoundException <br>
     * 		thrown if the path of file wasn't found. <br>   	
    */
	public void loadData() throws IOException, ClassNotFoundException{

		File scores = new File(SAVE_PATH_FILE_PEOPLE);

		if(scores.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scores));
			firstPlayer = (BestPlayers) ois.readObject();
			ois.close();
		}
	}
	
	/** 
     *Name: saveData.
     *Save the data of all winning players.<br> 
     *<b> post: </b> The data has been saved.
     *@throws IOException <br>
     *		thrown if...
     *	1. A local file that was no longer available is being read.
     *	2. Any process closed the stream while a stream is being used to read data.
     *  3. The disk space was no longer available while trying to write to a file.
    */
	public void saveData() throws IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_PEOPLE));
		oos.writeObject(firstPlayer);
		oos.close();
	}

}
