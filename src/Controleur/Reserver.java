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
        // Si la vuePaiement n'est pas encore initialisée, on la crée
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

        // Caché la vueReservation et afficher la vuePaiement
        vueReservation.setVisible(false);
        vuePaiement.setVisible(true);
    }

    private void enregistrerReservation() {
        java.sql.Date sqlDateArrivee = new Date(dateArrivee.getTime());
        java.sql.Date sqlDateDepart = new Date(dateDepart.getTime());

        Reservation reservation = new Reservation(
                Inscription.getUtilisateurId(),
                vueAccueil.getHebergementSelectionne().getId(),
                sqlDateArrivee,
                sqlDateDepart,
                nbAdultes,
                nbEnfants,
                prixTotal,
                "CONFIRMEE",
                new Date(System.currentTimeMillis())
        );

        reservationDAO.ajouter(reservation);

        Paiement paiement = new Paiement(
                reservation.getReservationId(),
                prixTotal,
                new Date(System.currentTimeMillis()),
                "termine"
        );

        paiementDAO.ajouter(paiement);

        //  Marquer les chambres comme non disponibles
        Hebergement hebergement = vueAccueil.getHebergementSelectionne();
        int nbChambresReservees = vueReservation.getNbChambres();
        List<Chambre> chambresDisponibles = hebergementDAO.getChambresDisponibles(hebergement.getId());

        for (int i = 0; i < nbChambresReservees && i < chambresDisponibles.size(); i++) {
            Chambre chambre = chambresDisponibles.get(i);
            chambreDAO.mettreAJourDisponibilite(chambre.getId(), false);
        }
    }
}
