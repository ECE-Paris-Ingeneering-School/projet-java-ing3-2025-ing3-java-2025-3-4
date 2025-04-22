package Modele;

public class Option {
    private int id;
    private String nomOption;

    public Option(int id, String nomOption) {
        this.id = id;
        this.nomOption = nomOption;
    }

    public Option(String nomOption) {
        this.nomOption = nomOption;
    }

    public int getId() {
        return id;
    }

    public String getNomOption() {
        return nomOption;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomOption(String nomOption) {
        this.nomOption = nomOption;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", nomOption='" + nomOption + '\'' +
                '}';
    }
}
