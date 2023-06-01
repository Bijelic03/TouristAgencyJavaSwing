package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Aranzman;
import service.CitanjeAranzmana;

import java.awt.*;
import java.util.ArrayList;

public class AranzmaniPanel extends JPanel {
    public AranzmaniPanel(ArrayList<Aranzman> aranzmani) {
        int rows = (int) Math.ceil(aranzmani.size() / 2.0); // Izračunavanje broja redova na osnovu broja kartica

        setLayout(new GridLayout(rows, 2));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Postavljanje paddinga na panel

        int horizontalGap = 10; // Horizontalni razmak između kartica
        int verticalGap = 10; // Vertikalni razmak između kartica
        ((GridLayout) getLayout()).setHgap(horizontalGap);
        ((GridLayout) getLayout()).setVgap(verticalGap);

        for (Aranzman aranzman : aranzmani) {
            AranzmanCard aranzmanCard = new AranzmanCard(aranzman);
            add(aranzmanCard);
        }
    }


}