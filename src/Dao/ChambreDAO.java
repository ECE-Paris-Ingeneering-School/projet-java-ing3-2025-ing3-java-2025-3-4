package Dao;

import Modele.Chambre;
import java.util.ArrayList;

public interface ChambreDAO {
    ArrayList<Chambre> getAll();
    ArrayList<Chambre> getByHebergementId(int hebergementId);
    void ajouter(Chambre chambre);
    Chambre chercher(int id);
    Chambre modifier(Chambre chambre);
    void supprimer(Chambre chambre);
}
