package Dao;

import Modele.Reduction;
import java.util.ArrayList;

public interface ReductionDAO {
    ArrayList<Reduction> getAll();
    void ajouter(Reduction reduction);
    Reduction chercher(int id);
    void supprimer(Reduction reduction);
    Reduction modifier(Reduction reduction);
}
