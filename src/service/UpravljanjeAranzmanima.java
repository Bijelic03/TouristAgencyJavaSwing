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
			System.out.println(datum);
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

			System.out.println("Aranzman je izmenjen.");
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

				System.out.println("Aranzman je izmenjen.");
				AranzmanCard.selectedCard.deleteCard();
			} catch (IOException e) {
				System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
			}
		}
	}

	public static boolean moguceIzmena(Long idAranzmana) {
		for (Rezervacija rezervacija : CitanjeRezervacija.rezervacije) {
			return (rezervacija.getAranzman().getId() == idAranzmana
					&& rezervacija.getStatusRezervacije() == StatusRezervacije.Kreirana);

		}
		return false;
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

			System.out.println("Izmenjen kapacitet aranzmana.");
		} catch (IOException e) {
			System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
		}
	}

}
