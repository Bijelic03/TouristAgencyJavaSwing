package main;


import java.util.Scanner;

import service.UpravljanjeKorisnicima;
import view.LoginWindow;

public class TuristickaAgencijaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UpravljanjeKorisnicima UpravljanjeKorisnicima = new UpravljanjeKorisnicima();
		UpravljanjeKorisnicima.ucitajKorisnike();
		

			LoginWindow loginWindow = new LoginWindow();
			loginWindow.setVisible(true);
			
	
		
	


	}

}
