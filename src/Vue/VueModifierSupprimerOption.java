package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueModifierSupprimerOption extends JFrame {
    private JTextField champId;
    private JTextField champNom;
    private JButton boutonModifier;
    private JButton boutonSupprimer;

    // Barre de navigation
    private JButton boutonAccueil;
    private JButton boutonDeconnexion;

    // Couleurs et polices pour un style cohérent
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueModifierSupprimerOption() {
        setTitle("Modifier/Supprimer une option");
        setSize(600, 400);
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
                        "Modifier ou supprimer une option",
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
        champId = createTextField();
        champNom = createTextField();

        // Boutons d'action
        boutonModifier = new JButton("Modifier l'option");
        boutonModifier.setFont(policeNormale);
        boutonModifier.setBackground(couleurPrincipale);
        boutonModifier.setForeground(Color.WHITE);
        boutonModifier.setFocusPainted(false);
        boutonModifier.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonModifier.setActionCommand("MODIFIER_OPTION");

        boutonSupprimer = new JButton("Supprimer l'option");
        boutonSupprimer.setFont(policeNormale);
        boutonSupprimer.setBackground(new Color(220, 53, 69)); // Rouge pour suppression
        boutonSupprimer.setForeground(Color.WHITE);
        boutonSupprimer.setFocusPainted(false);
        boutonSupprimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonSupprimer.setActionCommand("SUPPRIMER_OPTION");

        // Ajout des composants au formulaire
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        panelFormulaire.add(createLabel("ID de l'option :"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panelFormulaire.add(champId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulaire.add(createLabel("Nouveau nom :"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panelFormulaire.add(champNom, gbc);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.add(boutonModifier);
        panelBoutons.add(boutonSupprimer);

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
    public int getIdOption() {
        try {
            return Integer.parseInt(champId.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getNouveauNomOption() {
        return champNom.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonModifier.addActionListener(listener);
        boutonSupprimer.addActionListener(listener);
        boutonAccueil.addActionListener(listener);
        boutonDeconnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void resetChamps() {
        champId.setText("");
        champNom.setText("");
    }
}