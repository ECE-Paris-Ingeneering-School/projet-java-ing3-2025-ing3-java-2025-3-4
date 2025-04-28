package Dao;

import Modele.Avis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AvisDAOImpl {
    private DaoFactory daoFactory;

    public AvisDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void ajouterAvis(Avis avis) {
        String sql = "INSERT INTO Avis (utilisateur_id, hebergement_id, note, commentaire, date_creation) VALUES (?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(sql)) {

            stmt.setInt(1, avis.getUtilisateurId());
            stmt.setInt(2, avis.getHebergementId());
            stmt.setInt(3, avis.getNote());
            stmt.setString(4, avis.getCommentaire());
            stmt.setDate(5, avis.getDateCreation());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }
}
