package Vue;

import Controleur.Inscription;
import Dao.HebergementDAOImpl;
import Modele.Hebergement;
import Modele.Reduction;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class VueReservation extends JFrame {

    private JSpinner dateArriveeSpinner;
    private JSpinner dateDepartSpinner;
    private JSpinner spinnerNbAdultes;
    private JSpinner spinnerNbEnfants;
    private JTextField champPrixParNuit;
    private JTextField champPrixTotal;
    private JLabel labelReduction;
    private JButton btnValider;
    private JLabel labelMessage;

    private VueAccueil vueAccueil;
    private HebergementDAOImpl hebergementDAO;

    public VueReservation(VueAccueil vueAccueil, HebergementDAOImpl hebergementDAO) {
        this.vueAccueil = vueAccueil;
        this.hebergementDAO = hebergementDAO;

        setTitle("Réservation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10)); // 9 lignes pour ajout label réduction

        JLabel labelDateArrivee = new JLabel("Date d'arrivée :");
        dateArriveeSpinner = createDateSpinner(0); // Aujourd'hui

        JLabel labelDateDepart = new JLabel("Date de départ :");
        dateDepartSpinner = createDateSpinner(1); // Demain

        JLabel labelNbAdultes = new JLabel("Nombre d'adultes :");
        spinnerNbAdultes = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JLabel labelNbEnfants = new JLabel("Nombre d'enfants :");
        spinnerNbEnfants = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

        JLabel labelPrixParNuit = new JLabel("Prix par nuit (€) :");
        champPrixParNuit = new JTextField();
        champPrixParNuit.setEditable(false);

        JLabel labelReductionTitre = new JLabel("Réduction appliquée :");
        labelReduction = new JLabel(""); // Sera rempli dynamiquement
        labelReduction.setForeground(new Color(0, 128, 0)); // Vert

        JLabel labelPrixTotal = new JLabel("Prix total (€) :");
        champPrixTotal = new JTextField();
        champPrixTotal.setEditable(false);

        labelMessage = new JLabel("");
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
        labelMessage.setForeground(Color.RED);

        btnValider = new JButton("Valider la réservation");
        btnValider.setActionCommand("VALIDER_RESERVATION");

        panel.add(labelDateArrivee); panel.add(dateArriveeSpinner);
        panel.add(labelDateDepart); panel.add(dateDepartSpinner);
        panel.add(labelNbAdultes); panel.add(spinnerNbAdultes);
        panel.add(labelNbEnfants); panel.add(spinnerNbEnfants);
        panel.add(labelPrixParNuit); panel.add(champPrixParNuit);
        panel.add(labelReductionTitre); panel.add(labelReduction); // Nouvelle ligne
        panel.add(labelPrixTotal); panel.add(champPrixTotal);
        panel.add(labelMessage); panel.add(btnValider);

        add(panel, BorderLayout.CENTER);

        // Listeners pour recalculer automatiquement
        ChangeListener recalculListener = e -> calculerPrixTotal();
        dateArriveeSpinner.addChangeListener(recalculListener);
        dateDepartSpinner.addChangeListener(recalculListener);
        spinnerNbAdultes.addChangeListener(recalculListener);
        spinnerNbEnfants.addChangeListener(recalculListener);

        // Calcul initial au chargement
        SwingUtilities.invokeLater(this::calculerPrixTotal);
    }

    private JSpinner createDateSpinner(int daysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SpinnerDateModel model = new SpinnerDateModel(cal.getTime(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd-MM-yyyy"));
        return spinner;
    }

    private void calculerPrixTotal() {
        try {
            Hebergement h = vueAccueil.getHebergementSelectionne();
            if (h == null) {
                champPrixParNuit.setText("");
                champPrixTotal.setText("");
                labelMessage.setText("Aucun hébergement sélectionné.");
                labelReduction.setText("");
                return;
            }

            double prixParNuit = h.getPrixParNuit();
            labelReduction.setText("");

            if (Objects.equals(Inscription.getUtilisateurConnecte().getTypeUtilisateur(), "ancien")) {
                Reduction reduction = hebergementDAO.getReductionParHebergement(h.getId());
                if (reduction != null) {
                    prixParNuit = prixParNuit * (1 - reduction.getPourcentage() / 100.0);
                    labelReduction.setText(reduction.getPourcentage() + "% de réduction appliquée");
                }
            } else{
                labelReduction.setForeground(new Color(0, 0, 0));
                labelReduction.setText("-");
            }

            champPrixParNuit.setText(String.format(Locale.US, "%.2f", prixParNuit));

            Date dateArrivee = resetTime((Date) dateArriveeSpinner.getValue());
            Date dateDepart = resetTime((Date) dateDepartSpinner.getValue());
            int nbAdultes = (int) spinnerNbAdultes.getValue();
            int nbEnfants = (int) spinnerNbEnfants.getValue();

            if (!dateDepart.after(dateArrivee)) {
                champPrixTotal.setText("");
                labelMessage.setText("La date de départ doit être après celle d'arrivée.");
                return;
            }

            long difference = dateDepart.getTime() - dateArrivee.getTime();
            long nombreNuits = Math.max(1, difference / (1000 * 60 * 60 * 24));

            double prixTotal = prixParNuit * nombreNuits * nbAdultes
                    + (prixParNuit * 0.5 * nbEnfants * nombreNuits);

            champPrixTotal.setText(String.format(Locale.US, "%.2f", prixTotal));
            labelMessage.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            champPrixTotal.setText("");
            labelMessage.setText("Erreur lors du calcul.");
            labelReduction.setText("");
        }
    }

    private Date resetTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Getters
    public Date getDateArrivee() {
        return (Date) dateArriveeSpinner.getValue();
    }

    public Date getDateDepart() {
        return (Date) dateDepartSpinner.getValue();
    }

    public int getNbAdultes() {
        return (int) spinnerNbAdultes.getValue();
    }

    public int getNbEnfants() {
        return (int) spinnerNbEnfants.getValue();
    }

    public double getPrixTotal() {
        try {
            return Double.parseDouble(champPrixTotal.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion prix total : " + champPrixTotal.getText());
            return 0.0;
        }
    }

    public void ajouterEcouteur(ActionListener listener) {
        btnValider.addActionListener(listener);
    }
}
