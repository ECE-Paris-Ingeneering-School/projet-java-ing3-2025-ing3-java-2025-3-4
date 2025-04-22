package Vue;

// import des packages
import Modele.User;
import java.util.ArrayList;

public class VueUser {
    /**
     * Méthode qui affiche un client
     * @param user = objet d'un client
     */
    public void afficherClient(User user) {
        // Afficher un client
        System.out.println("Id client : " + user.getId() + " Nom : " + user.getNom()
                           + " Mail : " + user.getEmail());
    }

    /**
     * Méthode qui affiche la liste des clients
     * @param users = liste des clients
     */
    public void afficherListeClients(ArrayList<User> users) {
        // Afficher la liste des produits récupérés de l'objet de ProduitDAO
        for (User user : users) {
            afficherClient(user);
        }
    }
}
