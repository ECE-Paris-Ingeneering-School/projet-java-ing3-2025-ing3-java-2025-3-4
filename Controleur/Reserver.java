package Controleur;

import Dao.*;
import Modele.*;
import Vue.VueAccueil;
import Vue.VueMesReservations;
import Vue.VuePaiement;
import Vue.VueReservation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class Reserver implements ActionListener {
    private VueAccueil vueAccueil;
    private HebergementDAOImpl hebergementDAO;
    private ReservationDAOImpl reservationDAO;
    private PaiementDAOImpl paiementDAO;
    private VueReservation vueReservation;
    private VuePaiement vuePaiement;
    private AvisDAOImpl avisDAO;
    private UserDAOImpl userDAO;
    private ChambreDAOImpl chambreDAO;

    private java.util.Date dateArrivee;
    private java.util.Date dateDepart;
    private int nbAdultes;
    private int nbEnfants;
    private double prixTotal;

    public Reserver(VueAccueil vueAccueil, HebergementDAOImpl hebergementDAO,
                    ReservationDAOImpl reservationDAO, PaiementDAOImpl paiementDAO,
                    VueReservation vueReservation, AvisDAOImpl avisDAO, UserDAOImpl userDAO,
                    ChambreDAOImpl chambreDAO) {

        this.vueAccueil = vueAccueil;
        this.hebergementDAO = hebergementDAO;
        this.reservationDAO = reservationDAO;
        this.paiementDAO = paiementDAO;
        this.vueReservation = vueReservation;
        this.avisDAO = avisDAO;
        this.userDAO = userDAO;
        this.chambreDAO = chambreDAO;

        this.vueReservation.ajouterEcouteur(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "VALIDER_RESERVATION":
                lancerVuePaiement(); // Affiche la page de paiement
                break;

            case "PAYER":
                enregistrerReservation(); // Enregistre réservation ET paiement
                vuePaiement.setVisible(false);
                vueAccueil.setVisible(true);
                vueAccueil.afficherMessage("Paiement confirmé. Votre réservation est enregistrée !");

                if (Inscription.getUtilisateurConnecte().getTypeUtilisateur().equals("nouveau")) {
                    userDAO.updateTypeUtilisateur(Inscription.getUtilisateurId(), "ancien");
                }

                break;
        }
    }

    private void lancerVuePaiement() {
        if (vuePaiement == null) {
            dateArrivee = vueReservation.getDateArrivee();
            dateDepart = vueReservation.getDateDepart();
            nbAdultes = vueReservation.getNbAdultes();
            nbEnfants = vueReservation.getNbEnfants();
            prixTotal = vueReservation.getPrixTotal();

            Hebergement hebergement = vueAccueil.getHebergementSelectionne();
            User user = Inscription.getUtilisateurConnecte();

            vuePaiement = new VuePaiement(user, hebergement, dateArrivee, dateDepart, prixTotal);
            vuePaiement.ajouterEcouteur(this);
        }
        vueReservation.setVisible(false);
        vuePaiement.setVisible(true);
    }

    private void enregistrerReservation() {
        java.sql.Date sqlDateArrivee = new Date(dateArrivee.getTime());
        java.sql.Date sqlDateDepart = new Date(dateDepart.getTime());

        Hebergement hebergement = vueAccueil.getHebergementSelectionne();
        List<Chambre> chambresDisponibles = hebergementDAO.getChambresDisponibles(hebergement.getId(), sqlDateArrivee, sqlDateDepart);

        if (chambresDisponibles.isEmpty()) {
            vueAccueil.afficherMessage("Aucune chambre disponible pour cet hébergement aux dates choisies !");
            return;
        }

        // Réserver les chambres nécessaires
        int nbChambresReservees = vueReservation.getNbChambres();

        for (int i = 0; i < nbChambresReservees && i < chambresDisponibles.size(); i++) {
            Chambre chambre = chambresDisponibles.get(i);

            // Créer la réservation pour cette chambre
            Reservation reservation = new Reservation(
                    Inscription.getUtilisateurId(),
                    chambre.getId(),
                    sqlDateArrivee,
                    sqlDateDepart,
                    nbAdultes,
                    nbEnfants,
                    prixTotal,
                    "CONFIRMEE",
                    new Date(System.currentTimeMillis())
            );

            reservationDAO.ajouter(reservation);

            // Créer le paiement associé
            Paiement paiement = new Paiement(
                    reservation.getReservationId(),
                    prixTotal,
                    new Date(System.currentTimeMillis()),
                    "termine"
            );

            paiementDAO.ajouter(paiement);

            // Marquer la chambre comme non disponible
            chambreDAO.mettreAJourDisponibilite(chambre.getId(), false);
        }
    }

}
