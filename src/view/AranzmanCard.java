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
	
	
	public void refreshCard() {
	    // Ažurirajte sve labele kartice na osnovu trenutnog aranžmana
	    idLabel.setText("ID: " + UpravljanjeAranzmanima.currentAranzman.getId());
	    agentLabel.setText("Agent: " + UpravljanjeAranzmanima.currentAranzman.getTuristickiAgent().getIme() + " " + UpravljanjeAranzmanima.currentAranzman.getTuristickiAgent().getPrezime());
	    aranzmanLabel.setText("Tip aranzmana: " + UpravljanjeAranzmanima.currentAranzman.getTipAranzmana());
	    smestajLabel.setText("Tip smestaja: " + UpravljanjeAranzmanima.currentAranzman.getTipSmestaja());
	    datumLabel.setText("Datum: " + UpravljanjeAranzmanima.currentAranzman.getDostupanDatum());
	    kapacitetLabel.setText("Kapacitet: " + UpravljanjeAranzmanima.currentAranzman.getKapacitet());
	    cenaLabel.setText("Cena: " + UpravljanjeAranzmanima.currentAranzman.getCenaPoDanuPoOsobi());
	    popustLabel.setText("Popust: " + UpravljanjeAranzmanima.currentAranzman.getSajamskiPopust() + "%");
	}

	public AranzmanCard(Aranzman aranzman) {

		setLayout(new GridLayout(8, 1));
		setPreferredSize(new Dimension(200, 200));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setBackground(Color.WHITE);

		idLabel = new JLabel("ID: " + aranzman.getId());
		add(idLabel);

		agentLabel = new JLabel(
				"Agent: " + aranzman.getTuristickiAgent().getIme() + " " + aranzman.getTuristickiAgent().getPrezime());
		add(agentLabel);

		aranzmanLabel = new JLabel("Tip aranzmana: " + aranzman.getTipAranzmana());
		add(aranzmanLabel);

		smestajLabel = new JLabel("Tip smestaja: " + aranzman.getTipSmestaja());
		add(smestajLabel);

		datumLabel = new JLabel("Datum: " + aranzman.getDostupanDatum());
		add(datumLabel);

		kapacitetLabel = new JLabel("Kapacitet: " + aranzman.getKapacitet());
		add(kapacitetLabel);

		cenaLabel = new JLabel("Cena: " + aranzman.getCenaPoDanuPoOsobi());
		add(cenaLabel);

		popustLabel = new JLabel("Popust: " + aranzman.getSajamskiPopust() + "%");
		add(popustLabel);

		defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		selectedBorder = BorderFactory.createLineBorder(Color.RED, 2);

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