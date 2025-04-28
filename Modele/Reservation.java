package Modele;

import java.sql.Date;

public class Reservation {
    private int reservationId;
    private int utilisateurId;
    private int chambreId;
    private Date dateArrivee;
    private Date dateDepart;
    private int adultes;
    private int enfants;
    private double prixTotal;
    private String statut;
    private Date dateCreation;

    // Constructeur complet
    public Reservation(int reservationId, int utilisateurId, int chambreId, Date dateArrivee, Date dateDepart,
                       int adultes, int enfants, double prixTotal, String statut, Date dateCreation) {
        this.reservationId = reservationId;
        this.utilisateurId = utilisateurId;
        this.chambreId = chambreId;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.adultes = adultes;
        this.enfants = enfants;
        this.prixTotal = prixTotal;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }

    // Constructeur sans ID (à l'insertion)
    public Reservation(int utilisateurId, int chambreId, Date dateArrivee, Date dateDepart,
                       int adultes, int enfants, double prixTotal, String statut, Date dateCreation) {
        this(-1, utilisateurId, chambreId, dateArrivee, dateDepart, adultes, enfants, prixTotal, statut, dateCreation);
    }

    // Getters & Setters
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(int utilisateurId) { this.utilisateurId = utilisateurId; }


    public int getChambreId() { return chambreId; }
    public void setChambreId(int chambreId) { this.chambreId = chambreId; }

    public Date getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(Date dateArrivee) { this.dateArrivee = dateArrivee; }

    public Date getDateDepart() { return dateDepart; }
    public void setDateDepart(Date dateDepart) { this.dateDepart = dateDepart; }

    public int getAdultes() { return adultes; }
    public void setAdultes(int adultes) { this.adultes = adultes; }

    public int getEnfants() { return enfants; }
    public void setEnfants(int enfants) { this.enfants = enfants; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }


    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}
