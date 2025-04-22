package Controleur;

import Dao.UserDAOImpl;
import Modele.User;
import Vue.VueAdmin;
import Vue.VueConnexion;
import Vue.VueInscription;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Inscription implements ActionListener {

    private UserDAOImpl userDAO;
    private VueInscription vueInscription;
    private VueConnexion vueConnexion;
    private VueAdmin vueAdmin;



    public Inscription(UserDAOImpl userDAO, VueInscription vueInscription, VueConnexion vueConnexion, VueAdmin vueAdmin) {
        this.userDAO = userDAO;
        this.vueInscription = vueInscription;
        this.vueConnexion = vueConnexion;
        this.vueAdmin = vueAdmin;

        vueConnexion.setVisible(true);
        vueInscription.setVisible(false);
        vueAdmin.setVisible(false);

        this.vueInscription.ajouterEcouteur(this);
        this.vueConnexion.ajouterEcouteur(this);
        this.vueAdmin.ajouterEcouteur(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "INSCRIPTION":
                // Gérer l'inscription
                String nom = vueInscription.getNom();
                String prenom = vueInscription.getPrenom();
                String email = vueInscription.getEmail();
                String mdp = vueInscription.getMotDePasse();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                    vueInscription.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    User user = new User(prenom, nom, email, mdp, "nouveau");
                    userDAO.ajouter(user);
                    vueInscription.afficherMessage("Bienvenue " + prenom + " ! Inscription réussie");
                }
                break;

            case "CONNEXION":
                // Passer à la page de connexion
                vueInscription.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            case "CONNEXION_PAGE":
                // Gérer la connexion
                String emailC = vueConnexion.getEmail();
                String mdpC = vueConnexion.getMotDePasse();

                if (emailC.isEmpty() || mdpC.isEmpty()) {
                    vueConnexion.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    ///  verifier si user ancien (avec chercher dans bdd)
                    User user = new User(emailC, mdpC, "ancien");
                    vueConnexion.afficherMessage("Heureux de vous revoir " + user.getNom());

                }
                break;

            case "INSCRIPTION_PAGE":
                // Passer à la page d'inscription
                vueConnexion.setVisible(false);
                vueInscription.setVisible(true);
                break;

            case "ADMIN_CONNEXION":
                // Passer à la page Admin
                vueConnexion.setVisible(false);
                vueAdmin.setVisible(true);
                break;

            case "ADMIN":
                // Gérer connexion Admin
                String emailA = vueAdmin.getEmail();
                String mdpA = vueAdmin.getMotDePasse();

                if (emailA.isEmpty() || mdpA.isEmpty()) {
                    vueAdmin.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    ///  verifier si user admin (avec chercher dans bdd)
                    User user = new User(emailA, mdpA, "admin");
                    vueAdmin.afficherMessage("Heureux de vous revoir " + user.getNom());
                }
                break;

            case "RETOUR_CONNEXION":
                // Retour à la page de connexion depuis la page Admin
                vueAdmin.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            case "RETOUR_INSCRIPTION":
                // Retour à la page de connexion depuis la page d'inscription
                vueInscription.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            default:
                vueInscription.afficherMessage("Action inconnue : " + action);
        }
    }
}
