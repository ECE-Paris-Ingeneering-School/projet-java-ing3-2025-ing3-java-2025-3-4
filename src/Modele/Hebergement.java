package Modele;

public class Hebergement {
    private int id;
    private String nom;
    private String description;
    private String adresse;
    private String ville;
    private String pays;
    private double prixParNuit;
    private String categorie; // <- anciennement 'type'

    public Hebergement(int id, String nom, String description, String adresse, String ville, String pays, double prixParNuit, String categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
        this.prixParNuit = prixParNuit;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public String getPays() { return pays; }
    public double getPrixParNuit() { return prixParNuit; }
    public String getCategorie() { return categorie; }

    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setVille(String ville) { this.ville = ville; }
    public void setPays(String pays) { this.pays = pays; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
}
