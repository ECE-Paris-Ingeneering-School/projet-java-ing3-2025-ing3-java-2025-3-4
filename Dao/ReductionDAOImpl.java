package Dao;

import Modele.Reduction;

import java.sql.*;
import java.util.ArrayList;

public class ReductionDAOImpl implements ReductionDAO {
    private DaoFactory daoFactory;

    public ReductionDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Reduction> getAll() {
        ArrayList<Reduction> reductions = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("SELECT * FROM Reductions");

            while (resultats.next()) {
                Reduction reduction = new Reduction(
                        resultats.getInt("reduction_id"),
                        resultats.getInt("hebergement_id"),
                        resultats.getInt("pourcentage_reduction"),
                        resultats.getString("description"),
                        resultats.getDate("date_debut"),
                        resultats.getDate("date_fin")
                );
                reductions.add(reduction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des réductions.");
        }

        return reductions;
    }

    @Override
    public void ajouter(Reduction reduction) {
        String sql = "INSERT INTO Reductions (hebergement_id, pourcentage, description, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(sql);

            pstmt.setInt(1, reduction.getHebergementId());
            pstmt.setInt(2, reduction.getPourcentage());
            pstmt.setString(3, reduction.getDescription());
            pstmt.setDate(4, reduction.getDateDebut());
            pstmt.setDate(5, reduction.getDateFin());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la réduction.");
        }
    }

    @Override
    public Reduction chercher(int id) {
        Reduction reduction = null;

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement("SELECT * FROM Reductions WHERE reduction_id = ?");
            pstmt.setInt(1, id);

            ResultSet resultats = pstmt.executeQuery();

            if (resultats.next()) {
                reduction = new Reduction(
                        resultats.getInt("reduction_id"),
                        resultats.getInt("hebergement_id"),
                        resultats.getInt("pourcentage_reduction"),
                        resultats.getString("description"),
                        resultats.getDate("date_debut"),
                        resultats.getDate("date_fin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de la réduction.");
        }

        return reduction;
    }

    @Override
    public Reduction modifier(Reduction reduction) {
        String sql = "UPDATE Reductions SET hebergement_id = ?, pourcentage_reduction = ?, description = ?, date_debut = ?, date_fin = ? WHERE reduction_id = ?";

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(sql);

            pstmt.setInt(1, reduction.getHebergementId());
            pstmt.setInt(2, reduction.getPourcentage());
            pstmt.setString(3, reduction.getDescription());
            pstmt.setDate(4, reduction.getDateDebut());
            pstmt.setDate(5, reduction.getDateFin());
            pstmt.setInt(6, reduction.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la réduction.");
        }

        return reduction;
    }

    @Override
    public void supprimer(Reduction reduction) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM Reductions WHERE reduction_id = ?");
            pstmt.setInt(1, reduction.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la réduction.");
        }
    }
}
