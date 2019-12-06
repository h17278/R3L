package hei.devweb.projetit.dao;

import hei.devweb.projetit.entities.Utilisateur;

import java.util.List;

public interface UtilisateurDao {

    public Utilisateur getUtilisateur(Integer id);

    public Utilisateur addUtilisateur(Utilisateur user);

    public List<Utilisateur> listUtilisateur();

    public void setPassword (String pseudo, String newPassword);

    public void deleteUtilisateur(Integer id);

    public void isPres(Integer id);
}
