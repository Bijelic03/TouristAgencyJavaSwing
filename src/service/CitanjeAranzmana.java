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
import model.TipoviAranzmana;
import model.TipoviSmestaja;
import model.Uloga;

public class CitanjeAranzmana {
	public static ArrayList<Aranzman> aranzmani;

	public static ArrayList<Aranzman> ucitajAranzmane() {
		aranzmani = new ArrayList<Aranzman>();

		try {
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			aranzmani.clear();
			try (BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile))) {
				String line;
				while ((line = reader.readLine()) != null) {

					String[] lineSplit = line.split("\\|");

					String idStr = lineSplit[0];
					long id = Long.parseLong(idStr);

					String turistickiAgentIdStr = lineSplit[1];
					Long turistickiAgentId = Long.parseLong(turistickiAgentIdStr);
					Osoba turistickiAgent = UpravljanjeKorisnicima.getKorisnikById(turistickiAgentId);

					String tipAranzmanaStr = lineSplit[2];
					TipoviAranzmana tipAranzmana = TipoviAranzmana.valueOf(tipAranzmanaStr);

					String tipSmestajaStr = lineSplit[3];
					TipoviSmestaja tipSmestaja = TipoviSmestaja.valueOf(tipSmestajaStr);

					String datumPocetkaStr = lineSplit[4];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
					LocalDate datumPocetka = LocalDate.parse(datumPocetkaStr, formatter);

					String kapacitetStr = lineSplit[5];
					int kapacitet = Integer.parseInt(kapacitetStr);

					String cenaPoDanuPoOsobiStr = lineSplit[6];
					double cenaPoDanuPoOsobi = Double.parseDouble(cenaPoDanuPoOsobiStr);

					String popustStr = lineSplit[7];
					int popust = Integer.parseInt(popustStr);

					String adresaSlike = lineSplit[8];

					String aktivnostStr = lineSplit[9];
					boolean aktivnost = Boolean.parseBoolean(aktivnostStr);

					Aranzman aranzman = new Aranzman(id, turistickiAgent, tipAranzmana, tipSmestaja, datumPocetka,
							kapacitet, cenaPoDanuPoOsobi, popust, adresaSlike, aktivnost);

					if (aranzman.getAktivnost() && UpravljanjeKorisnicima.prijavljenaOsoba != null) {
						if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Administrator
								|| UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Turista) {
							aranzmani.add(aranzman);
						} else {
							if (aranzman.getTuristickiAgent().getId() == UpravljanjeKorisnicima.prijavljenaOsoba
									.getId()) {
								aranzmani.add(aranzman);

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
		return aranzmani;

	}
}
