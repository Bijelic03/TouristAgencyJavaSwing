package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import model.Osoba;
import model.Rezervacija;
import model.StatusRezervacije;

public class UpravljanjeRezervacijama {

	public static String[][] getPodaciORezervacijamaTabela() {
		int brojAktivnihRezervacija = 0;
		for (Rezervacija rezervacija : CitanjeRezervacija.ucitajRezervacije()) {

			brojAktivnihRezervacija++;

		}

		String[][] podaci = new String[brojAktivnihRezervacija][9];
		int indeks = 0;

		for (Rezervacija rezervacija : CitanjeRezervacija.ucitajRezervacije()) {

			podaci[indeks][0] = String.valueOf(rezervacija.getId());
			podaci[indeks][1] = String.valueOf(rezervacija.getAranzman().getTuristickiAgent().getImePrezime());
			podaci[indeks][2] = String.valueOf(rezervacija.getAranzman().getId());
			podaci[indeks][3] = String.valueOf(rezervacija.getBrojPutnika());
			podaci[indeks][4] = String.valueOf(rezervacija.getCena());
			podaci[indeks][5] = rezervacija.getAranzman().getDostupanDatum().format(DateTimeFormatter.ofPattern("d.M.y"));
			podaci[indeks][6] = String.valueOf(rezervacija.getBrojDana());
			podaci[indeks][7] = rezervacija.getDatumkreiranja().format(DateTimeFormatter.ofPattern("d.M.y"));
			podaci[indeks][8] = rezervacija.getStatusRezervacije().toString();
			indeks++;

		}

		return podaci;
	}
	
	public static Rezervacija getRezervacijaById(Long id) {
		for(Rezervacija rezervacija : CitanjeRezervacija.rezervacije) {
			if(rezervacija.getId() == id) {
				return rezervacija;
			}
		}
		return null;
	}
	
	
	public static void izmeniStatusRezervacije(Long id, StatusRezervacije status) {
		Rezervacija rezervacijaZaIzmenu = getRezervacijaById(id);
		if (rezervacijaZaIzmenu != null) {
			try {
				File rezervacijeFile = new File("podaci/rezervacije.txt");
				File tempFile = new File("podaci/rezervacije_temp.txt");

				BufferedReader reader = new BufferedReader(new FileReader(rezervacijeFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

				String line;

				while ((line = reader.readLine()) != null) {
					String[] tokens = line.split("\\|");
					Long currentId = Long.parseLong(tokens[0]);

					if (currentId.equals(id)) {
						line = tokens[0] + "|" + tokens[1] + "|" + tokens[2] + "|" + tokens[3] + "|" + tokens[4] + "|"
								+ tokens[5] + "|" + tokens[6] + "|" + status.toString();
					}
					writer.write(line);
					writer.newLine();
				}

				reader.close();
				writer.close();

				// Zamena originalne datoteke sa privremenom datotekom
				rezervacijeFile.delete();
				tempFile.renameTo(rezervacijeFile);

				System.out.println("Rezervacija je stornirana.");
			} catch (IOException e) {
				System.out.println("Greska prilikom storniranja rezervacije: " + e.getMessage());
			}
		} else {
			System.out.println("Rezervacija sa ID-jem " + id + " ne postoji.");
		}
	}


}