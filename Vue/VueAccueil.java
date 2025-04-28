package Vue;

import Dao.HebergementDAOImpl;
import Dao.OptionDAOImpl;
import Modele.Chambre;
import Modele.Hebergement;
import Modele.Option;
import Modele.Reduction;
import Controleur.Inscription;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;

public class VueAccueil extends JFrame {
    private JTable tableHebergements;
    private DefaultTableModel tableModel;

    private JTextField champLieu;
    private JSpinner spinnerPersonnes;
    private JTextField champDateDebut;
    private JTextField champDateFin;
    private JButton boutonRecherche;

    private JButton boutonAccueil;
    private JButton boutonMesReservations;
    private JButton boutonDeconnexion;

    private JPanel panelOptions;
    private List<JCheckBox> checkBoxesOptions;

    private ActionListener actionListener;

    private ArrayList<Hebergement> hebergementsAffiches;
    private Hebergement hebergementSelectionne;
    private Chambre chambreSelectionnee;

    private HebergementDAOImpl hebergementDAO;
    private OptionDAOImpl optionDAO;

    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueAccueil(HebergementDAOImpl hebergementDAO, OptionDAOImpl optionDAO) {
        this.hebergementDAO = hebergementDAO;
        this.optionDAO = optionDAO;
        this.checkBoxesOptions = new ArrayList<>();

        setTitle("Accueil - Rechercher un Hébergement");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panelPrincipal);

        JPanel barreNavigation = new JPanel();
        barreNavigation.setLayout(new BoxLayout(barreNavigation, BoxLayout.X_AXIS));
        barreNavigation.setBackground(couleurPrincipale);
        barreNavigation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boutonAccueil = createNavButton("Accueil", "NAV_ACCUEIL");
        boutonMesReservations = createNavButton("Mes Réservations", "NAV_MES_RESERVATIONS");
        boutonDeconnexion = createNavButton("Déconnexion", "DECONNEXION");

        barreNavigation.add(boutonAccueil);
        barreNavigation.add(Box.createHorizontalStrut(15));
        barreNavigation.add(boutonMesReservations);
        barreNavigation.add(Box.createHorizontalGlue());
        barreNavigation.add(boutonDeconnexion);

        JPanel panelRecherche = new JPanel(new GridBagLayout());
        panelRecherche.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        "Rechercher un hébergement",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        policeTitre,
                        couleurPrincipale
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelRecherche.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 10);

        JLabel lblLieu = createLabel("Lieu :");
        JLabel lblPersonnes = createLabel("Personnes :");
        JLabel lblDateDebut = createLabel("Date d'arrivée :");
        JLabel lblDateFin = createLabel("Date de départ :");

        champLieu = createTextField();
        spinnerPersonnes = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spinnerPersonnes.setFont(policeNormale);

        champDateDebut = createTextField();
        champDateDebut.setText("JJ/MM/AAAA");

        champDateFin = createTextField();
        champDateFin.setText("JJ/MM/AAAA");

        boutonRecherche = new JButton("Rechercher");
        boutonRecherche.setFont(policeNormale);
        boutonRecherche.setBackground(couleurPrincipale);
        boutonRecherche.setForeground(Color.WHITE);
        boutonRecherche.setFocusPainted(false);
        boutonRecherche.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRecherche.setActionCommand("RECHERCHER");

        gbc.gridx = 0; gbc.gridy = 0;
        panelRecherche.add(lblLieu, gbc);
        gbc.gridx = 1;
        panelRecherche.add(champLieu, gbc);
        gbc.gridx = 2;
        panelRecherche.add(lblPersonnes, gbc);
        gbc.gridx = 3;
        panelRecherche.add(spinnerPersonnes, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelRecherche.add(lblDateDebut, gbc);
        gbc.gridx = 1;
        panelRecherche.add(champDateDebut, gbc);
        gbc.gridx = 2;
        panelRecherche.add(lblDateFin, gbc);
        gbc.gridx = 3;
        panelRecherche.add(champDateFin, gbc);
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.BOTH;
        panelRecherche.add(boutonRecherche, gbc);

        panelOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOptions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        "Filtrer par options",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        policeTitre,
                        couleurPrincipale
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelOptions.setBackground(Color.WHITE);

        chargerOptionsDisponibles();
        JScrollPane scrollPaneOptions = new JScrollPane(panelOptions);
        scrollPaneOptions.setPreferredSize(new Dimension(1400, 80));
        scrollPaneOptions.setBorder(null);

        tableModel = new DefaultTableModel(
                new Object[]{"Photo", "Nom", "Lieu", "Description", "Prix (€)", "Note Moyenne", "Réduction", "Réserver"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return ImageIcon.class;
                if (columnIndex == 7) return JButton.class;
                return String.class;
            }
        };

        tableHebergements = new JTable(tableModel);
        tableHebergements.setRowHeight(80);
        tableHebergements.setFont(policeNormale);
        tableHebergements.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableHebergements.getTableHeader().setBackground(couleurSecondaire);
        tableHebergements.setSelectionBackground(new Color(230, 240, 250));

        tableHebergements.getColumnModel().getColumn(0).setPreferredWidth(120); // Photo
        tableHebergements.getColumnModel().getColumn(1).setPreferredWidth(200); // Nom
        tableHebergements.getColumnModel().getColumn(2).setPreferredWidth(160); // Lieu
        tableHebergements.getColumnModel().getColumn(3).setPreferredWidth(300); // Description
        tableHebergements.getColumnModel().getColumn(4).setPreferredWidth(90); // Prix
        tableHebergements.getColumnModel().getColumn(5).setPreferredWidth(110); // Note Moyenne
        tableHebergements.getColumnModel().getColumn(6).setPreferredWidth(150); // Réduction
        tableHebergements.getColumnModel().getColumn(7).setPreferredWidth(100); // Réserver

        JScrollPane scrollPaneTable = new JScrollPane(tableHebergements);
        scrollPaneTable.setBorder(BorderFactory.createLineBorder(couleurBordure));
        scrollPaneTable.getViewport().setBackground(Color.WHITE);

        ButtonRenderer buttonRenderer = new ButtonRenderer();
        buttonRenderer.setBackground(couleurPrincipale);
        buttonRenderer.setForeground(Color.WHITE);
        buttonRenderer.setFont(policeNormale);
        tableHebergements.getColumn("Réserver").setCellRenderer(buttonRenderer);
        tableHebergements.getColumn("Réserver").setCellEditor(new ButtonEditor(new JCheckBox()));

        JPanel panelHaut = new JPanel(new BorderLayout(0, 10));
        panelHaut.add(barreNavigation, BorderLayout.NORTH);
        panelHaut.add(panelRecherche, BorderLayout.CENTER);

        JPanel panelCentre = new JPanel(new BorderLayout());
        panelCentre.add(scrollPaneOptions, BorderLayout.NORTH);
        panelCentre.add(scrollPaneTable, BorderLayout.CENTER);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelCentre, BorderLayout.CENTER);
    }

    private void chargerOptionsDisponibles() {
        List<Option> options = optionDAO.getAll();
        for (Option opt : options) {
            JCheckBox checkBox = new JCheckBox(opt.getNomOption());
            checkBox.setFont(policeNormale);
            checkBox.putClientProperty("option", opt);
            panelOptions.add(checkBox);
            checkBoxesOptions.add(checkBox);
        }
    }

    public List<Option> getOptionsSelectionnees() {
        List<Option> optionsSelectionnees = new ArrayList<>();
        for (JCheckBox cb : checkBoxesOptions) {
            if (cb.isSelected()) {
                optionsSelectionnees.add((Option) cb.getClientProperty("option"));
            }
        }
        return optionsSelectionnees;
    }

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
        return textField;
    }

    public void afficherListeHebergements(ArrayList<Hebergement> hebergements) {
        this.hebergementsAffiches = hebergements;
        tableModel.setRowCount(0);

        int nbPersonnes = (int) spinnerPersonnes.getValue();
        String dateDebutStr = champDateDebut.getText();
        String dateFinStr = champDateFin.getText();

        // Convertir les dates en format java.sql.Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dateDebut = null;
        java.util.Date dateFin = null;

        try {
            dateDebut = sdf.parse(dateDebutStr);
            dateFin = sdf.parse(dateFinStr);

            // Conversion en java.sql.Date
            java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());
            java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());

            // Appel de la méthode avec les dates sql
            for (Hebergement h : hebergements) {
                boolean peutAccueillir = false;

                if (h.getCategorie().equalsIgnoreCase("hotel")) {
                    // Appeler la méthode getChambresDisponibles avec les dates de type java.sql.Date
                    List<Chambre> chambresDisponibles = hebergementDAO.getChambresDisponibles(h.getId(), sqlDateDebut, sqlDateFin);
                    int capaciteTotale = 0;

                    for (Chambre chambre : chambresDisponibles) {
                        capaciteTotale += chambre.getPlaceMax();
                    }

                    if (capaciteTotale >= nbPersonnes) {
                        peutAccueillir = true;
                    }
                } else {
                    if (h.getPlace() >= nbPersonnes) {
                        peutAccueillir = true;
                    }
                }

                if (peutAccueillir) {
                    double moyenne = hebergementDAO.calculerMoyenneNotes(h.getId());
                    int etoiles = (int) Math.round(moyenne);

                    h.setNoteMoyenne(moyenne);
                    h.setEtoiles(etoiles);
                    hebergementDAO.mettreAJourNoteEtEtoiles(h.getId(), moyenne, etoiles);

                    String noteStr = (moyenne == 0) ? "Aucune note" : String.format("%.1f / 5", moyenne);

                    ImageIcon image = null;
                    try {
                        if (h.getPhoto() != null && !h.getPhoto().isEmpty()) {
                            ImageIcon icon = new ImageIcon(h.getPhoto());
                            Image scaled = icon.getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH);
                            image = new ImageIcon(scaled);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur de chargement de l'image pour : " + h.getNom());
                    }

                    // Récupération de la réduction si utilisateur ancien
                    String reductionStr = "Pas de réduction";
                    if (Inscription.getUtilisateurConnecte() != null &&
                            "ancien".equals(Inscription.getUtilisateurConnecte().getTypeUtilisateur())) {
                        Reduction reduction = hebergementDAO.getReductionParHebergement(h.getId());
                        if (reduction != null) {
                            reductionStr = reduction.getPourcentage() + "% de réduction";
                        }
                    }

                    String lieu = h.getVille() + ", " + h.getPays();  // Combinaison de la ville et du pays

                    tableModel.addRow(new Object[]{
                            image,
                            h.getNom(),
                            lieu,  // Lieu combiné
                            h.getDescription(),
                            h.getPrixParNuit() + " €",
                            noteStr,
                            reductionStr,
                            "Réserver"
                    });
                }
            }

        } catch (Exception e) {
            afficherMessage("Format de date invalide. Utilisez JJ/MM/AAAA.");
            return;
        }

        ButtonRenderer buttonRenderer = new ButtonRenderer();
        buttonRenderer.setBackground(couleurPrincipale);
        buttonRenderer.setForeground(Color.WHITE);
        buttonRenderer.setFont(policeNormale);
        tableHebergements.getColumn("Réserver").setCellRenderer(buttonRenderer);
        tableHebergements.getColumn("Réserver").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    public String getLieuRecherche() {
        return champLieu.getText();
    }

    public int getNbPersonnes() {
        return (Integer) spinnerPersonnes.getValue();
    }

    public String getDateDebut() {
        return champDateDebut.getText();
    }

    public String getDateFin() {
        return champDateFin.getText();
    }

    public void ajouterEcouteur(ActionListener listener) {
        if (this.actionListener == null) {
            boutonRecherche.addActionListener(listener);
            boutonDeconnexion.addActionListener(listener);
            boutonAccueil.addActionListener(listener);
            boutonMesReservations.addActionListener(listener);
            this.actionListener = listener;
        }
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public Hebergement getHebergementSelectionne() {
        return hebergementSelectionne;
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(couleurPrincipale);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(true);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFont(policeNormale);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "Réserver" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(couleurPrincipale);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFont(policeNormale);

            button.addActionListener(e -> {
                fireEditingStopped();
                int row = tableHebergements.getSelectedRow();
                if (row >= 0 && row < hebergementsAffiches.size()) {
                    hebergementSelectionne = hebergementsAffiches.get(row);

                    // Récupérer les dates saisies dans l'interface
                    String dateDebutStr = champDateDebut.getText();
                    String dateFinStr = champDateFin.getText();

                    // Convertir les dates en java.sql.Date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date dateDebut = null;
                    java.util.Date dateFin = null;

                    try {
                        dateDebut = sdf.parse(dateDebutStr);
                        dateFin = sdf.parse(dateFinStr);

                        // Conversion en java.sql.Date
                        java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());
                        java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());

                        // Passer les dates en java.sql.Date à la méthode getChambresDisponibles
                        List<Chambre> chambresDisponibles = hebergementDAO.getChambresDisponibles(hebergementSelectionne.getId(), sqlDateDebut, sqlDateFin);
                        if (!chambresDisponibles.isEmpty()) {
                            Chambre chambreChoisie = chambresDisponibles.get(0); // Choisir la première chambre disponible
                            chambreSelectionnee = chambreChoisie;

                            int idChambreChoisie = chambreChoisie.getId();
                            System.out.println("Chambre sélectionnée : ID = " + idChambreChoisie);
                        }

                    } catch (Exception ex) {
                        afficherMessage("Format de date invalide. Utilisez JJ/MM/AAAA.");
                        return;  // Si le format de date est incorrect, on arrête l'exécution.
                    }

                    if (actionListener != null) {
                        actionListener.actionPerformed(new ActionEvent(VueAccueil.this, ActionEvent.ACTION_PERFORMED, "RESERVER"));
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "Réserver" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }
    }
}
