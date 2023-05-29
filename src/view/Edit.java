package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import model.Osoba;
import model.Uloga;
import service.UpravljanjeKorisnicima;

public class Edit extends JDialog {

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
	
	private JButton btnSacuvaj;
	
	private JButton btnOdustani;
	
	private boolean boolPol;
	
	private Uloga ulogaEnum;
	
	private TableGenerator korisniciTable;
	
	private int selectedRow = 1;

	Osoba selectedOsoba = new Osoba();
	
	public Edit() {
		// TODO Auto-generated constructor stub
	}
	public Edit(TableGenerator korisniciTable, Osoba selectedOsoba) {
		initComponents();
		this.korisniciTable = korisniciTable;
		this.selectedRow = korisniciTable.getSelectedRow();
		this.selectedOsoba = selectedOsoba;
		setFields();

	}
	
	private void setFields() {
	    if (selectedOsoba != null) {
	        txtUsername.setText(selectedOsoba.getUsername());
	        txtIme.setText(selectedOsoba.getIme());
	        txtPrezime.setText(selectedOsoba.getPrezime());
	        txtBrojTelefona.setText(selectedOsoba.getBrojTelefona());
	        txtJMBG.setText(selectedOsoba.getJmbg());
	        cmbPol.setSelectedItem(selectedOsoba.getPol() ? "Muški" : "Ženski");
	        txtAdresa.setText(selectedOsoba.getAdresa());
	        cmbUloga.setSelectedItem(selectedOsoba.getUloga().toString());
	        txtSifra.setText(selectedOsoba.getPassword());
	    } 
	}

	private void initComponents() {
		lblUsername = new JLabel("korisnicko ime:");
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
		txtUsername = new JTextField(20);
		txtIme = new JTextField(20);
		txtPrezime = new JTextField(20);
		txtBrojTelefona = new JTextField(20);
		txtJMBG = new JTextField(20);
		cmbPol = new JComboBox<>(new String[] { "Muški", "Ženski" });
		txtAdresa = new JTextField(20);
		cmbUloga = new JComboBox<>(new String[] { "Administrator", "Turisticki agent", "Turista" });

		txtSifra = new JPasswordField(20);
		txtPonoviSifru = new JPasswordField(20);

		btnSacuvaj = new JButton("Sačuvaj");
		btnOdustani = new JButton("Odustani");

		btnOdustani.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});

		btnSacuvaj.addActionListener(e -> {
			if (validateFields() == 1) {
				// Perform registration process
				JOptionPane.showMessageDialog(this, "Sifre se ne poklapaju!");
			} else if (validateFields() == 2) {
				JOptionPane.showMessageDialog(this, "Niste popunili sva polja!");
			} else if (validateFields() == 3) {
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
				
				UpravljanjeKorisnicima.editKorisnika(new Osoba(selectedOsoba.getId(), txtIme.getText(), txtPrezime.getText(),
				        txtBrojTelefona.getText(), txtJMBG.getText(), boolPol, txtAdresa.getText(),
				        txtUsername.getText(), new String(txtSifra.getPassword()), ulogaEnum, true));
                UpravljanjeKorisnicima.ucitajKorisnike();
               korisniciTable.refreshTableData(UpravljanjeKorisnicima.getPodaciOKorisnicimaTabela());
				dispose();

			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblUsername)
								.addComponent(lblIme).addComponent(lblPrezime).addComponent(lblBrojTelefona)
								.addComponent(lblJMBG).addComponent(lblPol).addComponent(lblAdresa)
								.addComponent(lblUloga).addComponent(lblSifra).addComponent(lblPonoviSifru))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(txtUsername)
								.addComponent(txtIme).addComponent(txtPrezime).addComponent(txtBrojTelefona)
								.addComponent(txtJMBG).addComponent(cmbPol).addComponent(txtAdresa)
								.addComponent(cmbUloga).addComponent(txtSifra).addComponent(txtPonoviSifru)))
						.addGroup(layout.createSequentialGroup().addComponent(btnSacuvaj)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnOdustani)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblUsername)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblIme)
								.addComponent(txtIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPrezime)
								.addComponent(txtPrezime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblBrojTelefona).addComponent(txtBrojTelefona, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblJMBG)
								.addComponent(txtJMBG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPol)
								.addComponent(cmbPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblAdresa)
								.addComponent(txtAdresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblUloga)
								.addComponent(cmbUloga, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblSifra)
								.addComponent(txtSifra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblPonoviSifru).addComponent(txtPonoviSifru, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSacuvaj)
								.addComponent(btnOdustani))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pack();
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
			return 1;
		}
		if (username.isEmpty() || ime.isEmpty() || prezime.isEmpty() || brojTelefona.isEmpty() || jmbg.isEmpty()
				|| adresa.isEmpty() || sifra.isEmpty() || ponoviSifru.isEmpty()) {
			return 2; // One or more fields are empty
		}

		return 3; // All fields are filled
	}

	public static void main(String[] args) {
		JFrame parent = new JFrame();
		parent.setResizable(false);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setSize(400, 300);

		JButton btnOpenEdit = new JButton("Otvaranje dijaloga za edit");
		btnOpenEdit.addActionListener(e -> {
			Edit dialog = new Edit();
			dialog.setVisible(true);
		});

		GroupLayout layout = new GroupLayout(parent.getContentPane());
		parent.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(130, 130, 130).addComponent(btnOpenEdit).addContainerGap(141, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(114, 114, 114).addComponent(btnOpenEdit).addContainerGap(153, Short.MAX_VALUE)));

		parent.setVisible(true);
	}
}