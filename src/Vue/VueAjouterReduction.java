package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjouterReduction extends JFrame {
    // Composants du formulaire
    private JTextField champHebergementId;
    private JTextField champPourcentage;
    private JTextField champDescription;
    private JTextField champDateDebut;
    private JTextField champDateFin;

    // Boutons d'action
    private JButton boutonAjouter;
    private JButton boutonRetour;

    // Barre de navigation
    private JButton boutonAccueil;
    private JButton boutonDeconnexion;

    // Couleurs et polices pour un style cohérent
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueAjouterReduction() {
        setTitle("Ajouter une réduction");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec une marge
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panelPrincipal);

        // --- Barre de navigation en haut avec style amélioré ---
        JPanel barreNavigation = new JPanel();
        barreNavigation.setLayout(new BoxLayout(barreNavigation, BoxLayout.X_AXIS));
        barreNavigation.setBackground(couleurPrincipale);
        barreNavigation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boutonAccueil = createNavButton("Accueil", "RETOUR_ACCUEIL");
        boutonDeconnexion = createNavButton("Déconnexion", "DECONNEXION");

        barreNavigation.add(boutonAccueil);
        barreNavigation.add(Box.createHorizontalStrut(15));
        barreNavigation.add(Box.createHorizontalGlue());
        barreNavigation.add(boutonDeconnexion);

        // --- Panel du formulaire avec style cohérent ---
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        panelFormulaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        "Formulaire d'ajout de réduction",
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
        gbc.insets = new Insets(5, 5, 10, 10);
        gbc.weightx = 1.0;

        // Création des champs avec style cohérent
        champHebergementId = createTextField();
        champPourcentage = createTextField();
        champDescription = createTextField();
        champDateDebut = createTextField();
        champDateFin = createTextField();

        // Boutons d'action
        boutonAjouter = new JButton("Ajouter la réduction");
        boutonAjouter.setFont(policeNormale);
        boutonAjouter.setBackground(couleurPrincipale);
        boutonAjouter.setForeground(Color.WHITE);
        boutonAjouter.setFocusPainted(false);
        boutonAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonAjouter.setActionCommand("AJOUT_REDUCTION_BDD");

        boutonRetour = new JButton("Retour à l'accueil");
        boutonRetour.setFont(policeNormale);
        boutonRetour.setBackground(couleurSecondaire);
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        // Ajout des composants au formulaire
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        panelFormulaire.add(createLabel("ID Hébergement :"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panelFormulaire.add(champHebergementId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulaire.add(createLabel("Pourcentage de réduction :"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panelFormulaire.add(champPourcentage, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulaire.add(createLabel("Description :"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        panelFormulaire.add(champDescription, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulaire.add(createLabel("Date début (YYYY-MM-DD) :"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        panelFormulaire.add(champDateDebut, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulaire.add(createLabel("Date fin (YYYY-MM-DD) :"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        panelFormulaire.add(champDateFin, gbc);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.add(boutonRetour);
        panelBoutons.add(boutonAjouter);

        // --- Layout global ---
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(barreNavigation, BorderLayout.NORTH);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulaire, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
    }

    // Méthodes utilitaires pour créer des composants avec style cohérent
    private JButton createNavButton(String text, String action) {
        JButton button = new JButton(text);
        button.setFont(policeNormale);
        button.setActionCommand(action);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(couleurPrincipale);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(policeNormale);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(policeNormale);
        textField.setMargin(new Insets(5, 5, 5, 5));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(couleurBordure),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    // Getters pour les valeurs des champs
    public String getHebergementId() {
        return champHebergementId.getText();
    }

    public String getPourcentage() {
        return champPourcentage.getText();
    }

    public String getDescription() {
        return champDescription.getText();
    }

    public String getDateDebut() {
        return champDateDebut.getText();
    }

    public String getDateFin() {
        return champDateFin.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouter.addActionListener(listener);
        boutonRetour.addActionListener(listener);
        boutonAccueil.addActionListener(listener);
        boutonDeconnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void resetChamps() {
        champHebergementId.setText("");
        champPourcentage.setText("");
        champDescription.setText("");
        champDateDebut.setText("");
        champDateFin.setText("");
    }
}