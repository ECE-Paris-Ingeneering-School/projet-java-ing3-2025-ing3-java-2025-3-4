package Controleur;

import Dao.HebergementDAOImpl;
import Dao.ReservationDAOImpl;
import Modele.Hebergement;
import Modele.Reservation;
import Vue.VueAccueil;
import Vue.VueConnexion;
import Vue.VueReservation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Accueil implements ActionListener {

    private VueAccueil vueAccueil;
    private HebergementDAOImpl hebergementDAO;
    private VueConnexion vueConnexion;
    private VueReservation vueReservation;
    private ReservationDAOImpl reservationDAO;

    private Reserver reserver;

    // Liste actuellement affichée, utile pour accéder aux bons hébergements
    private ArrayList<Hebergement> hebergementsAffiches;

    public Accueil(VueAccueil vueAccueil, HebergementDAOImpl hebergementDAO, VueConnexion vueConnexion,
                   Reserver reserver, VueReservation vueReservation, ReservationDAOImpl reservationDAO) {
        this.vueAccueil = vueAccueil;
        this.hebergementDAO = hebergementDAO;
        this.vueConnexion = vueConnexion;
        this.vueReservation = vueReservation;
        this.reservationDAO = reservationDAO;
        this.reserver = reserver;

        this.vueAccueil.ajouterEcouteur(this);
        this.vueAccueil.setVisible(false);
    }

    public void afficherAccueil() {
        hebergementsAffiches = hebergementDAO.getAll();
        vueAccueil.afficherListeHebergements(hebergementsAffiches);
        vueAccueil.setVisible(true);
        vueConnexion.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();


        switch (action) {
            case "RECHERCHER":
                String lieu = vueAccueil.getLieuRecherche();
                int nbPersonnes = vueAccueil.getNbPersonnes();
                String dateDebut = vueAccueil.getDateDebut();
                String dateFin = vueAccueil.getDateFin();

                filtrerHebergements(lieu, nbPersonnes, dateDebut, dateFin);
                break;

            case "DECONNEXION":
                vueAccueil.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            case "RESERVER":
                vueReservation = new VueReservation(vueAccueil);
                reserver = new Reserver(vueAccueil, hebergementDAO, reservationDAO, vueReservation);
                vueReservation.ajouterEcouteur(reserver);
                vueReservation.setVisible(true);
                break;

            default:
                vueAccueil.afficherMessage("Action inconnue : " + action);
        }
    }

    private void filtrerHebergements(String lieu, int nbPersonnes, String dateDebut, String dateFin) {
        ArrayList<Hebergement> tousHebergements = hebergementDAO.getAll();

        List<Hebergement> hebergementsFiltres = tousHebergements.stream()
                .filter(h -> h.getVille().toLowerCase().contains(lieu.toLowerCase())
                        || h.getPays().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());

        hebergementsAffiches = new ArrayList<>(hebergementsFiltres);
        vueAccueil.afficherListeHebergements(hebergementsAffiches);
    }


}
