package Vue;

import Modele.Hebergement;
import Modele.Option;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VueAssocierOptionsHebergement extends JFrame {
    private JComboBox<Hebergement> comboBoxHebergements;
    private JPanel panelOptions;
    private JButton boutonValider;
    private JButton boutonRetour;
    private List<JCheckBox> checkBoxesOptions = new ArrayList<>();

    // Barre de navigation
    private JButton boutonAccueil;
    private JButton boutonDeconnexion;

    // Couleurs et polices pour un style cohérent
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueAssocierOptionsHebergement() {
        setTitle("Associer des options à un hébergement");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        // --- Panel de sélection d'hébergement ---
        JPanel panelSelection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSelection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSelection.setBackground(Color.WHITE);

        JLabel lblHebergement = createLabel("Hébergement :");
        comboBoxHebergements = new JComboBox<>();
        comboBoxHebergements.setFont(policeNormale);
        comboBoxHebergements.setPreferredSize(new Dimension(300, 30));

        panelSelection.add(lblHebergement);
        panelSelection.add(comboBoxHebergements);

        // --- Panel des options avec style cohérent ---
        panelOptions = new JPanel();
        panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
        panelOptions.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelOptions);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(couleurBordure),
                "Options disponibles",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                policeTitre,
                couleurPrincipale
        ));
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // --- Boutons de validation ---
        boutonValider = new JButton("Valider l'association");
        boutonValider.setFont(policeNormale);
        boutonValider.setBackground(couleurPrincipale);
        boutonValider.setForeground(Color.WHITE);
        boutonValider.setFocusPainted(false);
        boutonValider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonValider.setActionCommand("VALIDER_ASSOCIATION");

        boutonRetour = new JButton("Retour");
        boutonRetour.setFont(policeNormale);
        boutonRetour.setBackground(couleurSecondaire);
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBoutons.setBackground(Color.WHITE);
        panelBoutons.add(boutonRetour);
        panelBoutons.add(boutonValider);

        // --- Layout global ---
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(barreNavigation, BorderLayout.NORTH);
        panelHaut.add(panelSelection, BorderLayout.CENTER);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
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

    public void setHebergements(List<Hebergement> hebergements) {
        comboBoxHebergements.removeAllItems();
        for (Hebergement h : hebergements) {
            comboBoxHebergements.addItem(h);
        }
    }

    public void setOptions(List<Option> options, List<Option> optionsAssociees) {
        panelOptions.removeAll();
        checkBoxesOptions.clear();

        for (Option opt : options) {
            JCheckBox checkBox = new JCheckBox(opt.getNomOption());
            checkBox.setFont(policeNormale);
            checkBox.setBackground(Color.WHITE);
            checkBox.setSelected(optionsAssociees.contains(opt));
            checkBox.putClientProperty("option", opt);
            checkBoxesOptions.add(checkBox);

            JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            optionPanel.setBackground(Color.WHITE);
            optionPanel.add(checkBox);
            panelOptions.add(optionPanel);
        }

        panelOptions.revalidate();
        panelOptions.repaint();
    }

    public Hebergement getHebergementSelectionne() {
        return (Hebergement) comboBoxHebergements.getSelectedItem();
    }

    public List<Option> getOptionsSelectionnees() {
        List<Option> selectionnees = new ArrayList<>();
        for (JCheckBox cb : checkBoxesOptions) {
            if (cb.isSelected()) {
                selectionnees.add((Option) cb.getClientProperty("option"));
            }
        }
        return selectionnees;
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonValider.addActionListener(listener);
        boutonRetour.addActionListener(listener);
        comboBoxHebergements.addActionListener(listener);
        boutonAccueil.addActionListener(listener);
        boutonDeconnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JComboBox<Hebergement> getComboBoxHebergements() {
        return comboBoxHebergements;
    }
}