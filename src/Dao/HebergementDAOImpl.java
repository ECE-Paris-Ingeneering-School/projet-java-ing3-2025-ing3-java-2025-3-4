package Dao;

import Modele.Avis;
import Modele.Hebergement;
import Modele.Reduction;

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
                        resultats.getString("photo") // Nouveau champ ajouté
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
                    "INSERT INTO Hebergements (nom, description, adresse, ville, pays, categorie, prix_par_nuit, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, h.getNom());
            ps.setString(2, h.getDescription());
            ps.setString(3, h.getAdresse());
            ps.setString(4, h.getVille());
            ps.setString(5, h.getPays());
            ps.setString(6, h.getCategorie());
            ps.setDouble(7, h.getPrixParNuit());
            ps.setString(8, h.getPhoto()); // Ajout du champ photo

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
                        resultats.getString("photo") // Ajout du champ photo
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
                    "UPDATE Hebergements SET nom = ?, description = ?, adresse = ?, ville = ?, pays = ?, categorie = ?, prix_par_nuit = ?, photo = ? WHERE hebergement_id = ?"
            );
            ps.setString(1, h.getNom());
            ps.setString(2, h.getDescription());
            ps.setString(3, h.getAdresse());
            ps.setString(4, h.getVille());
            ps.setString(5, h.getPays());
            ps.setString(6, h.getCategorie());
            ps.setDouble(7, h.getPrixParNuit());
            ps.setString(8, h.getPhoto()); // Ajout du champ photo
            ps.setInt(9, h.getId());

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
                    "SELECT * FROM avis WHERE hebergement_id = ?"
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
        String sql = "UPDATE hebergements SET note_moyenne = ?, etoiles = ? WHERE hebergement_id = ?";
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

        String sql = "SELECT hebergement_id, pourcentage,description FROM reductions";
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
        // Exemple de code pour récupérer la réduction depuis la base de données
        Reduction reduction = null;
        try {
            // Code pour interroger la base de données et obtenir la réduction
            String sql = "SELECT * FROM reductions WHERE hebergement_id = ?";
            // Utiliser un PreparedStatement pour éviter les injections SQL
            Connection conn = daoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idHebergement);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Créer l'objet Reduction à partir des données récupérées
                reduction = new Reduction(idHebergement, rs.getInt("pourcentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reduction;
    }



}
