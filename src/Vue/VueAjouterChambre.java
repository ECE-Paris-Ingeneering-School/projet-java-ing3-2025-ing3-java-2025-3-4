package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueAjouterChambre extends JFrame {
    private JTextField champHebergementId;
    private JTextField champCapacite;
    private JButton boutonAjouter;
    private JButton boutonRetour;

    public VueAjouterChambre() {
        setTitle("Ajouter une chambre");
        setSize(400, 200); // Taille réduite
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        champHebergementId = new JTextField(20);
        champCapacite = new JTextField(20);
        boutonAjouter = new JButton("Ajouter la chambre");
        boutonAjouter.setActionCommand("AJOUT_CHAMBRE_BDD");

        boutonRetour = new JButton("Retour à l'accueil");
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        JPanel panelChamps = new JPanel(new GridLayout(2, 2));
        panelChamps.add(new JLabel("ID Hébergement :"));
        panelChamps.add(champHebergementId);
        panelChamps.add(new JLabel("Capacité :"));
        panelChamps.add(champCapacite);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonRetour);

        getContentPane().setLayout(new BorderLayout());
        add(panelChamps, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
    }

    // Getters
    public String getHebergementId() {
        return champHebergementId.getText();
    }

    public String getCapacite() {
        return champCapacite.getText();
    }

    // Ajouter un ActionListener
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
        champCapacite.setText("");
    }
}
