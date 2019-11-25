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

}
