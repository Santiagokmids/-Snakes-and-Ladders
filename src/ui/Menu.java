package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.SnakesAndLadders;

public class Menu {

	public final static String MENU = "menu";
	public final static String SIMUL = "simul";
	public final static String NUM ="num";

	private static BufferedReader br;
	private SnakesAndLadders snakesAndLader;

	public Menu() throws IOException, InterruptedException, ClassNotFoundException {
		snakesAndLader = new SnakesAndLadders(0, 0);
		br = new BufferedReader(new InputStreamReader(System.in));
		startApp();
	}

	public void startApp() throws IOException, InterruptedException, ClassNotFoundException{
		System.out.println("==================================\n           BIENVENIDOS  \n==================================\n");
		menu();
	}

	public void menu() throws IOException, InterruptedException, ClassNotFoundException {
		System.out.println("----------------------------------"
				+ "\n      MENU DE OPCIONES\n----------------------------------\n1. Iniciar juego\n2. Puntuaciones\n"
				+ "3. Salir de la aplicación\n4. Creditos\n----------------------------------");
		selectOption();
	}

	public void selectOption() throws IOException, InterruptedException, ClassNotFoundException {

		System.out.println("\nIngrese un numero para elegir la opcion");
		String number = br.readLine();

		try {
			int opt = Integer.parseInt(number);

			if(opt != 3) {

				switch(opt) {
				case 1:
					selectOpt1();
					menu();
					break;

				case 2:
					scores();
					menu();
					break;

				case 4:
					System.out.println("\n==================================\n            CREDITOS  \n==================================\n"
							+ "  TRABAJO REALIZADO POR:\n\nLuis Miguel Ossa Arias\nSantiago Trochez Velasco\nAdicional: Juan Manuel Reyes\n");
					menu();
					break;

				default:
					System.out.println("Ingreso un numero NO VALIDO.");
					selectOption();
				}
			}else {
				System.out.println("\nGracias por usar la aplicacion :D.");
			}

		}catch(NumberFormatException nfe) {
			System.out.println("Ingrese un numero como opcion");
			selectOption();
		}
		br.close();
	}

	public void selectOpt1() throws IOException, InterruptedException, ClassNotFoundException {

		System.out.println("\nIngrese los siguientes datos separandolos por espacios:\n"
				+ "Numero de filas, numero de columnas, numero de serpientes, numero de escaleras, numero de jugadores, simbolos de cada jugador\n"
				+ "--- Los simbolos de cada jugador van juntos, y si no agrega ninguno, se seleccionaran de forma aleatoria. No se pueden repetir simbolos ---\n"
				+ "\n* SIMBOLOS QUE PUEDE USAR: *, !, O, X, %, $, #, +, &\n");

		String setting = br.readLine();
		boolean verify = snakesAndLader.splitString(setting);

		if(!verify) {
			System.out.println("Ingreso un valor INVALIDO");
			selectOpt1();
		}else {
			System.out.println(snakesAndLader.toString());
			gamePlay();
		}
	}

	public void gamePlay() throws InterruptedException, IOException, ClassNotFoundException {

		String nextLine = br.readLine();

		if(nextLine.isEmpty()) {
			String message =snakesAndLader.moveplayer();

			if(message.substring(message.length()-2).equals("s\n")) {
				System.out.println(message);
				System.out.println("Esciba el nickName del ganador\n");
				String nick = br.readLine();
				snakesAndLader.asignName(nick);
				
			}else {
				System.out.println(message);
				System.out.println(snakesAndLader.toString2());
				gamePlay();
			}

		}else if(nextLine.equalsIgnoreCase(NUM)) {
			System.out.println(snakesAndLader.toString());
			gamePlay();
		}else if(nextLine.equalsIgnoreCase(SIMUL)) {
			simulation();
		}else if(nextLine.equalsIgnoreCase(MENU)) {
			menu();
		}else {
			System.out.println("Ingreso una opcion invalida, ingrese nuevamente");
			gamePlay();
		}
	}

	private void simulation() throws InterruptedException, IOException, ClassNotFoundException {

		String message = snakesAndLader.moveplayer();

		if(message.substring(message.length()-2).equals("s\n")) {
			System.out.println(message);
			System.out.println("Esciba el nickName del ganador\n");
			String nick = br.readLine();
			snakesAndLader.asignName(nick);
		}else {
			System.out.println(message);
			System.out.println(snakesAndLader.toString2());
			Thread.sleep(2000);
			simulation();
		}
	}

	public void scores() throws ClassNotFoundException, IOException {

		String message;

			message = snakesAndLader.searchInOrder();
			boolean verify = snakesAndLader.loadData();
			if(!verify) {
				message = "NO HAY PUNTUACIONES GUARDADAS";
			}
			
		System.out.println("-----------------------------------"
				+ "\n           PUNTUACIONES\n-----------------------------------\n"+ message +"\n-----------------------------------");
	}
}
