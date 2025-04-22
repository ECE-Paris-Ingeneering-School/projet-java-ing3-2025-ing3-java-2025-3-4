package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Dans VueInscription.java
public class VueInscription extends JFrame {
    private JTextField champNom, champPrenom, champEmail;
    private JPasswordField champMdp;
    private JButton boutonInscription, boutonConnexion;

    public VueInscription() {
        setTitle("Inscription");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        champNom = new JTextField();
        champPrenom = new JTextField();
        champEmail = new JTextField();
        champMdp = new JPasswordField();

        boutonInscription = new JButton("S'inscrire");
        boutonInscription.setActionCommand("SINSCRIPTION");

        boutonConnexion = new JButton("J'ai déjà un compte");
        boutonConnexion.setActionCommand("RETOUR_INSCRIPTION");

        panel.add(new JLabel("Nom :"));
        panel.add(champNom);
        panel.add(new JLabel("Prénom :"));
        panel.add(champPrenom);
        panel.add(new JLabel("Email :"));
        panel.add(champEmail);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(champMdp);
        panel.add(boutonInscription);
        panel.add(boutonConnexion);

        add(panel);
    }

    public String getNom() {
        return champNom.getText();
    }

    public String getPrenom() {
        return champPrenom.getText();
    }

    public String getEmail() {
        return champEmail.getText();
    }

    public String getMotDePasse() {
        return new String(champMdp.getPassword());
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonInscription.addActionListener(listener);
        boutonConnexion.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
