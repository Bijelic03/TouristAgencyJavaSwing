package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.function.Function;

import view.TableGenerator;
import view.Register;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Osoba;
import model.Rezervacija;
import model.StatusRezervacije;
import model.Uloga;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;
import service.UpravljanjeRezervacijama;

public class RezervacijeWindow extends JFrame {

	private static final long serialVersionUID = -1393812183206771422L;

	private JButton storniraj = new JButton("Storniraj");

	private JButton otkazi = new JButton("Otkazi rezervaciju");

	private JButton odobri = new JButton("Odobri rezervaciju");

	private JButton exit = new JButton("Zatvori");

	private JPanel southButtons = new JPanel();

	private Register register = null;

	private Edit edit;

	private String[] rezervacijeColumnNames = { "Id", "Agent", "Id aranzmana", "Broj putnika", "Cena", "Datum polaska",
			"Broj dana", "Datum kreiranja rezervacije", "Status" };

	public RezervacijeWindow() {
		setTitle("Rezervacije");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		initGUI();
	}

	private void adminButtons() {
		southButtons.add(storniraj);

	}

	private void turistaButtons() {
		southButtons.add(otkazi);
	}

	private void agentButtons() {
		southButtons.add(odobri);
		southButtons.add(otkazi);
	}

	private void initGUI() {
		UpravljanjeKorisnicima.ucitajKorisnike();
		TableGenerator rezervacijeTable = new TableGenerator(UpravljanjeRezervacijama.getPodaciORezervacijamaTabela(),
				rezervacijeColumnNames);
		add(rezervacijeTable, BorderLayout.CENTER);

		if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Turista) {
			turistaButtons();
		}

		if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Administrator) {
			adminButtons();
		}

		if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.TuristickiAgent) {
			agentButtons();
		}
		southButtons.add(exit);

		add(southButtons, BorderLayout.SOUTH);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		storniraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rezervacijeTable.selectedRow != -1) {

					long selectedId = rezervacijeTable.getIdValueFromRow();
					Rezervacija selectedRezervacija = UpravljanjeRezervacijama.getRezervacijaById(selectedId);

					if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.TuristickiAgent) {
						if (selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Kreirana) {
							int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
									JOptionPane.YES_NO_OPTION);
							if (choice == JOptionPane.YES_OPTION) {
								UpravljanjeRezervacijama.izmeniStatusRezervacije(selectedId,
										StatusRezervacije.Neuspesna);
								UpravljanjeAranzmanima.izmeniKapacitetAranzmana(
										selectedRezervacija.getAranzman().getId(),
										selectedRezervacija.getBrojPutnika());

							}
						}
					}
					if (UpravljanjeKorisnicima.prijavljenaOsoba.getUloga() == Uloga.Administrator) {
						if (selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Kreirana
								|| selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Zavrsena) {
							int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
									JOptionPane.YES_NO_OPTION);
							if (choice == JOptionPane.YES_OPTION) {
								UpravljanjeRezervacijama.izmeniStatusRezervacije(selectedId,
										StatusRezervacije.Neuspesna);
								UpravljanjeAranzmanima.izmeniKapacitetAranzmana(
										selectedRezervacija.getAranzman().getId(),
										-selectedRezervacija.getBrojPutnika());

							}
						}
					}
					if (selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Otkazana
							|| selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Neuspesna) {
						rezervacijeTable.refreshTableData(UpravljanjeRezervacijama.getPodaciORezervacijamaTabela());
						JOptionPane.showMessageDialog(null, "Nije moguće stornirati ovu rezervaciju", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);

					}

				}
			}
		});

		otkazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rezervacijeTable.selectedRow != -1) {

					long selectedId = rezervacijeTable.getIdValueFromRow();
					Rezervacija selectedRezervacija = UpravljanjeRezervacijama.getRezervacijaById(selectedId);
					if (selectedRezervacija.getStatusRezervacije() == StatusRezervacije.Kreirana) {
						int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
								JOptionPane.YES_NO_OPTION);
						if (choice == JOptionPane.YES_OPTION) {
							UpravljanjeRezervacijama.izmeniStatusRezervacije(selectedId, StatusRezervacije.Otkazana);
							UpravljanjeAranzmanima.izmeniKapacitetAranzmana(selectedRezervacija.getAranzman().getId(),
									selectedRezervacija.getBrojPutnika());
							rezervacijeTable.refreshTableData(UpravljanjeRezervacijama.getPodaciORezervacijamaTabela());

						}
					}
				}
			}
		});

		odobri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rezervacijeTable.selectedRow != -1) {

					int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
							JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						UpravljanjeKorisnicima.disableKorisnik(rezervacijeTable.getIdValueFromRow());
						rezervacijeTable.removeSelectedRow();
					}

				}
			}

		});

	}
}
