package main;


import java.util.Scanner;

import service.CitanjeAranzmana;
import service.UpravljanjeKorisnicima;
import view.LoginWindow;

public class TuristickaAgencijaMain {
	
	public static void main(String[] args) {
		UpravljanjeKorisnicima.ucitajKorisnike();
		CitanjeAranzmana.ucitajAranzmane();

		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);

	}

}
