package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.TableGenerator;
import view.Register;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import service.UpravljanjeTuristickimAgencijama;
import service.UpravljanjeKorisnicima;

public class AdminWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1393812183206771422L;

	private JButton addUser = new JButton("Dodaj korisnika");
	
	private JButton delUser = new JButton("Obrisi korisnika");

	private JPanel southButtons = new JPanel();
	
	private Register register = new Register();
	private UpravljanjeKorisnicima upravljanjeKorisnicima = new UpravljanjeKorisnicima();
	
	String[][] data = { { "Kundan Kumar Jha", "4031", "CSE" }, { "Anand Jha", "6014", "IT" } };

	String[] korisniciColumnNames = { "Id", "Ime", "Prezime", "Broj telefona", "Jmbg", "Pol", "Adresa", "Username", "Sifra", "Uloga", "Aktivnost" };

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
		TableGenerator korisniciTable = new TableGenerator(upravljanjeKorisnicima.getPodaciOKorisnicima(), korisniciColumnNames);
		
		add(korisniciTable, BorderLayout.CENTER);
		southButtons.add(addUser);
		southButtons.add(delUser);
		add(southButtons, BorderLayout.SOUTH);
		
		
		addUser.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (register == null) {
		            register = new Register();
		        }
		        register.setVisible(true);

		    }
		});
		
		
		
		delUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (korisniciTable.selectedRow != -1) {
	            	korisniciTable.removeSelectedRow();
	            	System.out.println(korisniciTable.selectedRow);
	            }
			}
			

		});

	}
}
















