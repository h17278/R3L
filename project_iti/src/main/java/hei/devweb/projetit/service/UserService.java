package hei.devweb.projetit.service;

import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Utilisateur;

import java.util.List;

public class UserService {

    private static class UserServiceHolder {
        private final static UserService instance = new UserService() ;
    }

    public static UserService getInstance(){
        return UserServiceHolder.instance;
    }

    private UserService(){
    }

    private UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();

    public List<Utilisateur> listUtilisateur(){
        return utilisateurDaoImpl.listUtilisateur();
    }

    public Utilisateur getUser(Integer userid){
        if(userid == null){
            throw new IllegalArgumentException("Utilisateur  id est nul");
        }
        return utilisateurDaoImpl.getUtilisateur(userid);
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur){
        if(utilisateur == null){
            throw new IllegalArgumentException("Pas d'utilisateurs");
        }
        if (utilisateur.getPseudo() == null || "".equals(utilisateur.getPseudo())) {
            throw new IllegalArgumentException("Pseudo utilisateur null.");
        }
        if (utilisateur.getMail() == null || "".equals(utilisateur.getMail())) {
            throw new IllegalArgumentException("Mail utilisateur null.");
        }
        if (utilisateur.getMotdepasse() == null || "".equals(utilisateur.getMotdepasse())) {
            throw new IllegalArgumentException("Mot de passe utilisateur null.");
        }

        return utilisateurDaoImpl.addUtilisateur(utilisateur);

    }

}
