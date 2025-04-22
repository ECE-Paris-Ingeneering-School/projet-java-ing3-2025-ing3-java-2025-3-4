package Vue;

// import des packages
import Modele.Hebergement;
import java.util.ArrayList;

public class VueHebergement {
    /**
     * Méthode qui affiche un produit
     * @param product = objet d'un produit
     */
    public void afficherProduit(Hebergement product) {
        // Afficher un produit
        System.out.println("Id produit : " + product.getId() + " Nom : " + product.getNom()
                           + " prix = " + product.getPrixParNuit());
    }

    /**
     * Méthode qui affiche la liste des produits
     * @param hebergements = liste des produits
     */
    public void afficherListeProduits(ArrayList<Hebergement> hebergements) {
        // Afficher la liste des produits
        for (Hebergement product : hebergements) {
            afficherProduit(product);
        }
    }
}
