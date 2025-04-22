package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjouterOption extends JFrame {
    private JTextField champNom;
    private JButton boutonAjouter;

    public VueAjouterOption() {
        setTitle("Ajouter une option");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        champNom = new JTextField(20);
        boutonAjouter = new JButton("Ajouter l'option");
        boutonAjouter.setActionCommand("AJOUTER_OPTION");

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(champNom);
        panel.add(boutonAjouter);

        add(panel);
    }

    public String getNomOption() {
        return champNom.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouter.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
