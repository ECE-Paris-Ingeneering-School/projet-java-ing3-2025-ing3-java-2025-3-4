package Modele;

public class Hebergement {
    private int id;
    private String nom;
    private String description;
    private String adresse;
    private String ville;
    private String pays;
    private double prixParNuit;
    private String categorie;
    private double noteMoyenne;
    private int etoiles;
    private String photo;

    public Hebergement(int id, String nom, String description, String adresse, String ville, String pays, double prixParNuit, String categorie, String photo) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
        this.prixParNuit = prixParNuit;
        this.categorie = categorie;
        this.photo = photo;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public String getPays() { return pays; }
    public double getPrixParNuit() { return prixParNuit; }
    public String getCategorie() { return categorie; }
    public double getNoteMoyenne() { return noteMoyenne; }
    public int getEtoiles() { return etoiles; }
    public String getPhoto() { return photo; }

    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setVille(String ville) { this.ville = ville; }
    public void setPays(String pays) { this.pays = pays; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setNoteMoyenne(double noteMoyenne) { this.noteMoyenne = noteMoyenne; }
    public void setEtoiles(Integer etoiles) { this.etoiles = etoiles; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String toString(){
        return nom + "(" + ville +")";
    }
}
