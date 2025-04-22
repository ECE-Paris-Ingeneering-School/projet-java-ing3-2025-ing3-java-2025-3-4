package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueModifierSupprimerOption extends JFrame {
    private JTextField champId;
    private JTextField champNom;
    private JButton boutonModifier;
    private JButton boutonSupprimer;

    public VueModifierSupprimerOption() {
        setTitle("Modifier ou Supprimer une option");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        champId = new JTextField(5);
        champNom = new JTextField(15);
        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");

        boutonModifier.setActionCommand("MODIFIER_OPTION");
        boutonSupprimer.setActionCommand("SUPPRIMER_OPTION");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("ID de l'option :"));
        panel.add(champId);
        panel.add(new JLabel("Nouveau nom :"));
        panel.add(champNom);
        panel.add(boutonModifier);
        panel.add(boutonSupprimer);

        add(panel);
    }

    public int getIdOption() {
        return Integer.parseInt(champId.getText());
    }

    public String getNouveauNomOption() {
        return champNom.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonModifier.addActionListener(listener);
        boutonSupprimer.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
