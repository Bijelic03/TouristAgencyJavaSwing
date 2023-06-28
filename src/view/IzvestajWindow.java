package view;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Rezervacija;
import model.StatusRezervacije;
import service.CitanjeRezervacija;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;

public class IzvestajWindow extends JFrame {

	private double ukupnaZarada;
	private String[] aranzmaniColumnNames = { "Id", "Agent", "Tip aranzmana", "Vrsta smestaja", "Dostupan datum",
			"Kapacitet", "Cena po danu", "Sajamski popust" };

	public IzvestajWindow(Date datumOd, Date datumDo) {
		setTitle("Izveštaj");
		setSize(800, 600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		obradaRezervacija();
		TableGenerator aranzmaniTable = new TableGenerator(UpravljanjeAranzmanima.getPodaciOAranzmanimaTabela(),
				aranzmaniColumnNames);
		add(aranzmaniTable, BorderLayout.CENTER);

	}

	public void obradaAranzmana() {

	}

	public void obradaRezervacija() {

		for (Rezervacija rezervacija : CitanjeRezervacija.ucitajRezervacije()) {
			if (rezervacija.getStatusRezervacije() == StatusRezervacije.Zavrsena && rezervacija.getAranzman()
					.getTuristickiAgent().getId() == UpravljanjeKorisnicima.prijavljenaOsoba.getId()) {
				ukupnaZarada += rezervacija.getCena();

			}
		}
		System.out.println(ukupnaZarada);
	}

}
