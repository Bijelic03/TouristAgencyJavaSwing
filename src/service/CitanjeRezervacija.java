package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.Aranzman;
import model.Osoba;
import model.Rezervacija;
import model.StatusRezervacije;
import model.Uloga;

public class CitanjeRezervacija {
	public static ArrayList<Rezervacija> rezervacije;

	public static ArrayList<Rezervacija> ucitajRezervacije() {
		rezervacije = new ArrayList<Rezervacija>();

		try {
			File rezervacijeFile = new File("podaci/rezervacije.txt");
			rezervacije.clear();
			try (BufferedReader reader = new BufferedReader(new FileReader(rezervacijeFile))) {
				String line;
				while ((line = reader.readLine()) != null) {

					String[] lineSplit = line.split("\\|");

					String idStr = lineSplit[0];
					long id = Long.parseLong(idStr);

					String turistaIdStr = lineSplit[1];
					long turistaId = Long.parseLong(turistaIdStr);

					String aranzmanIdStr = lineSplit[2];
					long aranzmanId = Long.parseLong(aranzmanIdStr);

					Aranzman aranzman = UpravljanjeAranzmanima.getAranzmanById(aranzmanId);
					if(aranzman == null) {
						continue;
					}
					Osoba turista = UpravljanjeKorisnicima.getKorisnikById(turistaId);

					String brojPutnikaStr = lineSplit[3];
					int brojPutnika = Integer.parseInt(brojPutnikaStr);

					String cenaStr = lineSplit[4];
					double cena = Double.parseDouble(cenaStr);

					String datumKreiranjaStr = lineSplit[5];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
					LocalDate datumKreiranja = LocalDate.parse(datumKreiranjaStr, formatter);

					String brojDanaStr = lineSplit[6];
					int brojDana = Integer.parseInt(brojDanaStr);
					
					
					
					
					String statusStr = lineSplit[7];
					StatusRezervacije statusRezervacije = StatusRezervacije.valueOf(statusStr);
					Rezervacija rezervacija = new Rezervacija(id, turista, aranzman, brojPutnika, cena, datumKreiranja,
							brojDana, statusRezervacije);

					if (UpravljanjeKorisnicima.prijavljenaOsoba != null) {

						if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Administrator) {
							rezervacije.add(rezervacija);
						} else if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.TuristickiAgent) {
							if (rezervacija.getAranzman().getTuristickiAgent()
									.getId() == UpravljanjeKorisnicima.prijavljenaOsoba.getId()) {
								rezervacije.add(rezervacija);
							}
						} else if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Turista) {
							if (rezervacija.getTurista().getId() == UpravljanjeKorisnicima.prijavljenaOsoba.getId()) {
								rezervacije.add(rezervacija);

							}

						}
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
		return rezervacije;
	}
}
