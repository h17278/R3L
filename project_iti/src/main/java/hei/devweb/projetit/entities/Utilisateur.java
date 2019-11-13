package hei.devweb.projetit.entities;

public class Utilisateur {

    Integer idutilisateur;
    String pseudo;
    String motdepasse;
    String mail;
    Boolean president;
    Integer idclub;

    public Utilisateur(Integer idutilisateur, String pseudo, String motdepasse, String mail, Boolean president, Integer idclub) {
        this.idutilisateur = idutilisateur;
        this.pseudo = pseudo;
        this.motdepasse = motdepasse;
        this.mail = mail;
        this.president = president;
        this.idclub = idclub;
    }

    public Integer getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getPresident() {
        return president;
    }

    public void setPresident(Boolean president) {
        this.president = president;
    }

    public Integer getIdclub() {
        return idclub;
    }

    public void setIdclub(Integer idclub) {
        this.idclub = idclub;
    }
}
