package Dao;

import Modele.Option;
import java.sql.*;
import java.util.ArrayList;

public class OptionDAOImpl implements OptionDAO {
    private DaoFactory daoFactory;

    public OptionDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Option> getAll() {
        ArrayList<Option> options = new ArrayList<>();

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet resultats = statement.executeQuery("SELECT * FROM Options")) {

            while (resultats.next()) {
                Option o = new Option(
                        resultats.getInt("option_id"),
                        resultats.getString("nom_option")
                );
                options.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Impossible de récupérer la liste des options");
        }

        return options;
    }

    @Override
    public void ajouter(Option option) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("INSERT INTO Options (nom_option) VALUES (?)")) {

            ps.setString(1, option.getNomOption());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'option impossible");
        }
    }

    @Override
    public Option chercher(int id) {
        Option option = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT * FROM Options WHERE option_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                option = new Option(rs.getInt("option_id"), rs.getString("nom_option"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Option non trouvée");
        }

        return option;
    }

    @Override
    public Option modifier(Option option) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("UPDATE Options SET nom_option = ? WHERE option_id = ?")) {

            ps.setString(1, option.getNomOption());
            ps.setInt(2, option.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de l'option impossible");
        }

        return option;
    }

    @Override
    public void supprimer(Option option) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("DELETE FROM Options WHERE option_id = ?")) {

            ps.setInt(1, option.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de l'option impossible");
        }
    }

    @Override
    public void attribuerOptionAHebergement(int hebergementId, int optionId) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("INSERT INTO HebergementOptions (hebergement_id, option_id) VALUES (?, ?)")) {

            ps.setInt(1, hebergementId);
            ps.setInt(2, optionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Attribution de l'option au logement impossible");
        }
    }

    @Override
    public void retirerOptionAHebergement(int hebergementId, int optionId) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("DELETE FROM HebergementOptions WHERE hebergement_id = ? AND option_id = ?")) {

            ps.setInt(1, hebergementId);
            ps.setInt(2, optionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de l'option du logement impossible");
        }
    }
}
