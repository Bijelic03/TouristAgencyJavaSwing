package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UlogaSwitch;
import model.Osoba;
import service.UpravljanjeKorisnicima;

public class Login extends JPanel {

	private JLabel usernameText = new JLabel("Unesite korisničko ime");
	private JLabel passwordText = new JLabel("Unesite šifru");

	private JTextField usernameInput = new JTextField(10);
	private JPasswordField passwordInput = new JPasswordField(10);

	private JButton btnLOGIN = new JButton("Login");
	private JButton btnEXIT = new JButton("Exit");
	private JPanel buttons = new JPanel();
	private JPanel inputs = new JPanel();

	private Osoba trenutnaOsoba;

	public void zatvoriProzore() {
	    Window[] windows = Window.getWindows();
	    for (Window window : windows) {
	        window.dispose();
	    }
	}
	
	
	
	public Login() {
		buttons.add(btnLOGIN);
		buttons.add(btnEXIT);
		inputs.add(usernameText);
		inputs.add(usernameInput);
		inputs.add(passwordText);
		inputs.add(passwordInput);
		this.add(inputs);
		this.add(buttons);

		btnLOGIN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameValue = usernameInput.getText();
				String passwordValue = passwordInput.getText();
				UpravljanjeKorisnicima.ucitajKorisnike();
				trenutnaOsoba = UpravljanjeKorisnicima.Login(usernameValue, passwordValue);
				if (trenutnaOsoba != null) {
					zatvoriProzore();

					UlogaSwitch.prikaziUlogu(trenutnaOsoba);
				} else {
					JOptionPane.showMessageDialog(null, "Pogrešno korisničko ime ili šifra", "Greška",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnEXIT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zatvoriProzore();
			}
		});
	}
}