package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Dans VueInscription.java
public class VueInscription extends JFrame {
    private JTextField champNom, champPrenom, champEmail;
    private JPasswordField champMdp;
    private JButton boutonInscription, boutonRetour;
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 18);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);

    public VueInscription() {
        setTitle("Inscription");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setContentPane(panelPrincipal);

        // Panel d'en-tête avec titre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(couleurSecondaire);
        JLabel labelTitre = new JLabel("Inscription");
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
        JLabel labelNom = new JLabel("Nom :");
        labelNom.setFont(policeNormale);
        labelNom.setAlignmentX(Component.LEFT_ALIGNMENT);

        champNom = new JPasswordField(20);
        champNom.setFont(policeNormale);
        champNom.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        champNom.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelPrenom = new JLabel("Prénom :");
        labelPrenom.setFont(policeNormale);
        labelPrenom.setAlignmentX(Component.LEFT_ALIGNMENT);

        champPrenom = new JPasswordField(20);
        champPrenom.setFont(policeNormale);
        champPrenom.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        champPrenom.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        boutonInscription = new JButton("S'inscrire");
        boutonInscription.setFont(policeNormale);
        boutonInscription.setBackground(couleurPrincipale);
        boutonInscription.setForeground(Color.WHITE);
        boutonInscription.setFocusPainted(false);
        boutonInscription.setActionCommand("SINSCRIPTION");
        boutonInscription.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonInscription.setAlignmentX(Component.LEFT_ALIGNMENT);
        boutonInscription.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));

        boutonRetour = new JButton("J'ai déjà un compte");
        boutonRetour.setFont(policeNormale);
        boutonRetour.setBackground(couleurSecondaire);
        boutonRetour.setForeground(Color.DARK_GRAY);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setActionCommand("RETOUR_INSCRIPTION");
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setAlignmentX(Component.LEFT_ALIGNMENT);
        boutonRetour.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));

        // Ajout des composants avec des espaces
        panelFormulaire.add(labelNom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champNom);
        panelFormulaire.add(labelPrenom);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champPrenom);
        panelFormulaire.add(labelEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 15)));
        panelFormulaire.add(labelMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulaire.add(boutonInscription);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulaire.add(boutonRetour);

        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulaire, BorderLayout.CENTER);
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
        boutonRetour.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}