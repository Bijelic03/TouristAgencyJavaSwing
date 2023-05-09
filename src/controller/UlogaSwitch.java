package controller;

import java.awt.Window;

import model.Osoba;
import view.AdminWindow;
import view.LoginWindow;
import view.TuristickiAgentWindow;
import view.TuristaWindow;

public class UlogaSwitch {
	public static void prikaziUlogu(Osoba trenutnaOsoba) {
		
		Window window = LoginWindow.getWindows()[0];

	if(trenutnaOsoba != null) {
		window.dispose();

		switch (trenutnaOsoba.getUloga()) {
		case Administrator:{
			AdminWindow adminWindow = new AdminWindow();
			adminWindow.setVisible(true);
			System.out.println("Administrator");
			break;
		}
		case TuristickiAgent:{
			TuristickiAgentWindow turistickiAgentWindow = new TuristickiAgentWindow();
			turistickiAgentWindow.setVisible(true);
			System.out.println("Turisticki agent");
			break;
		}
		case Turista:{
			TuristaWindow turistaWindow = new TuristaWindow();
			turistaWindow.setVisible(true);
			System.out.println("Turista");
			break;
		}
		}
		
		System.out.println("Uspesno prijavljen!");
	} else {
		System.out.println("Niste uneli tacne podatke. Pokusajte ponovo.");
	}
}}
