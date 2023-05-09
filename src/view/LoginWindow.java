package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class LoginWindow extends JFrame {

	public LoginWindow() {
		setTitle("Turisticka agencija");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
	}
	
	private void initGUI() {
		// Dodavanje "OK" dugmeta na dno prozora
		Login loginPanel = new Login();
		add(loginPanel, BorderLayout.CENTER);

	}


}