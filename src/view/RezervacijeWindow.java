package view;
import javax.swing.JFrame;

public class RezervacijeWindow extends JFrame {
    public RezervacijeWindow() {
        super("RezervacijeWindow");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        RezervacijeWindow rezervacijeWindow = new RezervacijeWindow();
        rezervacijeWindow.setVisible(true);
    }
}