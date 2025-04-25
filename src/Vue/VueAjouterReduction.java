package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjouterReduction extends JFrame {
    private JTextField champHebergementId;
    private JTextField champPourcentage;
    private JTextField champDescription;
    private JTextField champDateDebut;
    private JTextField champDateFin;
    private JButton boutonAjouter;
    private JButton boutonRetour;

    public VueAjouterReduction() {
        setTitle("Ajouter une réduction");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        champHebergementId = new JTextField(20);
        champPourcentage = new JTextField(20);
        champDescription = new JTextField(20);
        champDateDebut = new JTextField(20);
        champDateFin = new JTextField(20);
        boutonAjouter = new JButton("Ajouter la réduction");
        boutonAjouter.setActionCommand("AJOUT_REDUCTION_BDD");

        boutonRetour = new JButton("Retour à l'accueil");
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        JPanel panelChamps = new JPanel(new GridLayout(5, 2));
        panelChamps.add(new JLabel("ID Hébergement :"));
        panelChamps.add(champHebergementId);
        panelChamps.add(new JLabel("Pourcentage de réduction :"));
        panelChamps.add(champPourcentage);
        panelChamps.add(new JLabel("Description :"));
        panelChamps.add(champDescription);
        panelChamps.add(new JLabel("Date début (YYYY-MM-DD) :"));
        panelChamps.add(champDateDebut);
        panelChamps.add(new JLabel("Date fin (YYYY-MM-DD) :"));
        panelChamps.add(champDateFin);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonRetour);

        getContentPane().setLayout(new BorderLayout());
        add(panelChamps, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    // Getters pour les champs
    public String getHebergementId() {
        return champHebergementId.getText();
    }

    public String getPourcentage() {
        return champPourcentage.getText();
    }

    public String getDescription() {
        return champDescription.getText();
    }

    public String getDateDebut() {
        return champDateDebut.getText();
    }

    public String getDateFin() {
        return champDateFin.getText();
    }

    // Ajouter un ActionListener pour les deux boutons
    public void ajouterEcouteur(ActionListener listener) {
        boutonAjouter.addActionListener(listener);
        boutonRetour.addActionListener(listener);
    }

    // Affichage de message
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Réinitialisation des champs
    public void resetChamps() {
        champHebergementId.setText("");
        champPourcentage.setText("");
        champDescription.setText("");
        champDateDebut.setText("");
        champDateFin.setText("");
    }
}
