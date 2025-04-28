package Controleur;

import Dao.UserDAOImpl;
import Modele.User;
import Vue.VueAdmin;
import Vue.VueConnexion;
import Vue.VueInscription;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Inscription implements ActionListener {

    // 🔥 Utilisateur connecté (accessible partout)
    private static User utilisateurConnecte = null;

    public static User getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static int getUtilisateurId() {
        return utilisateurConnecte != null ? utilisateurConnecte.getId() : -1;
    }

    // --- Attributs ---
    private UserDAOImpl userDAO;
    private VueInscription vueInscription;
    private VueConnexion vueConnexion;
    private VueAdmin vueAdmin;

    private Accueil accueil;
    private AccueilAdmin accueilAdmin;

    public Inscription(UserDAOImpl userDAO, VueInscription vueInscription, VueConnexion vueConnexion, VueAdmin vueAdmin, Accueil accueil, AccueilAdmin accueilAdmin) {
        this.userDAO = userDAO;
        this.vueInscription = vueInscription;
        this.vueConnexion = vueConnexion;
        this.vueAdmin = vueAdmin;
        this.accueil = accueil;
        this.accueilAdmin = accueilAdmin;

        vueConnexion.setVisible(true);
        vueInscription.setVisible(false);
        vueAdmin.setVisible(false);

        // Ajout des écouteurs d'actions
        this.vueInscription.ajouterEcouteur(this);
        this.vueConnexion.ajouterEcouteur(this);
        this.vueAdmin.ajouterEcouteur(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {

            case "SINSCRIPTION":
                String nom = vueInscription.getNom();
                String prenom = vueInscription.getPrenom();
                String email = vueInscription.getEmail();
                String mdp = vueInscription.getMotDePasse();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                    vueInscription.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    User userI = new User(prenom, nom, email, mdp, "nouveau");
                    userDAO.ajouter(userI);

                    User user = userDAO.chercher(email, mdp);

                    utilisateurConnecte = user; // On garde l'utilisateur connecté

                    vueInscription.afficherMessage("Bienvenue " + prenom + " ! Inscription réussie");
                    accueil.afficherAccueil();
                }
                break;

            case "CONNEXION":
                vueInscription.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            case "CONNEXION_PAGE":
                String emailC = vueConnexion.getEmail();
                String mdpC = vueConnexion.getMotDePasse();

                if (emailC.isEmpty() || mdpC.isEmpty()) {
                    vueConnexion.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    User user = userDAO.chercher(emailC, mdpC);

                    if (user != null) {
                        utilisateurConnecte = user; // Connexion réussie = on stocke l'utilisateur

                        vueConnexion.afficherMessage("Heureux de vous revoir " + user.getNom());
                        accueil.afficherAccueil();
                    } else {
                        vueConnexion.afficherMessage("Email ou mot de passe incorrect.");
                    }
                }
                break;

            case "INSCRIPTION_PAGE":
                vueConnexion.setVisible(false);
                vueInscription.setVisible(true);
                break;

            case "ADMIN_CONNEXION":
                vueConnexion.setVisible(false);
                vueAdmin.setVisible(true);
                break;

            case "ADMIN":
                String emailA = vueAdmin.getEmail();
                String mdpA = vueAdmin.getMotDePasse();

                if (emailA.isEmpty() || mdpA.isEmpty()) {
                    vueAdmin.afficherMessage("Tous les champs doivent être remplis.");
                } else {
                    User user = userDAO.chercher(emailA, mdpA);

                    if(Objects.equals(user.getTypeUtilisateur(), "admin")){
                        utilisateurConnecte = user;

                        vueConnexion.afficherMessage("Heureux de vous revoir " + user.getNom());
                        accueilAdmin.afficherAccueilAdmin();

                    } else {
                        vueConnexion.afficherMessage("L'utilisateur n'est pas administrateur");
                    }

                }
                break;

            case "RETOUR_CONNEXION":
                vueAdmin.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            case "RETOUR_INSCRIPTION":
                vueInscription.setVisible(false);
                vueConnexion.setVisible(true);
                break;

            default:
                vueInscription.afficherMessage("Action inconnue : " + action);
        }
    }
}
