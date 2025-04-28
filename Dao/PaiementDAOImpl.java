package Dao;

import Modele.Paiement;
import java.sql.*;
import java.util.ArrayList;

public class PaiementDAOImpl implements PaiementDAO {
    private DaoFactory daoFactory;

    public PaiementDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Paiement paiement) {
        String sql = "INSERT INTO Paiements (reservation_id, montant, date_paiement, statut_paiement) VALUES (?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, paiement.getReservationId());
            stmt.setDouble(2, paiement.getMontant());
            stmt.setDate(3, paiement.getDatePaiement());
            stmt.setString(4, paiement.getStatutPaiement());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    paiement.setPaiementId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du paiement : " + e.getMessage());
        }
    }

    @Override
    public Paiement chercherParId(int paiementId) {
        String sql = "SELECT * FROM Paiement WHERE paiement_id = ?";
        Paiement paiement = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, paiementId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paiement = mapResultSetToPaiement(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche du paiement : " + e.getMessage());
        }

        return paiement;
    }

    @Override
    public ArrayList<Paiement> getAll() {
        ArrayList<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM Paiement";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                paiements.add(mapResultSetToPaiement(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }

        return paiements;
    }

    @Override
    public ArrayList<Paiement> getParReservation(int reservationId) {
        ArrayList<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM Paiement WHERE reservation_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapResultSetToPaiement(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des paiements par réservation : " + e.getMessage());
        }

        return paiements;
    }

    private Paiement mapResultSetToPaiement(ResultSet rs) throws SQLException {
        return new Paiement(
                rs.getInt("paiement_id"),
                rs.getInt("reservation_id"),
                rs.getDouble("montant"),
                rs.getDate("date_paiement"),
                rs.getString("statut_paiement")
        );
    }
}
