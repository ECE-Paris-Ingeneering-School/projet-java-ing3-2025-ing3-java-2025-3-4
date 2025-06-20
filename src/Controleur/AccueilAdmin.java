package Controleur;

import Dao.*;
import Modele.Chambre;
import Modele.Hebergement;
import Modele.Option;
import Modele.Reduction;
import Vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class AccueilAdmin implements ActionListener {

    private VueAccueilAdmin vue;
    private OptionDAOImpl optionDAO;
    private HebergementDAOImpl hebergementDAO;
    private ReductionDAO reductionDAO;
    private ChambreDAOImpl chambreDAO;

    private VueAjoutHebergement vueAjoutHebergement;
    private VueAjouterOption vueAjouterOption;
    private VueModifierSupprimerOption vueModifierSupprimerOption;
    private VueAssocierOptionsHebergement vueAssocierOptionsHebergement;
    private VueAjouterReduction vueAjouterReduction;
    private VueAjouterChambre vueAjouterChambre;

    private static final String ACTION_STATS = "VOIR_STATS";

    public AccueilAdmin(VueAccueilAdmin vue,
                        VueAjoutHebergement vueAjoutHebergement,
                        VueAjouterOption vueAjouterOption,
                        VueModifierSupprimerOption vueModifierSupprimerOption,
                        VueAssocierOptionsHebergement vueAssocierOptionsHebergement,
                        VueAjouterReduction vueAjouterReduction,
                        OptionDAOImpl optionDAO,
                        HebergementDAOImpl hebergementDAO,
                        ReductionDAO reductionDAO,
                        VueAjouterChambre vueAjouterChambre,
                        ChambreDAOImpl chambreDAO) {

        this.vue = vue;
        this.vueAjoutHebergement = vueAjoutHebergement;
        this.vueAjouterOption = vueAjouterOption;
        this.vueModifierSupprimerOption = vueModifierSupprimerOption;
        this.vueAssocierOptionsHebergement = vueAssocierOptionsHebergement;
        this.vueAjouterReduction = vueAjouterReduction;
        this.vueAjouterChambre = vueAjouterChambre;

        this.optionDAO = optionDAO;
        this.hebergementDAO = hebergementDAO;
        this.reductionDAO = reductionDAO;
        this.chambreDAO = chambreDAO;

        // Masquer les vues sauf accueil
        vueAjoutHebergement.setVisible(false);
        vueAjouterOption.setVisible(false);
        vueModifierSupprimerOption.setVisible(false);
        vueAssocierOptionsHebergement.setVisible(false);
        vueAjouterReduction.setVisible(false);
        vueAjouterChambre.setVisible(false);

        // Ajouter les écouteurs
        vue.ajouterEcouteur(this);
        vueAjoutHebergement.ajouterEcouteur(this);
        vueAjouterOption.ajouterEcouteur(this);
        vueModifierSupprimerOption.ajouterEcouteur(this);
        vueAssocierOptionsHebergement.ajouterEcouteur(this);
        vueAjouterReduction.ajouterEcouteur(this);
        vueAjouterChambre.ajouterEcouteur(this);
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
                if (vueAjoutHebergement.getNom().isEmpty() || vueAjoutHebergement.getAdresse().isEmpty() ||
                        vueAjoutHebergement.getVille().isEmpty() || vueAjoutHebergement.getPays().isEmpty() ||
                        vueAjoutHebergement.getCategorie().isEmpty() || vueAjoutHebergement.getPrix().isEmpty() ||
                        vueAjoutHebergement.getDescription().isEmpty() || vueAjoutHebergement.getPhoto().isEmpty()) {
                    vue.afficherMessage("Veuillez remplir tous les champs, y compris la photo !");
                    return;
                }

                try {
                    Hebergement h = new Hebergement(
                            0,
                            vueAjoutHebergement.getNom(),
                            vueAjoutHebergement.getDescription(),
                            vueAjoutHebergement.getAdresse(),
                            vueAjoutHebergement.getVille(),
                            vueAjoutHebergement.getPays(),
                            Double.parseDouble(vueAjoutHebergement.getPrix()),
                            vueAjoutHebergement.getCategorie(),
                            vueAjoutHebergement.getPhoto(),
                            vueAjoutHebergement.getPlace(),
                            vueAjoutHebergement.getNbChambre()
                    );
                    hebergementDAO.ajouter(h);
                    vueAjoutHebergement.afficherMessage("Hébergement ajouté avec succès !");
                    vueAjoutHebergement.resetChamps();

                } catch (NumberFormatException ex) {
                    vueAjoutHebergement.afficherMessage("Le prix doit être un nombre valide.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    vueAjoutHebergement.afficherMessage("Erreur lors de l'ajout de l'hébergement.");
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
                List<Hebergement> hebergements = hebergementDAO.getAll();
                vueAssocierOptionsHebergement.setHebergements(hebergements);

                if (!hebergements.isEmpty()) {
                    Hebergement premier = hebergements.get(0);
                    List<Option> toutesOptions = optionDAO.getAll();
                    List<Option> optionsAssociees = optionDAO.getOptionsPourHebergement(premier.getId());
                    vueAssocierOptionsHebergement.setOptions(toutesOptions, optionsAssociees);
                }

                vueAssocierOptionsHebergement.getComboBoxHebergements().addActionListener(evt -> {
                    Hebergement selectionne = vueAssocierOptionsHebergement.getHebergementSelectionne();
                    if (selectionne != null) {
                        List<Option> toutesOptions = optionDAO.getAll();
                        List<Option> optionsAssociees = optionDAO.getOptionsPourHebergement(selectionne.getId());
                        vueAssocierOptionsHebergement.setOptions(toutesOptions, optionsAssociees);
                    }
                });
                break;

            case "VALIDER_ASSOCIATION":
                Hebergement heb = vueAssocierOptionsHebergement.getHebergementSelectionne();
                List<Option> selectionnees = vueAssocierOptionsHebergement.getOptionsSelectionnees();

                optionDAO.reinitialiserOptionsPourHebergement(heb.getId());
                for (Option opt : selectionnees) {
                    optionDAO.attribuerOptionAHebergement(heb.getId(), opt.getId());
                }

                vueAssocierOptionsHebergement.afficherMessage("Associations mises à jour !");
                break;

            case "AJOUTER_REDUCTION":
                vue.setVisible(false);
                vueAjouterReduction.setVisible(true);
                break;

            case "AJOUT_REDUCTION_BDD":
                String idStr = vueAjouterReduction.getHebergementId();
                String pourcentageStr = vueAjouterReduction.getPourcentage();
                String description = vueAjouterReduction.getDescription();
                String dateDebutStr = vueAjouterReduction.getDateDebut();
                String dateFinStr = vueAjouterReduction.getDateFin();

                if (idStr.isEmpty() || pourcentageStr.isEmpty() || description.isEmpty()
                        || dateDebutStr.isEmpty() || dateFinStr.isEmpty()) {
                    vueAjouterReduction.afficherMessage("Veuillez remplir tous les champs.");
                    return;
                }

                try {
                    int idHebergement = Integer.parseInt(idStr);
                    int pourcentage = Integer.parseInt(pourcentageStr);
                    Date dateDebut = Date.valueOf(dateDebutStr);
                    Date dateFin = Date.valueOf(dateFinStr);

                    Reduction reduction = new Reduction(idHebergement, pourcentage, description, dateDebut, dateFin);
                    reductionDAO.ajouter(reduction);

                    vueAjouterReduction.afficherMessage("Réduction ajoutée avec succès !");
                    vueAjouterReduction.resetChamps();

                } catch (NumberFormatException ex) {
                    vueAjouterReduction.afficherMessage("ID hébergement et pourcentage doivent être des nombres valides.");
                } catch (IllegalArgumentException ex) {
                    vueAjouterReduction.afficherMessage("Les dates doivent être au format YYYY-MM-DD.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    vueAjouterReduction.afficherMessage("Erreur lors de l'ajout en base.");
                }
                break;

            case "AJOUTER_CHAMBRE":
                vue.setVisible(false);
                vueAjouterChambre.setVisible(true);
                break;

            case "AJOUT_CHAMBRE_BDD":
                String hebergementIdStr = vueAjouterChambre.getHebergementId();
                String capaciteStr = vueAjouterChambre.getCapacite();

                if (hebergementIdStr.isEmpty() || capaciteStr.isEmpty()) {
                    vueAjouterChambre.afficherMessage("Veuillez remplir tous les champs.");
                    return;
                }

                try {
                    int idHebergement = Integer.parseInt(hebergementIdStr);
                    int capacite = Integer.parseInt(capaciteStr);

                    Chambre chambre = new Chambre(idHebergement, capacite, false);
                    chambreDAO.ajouter(chambre);
                    vueAjouterChambre.afficherMessage("Chambre ajoutée avec succès !");
                    vueAjouterChambre.resetChamps();

                } catch (NumberFormatException ex) {
                    vueAjouterChambre.afficherMessage("ID hébergement et capacité doivent être des nombres valides.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    vueAjouterChambre.afficherMessage("Erreur lors de l'ajout de la chambre.");
                }
                break;

            case "VOIR_STATS":
                afficherStats();
                break;

            case "RETOUR_ACCUEIL":
                vueAjoutHebergement.setVisible(false);
                vueAjouterOption.setVisible(false);
                vueModifierSupprimerOption.setVisible(false);
                vueAssocierOptionsHebergement.setVisible(false);
                vueAjouterReduction.setVisible(false);
                vueAjouterChambre.setVisible(false);
                vue.setVisible(true);
                break;

            case "DECONNEXION":
                vue.dispose();
                vueAjoutHebergement.dispose();
                vueAjouterChambre.dispose();
                vueAjouterOption.dispose();
                vueAjouterReduction.dispose();
                vueAssocierOptionsHebergement.dispose();
                vueModifierSupprimerOption.dispose();
                break;

            default:
                // fix bug vue_associer_option_hebergemennt
                if (e.getSource() instanceof JComboBox) {
                    return;
                }

                vue.afficherMessage("Action inconnue : " + action);
        }
    }

    private void afficherStats() {
        try {
            JTabbedPane onglets = new JTabbedPane();

            // Graphique 1: Réservations par catégorie
            DefaultPieDataset datasetCategories = hebergementDAO.getReservationsByCategory();
            JFreeChart chartCategories = ChartFactory.createPieChart(
                    "Réservations par Catégorie", datasetCategories, true, true, false);
            onglets.addTab("Par Catégorie", new ChartPanel(chartCategories));

            // Graphique 2: Nombre d'adultes et d'enfants
            DefaultCategoryDataset datasetAdultsChildren = hebergementDAO.getAdultsAndChildrenStats();
            JFreeChart chartAdultsChildren = ChartFactory.createBarChart(
                    "Nombre d'Adultes et d'Enfants", "Catégorie", "Nombre", datasetAdultsChildren);
            onglets.addTab("Adultes et Enfants", new ChartPanel(chartAdultsChildren));

            JFrame statsFrame = new JFrame("Statistiques Administrateur");
            statsFrame.setContentPane(onglets);
            statsFrame.setSize(800, 600);
            statsFrame.setLocationRelativeTo(vue);
            statsFrame.setVisible(true);

        } catch (SQLException ex) {
            vue.afficherMessage("Erreur lors de la récupération des données: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void afficherAccueilAdmin() {
        vue.setVisible(true);
    }
}