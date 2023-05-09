package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.UlogaSwitch;
import model.Osoba;
import service.UpravljanjeKorisnicima;

public class Login extends JPanel {

	private JLabel usernameText = new JLabel("unesite username");
	private JLabel passwordText = new JLabel("unesite sifru");

	private JTextField usernameInput = new JTextField(10);
	private JTextField passwordInput = new JTextField(10);

	private JButton btnLOGIN = new JButton("Login");
	private JButton btnEXIT = new JButton("Exit");
	private JPanel buttons = new JPanel();
	private JPanel inputs = new JPanel();
	
	private static Osoba trenutnaOsoba;

	
	public Login() {
		Window window = LoginWindow.getWindows()[0];
		buttons.add(btnLOGIN);
		buttons.add(btnEXIT);
		inputs.add(usernameText);

		inputs.add(usernameInput);
		inputs.add(passwordText);

		inputs.add(passwordInput);
		this.add(inputs);
		this.add(buttons);
		// Reakcija na Login dugme
		btnLOGIN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent username) {
				String usernameValue = usernameInput.getText();
				String passwordValue = passwordInput.getText();
				trenutnaOsoba = UpravljanjeKorisnicima.Login(usernameValue, passwordValue);
				UlogaSwitch.prikaziUlogu(trenutnaOsoba);
			}
		});
		btnEXIT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();

			}
		});
	}



}
