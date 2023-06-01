package view;
import javax.swing.*;

import service.UpravljanjeKorisnicima;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminWindow extends JFrame {
    private JButton korisniciButton;
    private JButton rezervacijeButton;
    private JButton aranzmaniButton;
    private JButton odjavaButton;
    private JButton exitButton;

    public AdminWindow() {
        setTitle("AdminWindow");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        korisniciButton = new JButton("Korisnici");
        rezervacijeButton = new JButton("Rezervacije");
        aranzmaniButton = new JButton("Aranzmani");
        buttonPanel.add(korisniciButton);
        buttonPanel.add(rezervacijeButton);
        buttonPanel.add(aranzmaniButton);

        JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        odjavaButton = new JButton("Odjava");
        exitButton = new JButton("Exit");
        
        // Prilagođavanje veličine dugmadi
        Dimension buttonSize = new Dimension(80, 30);
        korisniciButton.setPreferredSize(buttonSize);
        rezervacijeButton.setPreferredSize(buttonSize);
        aranzmaniButton.setPreferredSize(buttonSize);
        odjavaButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        
        sidePanel.add(odjavaButton);
        sidePanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.SOUTH);

        add(mainPanel);

        korisniciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KorisniciWindow korisniciWindow = new KorisniciWindow();
                korisniciWindow.setVisible(true);
            }
        });

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

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminWindow().setVisible(true);
            }
        });
    }
}