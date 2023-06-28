package view;

import com.toedter.calendar.JDateChooser;

import model.Aranzman;
import model.TipoviAranzmana;
import model.TipoviSmestaja;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.UUID;

public class AranzmaniCreate extends JDialog {

	private static final String DEFAULT_PATH = "slike/";

	private JLabel lblTipAranzmana;
	private JLabel lblTipSmestaja;
	private JLabel lblDatum;
	private JLabel lblKapacitet;
	private JLabel lblCena;
	private JLabel lblPopust;
	private JLabel lblSlika;

	private JComboBox<String> cmbTipAranzmana;
	private JComboBox<String> cmbTipSmestaja;
	private JDateChooser dateChooser;
	private JTextField txtKapacitet;
	private JTextField txtCena;
	private JTextField txtAPopust;
	private JTextField txtPutanjaDoSlike;
	private JButton btnKreiraj;
	private JButton btnOdustani;

	private int kapacitet;
	private double cena;
	private int sajamskiPopust;
	private String putanjaDoSlike;
	private long id;

	private TipoviSmestaja smestajEnum;
	private TipoviAranzmana aranzmanEnum;

	private JButton btnChoose;

	private JPanel aranzmanPanel;

	private Aranzman noviAranzman;

	public AranzmaniCreate(AranzmaniPanel aranzmanPanel) {
		this.aranzmanPanel = aranzmanPanel;
		initComponents();
	}

	private void initComponents() {
		lblTipAranzmana = new JLabel("Tip aranzmana:");
		lblTipSmestaja = new JLabel("Tip smestaja:");
		lblDatum = new JLabel("Datum:");
		lblKapacitet = new JLabel("Kapacitet:");
		lblCena = new JLabel("Cena:");
		lblPopust = new JLabel("Popust:");
		lblSlika = new JLabel("Slika:");

		cmbTipAranzmana = new JComboBox<>(new String[] { "Letovanje", "Zimovanje", "Evropski gradovi",
				"Daleka putovanja", "First minute", "Last minute", "Putovanja u toku praznika" });
		cmbTipSmestaja = new JComboBox<>(new String[] { "Hotel", "Apartman" });

		dateChooser = new JDateChooser();

		txtKapacitet = new JTextField(20);
		txtCena = new JTextField(20);
		txtAPopust = new JTextField(20);
		btnChoose = new JButton("Odaberi sliku");
		txtPutanjaDoSlike = new JTextField(20);

		btnChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(DEFAULT_PATH));
				int response = fileChooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String filePath = selectedFile.getName();
					txtPutanjaDoSlike.setText("slike/" + filePath);
				}
			}
		});

		btnKreiraj = new JButton("Kreiraj");
		btnOdustani = new JButton("Odustani");

		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearInputs();
				dispose();
			}
		});

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				clearInputs();
			}
		});

		btnKreiraj.addActionListener(e -> {
			int validation = validateFields();
			if (validation == 1) {
				JOptionPane.showMessageDialog(this, "Niste uneli popust kako treba!");
			}
			if (validation == 2) {
				JOptionPane.showMessageDialog(this, "Niste popunili sva polja!");
			} else if (validation == 4) {
				JOptionPane.showMessageDialog(this, "Niste uneli datum kako treba!");
			} else if (validation == 7) {
				JOptionPane.showMessageDialog(this, "Cena nije uneta kako treba!");
			}

			else if (validation == 6) {
				JOptionPane.showMessageDialog(this, "Uspesno ste popunili sva polja!");
				String tipSmestaja = cmbTipSmestaja.getSelectedItem().toString();

				if (tipSmestaja.equals("Apartman")) {
					smestajEnum = TipoviSmestaja.Apartman;
				} else {
					smestajEnum = TipoviSmestaja.Hotel;
				}

				String tipAranzmana = cmbTipAranzmana.getSelectedItem().toString();
				if (tipAranzmana.equals("Letovanje")) {
					aranzmanEnum = TipoviAranzmana.Letovanje;
				} else if (tipAranzmana.equals("Zimovanje")) {
					aranzmanEnum = TipoviAranzmana.Zimovanje;
				} else if (tipAranzmana.equals("Evropski gradovi")) {
					aranzmanEnum = TipoviAranzmana.EvropskiGradovi;
				} else if (tipAranzmana.equals("Daleka putovanja")) {
					aranzmanEnum = TipoviAranzmana.DalekaPutovanja;
				} else if (tipAranzmana.equals("First minute")) {
					aranzmanEnum = TipoviAranzmana.FirstMinute;
				} else if (tipAranzmana.equals("Last minute")) {
					aranzmanEnum = TipoviAranzmana.LastMinute;
				} else if (tipAranzmana.equals("Putovanja u toku praznika")) {
					aranzmanEnum = TipoviAranzmana.PutovanjaUtokuPraznika;
				}

				Date datum = dateChooser.getDate();
				LocalDate localDate = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				kapacitet = Integer.parseInt(txtKapacitet.getText());
				cena = Double.parseDouble(txtCena.getText());
				sajamskiPopust = Integer.parseInt(txtAPopust.getText());
				putanjaDoSlike = txtPutanjaDoSlike.getText();

				id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
				noviAranzman = new Aranzman(id, UpravljanjeKorisnicima.prijavljenaOsoba, aranzmanEnum, smestajEnum,
						localDate, kapacitet, cena, sajamskiPopust, putanjaDoSlike, true);
				UpravljanjeAranzmanima.dodajAranzman(noviAranzman);
				AranzmaniPanel.addAranzmanCard(aranzmanPanel, noviAranzman);
				dispose();
				clearInputs();
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridy++;
		panel.add(lblTipAranzmana, gbc);
		gbc.gridy++;
		panel.add(lblTipSmestaja, gbc);
		gbc.gridy++;
		panel.add(lblDatum, gbc);
		gbc.gridy++;
		panel.add(lblCena, gbc);
		gbc.gridy++;
		panel.add(lblPopust, gbc);
		gbc.gridy++;
		panel.add(lblKapacitet, gbc);
		gbc.gridy++;
		panel.add(lblSlika, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridy++;
		panel.add(cmbTipAranzmana, gbc);
		gbc.gridy++;
		panel.add(cmbTipSmestaja, gbc);
		gbc.gridy++;
		panel.add(dateChooser, gbc);
		gbc.gridy++;
		panel.add(txtCena, gbc);
		gbc.gridy++;
		panel.add(txtAPopust, gbc);
		gbc.gridy++;
		panel.add(txtKapacitet, gbc);
		gbc.gridy++;
		panel.add(txtPutanjaDoSlike, gbc);
		gbc.gridy++;
		panel.add(btnChoose, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnKreiraj);
		buttonPanel.add(btnOdustani);
		panel.add(buttonPanel, gbc);

		getContentPane().add(panel);
		pack();
	}

	private void clearInputs() {
		txtKapacitet.setText("");
		txtCena.setText("");
		txtAPopust.setText("");
		dateChooser.setDate(null);
		txtPutanjaDoSlike.setText("");
	}

	private int validateFields() {
		String kapacitet = txtKapacitet.getText();
		String cena = txtCena.getText();
		String popust = txtAPopust.getText();
		Date datum = dateChooser.getDate();
		LocalDate localDate = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (Integer.parseInt(popust) >= 100) {
			return 1;
		}
		if (kapacitet.isEmpty() || cena.isEmpty() || popust.isEmpty() || txtPutanjaDoSlike.getText().isEmpty()) {
			return 2; // One or more fields are empty
		}
		if (Integer.parseInt(kapacitet) <= 0) {
			return 3;
		}
		if (LocalDate.now().isAfter(localDate)) {
			return 4;
		}
		if (Double.parseDouble(cena) <= 0) {
			return 7;
		}

		return 6; // All fields are valid
	}
}
