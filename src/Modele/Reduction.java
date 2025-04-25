package Modele;

import java.sql.Date;

public class Reduction {
    private int id;
    private int hebergementId;
    private int pourcentage;
    private String description;
    private Date dateDebut;
    private Date dateFin;

    public Reduction(int id, int hebergementId, int pourcentage, String description, Date dateDebut, Date dateFin) {
        this.id = id;
        this.hebergementId = hebergementId;
        this.pourcentage = pourcentage;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reduction(int hebergementId, int pourcentage, String description, Date dateDebut, Date dateFin) {
        this.hebergementId = hebergementId;
        this.pourcentage = pourcentage;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reduction(int hebergementId, int pourcentage) {
        this.hebergementId = hebergementId;
        this.pourcentage = pourcentage;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public int getHebergementId() {
        return hebergementId;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHebergementId(int hebergementId) {
        this.hebergementId = hebergementId;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
