package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAccueilAdmin extends JFrame {

    private JButton boutonAjouterHebergement;
    private JButton boutonAjouterOption;
    private JButton boutonAssocierOption;
    private JButton boutonModifierSupprimerOption;
    private JButton boutonAjouterReduction;
    private JButton boutonDeconnexion;

    // Couleurs et polices pour un style cohérent
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 18);

    public VueAccueilAdmin() {
        setTitle("Accueil Admin - Gestion");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal avec bordure
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panelPrincipal);

        // Panel de titre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(couleurSecondaire);
        JLabel labelTitre = new JLabel("Administration");
        labelTitre.setForeground(couleurPrincipale);
        labelTitre.setFont(policeTitre);
        panelTitre.add(labelTitre);

        // Panel de boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(couleurBordure, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Création des boutons avec style uniforme
        boutonAjouterHebergement = createButton("Créer un Hébergement", "CREER_HEBERGEMENT");
        boutonAjouterOption = createButton("Créer une Option", "CREER_OPTION");
        boutonAssocierOption = createButton("Associer une Option", "ASSOCIER_OPTION");
        boutonModifierSupprimerOption = createButton("Modifier/Supprimer une Option", "MODIFIER_SUPPRIMER_OPTION");
        boutonAjouterReduction = createButton("Ajouter une Réduction", "AJOUTER_REDUCTION");

        // Bouton de déconnexion avec style spécial
        boutonDeconnexion = new JButton("Déconnexion");
        boutonDeconnexion.setFont(policeNormale);
        boutonDeconnexion.setBackground(new Color(217, 83, 79)); // Rouge pour déconnexion
        boutonDeconnexion.setForeground(Color.WHITE);
        boutonDeconnexion.setFocusPainted(false);
        boutonDeconnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonDeconnexion.setActionCommand("DECONNEXION");
        boutonDeconnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonDeconnexion.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));

        // Ajout des boutons avec espacement
        panelBoutons.add(boutonAjouterHebergement);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBoutons.add(boutonAjouterOption);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBoutons.add(boutonAssocierOption);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBoutons.add(boutonModifierSupprimerOption);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBoutons.add(boutonAjouterReduction);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, 20)));
        panelBoutons.add(boutonDeconnexion);

        // Ajout des panels au panel principal
        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelBoutons, BorderLayout.CENTER);
    }

    // Méthode utilitaire pour créer des boutons avec style uniforme
    private JButton createButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setFont(policeNormale);
        button.setBackground(couleurPrincipale);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(actionCommand);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        return button;
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouterHebergement.addActionListener(listener);
        boutonAjouterOption.addActionListener(listener);
        boutonAssocierOption.addActionListener(listener);
        boutonModifierSupprimerOption.addActionListener(listener);
        boutonAjouterReduction.addActionListener(listener);
        boutonDeconnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}