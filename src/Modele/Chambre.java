package Modele;

public class Chambre {
    private int idChambre;
    private int hebergementId;
    private int placeMax;
    private boolean dispo;

    public Chambre(int idChambre, int hebergementId, int placeMax, boolean dispo) {
        this.idChambre = idChambre;
        this.hebergementId = hebergementId;
        this.placeMax = placeMax;
        this.dispo = dispo;
    }

    public Chambre(int hebergementId, int placeMax, boolean dispo) {
        this.hebergementId = hebergementId;
        this.placeMax = placeMax;
        this.dispo = dispo;
    }

    public int getId() {
        return idChambre;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public int getHebergementId() {
        return hebergementId;
    }

    public void setHebergementId(int hebergementId) {
        this.hebergementId = hebergementId;
    }

    public int getPlaceMax() {
        return placeMax;
    }

    public void setPlaceMax(int placeMax) {
        this.placeMax = placeMax;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "idChambre=" + idChambre +
                ", hebergementId=" + hebergementId +
                ", placeMax=" + placeMax +
                '}';
    }
}
