package Controleur;

// import des packages
import Dao.*;
import Modele.*;
import Vue.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {


        DaoFactory dao = DaoFactory.getInstance("BookingApp", "root", "");

        HebergementDAOImpl daoHebergement = new HebergementDAOImpl(dao);
        UserDAOImpl daoUser = new UserDAOImpl(dao);

        OptionDAOImpl optionDAO = new OptionDAOImpl(dao);


        ///  inscription
        VueInscription vueInscription = new VueInscription();
        VueConnexion vueConnexion = new VueConnexion();
        VueAdmin vueAdmin = new VueAdmin();


        ///  accueil client
        VueAccueil vueAccueil = new VueAccueil(daoHebergement, optionDAO);

        ReservationDAOImpl daoReservation = new ReservationDAOImpl(dao);
        VueReservation vueReservation = new VueReservation(vueAccueil, daoHebergement);

        PaiementDAOImpl paiementDAO = new PaiementDAOImpl(dao);

        AvisDAOImpl avisDAO = new AvisDAOImpl(dao);

        ChambreDAOImpl chambreDAO = new ChambreDAOImpl(dao);


        //  reservation
        Reserver reserver = new Reserver(vueAccueil, daoHebergement, daoReservation, paiementDAO, vueReservation, avisDAO, daoUser, chambreDAO);

        Accueil accueil = new Accueil(vueAccueil, daoHebergement , vueConnexion, reserver, vueReservation, daoReservation, paiementDAO, avisDAO, daoUser, chambreDAO, optionDAO);


        /// accueil admin
        VueAccueilAdmin vueAccueilAdmin = new VueAccueilAdmin();
        OptionDAOImpl daoOption = new OptionDAOImpl(dao);
        VueAjouterOption vueAjouterOption = new VueAjouterOption();
        VueModifierSupprimerOption vueModifierSupprimerOption = new VueModifierSupprimerOption();
        VueAssocierOptionsHebergement vueAssocierOptionsHebergement = new VueAssocierOptionsHebergement();
        VueAjoutHebergement vueAjoutHebergement = new VueAjoutHebergement();
        VueAjouterReduction vueAjouterReduction = new VueAjouterReduction();
        ReductionDAOImpl reductionDAO = new ReductionDAOImpl(dao);
        VueAjouterChambre vueAjouterChambre = new VueAjouterChambre();


        AccueilAdmin controleurAdmin = new AccueilAdmin(
                vueAccueilAdmin,
                vueAjoutHebergement,
                vueAjouterOption,
                vueModifierSupprimerOption,
                vueAssocierOptionsHebergement,
                vueAjouterReduction,
                daoOption,
                daoHebergement,
                reductionDAO,
                vueAjouterChambre,
                chambreDAO
        );


        new Inscription(daoUser, vueInscription, vueConnexion, vueAdmin, accueil, controleurAdmin);


        // Fermer ma connexion
        dao.disconnect();
    }
}