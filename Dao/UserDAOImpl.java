package Dao;

import Modele.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    private DaoFactory daoFactory;

    public UserDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listeUsers = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ///  voir classe ResultSet ??
            ResultSet resultats = statement.executeQuery("SELECT * FROM Utilisateurs");

            while (resultats.next()) {
                int id = resultats.getInt("utilisateur_id");
                String prenom = resultats.getString("prenom");
                String nom = resultats.getString("nom");
                String email = resultats.getString("email");
                String motDePasse = resultats.getString("mot_de_passe");
                String typeUtilisateur = resultats.getString("type_utilisateur");
                Timestamp dateCreation = resultats.getTimestamp("date_creation");

                User user = new User(id, prenom, nom, email, motDePasse, typeUtilisateur, dateCreation);
                listeUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des utilisateurs.");
        }

        return listeUsers;
    }

    @Override
    public void ajouter(User user) {
        String sql = "INSERT INTO Utilisateurs (prenom, nom, email, mot_de_passe, type_utilisateur) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connexion = daoFactory.getConnection();
            ///  changer nom variable
            PreparedStatement pstmt = connexion.prepareStatement(sql);

            pstmt.setString(1, user.getPrenom());
            pstmt.setString(2, user.getNom());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getMotDePasse());
            pstmt.setString(5, user.getTypeUtilisateur());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout d'un utilisateur.");
        }
    }



    public User chercher(int id) {
        User user = null;

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement("SELECT * FROM Utilisateurs WHERE utilisateur_id = ?");
            pstmt.setInt(1, id);

            ResultSet resultats = pstmt.executeQuery();

            if (resultats.next()) {
                user = new User(
                        resultats.getInt("utilisateur_id"),
                        resultats.getString("prenom"),
                        resultats.getString("nom"),
                        resultats.getString("email"),
                        resultats.getString("mot_de_passe"),
                        resultats.getString("type_utilisateur"),
                        resultats.getTimestamp("date_creation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Utilisateur non trouvé.");
        }

        return user;
    }

    public User chercher(String email, String motDePasse) {
        User user = null;

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(
                    "SELECT * FROM Utilisateurs WHERE email = ? AND mot_de_passe = ?"
            );
            pstmt.setString(1, email.trim());
            pstmt.setString(2, motDePasse.trim());

            ResultSet resultats = pstmt.executeQuery();

            if (resultats.next()) {
                user = new User(
                        resultats.getInt("utilisateur_id"),
                        resultats.getString("prenom"),
                        resultats.getString("nom"),
                        resultats.getString("email"),
                        resultats.getString("mot_de_passe"),
                        resultats.getString("type_utilisateur"),
                        resultats.getTimestamp("date_creation")
                );
                System.out.println("Utilisateur trouvé : " + user.getEmail());
            } else {
                System.out.println("Aucun utilisateur trouvé avec ces identifiants.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur SQL lors de la recherche de l'utilisateur .");
        }

        return user;
    }

    @Override
    public User modifier(User user) {
        String sql = "UPDATE Utilisateurs SET prenom = ?, nom = ?, email = ?, mot_de_passe = ?, type_utilisateur = ? WHERE utilisateur_id = ?";

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(sql);

            pstmt.setString(1, user.getPrenom());
            pstmt.setString(2, user.getNom());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getMotDePasse());
            pstmt.setString(5, user.getTypeUtilisateur());
            pstmt.setInt(6, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de l'utilisateur.");
        }

        return user;
    }

    @Override
    public void supprimer(User user) {
        try {
            Connection connexion = daoFactory.getConnection();

            // Supprimer les réservations liées à cet utilisateur
            PreparedStatement psRes = connexion.prepareStatement("DELETE FROM Reservations WHERE utilisateur_id = ?");
            psRes.setInt(1, user.getId());
            psRes.executeUpdate();

            // Supprimer l'utilisateur
            PreparedStatement psUser = connexion.prepareStatement("DELETE FROM Utilisateurs WHERE utilisateur_id = ?");
            psUser.setInt(1, user.getId());
            psUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'utilisateur.");
        }
    }

    public void updateTypeUtilisateur(int userId, String nouveauType) {
        String sql = "UPDATE Utilisateurs SET type_utilisateur = ? WHERE utilisateur_id = ?";

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement pstmt = connexion.prepareStatement(sql);

            pstmt.setString(1, nouveauType);
            pstmt.setInt(2, userId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Type d'utilisateur mis à jour avec succès pour l'utilisateur ID : " + userId);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du type d'utilisateur.");
        }
    }

    public ArrayList<User> getAllNonAdmins() {
        ArrayList<User> listeUsers = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("SELECT * FROM Utilisateurs WHERE type_utilisateur != 'admin'");

            while (resultats.next()) {
                int id = resultats.getInt("utilisateur_id");
                String prenom = resultats.getString("prenom");
                String nom = resultats.getString("nom");
                String email = resultats.getString("email");
                String motDePasse = resultats.getString("mot_de_passe");
                String typeUtilisateur = resultats.getString("type_utilisateur");
                Timestamp dateCreation = resultats.getTimestamp("date_creation");

                User user = new User(id, prenom, nom, email, motDePasse, typeUtilisateur, dateCreation);
                listeUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des utilisateurs non-admins.");
        }

        return listeUsers;
    }

}
