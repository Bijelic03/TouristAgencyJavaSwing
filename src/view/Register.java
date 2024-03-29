package view;

import javax.swing.*;

import model.Osoba;
import model.Uloga;
import service.UpravljanjeKorisnicima;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.UUID;

public class Register extends JDialog {

    private JLabel lblUsername;
    private JLabel lblIme;
    private JLabel lblPrezime;
    private JLabel lblBrojTelefona;
    private JLabel lblJMBG;
    private JLabel lblPol;
    private JLabel lblAdresa;
    private JLabel lblSifra;
    private JLabel lblPonoviSifru;
    private JLabel lblUloga;

    private JTextField txtUsername;
    private JTextField txtIme;
    private JTextField txtPrezime;
    private JTextField txtBrojTelefona;
    private JTextField txtJMBG;
    private JComboBox<String> cmbPol;
    private JTextField txtAdresa;
    private JComboBox<String> cmbUloga;
    private JPasswordField txtSifra;
    private JPasswordField txtPonoviSifru;
    private JButton btnRegistracija;
    private JButton btnOdustani;
    private boolean boolPol;
    private Uloga ulogaEnum;
    private TableGenerator korisniciTable;

    public Register(TableGenerator korisniciTable) {
        this.korisniciTable = korisniciTable;
        initComponents();
    }

    private void initComponents() {
        lblUsername = new JLabel("Korisničko ime:");
        lblIme = new JLabel("Ime:");
        lblPrezime = new JLabel("Prezime:");
        lblBrojTelefona = new JLabel("Broj telefona:");
        lblJMBG = new JLabel("JMBG:");
        lblPol = new JLabel("Pol:");
        lblAdresa = new JLabel("Adresa:");
        lblUloga = new JLabel("Uloga:");
        lblSifra = new JLabel("Šifra:");
        lblPonoviSifru = new JLabel("Ponovi šifru:");

        txtUsername = new JTextField(20);
        txtIme = new JTextField(20);
        txtPrezime = new JTextField(20);
        txtBrojTelefona = new JTextField(20);
        txtJMBG = new JTextField(20);
        cmbPol = new JComboBox<>(new String[]{"Muški", "Ženski"});
        txtAdresa = new JTextField(20);
        cmbUloga = new JComboBox<>(new String[]{"Administrator", "Turisticki agent", "Turista"});

        txtSifra = new JPasswordField(20);
        txtPonoviSifru = new JPasswordField(20);

        btnRegistracija = new JButton("Registracija");
        btnOdustani = new JButton("Odustani");

        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearInputs();
            }
        });

        btnRegistracija.addActionListener(e -> {
            int validation = validateFields();

        	 if (validation == 2) {
                JOptionPane.showMessageDialog(this, "Niste popunili sva polja!");
            } else if (validation == 3) {
                JOptionPane.showMessageDialog(this, "JMBG nije validan! Mora sadržavati tačno 13 cifara.");
            } else if (validation == 4) {
                JOptionPane.showMessageDialog(this, "Šifra je prekratka! Mora sadržavati najmanje 8 karaktera.");
            }else if(validation == 1) {
            	JOptionPane.showMessageDialog(this, "Šifre se ne poklapaju!");
            }
            else if (validation == 5) {
                JOptionPane.showMessageDialog(this, "Šifra ne sadrži broj!");
            } else if (validation == 6) {
                JOptionPane.showMessageDialog(this, "Uspesno ste popunili registraciju!");
                String pol = cmbPol.getSelectedItem().toString();

                if (pol.equals("Muški")) {
                    boolPol = true;
                } else {
                    boolPol = false;
                }

                String uloga = cmbUloga.getSelectedItem().toString();
                if (uloga.equals("Turisticki agent")) {
                    ulogaEnum = Uloga.TuristickiAgent;
                } else if (uloga.equals("Administrator")) {
                    ulogaEnum = Uloga.Administrator;
                } else {
                    ulogaEnum = Uloga.Turista;
                }

                long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
                UpravljanjeKorisnicima.dodajKorisnika(new Osoba(id, txtIme.getText(), txtPrezime.getText(), txtBrojTelefona.getText(), txtJMBG.getText(),
                        boolPol, txtAdresa.getText(), txtUsername.getText(), new String(txtSifra.getPassword()), ulogaEnum, true));

                UpravljanjeKorisnicima.ucitajKorisnike();
                korisniciTable.refreshTableData(UpravljanjeKorisnicima.getPodaciOKorisnicimaTabela());
                clearInputs();
                dispose();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lblUsername)
                                                .addComponent(lblIme)
                                                .addComponent(lblPrezime)
                                                .addComponent(lblBrojTelefona)
                                                .addComponent(lblJMBG)
                                                .addComponent(lblPol)
                                                .addComponent(lblAdresa)
                                                .addComponent(lblUloga)
                                                .addComponent(lblSifra)
                                                .addComponent(lblPonoviSifru))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(txtUsername)
                                                .addComponent(txtIme)
                                                .addComponent(txtPrezime)
                                                .addComponent(txtBrojTelefona)
                                                .addComponent(txtJMBG)
                                                .addComponent(cmbPol)
                                                .addComponent(txtAdresa)
                                                .addComponent(cmbUloga)
                                                .addComponent(txtSifra)
                                                .addComponent(txtPonoviSifru)))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnRegistracija)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOdustani)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUsername)
                                .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblIme)
                                .addComponent(txtIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPrezime)
                                .addComponent(txtPrezime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblBrojTelefona)
                                .addComponent(txtBrojTelefona, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblJMBG)
                                .addComponent(txtJMBG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPol)
                                .addComponent(cmbPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblAdresa)
                                .addComponent(txtAdresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUloga)
                                .addComponent(cmbUloga, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSifra)
                                .addComponent(txtSifra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPonoviSifru)
                                .addComponent(txtPonoviSifru, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRegistracija)
                                .addComponent(btnOdustani))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    private void clearInputs() {
        txtIme.setText("");
        txtPrezime.setText("");
        txtBrojTelefona.setText("");
        txtJMBG.setText("");
        txtAdresa.setText("");
        txtUsername.setText("");
        txtSifra.setText("");
        txtPonoviSifru.setText("");
    }

    private int validateFields() {
        String username = txtUsername.getText();
        String ime = txtIme.getText();
        String prezime = txtPrezime.getText();
        String brojTelefona = txtBrojTelefona.getText();
        String jmbg = txtJMBG.getText();
        String adresa = txtAdresa.getText();
        String sifra = new String(txtSifra.getPassword());
        String ponoviSifru = new String(txtPonoviSifru.getPassword());

        if (!sifra.equals(ponoviSifru)) {
            return 1; // Passwords don't match
        }
        

        

        if (username.isEmpty() || ime.isEmpty() || prezime.isEmpty() || brojTelefona.isEmpty() || jmbg.isEmpty()
                || adresa.isEmpty() || sifra.isEmpty() || ponoviSifru.isEmpty()) {
            return 2; // One or more fields are empty
        }

        if (jmbg.length() != 13) {
            return 3; // JMBG is not valid (must have exactly 13 digits)
        }

        if (sifra.length() < 8) {
            return 4; // Password is too short (must have at least 8 characters)
        }

        if (!sifra.matches(".*\\d.*")) {
            return 5; // Password does not contain a number
        }

        return 6; // All fields are valid
    }
}
