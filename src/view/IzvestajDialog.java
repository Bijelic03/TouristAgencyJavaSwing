package view;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

public class IzvestajDialog extends JDialog {
    private JLabel lblOd;
    private JLabel lblDo;
    private JDateChooser dateChooserOd;
    private JDateChooser dateChooserDo;
    private JButton btnKreiraj;
    private JButton btnOtkazi;

    public IzvestajDialog() {
        setTitle("Izveštaj");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        lblOd = new JLabel("Od:");
        lblDo = new JLabel("Do:");

        dateChooserOd = new JDateChooser();
        dateChooserDo = new JDateChooser();

        btnKreiraj = new JButton("Kreiraj");
        btnOtkazi = new JButton("Otkaži");

        btnKreiraj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date datumOd = dateChooserOd.getDate();
                Date datumDo = dateChooserDo.getDate();

                // Provera unetih datuma i generisanje izveštaja
                if (datumOd == null || datumDo == null) {
                    JOptionPane.showMessageDialog(IzvestajDialog.this, "Unesite ispravne datume", "Greška",
                            JOptionPane.ERROR_MESSAGE);
                }else if(datumOd.compareTo(datumDo) > 0) {
                    JOptionPane.showMessageDialog(IzvestajDialog.this, "Niste uneli datume kakko treba", "Greška",
                            JOptionPane.ERROR_MESSAGE);
                }
                
                else{
                	IzvestajWindow izvestajWindow = new IzvestajWindow(datumOd, datumDo);
                	izvestajWindow.setVisible(true);
                    dispose();
                }
            }
        });

        btnOtkazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(lblOd, gbc);
        gbc.gridx++;
        panel.add(dateChooserOd, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(lblDo, gbc);
        gbc.gridx++;
        panel.add(dateChooserDo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(btnKreiraj, gbc);

        gbc.gridy++;
        panel.add(btnOtkazi, gbc);

        setContentPane(panel);
    }


}
