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
import view.AranzmanCard;
import java.text.SimpleDateFormat;

public class UpravljanjeAranzmanima {

	public static Aranzman currentAranzman;

	private static Long currentId;

	public static void dodajAranzman(Aranzman aranzman) {
		try {
			String datum = aranzman.getDostupanDatum().getDayOfMonth() + "." + aranzman.getDostupanDatum().getMonthValue() + "." + aranzman.getDostupanDatum().getYear();
			System.out.println(datum);
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(aranzmaniFile, true));
			writer.write(aranzman.getId() + "|" + aranzman.getTuristickiAgent().getId() + "|"
					+ aranzman.getTipAranzmana() + "|" + aranzman.getTipSmestaja() + "|" + datum
					+ "|" + aranzman.getKapacitet() + "|" + aranzman.getCenaPoDanuPoOsobi() + "|"
					+ aranzman.getSajamskiPopust() + "|" + aranzman.getPutanjaDoSlike() + "|"
					+ aranzman.getAktivnost());
			writer.newLine();
			writer.close();
			CitanjeAranzmana.aranzmani.add(aranzman);
		} catch (IOException e) {
			System.out.println("Greska prilikom upisivanja korisnika u datoteku: " + e.getMessage());
		}
	}

	public static void disableAranzman() {
	    if (AranzmanCard.selectedCard != null) {
	        currentAranzman.setAktivnost(false);
	        try {
	            File aranzmaniFile = new File("podaci/aranzmani.txt");
	            File tempFile = new File("podaci/aranzmani_temp.txt");

	            BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile));
	            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

	            String line;
	            int currentLine = 0;

	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	            while ((line = reader.readLine()) != null) {
	                String[] tokens = line.split("\\|");
	                currentId = Long.parseLong(tokens[0]);

	                if (currentId == currentAranzman.getId()) {
	                    line = currentAranzman.getId() + "|" + currentAranzman.getTuristickiAgent().getId() + "|"
	                            + currentAranzman.getTipAranzmana() + "|" + currentAranzman.getTipSmestaja() + "|"
	                            + currentAranzman.getDostupanDatum() + "|"
	                            + currentAranzman.getKapacitet() + "|" + currentAranzman.getCenaPoDanuPoOsobi()
	                            + "|" + currentAranzman.getSajamskiPopust() + "|" + currentAranzman.getPutanjaDoSlike()
	                            + "|" + currentAranzman.getAktivnost();
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
	            AranzmanCard.selectedCard.deleteCard();
	        } catch (IOException e) {
	            System.out.println("Greska prilikom izmene aranzmana: " + e.getMessage());
	        }
	    }
	}

}
