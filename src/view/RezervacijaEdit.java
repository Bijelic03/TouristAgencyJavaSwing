package view;

import javax.swing.*;

import model.Aranzman;
import model.Rezervacija;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeRezervacijama;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RezervacijaEdit extends JDialog {
	private JLabel labelBrojPutnika;
	private JTextField textFieldBrojPutnika;
	private JButton buttonPotvrdi;
	private JButton buttonOtkazi;

	public RezervacijaEdit(Rezervacija rezervacija, TableGenerator rezervacijaTable) {

		labelBrojPutnika = new JLabel("Broj putnika:");
		textFieldBrojPutnika = new JTextField(10);
		textFieldBrojPutnika.setText(String.valueOf(rezervacija.getBrojPutnika()));
		buttonPotvrdi = new JButton("Potvrdi");
		buttonOtkazi = new JButton("Otkaži");

		buttonPotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer brojPutnika = Integer.parseInt(textFieldBrojPutnika.getText());
				double cena = brojPutnika * rezervacija.getBrojDana() * rezervacija.getAranzman().getCenaPoDanuPoOsobi();

				if (brojPutnika <= 0) {
					JOptionPane.showMessageDialog(null, "Niste uneli broj putnika kako treba", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
				} else if (rezervacija.getAranzman().getKapacitet() + rezervacija.getBrojPutnika() - brojPutnika >= 0) {
					UpravljanjeAranzmanima.izmeniKapacitetAranzmana(rezervacija.getAranzman().getId(),
							rezervacija.getBrojPutnika()-brojPutnika);
					rezervacija.setBrojPutnika(brojPutnika);
					rezervacija.setCena(cena);
					UpravljanjeRezervacijama.editRezervacija(rezervacija);
					rezervacijaTable.refreshTableData(UpravljanjeRezervacijama.getPodaciORezervacijamaTabela());
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Nema dovoljno mesta", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
				}
				dispose();
			}
		});

		buttonOtkazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obrada otkazivanja
				dispose();
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(labelBrojPutnika);
		panel.add(textFieldBrojPutnika);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(buttonPotvrdi);
		buttonPanel.add(buttonOtkazi);

		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null); // Podešavanje na centar ekrana
	}
}
