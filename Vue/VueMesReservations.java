package Vue;

import Controleur.Inscription;
import Dao.*;
import Modele.Avis;
import Modele.Chambre;
import Modele.Hebergement;
import Modele.Reservation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class VueMesReservations extends JFrame {
    private JPanel panelPrincipal;
    private ReservationDAOImpl reservationDAO;
    private ChambreDAOImpl chambreDAO;
    private HebergementDAOImpl hebergementDAO;
    private AvisDAOImpl avisDAO;

    // Définition des couleurs et polices communes
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurFondSection = Color.WHITE;
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueMesReservations(ReservationDAOImpl reservationDAO, ChambreDAOImpl chambreDAO, HebergementDAOImpl hebergementDAO, AvisDAOImpl avisDAO) {
        this.reservationDAO = reservationDAO;
        this.chambreDAO = chambreDAO;
        this.hebergementDAO = hebergementDAO;
        this.avisDAO = avisDAO;

        setTitle("Mes Réservations");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(couleurSecondaire);

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.getViewport().setBackground(couleurSecondaire);

        afficherReservationsUtilisateur();

        add(scrollPane);
    }

    private void afficherReservationsUtilisateur() {
        int utilisateurId = Inscription.getUtilisateurId();
        ArrayList<Reservation> toutesLesReservations = reservationDAO.getAll();

        for (Reservation res : toutesLesReservations) {
            if (res.getUtilisateurId() == utilisateurId) {
                JPanel panelResa = new JPanel();
                panelResa.setLayout(new BoxLayout(panelResa, BoxLayout.Y_AXIS));
                panelResa.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                panelResa.setBackground(couleurFondSection);

                // Recherche de l'hébergement via la chambre
                Chambre chambre = chambreDAO.chercher(res.getChambreId());
                Hebergement hebergement = (chambre != null) ? hebergementDAO.chercher(chambre.getHebergementId()) : null;
                String nomHebergement = (hebergement != null) ? hebergement.getNom() : "Hébergement inconnu";

                JLabel titre = new JLabel( nomHebergement);
                titre.setFont(policeTitre);
                titre.setForeground(couleurPrincipale);
                panelResa.add(titre);

                panelResa.add(Box.createRigidArea(new Dimension(0, 10)));

                JTextArea infos = new JTextArea();
                infos.setEditable(false);
                infos.setBackground(couleurFondSection);
                infos.setFont(policeNormale);
                infos.setBorder(null);
                infos.setText(
                        "Date d'arrivée : " + res.getDateArrivee() +
                                "\nDate de départ : " + res.getDateDepart() +
                                "\nAdultes : " + res.getAdultes() +
                                "\nEnfants : " + res.getEnfants() +
                                "\nPrix total : " + res.getPrixTotal() + " €" +
                                "\nStatut : " + res.getStatut()
                );
                panelResa.add(infos);

                panelResa.add(Box.createRigidArea(new Dimension(0, 10)));

                // Panel Avis
                JPanel panelAvisNote = new JPanel(new BorderLayout(10, 10));
                panelAvisNote.setBackground(couleurFondSection);
                panelAvisNote.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(couleurBordure, 1),
                        "Votre avis",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        policeTitre,
                        couleurPrincipale
                ));

                JTextArea champAvis = new JTextArea(3, 30);
                champAvis.setLineWrap(true);
                champAvis.setWrapStyleWord(true);
                champAvis.setFont(policeNormale);
                JScrollPane scrollAvis = new JScrollPane(champAvis);
                scrollAvis.setBorder(BorderFactory.createLineBorder(couleurBordure, 1));
                panelAvisNote.add(scrollAvis, BorderLayout.CENTER);

                // Panel Note
                JPanel panelNote = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                panelNote.setBackground(couleurFondSection);
                JLabel labelNote = new JLabel("Note : ");
                labelNote.setFont(policeNormale);
                JComboBox<Integer> comboNote = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
                comboNote.setFont(policeNormale);
                panelNote.add(labelNote);
                panelNote.add(comboNote);
                panelAvisNote.add(panelNote, BorderLayout.NORTH);

                // Bouton pour soumettre l'avis
                JButton boutonAvis = new JButton("Entrer l'avis");
                boutonAvis.setBackground(couleurPrincipale);
                boutonAvis.setForeground(Color.WHITE);
                boutonAvis.setFont(policeNormale);
                boutonAvis.setFocusPainted(false);
                boutonAvis.setCursor(new Cursor(Cursor.HAND_CURSOR));

                boutonAvis.addActionListener(e -> {
                    int note = (int) comboNote.getSelectedItem();
                    String commentaire = champAvis.getText().trim();

                    if (commentaire.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer un commentaire.");
                        return;
                    }

                    if (hebergement == null) {
                        JOptionPane.showMessageDialog(this, "Hébergement non trouvé.");
                        return;
                    }

                    Avis avis = new Avis(
                            Inscription.getUtilisateurId(),
                            hebergement.getId(), // Utilisez l'ID de l'hébergement
                            note,
                            commentaire,
                            new Date(System.currentTimeMillis())
                    );

                    try {
                        avisDAO.ajouterAvis(avis);
                        JOptionPane.showMessageDialog(this, "Avis enregistré avec succès !");
                        boutonAvis.setEnabled(false);
                        champAvis.setEditable(false);
                        comboNote.setEnabled(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'avis : " + ex.getMessage());
                    }
                });

                panelAvisNote.add(boutonAvis, BorderLayout.SOUTH);

                panelResa.add(panelAvisNote);
                panelResa.setAlignmentX(Component.LEFT_ALIGNMENT);

                panelPrincipal.add(panelResa);
                panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // espace entre les réservations
            }
        }
    }
}
