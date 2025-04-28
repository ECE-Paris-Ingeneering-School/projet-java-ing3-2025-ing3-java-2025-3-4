package Dao;

import Modele.Reservation;
import java.util.ArrayList;

/**
 * Interface ReservationDAO définissant les méthodes d'accès aux données
 * de la table Reservations, indépendamment du stockage.
 */
public interface ReservationDAO {

    /**
     * Récupérer toutes les réservations de la base de données.
     * @return liste des objets Reservation.
     */
    public ArrayList<Reservation> getAll();

    /**
     * Ajouter une nouvelle réservation dans la base de données.
     * @param reservation objet de la classe Reservation à insérer.
     */
    public void ajouter(Reservation reservation);

    /**
     * Rechercher une réservation par son identifiant unique.
     * @param reservationID identifiant de la réservation.
     * @return l'objet Reservation correspondant.
     */
    public Reservation chercher(int reservationID);

    /**
     * Rechercher une réservation via les identifiants Utilisateur et Hébergement.
     * @param utilisateurID identifiant de l'utilisateur.
     * @param hebergementID identifiant de l'hébergement.
     * @return l'objet Reservation correspondant.
     */
    public Reservation chercherParUtilisateurEtHebergement(int utilisateurID, int hebergementID);

    /**
     * Modifier une réservation existante dans la base de données.
     * @param reservation objet Reservation à mettre à jour.
     * @return l'objet Reservation mis à jour.
     */
    public Reservation modifier(Reservation reservation);

    /**
     * Supprimer une réservation dans la base de données.
     * @param reservation objet Reservation à supprimer.
     */
    public void supprimer(Reservation reservation);
}
