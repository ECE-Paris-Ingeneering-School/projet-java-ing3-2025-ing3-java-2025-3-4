package Vue;

import Controleur.Inscription;
import Dao.AvisDAOImpl;
import Dao.HebergementDAOImpl;
import Dao.ReservationDAOImpl;
import Modele.Avis;
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
    private HebergementDAOImpl hebergementDAO;
    private AvisDAOImpl avisDAO;

    // Définition des couleurs et polices communes
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Color couleurFondSection = Color.WHITE;
    private final Color couleurBordure = new Color(220, 220, 220);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 16);

    public VueMesReservations(ReservationDAOImpl reservationDAO, HebergementDAOImpl hebergementDAO, AvisDAOImpl avisDAO) {
        this.reservationDAO = reservationDAO;
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

                Hebergement hebergement = hebergementDAO.chercher(res.getHebergementId());
                String nomHebergement = (hebergement != null) ? hebergement.getNom() : "Hébergement inconnu";

                JLabel titre = new JLabel(nomHebergement);
                titre.setFont(policeTitre);
                titre.setForeground(couleurPrincipale);
                panelResa.add(titre);

                panelResa.add(Box.createRigidArea(new Dimension(0, 10)));

                JTextArea infos = new JTextArea();
                infos.setEditable(false);
                infos.setBackground(couleurFondSection);
                infos.setFont(policeNormale);
                infos.setText(
                        "Date d'arrivée : " + res.getDateArrivee() +
                                "\nDate de départ : " + res.getDateDepart() +
                                "\nAdultes : " + res.getAdultes() +
                                "\nEnfants : " + res.getEnfants() +
                                "\nPrix total : " + res.getPrixTotal() + " €" +
                                "\nStatut : " + res.getStatut()
                );
                infos.setBorder(null);
                panelResa.add(infos);

                panelResa.add(Box.createRigidArea(new Dimension(0, 10)));

                // Panel Avis et Note
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

                // Zone de texte pour commentaire
                JTextArea champAvis = new JTextArea(3, 30);
                champAvis.setLineWrap(true);
                champAvis.setWrapStyleWord(true);
                champAvis.setFont(policeNormale);
                JScrollPane scrollAvis = new JScrollPane(champAvis);
                scrollAvis.setBorder(BorderFactory.createLineBorder(couleurBordure, 1));
                panelAvisNote.add(scrollAvis, BorderLayout.CENTER);

                // Sélecteur de note
                JPanel panelNote = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                panelNote.setBackground(couleurFondSection);
                JLabel labelNote = new JLabel("Note : ");
                labelNote.setFont(policeNormale);
                JComboBox<Integer> comboNote = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
                comboNote.setFont(policeNormale);
                panelNote.add(labelNote);
                panelNote.add(comboNote);
                panelAvisNote.add(panelNote, BorderLayout.NORTH);

                // Bouton pour entrer l'avis
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

                    Avis avis = new Avis(
                            Inscription.getUtilisateurId(),
                            res.getHebergementId(),
                            note,
                            commentaire,
                            new Date(System.currentTimeMillis())
                    );

                    avisDAO.ajouterAvis(avis);

                    JOptionPane.showMessageDialog(this, "Avis enregistré avec succès !");
                    boutonAvis.setEnabled(false);
                    champAvis.setEditable(false);
                    comboNote.setEnabled(false);
                });

                panelAvisNote.add(boutonAvis, BorderLayout.SOUTH);

                panelResa.add(panelAvisNote);
                panelResa.setAlignmentX(Component.LEFT_ALIGNMENT);

                panelPrincipal.add(panelResa);
                panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // Espace entre les réservations
            }
        }
    }
}