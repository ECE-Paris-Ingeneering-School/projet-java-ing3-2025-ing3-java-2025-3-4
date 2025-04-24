package Controleur;

import Dao.HebergementDAOImpl;
import Dao.OptionDAOImpl;
import Modele.Hebergement;
import Modele.Option;
import Vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilAdmin implements ActionListener {

    private VueAccueilAdmin vue;
    private OptionDAOImpl optionDAO;
    private HebergementDAOImpl hebergementDAO;
    private VueAjoutHebergement vueAjoutHebergement;
    private VueAjouterOption vueAjouterOption;
    private VueModifierSupprimerOption vueModifierSupprimerOption;
    private VueAssocierOptionsHebergement vueAssocierOptionsHebergement;

    public AccueilAdmin(VueAccueilAdmin vue,
                        VueAjoutHebergement vueAjoutHebergement,
                        VueAjouterOption vueAjouterOption,
                        VueModifierSupprimerOption vueModifierSupprimerOption,
                        VueAssocierOptionsHebergement vueAssocierOptionsHebergement,
                        OptionDAOImpl optionDAO,
                        HebergementDAOImpl hebergementDAO) {

        this.vue = vue;
        this.vueAjoutHebergement = vueAjoutHebergement;
        this.vueAjouterOption = vueAjouterOption;
        this.vueModifierSupprimerOption = vueModifierSupprimerOption;
        this.vueAssocierOptionsHebergement = vueAssocierOptionsHebergement;
        this.optionDAO = optionDAO;
        this.hebergementDAO = hebergementDAO;

        // Affichage initial
        vue.setVisible(true);
        vueAjoutHebergement.setVisible(false);
        vueAjouterOption.setVisible(false);
        vueModifierSupprimerOption.setVisible(false);
        vueAssocierOptionsHebergement.setVisible(false);

        // Écouteurs
        vue.ajouterEcouteur(this);
        vueAjoutHebergement.ajouterEcouteur(this);
        vueAjouterOption.ajouterEcouteur(this);
        vueModifierSupprimerOption.ajouterEcouteur(this);
        vueAssocierOptionsHebergement.ajouterEcouteur(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "CREER_HEBERGEMENT":
                vue.setVisible(false);
                vueAjoutHebergement.setVisible(true);
                break;

            case "AJOUTER_HEBERGEMENT":
                String nom = vueAjoutHebergement.getNom();
                String adresse = vueAjoutHebergement.getAdresse();
                String ville = vueAjoutHebergement.getVille();
                String pays = vueAjoutHebergement.getPays();
                String categorie = vueAjoutHebergement.getCategorie();
                String prixStr = vueAjoutHebergement.getPrix();
                String description = vueAjoutHebergement.getDescription();

                if (nom.isEmpty() || adresse.isEmpty() || ville.isEmpty() || pays.isEmpty() || prixStr.isEmpty()) {
                    vueAjoutHebergement.afficherMessage("Tous les champs doivent être remplis !");
                    break;
                }

                try {
                    double prix = Double.parseDouble(prixStr);
                    hebergementDAO.ajouter(new Hebergement(0,nom, adresse, ville, pays, categorie, prix, description));
                    vueAjoutHebergement.afficherMessage("Hébergement ajouté avec succès !");
                    vueAjoutHebergement.resetChamps();
                } catch (NumberFormatException ex) {
                    vueAjoutHebergement.afficherMessage("Le prix doit être un nombre valide !");
                }catch (Exception ex) {
                    ex.printStackTrace();
                    vue.afficherMessage("Erreur lors de l'ajout de l'hébergement.");
                }
                break;

            case "CREER_OPTION":
                vue.setVisible(false);
                vueAjouterOption.setVisible(true);
                break;

            case "AJOUTER_OPTION":
                String nomOption = vueAjouterOption.getNomOption();
                if (nomOption.isEmpty()) {
                    vueAjouterOption.afficherMessage("Le nom de l'option ne peut pas être vide.");
                } else {
                    optionDAO.ajouter(new Option(nomOption));
                    vueAjouterOption.afficherMessage("Option ajoutée avec succès !");
                    vueAjouterOption.resetChamps();
                }
                break;

            case "MODIFIER_SUPPRIMER_OPTION":
                vue.setVisible(false);
                vueModifierSupprimerOption.setVisible(true);
                break;

            case "MODIFIER_OPTION":
                int id = vueModifierSupprimerOption.getIdOption();
                String nouveauNom = vueModifierSupprimerOption.getNouveauNomOption();
                if (nouveauNom.isEmpty()) {
                    vueModifierSupprimerOption.afficherMessage("Le nom ne peut pas être vide.");
                } else {
                    optionDAO.modifier(new Option(id, nouveauNom));
                    vueModifierSupprimerOption.afficherMessage("Option modifiée !");
                    vueModifierSupprimerOption.resetChamps();
                }
                break;

            case "SUPPRIMER_OPTION":
                int idSupp = vueModifierSupprimerOption.getIdOption();
                optionDAO.supprimer(new Option(idSupp, ""));
                vueModifierSupprimerOption.afficherMessage("Option supprimée !");
                break;

            case "ASSOCIER_OPTION":
                vue.setVisible(false);
                vueAssocierOptionsHebergement.setVisible(true);
                break;

            case "VALIDER_ASSOCIATION":
                int idHeb = vueAssocierOptionsHebergement.getIdHebergement();
                int idOpt = vueAssocierOptionsHebergement.getIdOption();
                optionDAO.attribuerOptionAHebergement(idHeb, idOpt);
                vueAssocierOptionsHebergement.afficherMessage("Option associée à l'hébergement !");
                vueAssocierOptionsHebergement.resetChamps();
                break;

            case "RETIRER_OPTION":
                int idHebgRetirer = vueAssocierOptionsHebergement.getIdHebergement();
                int idOptionRetirer = vueAssocierOptionsHebergement.getIdOption();
                optionDAO.retirerOptionAHebergement(idHebgRetirer, idOptionRetirer);
                vueAssocierOptionsHebergement.afficherMessage("Option retirée !");
                break;

            case "RETOUR_ACCUEIL":
                vueAjoutHebergement.setVisible(false);
                vueAjouterOption.setVisible(false);
                vueModifierSupprimerOption.setVisible(false);
                vueAssocierOptionsHebergement.setVisible(false);
                vue.setVisible(true);
                break;

            case "DECONNEXION":
                vue.dispose();
                break;

            default:
                vue.afficherMessage("Action inconnue : " + action);
        }
    }
}
