package Controleur;

import Dao.HebergementDAOImpl;
import Modele.Hebergement;
import Vue.VueAccueil;
import Vue.VueConnexion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Accueil implements ActionListener {

    private VueAccueil vueAccueil;
    private HebergementDAOImpl hebergementDAO;
    private VueConnexion vueConnexion;

    public Accueil(VueAccueil vueAccueil, HebergementDAOImpl hebergementDAO, VueConnexion vueConnexion) {
        this.vueAccueil = vueAccueil;
        this.hebergementDAO = hebergementDAO;
        this.vueConnexion = vueConnexion;

        this.vueAccueil.ajouterEcouteur(this);

        afficherAccueil();
    }

    public void afficherAccueil() {
        ArrayList<Hebergement> hebergements = hebergementDAO.getAll();
        vueAccueil.afficherListeHebergements(hebergements);
        vueAccueil.setVisible(true);
        vueConnexion.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "DECONNEXION":
                vueAccueil.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            default:
                vueAccueil.afficherMessage("Action inconnue : " + action);
        }
    }
}
