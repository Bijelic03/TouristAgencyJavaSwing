package main;


import java.util.Scanner;

import service.UpravljanjeKorisnicima;
import view.LoginWindow;

public class TuristickaAgencijaMain {
	
	public static void main(String[] args) {
		UpravljanjeKorisnicima.ucitajKorisnike();

		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);

	}

}
