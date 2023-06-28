package view;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import model.Aranzman;
import model.Osoba;
import model.StatusRezervacije;
import model.Uloga;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;
import service.UpravljanjeRezervacijama;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RezervacijaCreate extends JDialog {
	private JTextField idAranzmanaField;
	private JComboBox<String> turistaComboBox;
	private Map<String, String> turistaMap;
	private long id;

	private JTextField brojPutnikaField;
	private JTextField brojDanaField;
	private JTextField cenaField;
	private Aranzman aranzman;

	private StatusRezervacije statusRezervacije;

	public RezervacijaCreate() {

		aranzman = UpravljanjeAranzmanima.currentAranzman;

		setTitle("Kreiranje rezervacije");
		setSize(400, 400);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panel.add(new JLabel("Id aranžmana:"));
		idAranzmanaField = new JTextField();
		idAranzmanaField.setText(String.valueOf(aranzman.getId()));
		idAranzmanaField.setEditable(false);
		panel.add(idAranzmanaField);

		panel.add(new JLabel("Turista:"));
		turistaComboBox = new JComboBox<>();
		turistaMap = new HashMap<>();
		if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.TuristickiAgent) {
			for (Osoba osoba : UpravljanjeKorisnicima.getKorisnici()) {
				if (osoba.getUloga() == Uloga.Turista && osoba.getAktivnost()) {
					turistaComboBox.addItem(osoba.getImePrezime());
					turistaMap.put(osoba.getImePrezime(), Long.toString(osoba.getId()));
				}
			}
		}
		if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Turista) {
			turistaComboBox.addItem(UpravljanjeKorisnicima.prijavljenaOsoba.getImePrezime());
			turistaComboBox.setEnabled(false);
		}

		panel.add(turistaComboBox);

		panel.add(new JLabel("Broj putnika:"));
		brojPutnikaField = new JTextField();
		panel.add(brojPutnikaField);

		panel.add(new JLabel("Broj dana:"));
		brojDanaField = new JTextField();
		panel.add(brojDanaField);

		JButton izracunajCenuButton = new JButton("Izračunaj cenu");
		panel.add(izracunajCenuButton);
		panel.add(new JLabel()); // Prazna labele da bi se dugme izračunaj cenu nalazilo u levoj koloni
		izracunajCenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				izracunajCenu();
			}
		});

		panel.add(new JLabel("Cena:"));
		cenaField = new JTextField();
		cenaField.setEditable(false);
		panel.add(cenaField);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton kreirajButton = new JButton("Kreiraj");
		buttonPanel.add(kreirajButton);
		kreirajButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
				Long turistaId;
				System.out.println(brojPutnikaField.getText());
				if (Integer.parseInt(brojPutnikaField.getText()) > aranzman.getKapacitet()) {
					JOptionPane.showMessageDialog(null,
							"Ostalo je samo " + aranzman.getKapacitet() + " mesta na aranzmanu.", "Greška",
							JOptionPane.ERROR_MESSAGE);
				} else {

					if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.TuristickiAgent) {
						statusRezervacije = StatusRezervacije.Zavrsena;
						String turista = (String) turistaComboBox.getSelectedItem();
						turistaId = Long.parseLong(turistaMap.get(turista));
					} else {
						statusRezervacije = StatusRezervacije.Kreirana;

						turistaId = UpravljanjeKorisnicima.prijavljenaOsoba.getId();
					}
					izracunajCenu();

					UpravljanjeRezervacijama.kreirajRezervaciju(id, turistaId, aranzman.getId(),
							Integer.parseInt(brojPutnikaField.getText()), Integer.parseInt(brojDanaField.getText()),
							Double.parseDouble(cenaField.getText()), statusRezervacije);
					UpravljanjeAranzmanima.izmeniKapacitetAranzmana(aranzman.getId(),
							-Integer.parseInt(brojPutnikaField.getText()));
					aranzman.setKapacitet(aranzman.getKapacitet() - Integer.parseInt(brojPutnikaField.getText()));
					AranzmanCard.selectedCard.refreshCard(aranzman);
					dispose();
				}
			}
		});

		JButton otkaziButton = new JButton("Otkaži");
		buttonPanel.add(otkaziButton);
		otkaziButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Dodavanje panela sa dugmadima na glavni panel
		panel.add(new JLabel());
		panel.add(buttonPanel);

		// Povezivanje akcija sa dugmadima
		izracunajCenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				izracunajCenu();
			}
		});

		// Dodavanje panela na dijalog
		add(panel);

		// Prikazivanje dijaloga
		setVisible(true);
	}

	private void izracunajCenu() {
		try {

			int brojPutnika = Integer.parseInt(brojPutnikaField.getText());
			int brojDana = Integer.parseInt(brojDanaField.getText());
			double cenaPoDanu = aranzman.getCenaPoDanuPoOsobi();
			double popust = (double) aranzman.getSajamskiPopust() / 100;
			double cena = brojPutnika * brojDana * cenaPoDanu;
			if (brojPutnika < 1 || brojDana < 1) {
				JOptionPane.showMessageDialog(null, "Niste uneli broj putnika ili broj dana kako treba!", "Greška",
						JOptionPane.ERROR_MESSAGE);
			} else {
				cena = cena - cena * popust;

				cenaField.setText(String.valueOf(cena));
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Unesite validne vrednosti za broj putnika i broj dana.", "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
