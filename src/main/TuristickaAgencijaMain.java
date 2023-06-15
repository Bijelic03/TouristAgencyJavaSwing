package main;

import java.util.Scanner;

import model.Aranzman;
import service.CitanjeAranzmana;
import service.CitanjeRezervacija;
import service.UpravljanjeKorisnicima;
import view.LoginWindow;

public class TuristickaAgencijaMain {

	public static void main(String[] args) {
		UpravljanjeKorisnicima.ucitajKorisnike();
		CitanjeAranzmana.ucitajAranzmane();
		CitanjeRezervacija.ucitajRezervacije();
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);

	}

}
