package Modele;

import java.sql.Date;

public class Reservation {
    private int reservationId;
    private int utilisateurId;
    private int hebergementId;
    private Date dateArrivee;
    private Date dateDepart;
    private int adultes;
    private int enfants;
    private double prixTotal;
    private String statut;
    private Date dateCreation;

    // Constructeur complet
    public Reservation(int reservationId, int utilisateurId, int hebergementId,
                       Date dateArrivee, Date dateDepart, int adultes,
                       int enfants, double prixTotal, String statut,
                       Date dateCreation) {
        this.reservationId = reservationId;
        this.utilisateurId = utilisateurId;
        this.hebergementId = hebergementId;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.adultes = adultes;
        this.enfants = enfants;
        this.prixTotal = prixTotal;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }

    // Constructeur simplifié pour création nouvelle réservation
    public Reservation(int utilisateurId, int hebergementId,
                       Date dateArrivee, Date dateDepart, int adultes,
                       int enfants, double prixTotal) {
        this(0, utilisateurId, hebergementId, dateArrivee, dateDepart,
                adultes, enfants, prixTotal, "en_attente", null);
    }

    // Getters
    public int getReservationId() { return reservationId; }
    public int getUtilisateurId() { return utilisateurId; }
    public int getHebergementId() { return hebergementId; }
    public Date getDateArrivee() { return dateArrivee; }
    public Date getDateDepart() { return dateDepart; }
    public int getAdultes() { return adultes; }
    public int getEnfants() { return enfants; }
    public double getPrixTotal() { return prixTotal; }
    public String getStatut() { return statut; }
    public Date getDateCreation() { return dateCreation; }

    // Setters
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public void setStatut(String statut) { this.statut = statut; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
    // Ajoutez d'autres setters si nécessaire
}