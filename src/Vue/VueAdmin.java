package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Dans VueAdmin.java
public class VueAdmin extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMdp;
    private JButton boutonConnexion, boutonRetour;
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 18);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);

    public VueAdmin() {
        setTitle("Connexion administrateur");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Définition des marges extérieures
        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setContentPane(panelPrincipal);

        // Panel d'en-tête avec titre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(couleurSecondaire);
        JLabel labelTitre = new JLabel("Connexion administrateur");
        labelTitre.setFont(policeTitre);
        labelTitre.setForeground(couleurPrincipale);
        panelTitre.add(labelTitre);

        // Panel central avec formulaire
        JPanel panelFormulaire = new JPanel();
        panelFormulaire.setLayout(new BoxLayout(panelFormulaire, BoxLayout.Y_AXIS));
        panelFormulaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelFormulaire.setBackground(Color.WHITE);

        // Création des composants
        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setFont(policeNormale);
        labelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        champEmail = new JTextField(20);
        champEmail.setFont(policeNormale);
        champEmail.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        champEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelMdp = new JLabel("Mot de passe :");
        labelMdp.setFont(policeNormale);
        labelMdp.setAlignmentX(Component.LEFT_ALIGNMENT);

        champMdp = new JPasswordField(20);
        champMdp.setFont(policeNormale);
        champMdp.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        champMdp.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Boutons d'action
        boutonConnexion = new JButton("Se connecter (admin)");
        boutonConnexion.setFont(policeNormale);
        boutonConnexion.setBackground(couleurPrincipale);
        boutonConnexion.setForeground(Color.WHITE);
        boutonConnexion.setFocusPainted(false);
        boutonConnexion.setActionCommand("ADMIN");
        boutonConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonConnexion.setAlignmentX(Component.LEFT_ALIGNMENT);
        boutonConnexion.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));


        // Ajout des composants avec des espaces
        panelFormulaire.add(labelEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 15)));
        panelFormulaire.add(labelMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulaire.add(boutonConnexion);

        // Panel pour le bouton retour en bas
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBas.setBackground(couleurSecondaire);
        boutonRetour = new JButton("Retour");
        boutonRetour.setFont(new Font("Arial", Font.PLAIN, 12));
        boutonRetour.setActionCommand("RETOUR_CONNEXION");
        boutonRetour.setBorderPainted(false);
        boutonRetour.setContentAreaFilled(false);
        boutonRetour.setForeground(new Color(100, 100, 100));
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBas.add(boutonRetour);

        // Ajout des panels au conteneur principal
        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulaire, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);
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