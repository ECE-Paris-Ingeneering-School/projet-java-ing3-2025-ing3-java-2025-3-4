package Controleur;

// import des packages
import Dao.*;
import Modele.*;
import Vue.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Déclaration et instanciation des objets des classes DaoFactory, ProduitDAOImpl, VueProduit,
        // ClientDAOImpl, VueClient, CommanderDAOImpl et VueCommander
        DaoFactory dao = DaoFactory.getInstance("BookingApp", "root", "root");

        HebergementDAOImpl daoHebergement = new HebergementDAOImpl(dao);
        VueHebergement vueHebergement = new VueHebergement();
        UserDAOImpl daoUser = new UserDAOImpl(dao);
        VueUser vueUser = new VueUser();
        ReservationDAOImpl daoReservation = new ReservationDAOImpl(dao);
        VueReservation vueReservation = new VueReservation();

        // Récupérer la liste des produits de la base de données avec l'objet prodao de la classe ProduitDAOImpl
        ArrayList<Hebergement> hebergements = daoHebergement.getAll();

        // Afficher la liste des produits récupérés avec l'objet vuepro de la classe VueProduit
        vueHebergement.afficherListeProduits(hebergements);

        // Récupérer la liste des clients de la base de données avec l'objet clidao de la classe ClientDAOImpl
        ArrayList<User> users = daoUser.getAll();

        // Afficher la liste des clients récupérés avec l'objet vuecli de la classe VueClient
        vueUser.afficherListeClients(users);

        // Récupérer la liste des commandes de la base de données avec l'objet comdao de la classe CommanderDAOImpl
        ArrayList<Reservation> achats = daoReservation.getAll();

        // Afficher la liste des commandes récupérées avec l'objet vuecom de la classe VueCommander
        vueReservation.afficherListeCommandes(achats, dao);

        ///  inscription
        VueInscription vueInscription = new VueInscription();
        VueConnexion vueConnexion = new VueConnexion();
        VueAdmin vueAdmin = new VueAdmin();
        //new Inscription(daoUser, vueInscription, vueConnexion, vueAdmin);


        // a mettre depuis inscription après
        VueAccueil vueAccueil = new VueAccueil();
        //new Accueil(vueAccueil, daoHebergement ,vueConnexion);


        /// centralisation via AccueilAdmin
        VueAccueilAdmin accueilAdmin = new VueAccueilAdmin();
        OptionDAOImpl daoOption = new OptionDAOImpl(dao);
        HebergementDAOImpl daoHebergementAdmin = new HebergementDAOImpl(dao);
        VueAjouterOption vueAjouterOption = new VueAjouterOption();
        VueModifierSupprimerOption vueModifierSupprimerOption = new VueModifierSupprimerOption();
        VueAssocierOptionsHebergement vueAssocierOptionsHebergement = new VueAssocierOptionsHebergement();
        VueAjoutHebergement vueAjoutHebergement = new VueAjoutHebergement();

        AccueilAdmin controleurAdmin = new AccueilAdmin(
                accueilAdmin,
                vueAjoutHebergement,
                vueAjouterOption,
                vueModifierSupprimerOption,
                vueAssocierOptionsHebergement,
                daoOption,
                daoHebergementAdmin
        );

        // Fermer ma connexion
        dao.disconnect();
    }
}
