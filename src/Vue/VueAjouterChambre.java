package Vue;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjouterChambre extends JFrame {
    private JTextField champHebergementId;
    private JTextField champCapacite;
    private JButton boutonAjouter;
    private JButton boutonRetour;

    // Couleurs et polices pour un style cohérent
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueAjouterChambre() {
        setTitle("Ajouter une chambre");
        setSize(500, 250); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec une marge
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panelPrincipal);

        // --- Panel du formulaire avec style cohérent ---
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        panelFormulaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        "Ajouter une nouvelle chambre",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        policeTitre,
                        couleurPrincipale
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelFormulaire.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Création des champs avec style cohérent
        champHebergementId = new JTextField(20);
        champCapacite = new JTextField(20);

        // Labels pour les champs
        JLabel labelHebergementId = createLabel("ID Hébergement :");
        JLabel labelCapacite = createLabel("Capacité :");

        // Ajout des composants au formulaire
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        panelFormulaire.add(labelHebergementId, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panelFormulaire.add(champHebergementId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulaire.add(labelCapacite, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panelFormulaire.add(champCapacite, gbc);

        // --- Panel des boutons ---
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBoutons.setBackground(Color.WHITE);
        boutonAjouter = new JButton("Ajouter la chambre");
        boutonAjouter.setBackground(couleurPrincipale);
        boutonAjouter.setForeground(Color.WHITE);
        boutonAjouter.setActionCommand("AJOUT_CHAMBRE_BDD");

        boutonRetour = new JButton("Retour à l'accueil");
        boutonRetour.setBackground(couleurSecondaire);
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonRetour);

        // --- Layout global ---
        panelPrincipal.add(panelFormulaire, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
    }

    // Méthodes utilitaires pour créer des composants avec style cohérent
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(policeNormale);
        return label;
    }

    public String getHebergementId() {
        return champHebergementId.getText();
    }

    public String getCapacite() {
        return champCapacite.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouter.addActionListener(listener);
        boutonRetour.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void resetChamps() {
        champHebergementId.setText("");
        champCapacite.setText("");
    }
}
