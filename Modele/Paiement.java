package Modele;

import java.sql.Date;

public class Paiement {
    private int paiementId;
    private int reservationId;
    private double montant;
    private Date datePaiement;
    private String statutPaiement;

    // Constructeur complet
    public Paiement(int paiementId, int reservationId, double montant, Date datePaiement, String statutPaiement) {
        this.paiementId = paiementId;
        this.reservationId = reservationId;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statutPaiement = statutPaiement;
    }

    // Constructeur sans ID (à l'insertion)
    public Paiement(int reservationId, double montant, Date datePaiement, String statutPaiement) {
        this(-1, reservationId, montant, datePaiement, statutPaiement);
    }

    // Getters & Setters
    public int getPaiementId() { return paiementId; }
    public void setPaiementId(int paiementId) { this.paiementId = paiementId; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public String getStatutPaiement() { return statutPaiement; }
    public void setStatutPaiement(String statutPaiement) { this.statutPaiement = statutPaiement; }
}
