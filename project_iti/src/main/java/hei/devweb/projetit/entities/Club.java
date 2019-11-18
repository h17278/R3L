package hei.devweb.projetit.entities;

public class Club {

    private Integer id;
    private String name;
    private String lien;

    public Club(Integer id, String name, String lien) {
        this.id = id;
        this.name = name;
        this.lien = lien;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public void setId(Integer id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}
