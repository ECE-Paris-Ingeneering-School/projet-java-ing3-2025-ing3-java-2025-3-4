package Dao;

import Modele.Chambre;

import java.sql.*;
import java.util.ArrayList;

public class ChambreDAOImpl implements ChambreDAO {
    private DaoFactory daoFactory;

    public ChambreDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Chambre> getAll() {
        ArrayList<Chambre> chambres = new ArrayList<>();
        try {
            Connection conn = daoFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chambre");

            while (rs.next()) {
                chambres.add(new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getInt("hebergement_id"),
                        rs.getInt("place_max")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

    @Override
    public ArrayList<Chambre> getByHebergementId(int hebergementId) {
        ArrayList<Chambre> chambres = new ArrayList<>();
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM chambre WHERE hebergement_id = ?");
            ps.setInt(1, hebergementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chambres.add(new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getInt("hebergement_id"),
                        rs.getInt("place_max")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

    @Override
    public void ajouter(Chambre chambre) {
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO chambre (hebergement_id, place_max) VALUES (?, ?)");
            ps.setInt(1, chambre.getHebergementId());
            ps.setInt(2, chambre.getPlaceMax());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Chambre chercher(int id) {
        Chambre chambre = null;
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM chambre WHERE id_chambre = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                chambre = new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getInt("hebergement_id"),
                        rs.getInt("place_max")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambre;
    }

    @Override
    public Chambre modifier(Chambre chambre) {
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE chambre SET hebergement_id = ?, place_max = ? WHERE id_chambre = ?");
            ps.setInt(1, chambre.getHebergementId());
            ps.setInt(2, chambre.getPlaceMax());
            ps.setInt(3, chambre.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambre;
    }

    @Override
    public void supprimer(Chambre chambre) {
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM chambre WHERE id_chambre = ?");
            ps.setInt(1, chambre.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mettreAJourDisponibilite(int idChambre, boolean disponible) {
        String sql = "UPDATE chambre SET dispo = ? WHERE id_chambre = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, idChambre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
