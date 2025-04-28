package Vue;

import Modele.User;
import Modele.Reservation;
import Dao.ReservationDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VueUtilisateursReservations extends JFrame {
    private JTable tableUtilisateurs;
    private JButton boutonRetour;
    private JPanel panelPrincipal;
    private JPanel panelUtilisateurs;


    public VueUtilisateursReservations() {


        setTitle("Gestion des Utilisateurs et Réservations");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout(15, 15));
        setContentPane(panelPrincipal);

        // Titre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTitre = new JLabel("Utilisateurs et Réservations");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitre.add(labelTitre);
        panelPrincipal.add(panelTitre, BorderLayout.NORTH);

        // Table des utilisateurs
        tableUtilisateurs = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableUtilisateurs);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Bas de page pour les boutons
        JPanel panelBas = new JPanel(new BorderLayout()); // Un autre BorderLayout pour gérer les boutons en bas

        // Liste des utilisateurs et boutons
        panelUtilisateurs = new JPanel();
        panelUtilisateurs.setLayout(new BoxLayout(panelUtilisateurs, BoxLayout.Y_AXIS)); // 1 colonne
        JScrollPane scrollPanel = new JScrollPane(panelUtilisateurs); // Scroll des utilisateurs si nécessaire
        panelBas.add(scrollPanel, BorderLayout.CENTER); // Ajout des utilisateurs dans la zone CENTRE du bas

        // Ajout du bouton "Retour" au bas de la fenêtre
        boutonRetour = new JButton("Retour");
        boutonRetour.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonRetour.setBackground(new Color(200, 200, 200));
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setActionCommand("RETOUR_ACCUEIL");

        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrer les boutons
        panelBoutons.add(boutonRetour);

        panelBas.add(panelBoutons, BorderLayout.SOUTH); // Placer les boutons au bas du bas (SOUTH)

        panelPrincipal.add(panelBas, BorderLayout.SOUTH); // Ajout de la section basse complète

    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonRetour.addActionListener(listener);
    }

    public void ajouterEcouteurTable(ActionListener listener) {
        tableUtilisateurs.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "VOIR_RESERVATIONS"));
            }
        });
    }

    public JTable getTableUtilisateurs() {
        return tableUtilisateurs;
    }

    public int getSelectedUserId() {
        int selectedRow = tableUtilisateurs.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableUtilisateurs.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public void remplirTable(Object[][] donnees) {
        String[] colonnes = {"ID Utilisateur", "Nom", "Prénom"};

        DefaultTableModel model = new DefaultTableModel(donnees, colonnes) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Aucune cellule éditable
            }
        };
        tableUtilisateurs.setModel(model);
    }



    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


}
