package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueConnexion extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMdp;
    private JButton boutonConnexion, boutonInscription, boutonConnexionAdmin;
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurSecondaire = new Color(245, 245, 245);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 18);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 14);

    public VueConnexion() {
        setTitle("Connexion");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        setContentPane(panelPrincipal);


        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(couleurSecondaire);
        JLabel labelTitre = new JLabel("Bienvenue");
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
        boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setFont(policeNormale);
        boutonConnexion.setBackground(couleurPrincipale);
        boutonConnexion.setForeground(Color.BLACK);
        boutonConnexion.setFocusPainted(false);
        boutonConnexion.setActionCommand("CONNEXION_PAGE");
        boutonConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonConnexion.setAlignmentX(Component.LEFT_ALIGNMENT);
        boutonConnexion.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));

        boutonInscription = new JButton("Je n'ai pas de compte");
        boutonInscription.setFont(policeNormale);
        boutonInscription.setBackground(couleurSecondaire);
        boutonInscription.setForeground(Color.DARK_GRAY);
        boutonInscription.setFocusPainted(false);
        boutonInscription.setActionCommand("INSCRIPTION_PAGE");
        boutonInscription.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonInscription.setAlignmentX(Component.LEFT_ALIGNMENT);
        boutonInscription.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));


        panelFormulaire.add(labelEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champEmail);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 15)));
        panelFormulaire.add(labelMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulaire.add(champMdp);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulaire.add(boutonConnexion);
        panelFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulaire.add(boutonInscription);

        // Panel pour le bouton admin en bas
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBas.setBackground(couleurSecondaire);
        boutonConnexionAdmin = new JButton("Connexion Admin");
        boutonConnexionAdmin.setFont(new Font("Arial", Font.PLAIN, 12));
        boutonConnexionAdmin.setActionCommand("ADMIN_CONNEXION");
        boutonConnexionAdmin.setBorderPainted(false);
        boutonConnexionAdmin.setContentAreaFilled(false);
        boutonConnexionAdmin.setForeground(new Color(100, 100, 100));
        boutonConnexionAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBas.add(boutonConnexionAdmin);


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
        boutonInscription.addActionListener(listener);
        boutonConnexionAdmin.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}