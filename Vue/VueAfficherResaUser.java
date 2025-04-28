package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VueAfficherResaUser extends JFrame {

    private JTable tableReservations;
    private DefaultTableModel modelTable;
    private JButton boutonRetour;

    public VueAfficherResaUser(List<Object[]> reservations) {
        setTitle("Réservations de l'Utilisateur");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Juste fermer la fenêtre, pas toute l'application

        // Colonnes du tableau
        String[] colonnes = {"ID Réservation", "Date début", "Date fin", "Hébergement", "Statut"};

        modelTable = new DefaultTableModel(colonnes, 0);
        tableReservations = new JTable(modelTable);

        remplirTableau(reservations);

        JScrollPane scrollPane = new JScrollPane(tableReservations);
        add(scrollPane, BorderLayout.CENTER);

        // --- Ajout du bouton retour en bas ---
        boutonRetour = new JButton("Retour");
        boutonRetour.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonRetour.setBackground(new Color(200, 200, 200));
        boutonRetour.setForeground(Color.BLACK);
        boutonRetour.setFocusPainted(false);
        boutonRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonRetour.setActionCommand("RETOUR_UTILISATEURS");

        JPanel panelBas = new JPanel();
        panelBas.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBas.add(boutonRetour);

        add(panelBas, BorderLayout.SOUTH);
    }

    private void remplirTableau(List<Object[]> reservations) {
        for (Object[] reservation : reservations) {
            modelTable.addRow(reservation);
        }
    }

    public void ajouterEcouteur(ActionListener listener) {
        boutonRetour.addActionListener(listener);
    }


    public void setReservations(List<Object[]> reservations) {
        modelTable.setRowCount(0);
        for (Object[] reservation : reservations) {
            modelTable.addRow(reservation);
        }
    }
}
