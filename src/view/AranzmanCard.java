package view;

import javax.swing.*;
import javax.swing.border.Border;

import model.Aranzman;
import service.UpravljanjeAranzmanima;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AranzmanCard extends JPanel {
	private JLabel idLabel;
	private JLabel agentLabel;
	private JLabel aranzmanLabel;
	private JLabel smestajLabel;
	private JLabel datumLabel;
	private JLabel kapacitetLabel;
	private JLabel cenaLabel;
	private JLabel popustLabel;
	private JLabel slikaLabel; // Dodana komponenta za prikaz slike

	public static AranzmanCard selectedCard;
	private Border defaultBorder;
	private Border selectedBorder;

	public void deleteCard() {
		if (selectedCard == this) {
			selectedCard = null;
		}
		Container parent = getParent();
		if (parent != null) {
			parent.remove(this);
			parent.revalidate();
			parent.repaint();
		}
	}
	
	
	/*public void refreshCard() {
		// Ažurirajte sve labele kartice na osnovu trenutnog aranžmana
		Aranzman aranzman = UpravljanjeAranzmanima.currentAranzman;
		idLabel.setText("ID: " + aranzman.getId());
		agentLabel.setText(
				"Agent: " + aranzman.getTuristickiAgent().getIme() + " " + aranzman.getTuristickiAgent().getPrezime());
		aranzmanLabel.setText("Tip aranzmana: " + aranzman.getTipAranzmana());
		smestajLabel.setText("Tip smestaja: " + aranzman.getTipSmestaja());
		datumLabel.setText("Datum: " + aranzman.getDostupanDatum());
		kapacitetLabel.setText("Kapacitet: " + aranzman.getKapacitet());
		cenaLabel.setText("Cena: " + aranzman.getCenaPoDanuPoOsobi());
		popustLabel.setText("Popust: " + aranzman.getSajamskiPopust() + "%");
		try {
			String putanjaDoSlike = aranzman.getPutanjaDoSlike();
			ImageIcon imageIcon = new ImageIcon(getClass().getResource(putanjaDoSlike));
			Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			System.out.println(aranzman.getPutanjaDoSlike());
			slikaLabel.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public AranzmanCard(Aranzman aranzman) {
		setLayout(new BorderLayout()); 
		setBackground(Color.WHITE); 

		JPanel textPanel = new JPanel(new GridLayout(9, 1));
		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
																			
		textPanel.setBackground(Color.WHITE); 

		idLabel = new JLabel("ID: " + aranzman.getId());
		textPanel.add(idLabel);

		agentLabel = new JLabel(
				"Agent: " + aranzman.getTuristickiAgent().getIme() + " " + aranzman.getTuristickiAgent().getPrezime());
		textPanel.add(agentLabel);

		aranzmanLabel = new JLabel("Tip aranzmana: " + aranzman.getTipAranzmana());
		textPanel.add(aranzmanLabel);

		smestajLabel = new JLabel("Tip smestaja: " + aranzman.getTipSmestaja());
		textPanel.add(smestajLabel);

		datumLabel = new JLabel("Datum: " + aranzman.getDostupanDatum());
		textPanel.add(datumLabel);

		kapacitetLabel = new JLabel("Kapacitet: " + aranzman.getKapacitet());
		textPanel.add(kapacitetLabel);

		cenaLabel = new JLabel("Cena: " + aranzman.getCenaPoDanuPoOsobi());
		textPanel.add(cenaLabel);

		popustLabel = new JLabel("Popust: " + aranzman.getSajamskiPopust() + "%");
		textPanel.add(popustLabel);

		add(textPanel, BorderLayout.WEST); // Postavite textPanel na lijevu stranu
		setBorder(defaultBorder);

		try {
			ImageIcon imgThisImg = new ImageIcon(aranzman.getPutanjaDoSlike());
			Image image = imgThisImg.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			ImageIcon scaledImageIcon = new ImageIcon(image);
			slikaLabel = new JLabel(scaledImageIcon); 
			slikaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
																				
			add(slikaLabel, BorderLayout.EAST);
		} catch (Exception e) {
			e.printStackTrace();
		}

		defaultBorder = BorderFactory.createLineBorder(Color.WHITE, 1);
		selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 1);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedCard != null && selectedCard != AranzmanCard.this) {
					selectedCard.setBorder(defaultBorder);
				}
				selectedCard = AranzmanCard.this;
				UpravljanjeAranzmanima.currentAranzman = aranzman;
				setBorder(selectedBorder);
			}
		});
	}

}
