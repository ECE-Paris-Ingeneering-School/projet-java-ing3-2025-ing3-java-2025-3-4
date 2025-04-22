package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAssocierOptionsHebergement extends JFrame {
    private JTextField champIdHebergement;
    private JTextField champIdOption;
    private JButton boutonAssocier;
    private JButton boutonRetirer;

    public VueAssocierOptionsHebergement() {
        setTitle("Associer ou Retirer une option");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        champIdHebergement = new JTextField(5);
        champIdOption = new JTextField(5);
        boutonAssocier = new JButton("Associer");
        boutonRetirer = new JButton("Retirer");

        boutonAssocier.setActionCommand("ASSOCIER_OPTION");
        boutonRetirer.setActionCommand("RETIRER_OPTION");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("ID Hébergement :"));
        panel.add(champIdHebergement);
        panel.add(new JLabel("ID Option :"));
        panel.add(champIdOption);
        panel.add(boutonAssocier);
        panel.add(boutonRetirer);

        add(panel);
    }

    public int getIdHebergement() {
        return Integer.parseInt(champIdHebergement.getText());
    }

    public int getIdOption() {
        return Integer.parseInt(champIdOption.getText());
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAssocier.addActionListener(listener);
        boutonRetirer.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
