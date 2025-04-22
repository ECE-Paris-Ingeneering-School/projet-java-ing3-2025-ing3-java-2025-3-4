package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Dans VueAdmin.java
public class VueAdmin extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMdp;
    private JButton boutonConnexion;
    private JButton boutonRetour;

    public VueAdmin() {
        setTitle("Connexion Administrateur");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        champEmail = new JTextField();
        champMdp = new JPasswordField();

        boutonConnexion = new JButton("Se connecter (Admin)");
        boutonConnexion.setActionCommand("ADMIN");

        boutonRetour = new JButton("Retour");
        boutonRetour.setActionCommand("RETOUR_CONNEXION");

        panel.add(new JLabel("Email Administrateur :"));
        panel.add(champEmail);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(champMdp);
        panel.add(boutonConnexion);
        panel.add(boutonRetour);

        add(panel);
    }

    public String getEmail() {
        return champEmail.getText();
    }

    public String getMotDePasse() {
        return new String(champMdp.getPassword());
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonConnexion.addActionListener(listener);
        boutonRetour.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
