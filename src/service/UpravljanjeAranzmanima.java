package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import model.Aranzman;
import view.AranzmanCard;

public class UpravljanjeAranzmanima {
	
	public static Aranzman	currentAranzman;
	
	private static Long currentId;

	public static void disableAranzman() {
		if (AranzmanCard.selectedCard != null) {
		System.out.println(currentAranzman.getPutanjaDoSlike());
		currentAranzman.setAktivnost(false);
		System.out.println("aktivnost promenjena na false");
		try {
			File aranzmaniFile = new File("podaci/aranzmani.txt");
			File tempFile = new File("podaci/aranzmani_temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(aranzmaniFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			int currentLine = 0;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\|");
				currentId = Long.parseLong(tokens[0]);

				if (currentId == currentAranzman.getId()) {
					// Izmena linije
					line = currentAranzman.getId() + "|" + currentAranzman.getTuristickiAgent().getId() + "|"
						    + currentAranzman.getTipAranzmana() + "|" + currentAranzman.getTipSmestaja() + "|"
						    + currentAranzman.getDostupanDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "|"
						    + currentAranzman.getKapacitet() + "|" + currentAranzman.getCenaPoDanuPoOsobi() + "|"
						    + currentAranzman.getSajamskiPopust() + "|" + currentAranzman.getPutanjaDoSlike() + "|"
						    + currentAranzman.getAktivnost();
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
	}}

}
