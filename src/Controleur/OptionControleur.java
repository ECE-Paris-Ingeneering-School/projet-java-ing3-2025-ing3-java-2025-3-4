package Controleur;

import Dao.OptionDAOImpl;
import Dao.DaoFactory;
import Modele.Option;
import Vue.VueAjouterOption;
import Vue.VueAssocierOptionsHebergement;
import Vue.VueModifierSupprimerOption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionControleur implements ActionListener {

    private OptionDAOImpl optionDAO;
    private DaoFactory daoFactory;

    private VueAjouterOption vueAjouterOption;
    private VueModifierSupprimerOption vueModifierSupprimerOption;
    private VueAssocierOptionsHebergement vueAssocierOptionsHebergement;

    public OptionControleur(OptionDAOImpl optionDAO,
                            VueAjouterOption vueAjouterOption,
                            VueModifierSupprimerOption vueModifierSupprimerOption,
                            VueAssocierOptionsHebergement vueAssocierOptionsHebergement,
                            DaoFactory daoFactory) {

        this.optionDAO = optionDAO;
        this.vueAjouterOption = vueAjouterOption;
        this.vueModifierSupprimerOption = vueModifierSupprimerOption;
        this.vueAssocierOptionsHebergement = vueAssocierOptionsHebergement;
        this.daoFactory = daoFactory;

        vueAjouterOption.ajouterEcouteur(this);
        vueModifierSupprimerOption.ajouterEcouteur(this);
        vueAssocierOptionsHebergement.ajouterEcouteur(this);

        vueAjouterOption.setVisible(false);
        vueModifierSupprimerOption.setVisible(false);
        vueAssocierOptionsHebergement.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "AJOUTER_OPTION":
                String nomOption = vueAjouterOption.getNomOption();
                if (nomOption.isEmpty()) {
                    vueAjouterOption.afficherMessage("Le nom de l'option ne peut pas être vide.");
                } else {
                    optionDAO.ajouter(new Option(nomOption));
                    vueAjouterOption.afficherMessage("Option ajoutée avec succès !");
                }
                break;

            case "MODIFIER_OPTION":
                int idModif = vueModifierSupprimerOption.getIdOption();
                String newNom = vueModifierSupprimerOption.getNouveauNomOption();
                if (newNom.isEmpty()) {
                    vueModifierSupprimerOption.afficherMessage("Le nom ne peut pas être vide.");
                } else {
                    optionDAO.modifier(new Option(idModif, newNom));
                    vueModifierSupprimerOption.afficherMessage("Option modifiée !");
                }
                break;

            case "SUPPRIMER_OPTION":
                int idSupp = vueModifierSupprimerOption.getIdOption();
                optionDAO.supprimer(new Option(idSupp, ""));
                vueModifierSupprimerOption.afficherMessage("Option supprimée !");
                break;

            case "ASSOCIER_OPTION":
                int idHebgAssoc = vueAssocierOptionsHebergement.getIdHebergement();
                int idOptionAssoc = vueAssocierOptionsHebergement.getIdOption();
                optionDAO.attribuerOptionAHebergement(idHebgAssoc, idOptionAssoc);
                vueAssocierOptionsHebergement.afficherMessage("Option associée !");
                break;

            case "RETIRER_OPTION":
                int idHebgRetirer = vueAssocierOptionsHebergement.getIdHebergement();
                int idOptionRetirer = vueAssocierOptionsHebergement.getIdOption();
                optionDAO.retirerOptionAHebergement(idHebgRetirer, idOptionRetirer);
                vueAssocierOptionsHebergement.afficherMessage("Option retirée !");
                break;

            default:
                System.out.println("Action inconnue : " + action);
        }
    }
}
