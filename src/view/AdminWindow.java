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
import service.UpravljanjeTuristickimAgencijama;
import service.UpravljanjeKorisnicima;

public class AdminWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1393812183206771422L;

	private JButton addUser = new JButton("Dodaj korisnika");
	
	private JButton editUser = new JButton("Izmeni korisnika");

	private JButton delUser = new JButton("Obrisi korisnika");
	
	private JButton logOff = new JButton("Odjavi se");


	private JPanel southButtons = new JPanel();

	private Register register = null;
	
	private Edit edit;
	
	private UpravljanjeKorisnicima upravljanjeKorisnicima = new UpravljanjeKorisnicima();

	String[][] data = { { "Kundan Kumar Jha", "4031", "CSE" }, { "Anand Jha", "6014", "IT" } };

	String[] korisniciColumnNames = { "Id", "Ime", "Prezime", "Broj telefona", "Jmbg", "Pol", "Adresa", "Username",
			"Sifra", "Uloga" };

	public AdminWindow() {
		setTitle("Admin window");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
	}

	private void initGUI() {
		upravljanjeKorisnicima.ucitajKorisnike();
		TableGenerator korisniciTable = new TableGenerator(upravljanjeKorisnicima.getPodaciOKorisnicimaTabela(),korisniciColumnNames);

		add(korisniciTable, BorderLayout.CENTER);
		southButtons.add(addUser);
		southButtons.add(editUser);
		southButtons.add(delUser);
		southButtons.add(logOff);

		add(southButtons, BorderLayout.SOUTH);

		logOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        dispose();
				LoginWindow loginWindow = new LoginWindow();
				loginWindow.setVisible(true);
			}
		});
		
		
		
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (register == null) {
					register = new Register(korisniciTable);

				}
				register.setVisible(true);
			//	korisniciTable.addRow(korisniciColumnNames);
			}
		});
		
		editUser.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (korisniciTable.selectedRow != -1) {
		            long selectedId =korisniciTable.getIdValueFromRow();
		            Osoba selectedOsoba = upravljanjeKorisnicima.getKorisnikById(selectedId);
		            
		            edit = new Edit(korisniciTable, selectedOsoba);
		            edit.setVisible(true);
		        }
		    }
		});

		delUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (korisniciTable.selectedRow != -1) {

					int choice = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "",
							JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						upravljanjeKorisnicima.disableKorisnik(korisniciTable.getIdValueFromRow());
						korisniciTable.removeSelectedRow();
					} 

				}
			}

		});

	}
}
