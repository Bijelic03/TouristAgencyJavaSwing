package main;


import java.util.Scanner;

import service.UpravljanjeKorisnicima;
import service.UpravljanjeTuristickimAgencijama;
import view.LoginWindow;

public class TuristickaAgencijaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UpravljanjeKorisnicima UpravljanjeKorisnicima = new UpravljanjeKorisnicima();
		UpravljanjeTuristickimAgencijama UpravljanjeTuristickimAgencijama = new UpravljanjeTuristickimAgencijama();
		UpravljanjeKorisnicima.ucitajKorisnike();
		UpravljanjeTuristickimAgencijama.ucitajAgencije();

			LoginWindow loginWindow = new LoginWindow();
			loginWindow.setVisible(true);
			
	
		
	


	}

}
