package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * This class contains methods, attributes,  and relations of a snakes and ladders.
 * @version 1
 * @author Santiago Trochez Velasco, https://github.com/Santiagokmids <br>
 * @author Luis Miguel Ossa Arias, https://github.com/Itsumohitoride <br>
 * Based on the linked matrix of https://github.com/seyerman
 */

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

	/**
	 * <b>name:</b> SnakesAndLadders <br>
	 * Create an object snakes and ladders. <br>
	 * <b>post:</b> An object snakes and ladders has created. <br>
	 * @param matrixRows is the number of rows. matrixRows grater than 0.
	 * @param matrixCols is the number of columns. matrixCols grater than 0.
	 */

	public SnakesAndLadders(int matrixRows, int matrixCols) {
		this.matrixRows = matrixRows;
		this.matrixCols = matrixCols;
		snakes = 0;
		ladders= 0;
		players = 0;
		symbols = "";
		createNewMatrix();
	}

	/**
	 * <b>name:</b>> createNewMatrix. <br>
	 * Create a new matrix to the game. <br>
	 * <b>post:</b>> A new matrix for the game has been created. <br>
	 */

	private void createNewMatrix() {

		numberPlayer = 0;
		numberPlayerVerify = 0;
		root = new Node(0,0);
		createNewRow(0,0,root);
		asingPosition(0,matrixRows-1,0);
	}

	/**
	 * <b>name:</b> createNewRow. <br>
	 * Create the rows of the matrix. <br>
	 * <b>post:</b> The rows of the matrix has been added. <br>  
	 * @param i is the first position to know where the new row was added. i greater than 0.
	 * @param j is the second position to know where the new row was added. j greater than 0.
	 * @param currentRootRow is the node that will be changing. currentRootRow != null y currentRootRow != "".
	 */

	private void createNewRow(int i, int j, Node currentRootRow) {

		createNewCol(i,j+1,currentRootRow,currentRootRow.getUp());

		if(i+1 < matrixRows) {

			Node downRootRow = new Node(i+1,j);
			downRootRow.setUp(currentRootRow);
			currentRootRow.setDown(downRootRow);
			createNewRow(i+1,j,downRootRow);
		}
	}

	/**
	 * <b>name:</b> createNewCol. <br>
	 * Create the columns of the matrix.
	 * <b>post:</b> The columns of the matrix has been added. <br>
	 * @param i is the first position to know where the new column was added. i greater than 0.
	 * @param j is the first second to know where the new column was added. j greater than 0.
	 * @param previous is the node previous to the new node. previous != null.
	 * @param rowPrevious is the node up to the new node.
	 */

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

	/**
	 * <b>name:</b> addPlayer. <br>
	 * Save the player who won the game. <br>
	 * <b>post:</b> the player who won the game has been added. <br>
	 * @param name is the name of the new player. name != "" y name != null.
	 * @param row is the size of the rows in the game. row greater than 0.
	 * @param col is the size of the columns in the game. col greater than 0.
	 * @param snakes is the number of snakes in the game.
	 * @param ladders is the number of ladders in the game.
	 * @param players is the number of players in the game. player greater than 0.
	 * @param score is the score of the player who won game. 
	 * @param symbol is the symbol of the player who won game. symbol != "" y symbol != null.
	 * @param otherPlayers are the symbols of the other player who plays the game.
	 * @throws IOException <br>
	 * 		   thrown if an exceptions produced by failed or interrupted I/O operations.
	 * @throws ClassNotFoundException <br>
	 * 		   thrown if exception that was raised while loading the class.
	 */

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

	/**
	 * <b>name:</b> searchInOrder. <br>
	 * Search a player. <br>
	 * <b>post:</b> A player has been searched in the data. <br>
	 * @return <code>boolean</code> specifying, is the result to search a player.
	 * @throws ClassNotFoundException <br> 
	 *         thrown if exception that was raised while loading the class.
	 * @throws IOException <br>
	 * 		   thrown if an exceptions produced by failed or interrupted I/O operations.
	 */

	public String searchInOrder() throws ClassNotFoundException, IOException {
		loadData();
		return searchInOrder(firstPlayer);
	}

	/**
	 * <b>name:</b> searchInOrder. <br>
	 * Search in order the player who won the game. <br>
	 * <b>post:</b> The player who won a game has been searched. <br>
	 * @param player is the player in the root of the binary tree.
	 * @return <code>String</code> specifying message is the format of the player who won a game.
	 */

	public String searchInOrder(BestPlayers player) {

		String message = "";

		if(player != null) {
			message += searchInOrder(player.getPrevious());
			message += player.toString();
			message += searchInOrder(player.getNext());
		}

		return message;
	}

	/**
	 * <b>name:</b> asingPosition. <br>
	 * Save the position of all the nodes. <br>
	 * <b>post:</b> the position of all the nodes has been assigned. <br>
	 * @param position is the position who put in the node. position greater than 0.
	 * @param i is the first position of the node. position != null.
	 * @param j is the second position of the node. position != null.
	 */

	private void asingPosition(int position, int i,int j) {

		Node newNode = searchNode(i,j);

		if(newNode != null) {
			asingNext(newNode,position+1);
		}
	}

	/**
	 * <b>name:</b> asingNext. <br>
	 * Is the method who move to the next node. <br>
	 * <b>post:</b> Move to the next node. <br> 
	 * @param newNode is the node that will be changing. newNode != null. 
	 * @param position is the position who put in the node. position greater than 0.
	 */

	private void asingNext(Node newNode, int position) {

		newNode.setPosition(position);

		if(newNode.getNext() != null) {
			asingNext(newNode.getNext(),position+1);
		}else if(newNode.getUp() != null){
			asingPrev(newNode.getUp(),position+1);
		}
	}

	/**
	 * <b>name:</b> asingPrev. <br>
	 * Is the method who move to the previous node. <br>
	 * <b>post:</b> Move to the previous node. <br>
	 * @param newNode is the node that will be changing. newNode != null. 
	 * @param position is the position who put in the node. position greater than 0.
	 */

	private void asingPrev(Node newNode, int position) {

		newNode.setPosition(position);

		if(newNode.getPrevious() != null) {

			asingPrev(newNode.getPrevious(),position+1);
		}else  if(newNode.getUp() != null){
			asingNext(newNode.getUp(),position+1);
		}
	}

	/**
	 * <b>name:</b> addPlayer. <br>
	 * Add a player in the binary tree. <br>
	 * <b>post:</b> The player has been added in the binary tree. <br>
	 * @param current is the player that will be changing. current != null.
	 * @param newPlayer is the player who won the game. newPlayer != null.
	 */

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

	/**
	 * <b>name:</b> addSettingSnake. <br>
	 * Add the first position of the snake. <br>
	 * <b>post:</b> the first position of the snake has been added. <br>
	 * @param snakes is the representation of the snake.
	 * @param i is the position of the snake.
	 */

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

	/**
	 * <b>name:</b> addSecondSnake. <br>
	 * Add the second position of the snake. <br>
	 * <b>post:</b> the second position of the snake has been added. 
	 * @param ladder is the node who put the second position of the snake. ladder != null.
	 * @param row is the row from which the new position is to be searched.
	 */

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

	/**
	 * <b>name:</b> addSettingLadders. <br>
	 * Add the first position of the ladder. <br>
	 * <b>post:</b> the first position of the ladder has been added. <br>
	 * @param ladders is the representation of the ladder. ladders greater than 0.
	 */

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

	/**
	 * <b>name:</b> addSecondladders. <br>
	 * Add the second position of the ladder. <br>
	 * <b>post:</b> The second position of the ladder has been added. <br>
	 * @param ladder is the representation of the ladder. ladder greater than 0.
	 * @param row is the row from which the new position is to be searched.
	 */

	private void addSecondladders(Node ladder, int row) {

		int selectedRow = (int)Math.floor(Math.random()*(matrixRows));
		int selectedCol = (int)Math.floor(Math.random()*matrixCols);

		Node searched = searchNode(selectedRow, selectedCol);

		if(searched.getRow() != row && searched.getPosition() != 1 && searched.getPosition() != (matrixCols*matrixRows) && searched.getSnake() == ' ' && searched.getLadder() == 0){

			searched.setLadder(ladder.getLadder());
		}else {
			addSecondladders(ladder,row);
		}
	}

	/**
	 * <b>name:</b> searchNode. <br>
	 * Search a node. <br>
	 * <b>post:</b> A node has been searched. <br>
	 * @param row is the row of the node to search. row greater or equals to 0.
	 * @param col is the column of the node to search. col greater or equals to 0.
	 * @return <code>Node</code> specifying is the result of find an node.
	 */

	public Node searchNode(int row, int col) {
		return searchNode(root, row, col);
	}

	/**
	 * <b>name:</b> searchNode. <br>
	 * Search a node. <br>
	 * <b>post:</b> A node has been searched. <br>
	 * @param current is the node that will be changing.
	 * @param row is the row of the node to search. row greater or equals to 0.
	 * @param col is the column of the node to search. col greater or equals to 0.
	 * @return <code>Node</code> specifying searched is the result of find an node.
	 */

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

	/**
	 * <b>name:</b> splitString. <br>
	 * Split the string with the specification of the game. <br>
	 * <b>post:</b> Split the specification of the game and add that in the game. <br>
	 * @param settings is the string with the specification of the game.
	 * @return <code>boolean</code> specifying stop is the process splitting the string.
	 */

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

				if(snakes+ladders < (int)((row*col)-2)/2) {
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

	/**
	 * <b>name:</b> asignPlayers. <br>
	 * Add symbol for the players. <br> 
	 * <b>post:</b> The symbol for the players has been added. <br>
	 * @param player is the player to whom the symbol is assigned. player != null.
	 * @param play is the number of players.
	 * @param players is the string of the players. players != null y players != "".
	 */

	private void asignPlayers(Player player,int play,String players) {

		if(player != null && play >= 0) {
			players += player.getSymbol();
			if(player.getNext() != null) {
				asignPlayers(player.getNext(), play--,players);
			}
		}
	}

	/**
	 * <b>name:</b> verifySymbols. <br>
	 * Verify the symbols of the players. <br>
	 * <b>post:</b> the symbols of the players has been verify. <br>
	 * @param players is the number of players.
	 */

	private void verifySymbols(int players) {
		if(players >= 10) {
			verify++;
		}
	}

	/**
	 * <b>name:</b> insertValuePlayer. <br>
	 * Insert the order of the players. <br>
	 * <b>post:</b> The order of the players has been added. <br>
	 * @param current is the player that will be changing.
	 * @param i is the number of iteration.
	 * @param cont is the value of the player.
	 */

	private void insertValuePlayer(Player current,int i,int cont) {

		if(current != null && cont <= i) {

			current.setPosition(cont);
			insertValuePlayer(current.getNext(), i, cont+1);
		}
	}

	/**
	 * <b>name:</b> addSettingPlayers. <br>
	 * Add the symbol of a player. <br>
	 * <b>post:</b> The symbol of the player has been added. <br>
	 * @param first is the first player.
	 * @param players is the  number of players.
	 * @param index	is the number of the iteration.
	 * @param cont is the number to add the symbol of a player.
	 */

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

	/**
	 * <b>name:</b> verifySymbols. <br>
	 * Verify the symbols of the players. <br>
	 * <b>post:</b> The symbols of the players has been verifying. <br>
	 * @param player is the player to verify the symbol.
	 * @param players is the number of players.
	 */

	private void verifySymbols(Player player,int players) {

		if(player.getNext() != null && players > 0) {
			searchSymbols(getFirst().getFirst(), player.getNext());
			verifySymbols(player.getNext(),players-1);
		}

	}

	/**
	 * <b>name:</b> addSymbols. <br>
	 * Add the symbol to a player. <br>
	 * <b>post:</b> The symbol of a player has been added. <br>
	 * @param index is the number of players. index greater than 0.
	 */

	public void addSymbols(int index) {

		selectSymbols(getFirst(),getFirst().getFirst(),index);
	}

	/**
	 * <b>name:</b> selectSymbols. <br>
	 * Select a symbol to add in a player. <br>
	 * <b>post:</b> A symbol to add in a player has been added. <br>
	 * @param first is the node with the position (0, n). first != null. 
	 * @param player is the player to add the symbol. player != null.
	 * @param index is the number of players.
	 */

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

	/**
	 * <b>name:</b> asignSymbol. <br>
	 * Add the symbol to a player.
	 * <b>post:</b> a symbol has been added to a player.
	 * @param player is the player to add the symbol.
	 * @param index is the number of players.
	 * @param symbol is the symbol to add to a player.
	 */

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

	/**
	 * <b>name:</b> searchNext. <br>
	 * Search a player by the symbol. <br>
	 * <b>post:</b> A player has been searched. <br>
	 * @param player is the player to find. 
	 * @param symbol is the symbol of the player.
	 */

	public void searchNext(Player player, char symbol) {
		if(player.getNext() == null) {
			player.setNext(new Player(symbol));
		}
	}

	/**
	 * <b>name:</b> searchSymbols. <br>
	 * Search a player by the symbol. <br>
	 * <b>post:</b> a player has been searched by the symbol. <br>
	 * @param player is the player to compared.
	 * @param current is the player to will be changing. 
	 */
	private void searchSymbols(Player player,Player current) {

		if(current != null && player != null && player.getSymbol() == (current.getSymbol()) && player.getNext() != current.getNext()) {
			verify++;

		}else if(player != null && player.getNext() != null) {
			searchSymbols(player.getNext(),current);
		}

	}

	/**
	 * <b>name:</b> searchSymbolsAzar. <br>
	 * Select a symbol at random. <br>
	 * <b>post:</b> a random symbol has been selected. <br>
	 * @param player is the player to compared.
	 * @param current is the player to will be changing. 
	 */
	private void searchSymbolsAzar(Player player,Player current) {

		if(current != null && player != null && player.getSymbol() == (current.getSymbol())) {
			verify++;

		}else if(player != null && player.getNext() != null) {
			searchSymbolsAzar(player.getNext(),current);
		}
	}

	/**
	 * <b>name:</b> moveplayer. <br>
	 * Send to search to a player for move. <br>
	 * <b>post:</b> A message have the move of the player. <br>
	 * @return current is the player to will be changing. 
	 */
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

	/**
	 * <b>name:</b> moveplayer. <br>
	 * Send to search to a player for move. <br>
	 * <b>post:</b> A message have the move of the player. <br>
	 * @param current. Player that is used for compared with other player found.
	 * @return current is the player to will be changing. 
	 */
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
	 *<b>name:</b>  asignName.
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
	 *<b>name:</b>  move.
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
	 *<b>name:</b>  movePlayer.
	 *Send a player found to search their position.<br> 
	 *<b> post: </b> The position of the player has been found.
	 *@param player. Player that is wanted for can move.
	 *@param position. Position when the player will be wanted.
	 */
	private void movePlayerNode(Player player, int position) {
		movePlayerNode(getFirst(),player,position);
	}

	/** 
	 *<b>name:</b>  movePlayerNode.
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
	 *<b>name:</b>  movePlayerPrev.
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
	 *<b>name:</b>  movePlayerNodeUp.
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
	 *<b>name:</b>  movePlayerNodeUp.
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
	 *<b>name:</b>  moveInOrder.
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
	 *<b>name:</b>  findSnakesAndLadders.
	 *Search the snakes and ladders with the method nextSnakesAndLadders.<br> 
	 *<b> post: </b> The method has send to search the snakes and ladders.
	 *@param current. Node where will be wanted the snakes and ladders.
	 *@param player. Player that is moved depending if in the node there a snake or ladder.
	 */
	private void findSnakesAndLadders(Node current, Player player) {
		nextSnakesAndLadders(getFirst(),current,player);
	}

	/** 
	 *<b>name:</b>  nextSnakesAndLadders.
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
	 *<b>name:</b>  searchLadderNext.
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
	 *<b>name:</b>  searchLadderPrev.
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
	 *<b>name:</b>  prevSanakesAndLadders.
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
	 *<b>name:</b>  searchNode.
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
	 *<b>name:</b>  nextNode.
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
	 *<b>name:</b>  prevNode.
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
	 *<b>name:</b>  searchPlayer.
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
	 *<b>name:</b>  findPlayer.
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
	 *<b>name:</b>  findNext.
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
	 *<b>name:</b>  findNext.
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
	 *<b>name:</b>  findPlayerToMove.
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
	 *<b>name:</b>  findPlayerCurrent.
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
			player = findPlayerCurrent(position,current.getNext());
		}

		return player;
	}

	/** 
	 *<b>name:</b>  random.
	 *Choose a number between 1 and 6 for the dice.<br> 
	 *<b> post: </b> A number has been assigns.
	 *@return int random. Chosen number.
	 */
	public int random() {

		int random = (int) Math.floor(Math.random()*6+1);

		return random;
	}

	/** 
	 *<b>name:</b>  getPlayer.
	 *Get the value of the node root with scores.<br> 
	 *<b> post: </b> Root with the scores.
	 *@return Node root. This is the node that have the scores.
	 */
	public Node getRoot() {
		return root;
	}

	/** 
	 *<b>name:</b>  setRoot.
	 *Change the root of the node for the scores.<br> 
	 *<b> post: </b> New root of the nodes.
	 *@param root. Node that will be the root of the nodes.
	 */
	public void setRoot(Node root) {
		this.root = root;
	}

	/** 
	 *<b>name:</b>  getMatrixRows.
	 *Get the rows of the matrix.<br> 
	 *<b> post: </b> Values of the rows of the matrix.
	 *@return int matrixRows. This is the rows of the matrix.
	 */
	public int getMatrixRows() {
		return matrixRows;
	}

	/** 
	 *<b>name:</b>  setMatrixRows.
	 *Change the rows of the matrix.<br> 
	 *<b> post: </b> New rows of the matrix.
	 *@param matrixRows. Rows that will be new rows of the matrix.
	 */
	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}

	/** 
	 *<b>name:</b>  getMatrixCols.
	 *Get the columns of the matrix.<br> 
	 *<b> post: </b> Values of the columns of the matrix.
	 *@return int getMatrixCols. This is the columns of the matrix.
	 */
	public int getMatrixCols() {
		return matrixCols;
	}

	/** 
	 *<b>name:</b>  setMatrixCols.
	 *Change the columns of the matrix.<br> 
	 *<b> post: </b> New columns of the matrix.
	 *@param matrixCols. Columns that will be new columns of the matrix.
	 */
	public void setMatrixCols(int matrixCols) {
		this.matrixCols = matrixCols;
	}

	/** 
	 *<b>name:</b>  firstPlayer.
	 *Get the first player of the root of nodes.<br> 
	 *<b> post: </b> First player of the root.
	 *@return BestPlayers firstPlayer. This is first player of the root.
	 */
	public BestPlayers getFirstPlayer() {
		return firstPlayer;
	}

	/** 
	 *<b>name:</b>  setFirstPlayer.
	 *Change the first player of the root.<br> 
	 *<b> post: </b> New first player of the root.
	 *@param firstPlayer. First player of the root.
	 */
	public void setFirstPlayer(BestPlayers firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	/** 
	 *<b>name:</b>  getSnakes.
	 *Get the snakes that there in game.<br> 
	 *<b> post: </b> Snakes in the game.
	 *@return int snakes. This are the snakes of the game.
	 */
	public int getSnakes() {
		return snakes;
	}

	/** 
	 *<b>name:</b>  setSnakes.
	 *Change the snakes of the game.<br> 
	 *<b> post: </b> New snakes in the game.
	 *@param snakes. New snakes for the game.
	 */
	public void setSnakes(int snakes) {
		this.snakes = snakes;
	}

	/** 
	 *<b>name:</b>  getLadders.
	 *Get the ladders that there in game.<br> 
	 *<b> post: </b> Ladders in the game.
	 *@return int ladders. This are the ladders of the game.
	 */
	public int getLadders() {
		return ladders;
	}

	/** 
	 *<b>name:</b>  setLadders.
	 *Change the ladders of the game.<br> 
	 *<b> post: </b> New ladders in the game.
	 *@param ladders. New ladders for the game.
	 */
	public void setLadders(int ladders) {
		this.ladders = ladders;
	}

	/** 
	 *<b>name:</b>  getPlayers.
	 *Get the players that there in game.<br> 
	 *<b> post: </b> Players in the game.
	 *@return int players. This are the players of the game.
	 */
	public int getPlayers() {
		return players;
	}

	/** 
	 *<b>name:</b>  setPlayers.
	 *Change the players of the game.<br> 
	 *<b> post: </b> New players in the game.
	 *@param players. New players for the game.
	 */
	public void setPlayers(int players) {
		this.players = players;
	}

	/** 
	 *<b>name:</b>  getSymbols.
	 *Get the symbols that there in game.<br> 
	 *<b> post: </b> Symbols in the game.
	 *@return String symbols. This are the symbols of the game.
	 */
	public String getSymbols() {
		return symbols;
	}

	/** 
	 *<b>name:</b>  setSymbols.
	 *Change the symbols of the game.<br> 
	 *<b> post: </b> New symbols in the game.
	 *@param symbols. New symbols for the game.
	 */
	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}

	/** 
	 *<b>name:</b>  getVerify.
	 *Get the value of verify.<br> 
	 *<b> post: </b> Value of verify.
	 *@return int verify. This is the value of verify.
	 */
	public static int getVerify() {
		return verify;
	}

	/** 
	 *<b>name:</b>  setVerify.
	 *Change the value of verify.<br> 
	 *<b> post: </b> New value of verify.
	 *@param verify. New the value of verify.
	 */
	public static void setVerify(int verify) {
		SnakesAndLadders.verify = verify;
	}

	/** 
	 *<b>name:</b>  getNumberPlayer.
	 *Get the number of Players in the game.<br> 
	 *<b> post: </b> The number of Players in the game.
	 *@return int numberPlayer. This is the number of Players in the game.
	 */
	public static int getNumberPlayer() {
		return numberPlayer;
	}

	/** 
	 *<b>name:</b>  setVerify.
	 *Change the number of Players in the game.<br> 
	 *<b> post: </b> New number of Players in the game.
	 *@param numberPlayer. New number of Players in the game.
	 */
	public static void setNumberPlayer(int numberPlayer) {
		SnakesAndLadders.numberPlayer = numberPlayer;
	}

	/** 
	 *<b>name:</b>  getNumberPlayerVerify.
	 *Get the value for verify the number of Players in the game.<br> 
	 *<b> post: </b> The value for verify the number of Players in the game.
	 *@return int numberPlayerVerify. This is the value for verify the number of Players in the game.
	 */
	public static int getNumberPlayerVerify() {
		return numberPlayerVerify;
	}

	/** 
	 *<b>name:</b>  setNumberPlayerVerify.
	 *Change the value for verify the number of Players in the game.<br> 
	 *<b> post: </b> New value for verify the number of Players in the game.
	 *@param numberPlayerVerify. New the value for verify the number of Players in the game.
	 */
	public static void setNumberPlayerVerify(int numberPlayerVerify) {
		SnakesAndLadders.numberPlayerVerify = numberPlayerVerify;
	}

	/** 
	 *<b>name:</b>  toString.
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
	 *<b>name:</b>  toStringRow.
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
	 *<b>name:</b>  toStringCol.
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
	 *<b>name:</b>  toStringScoreTable.
	 *Show the score table of the game.<br> 
	 *<b> post: </b> Score table of the players of the game.
	 *@throws IOException <br>
	 *		thrown if...
	 *	1. A local file that was no longer available is being read.
	 *	2. Any process closed the stream while a stream is being used to read data.
	 *  3. The disk space was no longer available while trying to write to a file.
	 *@throws ClassNotFoundException <br>
	 * 		thrown if the path of file wasn't found. <br>   	     
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
	 *<b>name:</b>  toStringScores.
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
	 *<b>name:</b>  toString2.
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
	 *<b>name:</b>  toStringRow2.
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
	 *<b>name:</b>  toStringCol2.
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
	 *<b>name:</b>  getFirst.
	 *Get the first node of the nodes.<br> 
	 *<b> post: </b> The first node.
	 *@return Node searchNode. This is the message where is the first node.
	 */
	private Node getFirst() {
		return searchNode(matrixRows - 1, 0);
	}

	/** 
	 *<b>name:</b>  loadData.
	 *Load the data of all winning players.<br> 
	 *<b> post: </b> The data has been loaded.
	 *@return <code>boolean</code> verify. This boolean have a value if the data was loaded. If was loaded, verify is true. 	
	 */
	public boolean loadData() {

		File scores = new File(SAVE_PATH_FILE_PEOPLE);
		boolean verify = true;

		if(scores.exists()){
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scores));;
				firstPlayer = (BestPlayers) ois.readObject();

				ois.close();
			}catch(ClassNotFoundException | IOException r) {
				verify = false;
			}
		}
		return verify;
	}

	/** 
	 *<b>name:</b>  saveData.
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
