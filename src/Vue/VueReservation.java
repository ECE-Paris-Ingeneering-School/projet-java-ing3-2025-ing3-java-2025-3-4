package Vue;

import Controleur.Inscription;
import Dao.HebergementDAOImpl;
import Modele.Hebergement;
import Modele.Reduction;
import Modele.Chambre;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VueReservation extends JFrame {

    private JSpinner dateArriveeSpinner;
    private JSpinner dateDepartSpinner;
    private JSpinner spinnerNbAdultes;
    private JSpinner spinnerNbEnfants;
    private JSpinner spinnerNbChambres;
    private JLabel labelChambresFixes;

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
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));

        JLabel labelDateArrivee = new JLabel("Date d'arrivée :");
        dateArriveeSpinner = createDateSpinner(0);

        JLabel labelDateDepart = new JLabel("Date de départ :");
        dateDepartSpinner = createDateSpinner(1);

        JLabel labelNbAdultes = new JLabel("Nombre d'adultes :");
        spinnerNbAdultes = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        JLabel labelNbEnfants = new JLabel("Nombre d'enfants :");
        spinnerNbEnfants = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        JLabel labelNbChambres = new JLabel("Nombre de chambres :");
        spinnerNbChambres = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        labelChambresFixes = new JLabel("");
        labelChambresFixes.setVisible(false);

        JLabel labelPrixParNuit = new JLabel("Prix par nuit (€) :");
        champPrixParNuit = new JTextField();
        champPrixParNuit.setEditable(false);

        JLabel labelReductionTitre = new JLabel("Réduction appliquée :");
        labelReduction = new JLabel("");
        labelReduction.setForeground(new Color(0, 0, 0));

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
        panel.add(labelNbChambres); panel.add(spinnerNbChambres);
        panel.add(new JLabel("")); panel.add(labelChambresFixes);
        panel.add(labelPrixParNuit); panel.add(champPrixParNuit);
        panel.add(labelReductionTitre); panel.add(labelReduction);
        panel.add(labelPrixTotal); panel.add(champPrixTotal);
        panel.add(labelMessage); panel.add(btnValider);

        add(panel, BorderLayout.CENTER);

        ChangeListener recalculListener = e -> calculerPrixTotal();
        dateArriveeSpinner.addChangeListener(recalculListener);
        dateDepartSpinner.addChangeListener(recalculListener);
        spinnerNbAdultes.addChangeListener(recalculListener);
        spinnerNbEnfants.addChangeListener(recalculListener);
        spinnerNbChambres.addChangeListener(recalculListener);

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
                    prixParNuit *= (1 - reduction.getPourcentage() / 100.0);
                    labelReduction.setForeground(new Color(0, 128, 0));
                    labelReduction.setText(reduction.getPourcentage() + "% de réduction appliquée");
                } else {
                    labelReduction.setText("-");
                }
            } else {
                labelReduction.setText("-");
            }

            champPrixParNuit.setText(String.format(Locale.US, "%.2f", prixParNuit));

            Date dateArrivee = resetTime((Date) dateArriveeSpinner.getValue());
            Date dateDepart = resetTime((Date) dateDepartSpinner.getValue());

            if (!dateDepart.after(dateArrivee)) {
                champPrixTotal.setText("");
                labelMessage.setText("La date de départ doit être après celle d'arrivée.");
                return;
            }

            long difference = dateDepart.getTime() - dateArrivee.getTime();
            long nombreNuits = Math.max(1, difference / (1000 * 60 * 60 * 24));

            int nbAdultes = (int) spinnerNbAdultes.getValue();
            int nbEnfants = (int) spinnerNbEnfants.getValue();
            int nbPersonnes = nbAdultes + nbEnfants;

            boolean estHotel = h.getCategorie().equalsIgnoreCase("hotel");
            double prixTotal;

            if (estHotel) {
                spinnerNbChambres.setVisible(true);
                spinnerNbChambres.setEnabled(true);
                labelChambresFixes.setVisible(true);

                List<Chambre> chambresDisponibles = hebergementDAO.getChambresDisponibles(h.getId());
                int nbChambresDispo = chambresDisponibles.size();
                labelChambresFixes.setText(nbChambresDispo + " chambre(s) disponible(s)");

                SpinnerNumberModel model = (SpinnerNumberModel) spinnerNbChambres.getModel();
                model.setMaximum(nbChambresDispo);

                int nbChambresDemandees = (int) spinnerNbChambres.getValue();
                if (nbChambresDemandees > nbChambresDispo) {
                    labelMessage.setText("Pas assez de chambres disponibles.");
                    champPrixTotal.setText("");
                    return;
                }

                int capaciteTotale = 0;
                for (int i = 0; i < nbChambresDemandees && i < chambresDisponibles.size(); i++) {
                    capaciteTotale += chambresDisponibles.get(i).getPlaceMax();
                }

                if (capaciteTotale < nbPersonnes) {
                    labelMessage.setText("La capacité des chambres sélectionnées est insuffisante.");
                    champPrixTotal.setText("");
                    return;
                }

                prixTotal = prixParNuit * nombreNuits * nbChambresDemandees;

            } else {
                spinnerNbChambres.setVisible(false);
                spinnerNbChambres.setEnabled(false);
                labelChambresFixes.setVisible(true);
                labelChambresFixes.setText("Logement avec " + h.getNbChambres() + " chambre(s)");

                prixTotal = prixParNuit * nombreNuits;
            }

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

    public int getNbChambres() {
        return (int) spinnerNbChambres.getValue();
    }

    public void ajouterEcouteur(ActionListener listener) {
        btnValider.addActionListener(listener);
    }
}
