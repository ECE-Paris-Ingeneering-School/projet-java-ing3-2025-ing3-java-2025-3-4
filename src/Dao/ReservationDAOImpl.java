package Dao;

import Modele.Reservation;
import java.sql.*;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    private DaoFactory daoFactory;

    public ReservationDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet resultats = stmt.executeQuery()) {

            while (resultats.next()) {
                Reservation reservation = mapResultSetToReservation(resultats);
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération des réservations");
        }

        return reservations;
    }

    @Override
    public void ajouter(Reservation reservation) {
        String sql = "INSERT INTO Reservations (utilisateur_id, hebergement_id, date_arrivee, date_depart, " +
                "adultes, enfants, prix_total, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setReservationParameters(stmt, reservation);
            stmt.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservationId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de l'ajout de la réservation");
        }
    }

    @Override
    public Reservation chercher(int reservationId) {
        Reservation reservation = null;
        String sql = "SELECT * FROM Reservations WHERE reservation_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            try (ResultSet resultats = stmt.executeQuery()) {
                if (resultats.next()) {
                    reservation = mapResultSetToReservation(resultats);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la recherche de la réservation");
        }

        return reservation;
    }

    @Override
    public Reservation chercherParUtilisateurEtHebergement(int utilisateurId, int hebergementId) {
        Reservation reservation = null;
        String sql = "SELECT * FROM Reservations WHERE utilisateur_id = ? AND hebergement_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, hebergementId);
            try (ResultSet resultats = stmt.executeQuery()) {
                if (resultats.next()) {
                    reservation = mapResultSetToReservation(resultats);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la recherche de la réservation par utilisateur et hébergement");
        }

        return reservation;
    }

    @Override
    public Reservation modifier(Reservation reservation) {
        String sql = "UPDATE Reservations SET utilisateur_id = ?, hebergement_id = ?, date_arrivee = ?, " +
                "date_depart = ?, adultes = ?, enfants = ?, prix_total = ?, statut = ? " +
                "WHERE reservation_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            setReservationParameters(stmt, reservation);
            stmt.setInt(9, reservation.getReservationId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la mise à jour de la réservation");
        }

        return reservation;
    }

    @Override
    public void supprimer(Reservation reservation) {
        String sql = "DELETE FROM Reservations WHERE reservation_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getReservationId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la suppression de la réservation");
        }
    }

    // Méthodes utilitaires privées
    private Reservation mapResultSetToReservation(ResultSet resultats) throws SQLException {
        return new Reservation(
                resultats.getInt("reservation_id"),
                resultats.getInt("utilisateur_id"),
                resultats.getInt("hebergement_id"),
                resultats.getDate("date_arrivee"),
                resultats.getDate("date_depart"),
                resultats.getInt("adultes"),
                resultats.getInt("enfants"),
                resultats.getDouble("prix_total"),
                resultats.getString("statut"),
                resultats.getDate("date_creation")
        );
    }

    private void setReservationParameters(PreparedStatement stmt, Reservation reservation) throws SQLException {
        stmt.setInt(1, reservation.getUtilisateurId());
        stmt.setInt(2, reservation.getHebergementId());
        stmt.setDate(3, reservation.getDateArrivee());
        stmt.setDate(4, reservation.getDateDepart());
        stmt.setInt(5, reservation.getAdultes());
        stmt.setInt(6, reservation.getEnfants());
        stmt.setDouble(7, reservation.getPrixTotal());
        stmt.setString(8, reservation.getStatut());
    }

    private void handleSQLException(SQLException e, String message) {
        e.printStackTrace();
        System.out.println(message + ": " + e.getMessage());
        // Vous pourriez aussi logger cette erreur ou la propager
    }
}