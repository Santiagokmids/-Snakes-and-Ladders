package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.SnakesAndLadders;

public class Main {

	private SnakesAndLadders snakesAndLader;
	private static BufferedReader br;

	public Main() {
		snakesAndLader = new SnakesAndLadders();
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void main(String[] args) throws IOException{
		Main objMain = new Main();
		objMain.startApp();
	}

	public void startApp() throws IOException{
		System.out.println("==================================\n            BIENVENIDOS  \n==================================\n");
		menu();
	}
	
	public void menu() throws IOException {
		System.out.println("----------------------------------"
				+ "\n      MENU DE OPCIONES\n----------------------------------\n1. Iniciar juego\n2. Puntuaciones\n"
				+ "3. Salir de la aplicación\n4. Creditos\n----------------------------------");
		selectOption();
	}
	
	public void selectOption() throws IOException {
		
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
					//metodo mostar posiciones
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
				System.out.println("\nGracias por usar el programa :D.");
			}
			
		}catch(NumberFormatException nfe) {
			System.out.println("Ingrese un numero como opcion");
			selectOption();
		}
	}
	
	public void selectOpt1() {
		System.out.println("\nIngrese los siguientes datos separandolos por espacios:\n"
				+ "Numero de filas, numero de columnas, numero de serpientes, numero de escaleras, numero de jugadores, simbolos de cada jugador\n"
				+ "--- Los simbolos de cada jugador van juntos, y si no agrega ninguno, se seleccionaran de forma aleatoria ---\n"
				+ "\n* SIMBOLOS QUE PUEDE USAR:  |, *, !, O, X, %, $, #, +, &\n");
	}
}

