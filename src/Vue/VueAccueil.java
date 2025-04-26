package Vue;

import Dao.HebergementDAOImpl;
import Modele.Hebergement;
import Modele.Reduction;
import Controleur.Inscription;  // Assure-toi d'importer la classe Inscription si nécessaire

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    private ActionListener actionListener;

    private ArrayList<Hebergement> hebergementsAffiches;
    private Hebergement hebergementSelectionne;

    private HebergementDAOImpl hebergementDAO;

    public VueAccueil(HebergementDAOImpl hebergementDAO) {
        this.hebergementDAO = hebergementDAO;
        setTitle("Accueil - Rechercher un Hébergement");
        setSize(1300, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Barre de navigation en haut ---
        JPanel barreNavigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutonAccueil = new JButton("Accueil");
        boutonAccueil.setActionCommand("NAV_ACCUEIL");

        boutonMesReservations = new JButton("Mes Réservations");
        boutonMesReservations.setActionCommand("NAV_MES_RESERVATIONS");

        boutonDeconnexion = new JButton("Déconnexion");
        boutonDeconnexion.setActionCommand("DECONNEXION");

        barreNavigation.add(boutonAccueil);
        barreNavigation.add(boutonMesReservations);
        barreNavigation.add(Box.createHorizontalStrut(20));
        barreNavigation.add(boutonDeconnexion);

        // --- Barre de recherche ---
        JPanel panelRecherche = new JPanel(new GridLayout(2, 5, 10, 10));
        panelRecherche.setBorder(BorderFactory.createTitledBorder("Rechercher un hébergement"));

        champLieu = new JTextField();
        spinnerPersonnes = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        champDateDebut = new JTextField("JJ/MM/AAAA");
        champDateFin = new JTextField("JJ/MM/AAAA");
        boutonRecherche = new JButton("Rechercher");
        boutonRecherche.setActionCommand("RECHERCHER");

        panelRecherche.add(new JLabel("Lieu :"));
        panelRecherche.add(new JLabel("Personnes :"));
        panelRecherche.add(new JLabel("Date d'arrivée :"));
        panelRecherche.add(new JLabel("Date de départ :"));
        panelRecherche.add(new JLabel(""));

        panelRecherche.add(champLieu);
        panelRecherche.add(spinnerPersonnes);
        panelRecherche.add(champDateDebut);
        panelRecherche.add(champDateFin);
        panelRecherche.add(boutonRecherche);

        // --- Tableau des hébergements ---
        tableModel = new DefaultTableModel(
                new Object[]{"Photo", "Nom", "Ville", "Pays", "Catégorie", "Description", "Prix (€)", "Note Moyenne", "Étoiles", "Réduction", "Réserver"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10;  // "Réserver" est cliquable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return ImageIcon.class;
                if (columnIndex == 9 || columnIndex == 10) return JButton.class;
                return String.class;
            }
        };

        tableHebergements = new JTable(tableModel);
        tableHebergements.setRowHeight(80);
        JScrollPane scrollPane = new JScrollPane(tableHebergements);

        tableHebergements.getColumn("Réserver").setCellRenderer(new ButtonRenderer());
        tableHebergements.getColumn("Réserver").setCellEditor(new ButtonEditor(new JCheckBox()));

        // --- Layout global ---
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(barreNavigation, BorderLayout.NORTH);
        panelHaut.add(panelRecherche, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panelHaut, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void afficherListeHebergements(ArrayList<Hebergement> hebergements) {
        this.hebergementsAffiches = hebergements;
        tableModel.setRowCount(0);

        // Vérification du type d'utilisateur connecté
        boolean utilisateurAncien = Inscription.getUtilisateurConnecte().getTypeUtilisateur().equals("ancien");

        // Récupérer toutes les réductions uniquement si l'utilisateur est "ancien"
        List<Reduction> reductions = new ArrayList<>();
        if (utilisateurAncien) {
            reductions = hebergementDAO.getAllReductions();
        }

        for (Hebergement h : hebergements) {
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

            // Vérifier s'il y a une réduction pour cet hébergement
            String reductionStr = "Pas de réduction";
            for (Reduction reduction : reductions) {
                if (reduction.getHebergementId() == h.getId()) {
                    reductionStr = reduction.getPourcentage() + "% - " + reduction.getDescription();
                    break;
                }
            }

            tableModel.addRow(new Object[]{
                    image,
                    h.getNom(),
                    h.getVille(),
                    h.getPays(),
                    h.getCategorie(),
                    h.getDescription(),
                    h.getPrixParNuit() + " €",
                    noteStr,
                    genererEtoiles(h.getEtoiles()),
                    reductionStr,
                    "Réserver"
            });
        }

        tableHebergements.getColumn("Réserver").setCellRenderer(new ButtonRenderer());
        tableHebergements.getColumn("Réserver").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private String genererEtoiles(int etoilesPleines) {
        StringBuilder etoiles = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            etoiles.append(i < etoilesPleines ? "★" : "☆");
        }
        return etoiles.toString();
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
            button.addActionListener(e -> {
                fireEditingStopped();
                int row = tableHebergements.getSelectedRow();
                if (row >= 0 && row < hebergementsAffiches.size()) {
                    hebergementSelectionne = hebergementsAffiches.get(row);
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
