package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Uloga;
import service.CitanjeAranzmana;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;

public class AranzmaniWindow extends JFrame {

	private static final long serialVersionUID = -1393812183206771422L;

	private JButton addAranzman = new JButton("Dodaj aranzman");
	private JButton editAranzman = new JButton("Izmeni aranzman");
	private JButton delAranzman = new JButton("Obrisi aranzman");
	private JButton rezervisiAranzman = new JButton("Rezervisi aranzman");
	private JButton exit = new JButton("Zatvori");
	private JPanel southButtons = new JPanel();
	private AranzmaniCreate aranzmaniCreate = null;
	private AranzmaniEdit aranzmaniEdit = null;

	public AranzmaniWindow() {
		setTitle("Aranzmani");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		initGUI();
	}

	private void adminButtons() {

	}

	private void turistaButtons() {
		southButtons.add(rezervisiAranzman);
	}

	private void agentButtons() {
		southButtons.add(rezervisiAranzman);
		southButtons.add(editAranzman);
		southButtons.add(delAranzman);
		southButtons.add(addAranzman);
	}

	private void initGUI() {
		AranzmaniPanel aranzmanPanel = new AranzmaniPanel(CitanjeAranzmana.ucitajAranzmane());
		JScrollPane scrollPane = new JScrollPane(aranzmanPanel);
		add(scrollPane, BorderLayout.CENTER);

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

		addAranzman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (aranzmaniCreate == null) {
					aranzmaniCreate = new AranzmaniCreate(aranzmanPanel);
				}
				aranzmaniCreate.setVisible(true);
			}
		});

		editAranzman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (UpravljanjeAranzmanima.currentAranzman != null) {
					if(UpravljanjeAranzmanima.moguceIzmena(UpravljanjeAranzmanima.currentAranzman.getId())) {
						aranzmaniEdit = new AranzmaniEdit(aranzmanPanel);
						aranzmaniEdit.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Nije moguće izmeniti ovaj aranzman (postoje kreirane rezervacije)", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
					}
		

				}
			}
		});

		delAranzman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					UpravljanjeAranzmanima.disableAranzman();
				}
			}
		});
	}
}
