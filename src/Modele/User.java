package Modele;

import java.sql.Timestamp;

public class User {
        private int id;
        private String prenom;
        private String nom;
        private String email;
        private String motDePasse;
        private String typeUtilisateur;
        private Timestamp dateCreation;

        public User(String email, String motDePasse, String typeUtilisateur){
                this.email = email;
                this.motDePasse = motDePasse;
                this.typeUtilisateur = typeUtilisateur;
        }

        // Constructeur complet
        public User(int id, String prenom, String nom, String email, String motDePasse, String typeUtilisateur, Timestamp dateCreation) {
                this.id = id;
                this.prenom = prenom;
                this.nom = nom;
                this.email = email;
                this.motDePasse = motDePasse;
                this.typeUtilisateur = typeUtilisateur;
                this.dateCreation = dateCreation;
        }

        // Constructeur sans id ni date_creation (ex: pour ajout)
        public User(String prenom, String nom, String email, String motDePasse, String typeUtilisateur) {
                this.prenom = prenom;
                this.nom = nom;
                this.email = email;
                this.motDePasse = motDePasse;
                this.typeUtilisateur = typeUtilisateur;
        }

        // Getters
        public int getId() { return id; }
        public String getPrenom() { return prenom; }
        public String getNom() { return nom; }
        public String getEmail() { return email; }
        public String getMotDePasse() { return motDePasse; }
        public String getTypeUtilisateur() { return typeUtilisateur; }
        public Timestamp getDateCreation() { return dateCreation; }

        // Setters
        public void setId(int id) { this.id = id; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public void setNom(String nom) { this.nom = nom; }
        public void setEmail(String email) { this.email = email; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
        public void setTypeUtilisateur(String typeUtilisateur) { this.typeUtilisateur = typeUtilisateur; }
        public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }
}
