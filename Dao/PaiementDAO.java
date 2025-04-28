package Dao;

import Modele.Paiement;
import java.util.ArrayList;

public interface PaiementDAO {
    void ajouter(Paiement paiement);
    Paiement chercherParId(int paiementId);
    ArrayList<Paiement> getAll();
    ArrayList<Paiement> getParReservation(int reservationId);
}
