package controller;

import java.awt.Window;

import model.Osoba;
import view.AdminWindow;
import view.LoginWindow;
import view.TuristickiAgentWindow;
import view.TuristaWindow;

public class UlogaController {

	public static void prikaziUlogu(Osoba trenutnaOsoba) {

		Window window = LoginWindow.getWindows()[0];

		if (trenutnaOsoba != null) {
			window.dispose();

			switch (trenutnaOsoba.getUloga()) {
				case Administrator: {
					AdminWindow adminWindow = new AdminWindow();
					adminWindow.setVisible(true);
					break;
				}
				case TuristickiAgent: {
					TuristickiAgentWindow turistickiAgentWindow = new TuristickiAgentWindow();
					turistickiAgentWindow.setVisible(true);
					break;
				}
				case Turista: {
					TuristaWindow turistaWindow = new TuristaWindow();
					turistaWindow.setVisible(true);
					break;
				}
			}
		} else {
		}
	}
}
