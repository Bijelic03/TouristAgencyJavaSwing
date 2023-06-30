package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import model.Aranzman;
import model.Osoba;
import model.Rezervacija;
import model.StatusRezervacije;
import view.AranzmanCard;
import java.text.SimpleDateFormat;

public class UpravljanjeAranzmanima {

	public static Aranzman currentAranzman;

	private static Long currentId;

	public static void dodajAranzman(Aranzman aranzman) {
		try {
			String datum = aranzman.getDostupanDatum().getDayOfMonth() + "."
					+ aranzman.getDostupanDatum().getMonthValue() + "." + aranzman.getDostupanDatum().getYear();
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(aranzmaniFile, true));
			writer.write(
					aranzman.getId() + "|" + aranzman.getTuristickiAgent().getId() + "|" + aranzman.getTipAranzmana()
							+ "|" + aranzman.getTipSmestaja() + "|" + datum + "|" + aranzman.getKapacitet() + "|"
							+ aranzman.getCenaPoDanuPoOsobi() + "|" + aranzman.getSajamskiPopust() + "|"
							+ aranzman.getPutanjaDoSlike() + "|" + aranzman.getAktivnost());
			writer.newLine();
			writer.close();
			CitanjeAranzmana.aranzmani.add(aranzman);
		} catch (IOException e) {
			System.out.println("Greska prilikom upisivanja korisnika u datoteku: " + e.getMessage());
		}
	}

	public static void editAranzman(Aranzman aranzman) {
		Aranzman aranzmanZaIzmenu = aranzman;

		try {
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			File tempFile = new File("podaci/aranzmani_temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			int currentLine = 0;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\|");
				long currentId = Long.parseLong(tokens[0]);

				if (currentId == aranzman.getId()) {
					// Izmena linije
					String datum = aranzmanZaIzmenu.getDostupanDatum().getDayOfMonth() + "."
							+ aranzmanZaIzmenu.getDostupanDatum().getMonthValue() + "."
							+ aranzmanZaIzmenu.getDostupanDatum().getYear();
					line = aranzmanZaIzmenu.getId() + "|" + aranzmanZaIzmenu.getTuristickiAgent().getId() + "|"
							+ aranzmanZaIzmenu.getTipAranzmana() + "|" + aranzmanZaIzmenu.getTipSmestaja() + "|" + datum
							+ "|" + aranzmanZaIzmenu.getKapacitet() + "|" + aranzmanZaIzmenu.getCenaPoDanuPoOsobi()
							+ "|" + aranzmanZaIzmenu.getSajamskiPopust() + "|" + aranzmanZaIzmenu.getPutanjaDoSlike()
							+ "|" + aranzmanZaIzmenu.getAktivnost();
				}
				writer.write(line);
				writer.newLine();
				currentLine++;
			}

			reader.close();
			writer.close();

			// Zamena originalnog fajla sa privremenim fajlom
			aranzmaniFile.delete();
			tempFile.renameTo(aranzmaniFile);

		} catch (IOException e) {
			System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
		}
	}

	public static Aranzman getAranzmanById(long id) {
		for (Aranzman aranzman : CitanjeAranzmana.ucitajAranzmane()) {
			if (aranzman.getId() == id) {
				return aranzman;
			}
		}
		return null;
	}

	public static void disableAranzman() {
		if (AranzmanCard.selectedCard != null) {
			currentAranzman.setAktivnost(false);
			try {
				File aranzmaniFile = new File("podaci/aranzmani.txt");
				File tempFile = new File("podaci/aranzmani_temp.txt");

				String datum = currentAranzman.getDostupanDatum().getDayOfMonth() + "."
						+ currentAranzman.getDostupanDatum().getMonthValue() + "."
						+ currentAranzman.getDostupanDatum().getYear();

				BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

				String line;

				while ((line = reader.readLine()) != null) {
					String[] tokens = line.split("\\|");
					currentId = Long.parseLong(tokens[0]);

					if (currentId == currentAranzman.getId()) {
						line = currentAranzman.getId() + "|" + currentAranzman.getTuristickiAgent().getId() + "|"
								+ currentAranzman.getTipAranzmana() + "|" + currentAranzman.getTipSmestaja() + "|"
								+ datum + "|" + currentAranzman.getKapacitet() + "|"
								+ currentAranzman.getCenaPoDanuPoOsobi() + "|" + currentAranzman.getSajamskiPopust()
								+ "|" + currentAranzman.getPutanjaDoSlike() + "|" + currentAranzman.getAktivnost();
					}
					writer.write(line);
					writer.newLine();
				}

				reader.close();
				writer.close();

				// Zamena originalnog fajla sa privremenim fajlom
				aranzmaniFile.delete();
				tempFile.renameTo(aranzmaniFile);

				AranzmanCard.selectedCard.deleteCard();
			} catch (IOException e) {
				System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
			}
		}
	}

	public static boolean moguceIzmena(Long idAranzmana) {

		for (Rezervacija rezervacija : CitanjeRezervacija.ucitajRezervacije()) {

			if (rezervacija.getAranzman().getId() == idAranzmana
					&& rezervacija.getStatusRezervacije() == StatusRezervacije.Kreirana) {
				return false;
			}

		}
		return true;
	}

	public static String[][] getPodaciOAranzmanimaTabela() {
		int aktivniAranzmani = 0;
		CitanjeAranzmana.ucitajAranzmane();
		for (Aranzman aranzman : CitanjeAranzmana.aranzmani) {
			if (aranzman.getAktivnost()) {
				aktivniAranzmani++;
			}
		}

		String[][] podaci = new String[aktivniAranzmani][8];
		int indeks = 0;

		for (Aranzman aranzman : CitanjeAranzmana.aranzmani) {
			if (aranzman.getAktivnost()) {
				podaci[indeks][0] = String.valueOf(aranzman.getId());
				podaci[indeks][1] = aranzman.getTuristickiAgent().getImePrezime();
				podaci[indeks][2] = aranzman.getTipAranzmana().toString();
				podaci[indeks][3] = aranzman.getTipSmestaja().toString();
				podaci[indeks][4] = aranzman.getDostupanDatum().toString();
				podaci[indeks][5] = String.valueOf(aranzman.getKapacitet());
				podaci[indeks][6] = String.valueOf(aranzman.getCenaPoDanuPoOsobi());
				podaci[indeks][7] = String.valueOf(aranzman.getSajamskiPopust()) + "%";
				indeks++;
			}
		}

		return podaci;
	}

	public static void izmeniKapacitetAranzmana(Long id, int brojPutnika) {
		Aranzman aranzman = getAranzmanById(id);

		aranzman.setKapacitet(aranzman.getKapacitet() + brojPutnika);
		try {
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			File tempFile = new File("podaci/aranzmani_temp.txt");

			String datum = aranzman.getDostupanDatum().getDayOfMonth() + "."
					+ aranzman.getDostupanDatum().getMonthValue() + "." + aranzman.getDostupanDatum().getYear();

			BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\|");
				currentId = Long.parseLong(tokens[0]);

				if (currentId == aranzman.getId()) {
					line = aranzman.getId() + "|" + aranzman.getTuristickiAgent().getId() + "|"
							+ aranzman.getTipAranzmana() + "|" + aranzman.getTipSmestaja() + "|" + datum + "|"
							+ aranzman.getKapacitet() + "|" + aranzman.getCenaPoDanuPoOsobi() + "|"
							+ aranzman.getSajamskiPopust() + "|" + aranzman.getPutanjaDoSlike() + "|"
							+ aranzman.getAktivnost();
				}
				writer.write(line);
				writer.newLine();
			}

			reader.close();
			writer.close();

			// Zamena originalnog fajla sa privremenim fajlom
			aranzmaniFile.delete();
			tempFile.renameTo(aranzmaniFile);

		} catch (IOException e) {
			System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
		}
	}

}
