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
        String sql = "INSERT INTO Reservations (utilisateur_id, id_chambre, date_arrivee, date_depart, " +
                "adultes, enfants, prix_total, statut, date_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // On fixe la date de création à maintenant si elle est nulle
            if (reservation.getDateCreation() == null) {
                reservation.setDateCreation(new Date(System.currentTimeMillis()));
            }

            // Passage des paramètres
            setReservationParameters(stmt, reservation);

            stmt.executeUpdate();

            // Récupération de l'ID généré
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
    public Reservation chercherParUtilisateurEtHebergement(int utilisateurId, int chambreId) {
        Reservation reservation = null;
        String sql = "SELECT * FROM Reservations WHERE utilisateur_id = ? AND id_chambre = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, chambreId);
            try (ResultSet resultats = stmt.executeQuery()) {
                if (resultats.next()) {
                    reservation = mapResultSetToReservation(resultats);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la recherche de la réservation par utilisateur et chambre");
        }

        return reservation;
    }

    @Override
    public Reservation modifier(Reservation reservation) {
        String sql = "UPDATE Reservations SET utilisateur_id = ?, id_chambre = ?, date_arrivee = ?, " +
                "date_depart = ?, adultes = ?, enfants = ?, prix_total = ?, statut = ?, date_creation = ? " +
                "WHERE reservation_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            setReservationParameters(stmt, reservation);
            stmt.setInt(10, reservation.getReservationId());
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
        // Récupération des dates sous format String (si elles sont stockées sous un autre type dans la BDD)
        String dateArriveeString = resultats.getString("date_arrivee");
        String dateDepartString = resultats.getString("date_depart");

        // Conversion des dates de String à java.sql.Date (si nécessaire)
        java.sql.Date dateArrivee = null;
        java.sql.Date dateDepart = null;

        if (dateArriveeString != null) {
            dateArrivee = java.sql.Date.valueOf(dateArriveeString);  // Conversion
        }

        if (dateDepartString != null) {
            dateDepart = java.sql.Date.valueOf(dateDepartString);  // Conversion
        }

        return new Reservation(
                resultats.getInt("reservation_id"),
                resultats.getInt("utilisateur_id"),
                resultats.getInt("id_chambre"),
                dateArrivee,       // Utilisation de la date convertie
                dateDepart,        // Utilisation de la date convertie
                resultats.getInt("adultes"),
                resultats.getInt("enfants"),
                resultats.getDouble("prix_total"),
                resultats.getString("statut"),
                resultats.getDate("date_creation")  // Cette date est déjà un java.sql.Date
        );
    }


    private void setReservationParameters(PreparedStatement stmt, Reservation reservation) throws SQLException {
        stmt.setInt(1, reservation.getUtilisateurId());
        stmt.setInt(2, reservation.getChambreId());
        stmt.setDate(3, reservation.getDateArrivee());  // java.sql.Date
        stmt.setDate(4, reservation.getDateDepart());   // java.sql.Date
        stmt.setInt(5, reservation.getAdultes());
        stmt.setInt(6, reservation.getEnfants());
        stmt.setDouble(7, reservation.getPrixTotal());
        stmt.setString(8, reservation.getStatut());
        stmt.setDate(9, reservation.getDateCreation()); // java.sql.Date
    }


    private void handleSQLException(SQLException e, String message) {
        e.printStackTrace();
        System.out.println(message + ": " + e.getMessage());
    }

    public ArrayList<Reservation> getReservationsParUtilisateur(int utilisateurId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE utilisateur_id = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet resultats = stmt.executeQuery();

            while (resultats.next()) {
                Reservation reservation = mapResultSetToReservation(resultats);
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération des réservations de l'utilisateur");
        }

        return reservations;
    }
}
