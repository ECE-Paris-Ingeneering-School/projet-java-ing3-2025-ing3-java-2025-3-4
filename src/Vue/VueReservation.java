package Vue;

// import des packages

import Modele.*;
import Dao.*;

import java.util.ArrayList;

public class VueReservation {
    /**
     * Méthode qui affiche une commande
     *
     * @param achat objet de la classe Commander et dao objet de la classe DaoFactory
     */
    public void afficherCommande(Reservation achat, DaoFactory dao) {
        // Récupérer un clientID du getter getClientId de l'objet achat
        int userID = achat.getUtilisateurId();

        // Instancier un objet de la classe ClientDAOImpl avec l'objet dao de DaoFactoru en paramètre
        UserDAOImpl clidaoimpl = new UserDAOImpl(dao);

        // Instancier un objet de la classe Client avec le clientID en paramètre
        User user = clidaoimpl.chercher(userID);

        // Récupérer un produitID du getter getProduitId de l'objet achat
        int hebergementID = achat.getHebergementId();

        ///  get d'autres trucs  ??

        // Instancier un objet de la classe ProduitDAOImpl avec l'objet dao de DaoFactoru en paramètre
        HebergementDAOImpl prodaoimpl = new HebergementDAOImpl(dao);

        // Instancier un objet de la classe Produit avec le produitID en paramètre
        Hebergement product = prodaoimpl.chercher(hebergementID);

        // Afficher les informations du client et du produit pour l'objet achat en paramètre
        System.out.println("Id Client : " + userID + " Nom : " + user.getNom() + " Mail : "+ user.getEmail()
                           + " commande Id Produit : " + hebergementID + " Nom : " + product.getNom()
                           + " prix = " + product.getPrixParNuit());
    }

    /**
     * Méthode qui affiche la liste des commandes
     * @param achats liste des produits et dao objet de la classe DaoFactory
     */

    public void afficherListeCommandes(ArrayList<Reservation> achats, DaoFactory dao) {
        // Afficher la liste des produits
        for (Reservation achat : achats) {
            //afficherCommande(achat);
            afficherCommande(achat, dao);
        }
    }
}
