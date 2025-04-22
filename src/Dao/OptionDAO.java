package Dao;

import Modele.Option;
import java.util.ArrayList;

public interface OptionDAO {
    public ArrayList<Option> getAll();
    public void ajouter(Option option);
    public Option chercher(int id);
    public Option modifier(Option option);
    public void supprimer(Option option);

    public void attribuerOptionAHebergement(int hebergementId, int optionId);
    public void retirerOptionAHebergement(int hebergementId, int optionId);
}
