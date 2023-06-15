package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Aranzman;

import java.awt.*;
import java.util.ArrayList;

public class AranzmaniPanel extends JPanel {
    public AranzmaniPanel(ArrayList<Aranzman> aranzmani) {
        setLayout(new GridLayout(0, 2)); // Set GridLayout with 2 columns and variable number of rows
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Set padding for the panel

        for (Aranzman aranzman : aranzmani) {
            AranzmanCard aranzmanCard = new AranzmanCard(aranzman);
            add(aranzmanCard);
        }
    }

    public static void addAranzmanCard(JPanel aranzmaniPanel, Aranzman aranzman) {
        AranzmanCard newAranzmanCard = new AranzmanCard(aranzman);
        aranzmaniPanel.add(newAranzmanCard);
        aranzmaniPanel.revalidate();
        aranzmaniPanel.repaint();
    }
}
