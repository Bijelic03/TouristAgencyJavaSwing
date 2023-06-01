package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

	public AranzmaniWindow() {
		setTitle("Aranzmani");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		initGUI();
	}

	private void adminButtons() {
		southButtons.add(addAranzman);
		southButtons.add(editAranzman);
		southButtons.add(delAranzman);

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
		add(aranzmanPanel, BorderLayout.NORTH);

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

			}
		});

		editAranzman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		delAranzman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					UpravljanjeAranzmanima.disableAranzman();
				} 
				
				

			}

		});

	}
}
