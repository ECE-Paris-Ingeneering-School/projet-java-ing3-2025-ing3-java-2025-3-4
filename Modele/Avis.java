package Modele;

import java.sql.Date;

public class Avis {
    private int avisId;
    private int utilisateurId;
    private int hebergementId;
    private int note;
    private String commentaire;
    private Date dateCreation;

    public Avis(int avisId, int utilisateurId, int hebergementId, int note, String commentaire, Date dateCreation) {
        this.avisId = avisId;
        this.utilisateurId = utilisateurId;
        this.hebergementId = hebergementId;
        this.note = note;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
    }

    public Avis(int utilisateurId, int hebergementId, int note, String commentaire, Date dateCreation) {
        this(-1, utilisateurId, hebergementId, note, commentaire, dateCreation);
    }

    // Getters et setters
    public int getAvisId() { return avisId; }
    public int getUtilisateurId() { return utilisateurId; }
    public int getHebergementId() { return hebergementId; }
    public int getNote() { return note; }
    public String getCommentaire() { return commentaire; }
    public Date getDateCreation() { return dateCreation; }

    public void setAvisId(int avisId) { this.avisId = avisId; }
}
