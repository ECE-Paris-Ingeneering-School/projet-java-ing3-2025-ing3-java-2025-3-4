package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class VueAjoutHebergement extends JFrame {
    // Composants du formulaire
    private JTextField champNom;
    private JTextField champAdresse;
    private JTextField champVille;
    private JTextField champPays;
    private JComboBox<String> comboCategorie;
    private JTextField champPrix;
    private JTextArea champDescription;
    private JTextField champPhoto;
    private JTextField champPlaceMax; // Nouveau champ
    private JTextField champNbChambres; // Nouveau champ
    private JButton boutonParcourir;

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

    public VueAjoutHebergement() {
        setTitle("Ajouter un Hébergement");
        setSize(800, 600);
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
                        "Formulaire d'ajout d'hébergement",
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
        champNom = createTextField();
        champAdresse = createTextField();
        champVille = createTextField();
        champPays = createTextField();
        champPlaceMax = createTextField(); // Champ place max
        champNbChambres = createTextField(); // Champ nb chambres

        String[] categories = {"Appartement", "Hôtel", "Villa", "Camping", "Resort"};
        comboCategorie = new JComboBox<>(categories);
        comboCategorie.setFont(policeNormale);

        champPrix = createTextField();

        champDescription = new JTextArea(3, 20);
        champDescription.setFont(policeNormale);
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);
        JScrollPane scrollDescription = new JScrollPane(champDescription);
        scrollDescription.setBorder(BorderFactory.createLineBorder(couleurBordure));

        champPhoto = createTextField();
        boutonParcourir = new JButton("Parcourir...");
        boutonParcourir.setFont(policeNormale);
        boutonParcourir.setBackground(couleurPrincipale);
        boutonParcourir.setForeground(Color.WHITE);
        boutonParcourir.setFocusPainted(false);
        boutonParcourir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonParcourir.addActionListener(e -> choisirFichierPhoto());

        JPanel panelPhoto = new JPanel(new BorderLayout(5, 0));
        panelPhoto.add(champPhoto, BorderLayout.CENTER);
        panelPhoto.add(boutonParcourir, BorderLayout.EAST);

        // Boutons d'action
        boutonAjouter = new JButton("Ajouter");
        boutonAjouter.setFont(policeNormale);
        boutonAjouter.setBackground(couleurPrincipale);
        boutonAjouter.setForeground(Color.WHITE);
        boutonAjouter.setFocusPainted(false);
        boutonAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonAjouter.setActionCommand("AJOUTER_HEBERGEMENT");

        boutonRetour = new JButton("Retour");
        boutonRetour.setFont(policeNormale);
        boutonRetour.setBackground(couleurSecondaire);
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        // Ajout des composants au formulaire
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        panelFormulaire.add(createLabel("Nom :"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panelFormulaire.add(champNom, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulaire.add(createLabel("Adresse :"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panelFormulaire.add(champAdresse, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulaire.add(createLabel("Ville :"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        panelFormulaire.add(champVille, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulaire.add(createLabel("Pays :"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        panelFormulaire.add(champPays, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulaire.add(createLabel("Catégorie :"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        panelFormulaire.add(comboCategorie, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulaire.add(createLabel("Prix par nuit (€) :"), gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        panelFormulaire.add(champPrix, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulaire.add(createLabel("Description :"), gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        panelFormulaire.add(scrollDescription, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        panelFormulaire.add(createLabel("Photo (chemin) :"), gbc);

        gbc.gridx = 1; gbc.gridy = 7;
        panelFormulaire.add(panelPhoto, gbc);

        // Ajout des nouveaux champs
        gbc.gridx = 0; gbc.gridy = 8;
        panelFormulaire.add(createLabel("Place max :"), gbc);

        gbc.gridx = 1; gbc.gridy = 8;
        panelFormulaire.add(champPlaceMax, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        panelFormulaire.add(createLabel("Nombre de chambres :"), gbc);

        gbc.gridx = 1; gbc.gridy = 9;
        panelFormulaire.add(champNbChambres, gbc);

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

    private void choisirFichierPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fichier = fileChooser.getSelectedFile();
            champPhoto.setText(fichier.getPath());
        }
    }

    // Getters pour les valeurs des champs
    public String getNom() { return champNom.getText(); }
    public String getAdresse() { return champAdresse.getText(); }
    public String getVille() { return champVille.getText(); }
    public String getPays() { return champPays.getText(); }
    public String getCategorie() { return (String) comboCategorie.getSelectedItem(); }
    public String getPrix() { return champPrix.getText(); }
    public String getDescription() { return champDescription.getText(); }
    public String getPhoto() { return champPhoto.getText(); }
    public int getPlace() { return Integer.parseInt(champPlaceMax.getText()); } // Nouveau getter
    public int getNbChambre() { return Integer.parseInt(champNbChambres.getText()); } // Nouveau getter

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
        champNom.setText("");
        champAdresse.setText("");
        champVille.setText("");
        champPays.setText("");
        comboCategorie.setSelectedIndex(0);
        champPrix.setText("");
        champDescription.setText("");
        champPhoto.setText("");
        champPlaceMax.setText(""); // Réinitialiser le champ place max
        champNbChambres.setText(""); // Réinitialiser le champ nb chambres
    }
}
