package view;

import javax.swing.*;

import service.UpravljanjeKorisnicima;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TuristickiAgentWindow extends JFrame {
	private JButton rezervacijeButton;
	private JButton aranzmaniButton;
	private JButton odjavaButton;
	private JButton exitButton;
	private JButton izvestajButton;

	public TuristickiAgentWindow() {
		setTitle("Agent window");
		setSize(400, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		rezervacijeButton = new JButton("Rezervacije");
		aranzmaniButton = new JButton("Aranzmani");
		buttonPanel.add(rezervacijeButton);
		buttonPanel.add(aranzmaniButton);

		JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		odjavaButton = new JButton("Odjava");
		exitButton = new JButton("Exit");
		izvestajButton = new JButton("Izveštaj");

		// Prilagođavanje veličine dugmadi
		Dimension buttonSize = new Dimension(80, 30);
		rezervacijeButton.setPreferredSize(buttonSize);
		aranzmaniButton.setPreferredSize(buttonSize);
		odjavaButton.setPreferredSize(buttonSize);
		exitButton.setPreferredSize(buttonSize);
		izvestajButton.setPreferredSize(buttonSize);

		sidePanel.add(izvestajButton);
		sidePanel.add(odjavaButton);
		sidePanel.add(exitButton);

		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(sidePanel, BorderLayout.SOUTH);

		add(mainPanel);

		rezervacijeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RezervacijeWindow rezervacijeWindow = new RezervacijeWindow();
				rezervacijeWindow.setVisible(true);
			}
		});

		aranzmaniButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AranzmaniWindow aranzmaniWindow = new AranzmaniWindow();
				aranzmaniWindow.setVisible(true);
			}
		});

		odjavaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UpravljanjeKorisnicima.odjavaOsoba();
				LoginWindow loginWindow = new LoginWindow();
				loginWindow.setVisible(true);
			}
		});

		izvestajButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IzvestajDialog izvestajDialog = new IzvestajDialog();
				izvestajDialog.setVisible(true);

			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

}