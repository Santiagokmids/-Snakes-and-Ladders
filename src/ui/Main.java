package ui;

import java.io.IOException;

public class Main {
	
	private static Menu menu;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		Main objMain = new Main();
		menu = new Menu();
		objMain.getMenu();
	}

	public Menu getMenu() {
		return menu;
	}
}