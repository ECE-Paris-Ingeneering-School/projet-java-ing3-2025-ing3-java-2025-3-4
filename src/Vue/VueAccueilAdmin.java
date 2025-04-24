package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAccueilAdmin extends JFrame {

    private JButton boutonAjouterHebergement;
    private JButton boutonAjouterOption;
    private JButton boutonAssocierOption;
    private JButton boutonModifierSupprimerOption;
    private JButton boutonDeconnexion;

    public VueAccueilAdmin() {
        setTitle("Accueil Admin - Gestion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelBoutons = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        boutonAjouterHebergement = new JButton("Créer un Hébergement");
        boutonAjouterHebergement.setActionCommand("CREER_HEBERGEMENT");

        boutonAjouterOption = new JButton("Créer une Option");
        boutonAjouterOption.setActionCommand("CREER_OPTION");

        boutonAssocierOption = new JButton("Associer une Option");
        boutonAssocierOption.setActionCommand("ASSOCIER_OPTION");

        boutonModifierSupprimerOption = new JButton("Modifier/Supprimer une Option");
        boutonModifierSupprimerOption.setActionCommand("MODIFIER_SUPPRIMER_OPTION");

        boutonDeconnexion = new JButton("Déconnexion");
        boutonDeconnexion.setActionCommand("DECONNEXION");

        panelBoutons.add(boutonAjouterHebergement);
        panelBoutons.add(boutonAjouterOption);
        panelBoutons.add(boutonAssocierOption);
        panelBoutons.add(boutonModifierSupprimerOption);
        panelBoutons.add(boutonDeconnexion);

        add(panelBoutons);
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouterHebergement.addActionListener(listener);
        boutonAjouterOption.addActionListener(listener);
        boutonAssocierOption.addActionListener(listener);
        boutonModifierSupprimerOption.addActionListener(listener);
        boutonDeconnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
