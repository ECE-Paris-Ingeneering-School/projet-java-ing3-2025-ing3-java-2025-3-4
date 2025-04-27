package Vue;

import Modele.Hebergement;
import Modele.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VuePaiement extends JFrame {

    private JLabel labelNomUser;
    private JLabel labelNomHebergement;
    private JLabel labelDates;
    private JLabel labelPrixTotal;

    private JButton boutonPayer;

    // Couleurs et polices
    private final Color couleurPrincipale = new Color(60, 141, 188);
    private final Color couleurFond = new Color(245, 245, 245);
    private final Font policeNormale = new Font("Arial", Font.PLAIN, 16);
    private final Font policeTitre = new Font("Arial", Font.BOLD, 18);

    public VuePaiement(User user, Hebergement hebergement, Date dateDebut, Date dateFin, double prixTotal) {
        setTitle("Paiement");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(couleurFond);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Nouveau panel pour le titre
        JLabel titreLabel = new JLabel("Récapitulatif de la réservation");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Plus gros et gras
        titreLabel.setForeground(couleurPrincipale); // Même couleur que ton bouton
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Marge autour

        panelPrincipal.add(titreLabel, BorderLayout.NORTH); // Ajoute en haut de la fenêtre

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        labelNomUser = new JLabel(" Nom : " + user.getPrenom() + " " + user.getNom());
        labelNomHebergement = new JLabel("Hébergement : " + hebergement.getNom());
        labelDates = new JLabel("Du " + sdf.format(dateDebut) + " au " + sdf.format(dateFin));
        labelPrixTotal = new JLabel("Total à payer : " + prixTotal + " €");

        labelNomUser.setFont(policeNormale);
        labelNomHebergement.setFont(policeNormale);
        labelDates.setFont(policeNormale);
        labelPrixTotal.setFont(policeTitre);

        labelNomUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelNomHebergement.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelDates.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPrixTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        boutonPayer = new JButton("Payer maintenant");
        boutonPayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonPayer.setActionCommand("PAYER");
        styliserBouton(boutonPayer);

        panelForm.add(labelNomUser);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        panelForm.add(labelNomHebergement);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        panelForm.add(labelDates);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        panelForm.add(labelPrixTotal);
        panelForm.add(Box.createRigidArea(new Dimension(0, 20)));
        panelForm.add(boutonPayer);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private void styliserBouton(JButton bouton) {
        bouton.setBackground(couleurPrincipale);
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Arial", Font.BOLD, 16));
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setPreferredSize(new Dimension(180, 40));
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonPayer.addActionListener(listener);
    }

    public JButton getBoutonPayer() {
        return boutonPayer;
    }
}