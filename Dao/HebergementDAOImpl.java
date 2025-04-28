package Dao;

import Modele.Avis;
import Modele.Chambre;
import Modele.Hebergement;
import Modele.Reduction;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HebergementDAOImpl implements HebergementDAO {
    private DaoFactory daoFactory;

    public HebergementDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Hebergement> getAll() {
        ArrayList<Hebergement> listeHebergements = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("SELECT * FROM Hebergements");

            while (resultats.next()) {
                Hebergement h = new Hebergement(
                        resultats.getInt("hebergement_id"),
                        resultats.getString("nom"),
                        resultats.getString("description"),
                        resultats.getString("adresse"),
                        resultats.getString("ville"),
                        resultats.getString("pays"),
                        resultats.getDouble("prix_par_nuit"),
                        resultats.getString("categorie"),
                        resultats.getString("photo"),
                        resultats.getInt("nb_chambre"),
                        resultats.getInt("place")
                );
                listeHebergements.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Impossible de récupérer la liste des hébergements");
        }

        return listeHebergements;
    }

    @Override
    public void ajouter(Hebergement h) {
        try {
            Connection connexion = daoFactory.getConnection();

            PreparedStatement ps = connexion.prepareStatement(
                    "INSERT INTO Hebergements (nom, description, adresse, ville, pays, categorie, prix_par_nuit, photo, place, nb_chambre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, h.getNom());
            ps.setString(2, h.getDescription());
            ps.setString(3, h.getAdresse());
            ps.setString(4, h.getVille());
            ps.setString(5, h.getPays());
            ps.setString(6, h.getCategorie());
            ps.setDouble(7, h.getPrixParNuit());
            ps.setString(8, h.getPhoto());
            ps.setInt(9, h.getPlace());
            ps.setInt(10, h.getNbChambres());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'hébergement impossible");
        }
    }

    @Override
    public Hebergement chercher(int id) {
        Hebergement h = null;

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("SELECT * FROM Hebergements WHERE hebergement_id = " + id);

            if (resultats.next()) {
                h = new Hebergement(
                        resultats.getInt("hebergement_id"),
                        resultats.getString("nom"),
                        resultats.getString("description"),
                        resultats.getString("adresse"),
                        resultats.getString("ville"),
                        resultats.getString("pays"),
                        resultats.getDouble("prix_par_nuit"),
                        resultats.getString("categorie"),
                        resultats.getString("photo"),
                        resultats.getInt("place"),
                        resultats.getInt("nb_chambre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hébergement non trouvé");
        }

        return h;
    }

    @Override
    public Hebergement modifier(Hebergement h) {
        try {
            Connection connexion = daoFactory.getConnection();

            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE Hebergements SET nom = ?, description = ?, adresse = ?, ville = ?, pays = ?, categorie = ?, prix_par_nuit = ?, photo = ?, place = ?, nb_chambre = ? WHERE hebergement_id = ?"
            );
            ps.setString(1, h.getNom());
            ps.setString(2, h.getDescription());
            ps.setString(3, h.getAdresse());
            ps.setString(4, h.getVille());
            ps.setString(5, h.getPays());
            ps.setString(6, h.getCategorie());
            ps.setDouble(7, h.getPrixParNuit());
            ps.setString(8, h.getPhoto());
            ps.setInt(9, h.getPlace());
            ps.setInt(10, h.getNbChambres());
            ps.setInt(11, h.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification impossible");
        }

        return h;
    }

    @Override
    public void supprimer(Hebergement h) {
        try {
            Connection connexion = daoFactory.getConnection();

            PreparedStatement ps = connexion.prepareStatement(
                    "DELETE FROM Hebergements WHERE hebergement_id = ?"
            );
            ps.setInt(1, h.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression impossible");
        }
    }

    public ArrayList<Avis> getAvisParHebergement(int hebergementId) {
        ArrayList<Avis> avisList = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM Avis WHERE hebergement_id = ?"
            );
            ps.setInt(1, hebergementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Avis avis = new Avis(
                        rs.getInt("avis_id"),
                        rs.getInt("utilisateur_id"),
                        rs.getInt("hebergement_id"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getDate("date_creation")
                );
                avisList.add(avis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des avis.");
        }

        return avisList;
    }

    public double calculerMoyenneNotes(int hebergementId) {
        ArrayList<Avis> avisList = getAvisParHebergement(hebergementId);
        if (avisList.isEmpty()) {
            return 0;
        }
        int totalNotes = 0;
        for (Avis avis : avisList) {
            totalNotes += avis.getNote();
        }
        return (double) totalNotes / avisList.size();
    }

    public void mettreAJourNoteEtEtoiles(int hebergementId, double noteMoyenne, int etoiles) {
        String sql = "UPDATE Hebergements SET note_moyenne = ?, etoiles = ? WHERE hebergement_id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, noteMoyenne);
            stmt.setInt(2, etoiles);
            stmt.setInt(3, hebergementId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reduction> getAllReductions() {
        List<Reduction> reductions = new ArrayList<>();

        String sql = "SELECT hebergement_id, pourcentage, description FROM reductions";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int hebergementId = rs.getInt("hebergement_id");
                int pourcentage = rs.getInt("pourcentage");
                String description = rs.getString("description");
                reductions.add(new Reduction(hebergementId, pourcentage, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reductions;
    }

    public Reduction getReductionParHebergement(int idHebergement) {
        Reduction reduction = null;
        try {
            String sql = "SELECT * FROM reductions WHERE hebergement_id = ?";
            Connection conn = daoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idHebergement);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reduction = new Reduction(idHebergement, rs.getInt("pourcentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reduction;
    }

    public List<Chambre> getChambresDisponibles(int hebergementId, Date dateArrivee, Date dateDepart) {
        List<Chambre> chambresDisponibles = new ArrayList<>();
        try {
            Connection conn = daoFactory.getConnection();

            // 1. Sélectionner toutes les chambres de l'hébergement
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM chambre WHERE hebergement_id = ?"
            );
            ps.setInt(1, hebergementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idChambre = rs.getInt("id_chambre");
                int placeMax = rs.getInt("place_max");

                // 2. Pour chaque chambre, vérifier si elle est libre aux dates demandées
                if (estChambreDisponible(conn, idChambre, dateArrivee, dateDepart)) {
                    Chambre chambre = new Chambre(idChambre, hebergementId, placeMax);
                    chambresDisponibles.add(chambre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chambresDisponibles;
    }

    private boolean estChambreDisponible(Connection conn, int idChambre, Date dateArrivee, Date dateDepart) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Reservations " +
                            "WHERE id_chambre = ? " +
                            "AND NOT (date_depart <= ? OR date_arrivee >= ?)"
            );
            ps.setInt(1, idChambre);
            ps.setDate(2, new java.sql.Date(dateArrivee.getTime()));
            ps.setDate(3, new java.sql.Date(dateDepart.getTime()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===========================================
    // Méthodes ajoutées pour les statistiques
    // ===========================================

    public DefaultPieDataset getReservationsByCategory() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = "SELECT h.categorie, COUNT(r.reservation_id) as count " +
                "FROM Hebergements h " +
                "LEFT JOIN chambre c ON h.hebergement_id = c.hebergement_id " +
                "LEFT JOIN Reservations r ON c.id_chambre = r.id_chambre " +
                "GROUP BY h.categorie";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getString("categorie"), rs.getInt("count"));
            }
        }
        return dataset;
    }

    public DefaultCategoryDataset getAdultsAndChildrenStats() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT SUM(adultes) as total_adultes, SUM(enfants) as total_enfants FROM Reservations";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                dataset.addValue(rs.getInt("total_adultes"), "Nombre", "Adultes");
                dataset.addValue(rs.getInt("total_enfants"), "Nombre", "Enfants");
            }
        }
        return dataset;
    }
}
