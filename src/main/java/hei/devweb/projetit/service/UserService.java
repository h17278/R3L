package hei.devweb.projetit.service;

import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PasswordDontMatchException;
import hei.devweb.projetit.exception.PseudoAlreadyExistException;
import hei.devweb.projetit.exception.UtilisateurNotFoundException;

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


    private UtilisateurDao utilisateurDao = new UtilisateurDaoImpl();

    public List<Utilisateur> listUtilisateur(){
        return utilisateurDao.listUtilisateur();
    }

    public void setPassword(String pseudo, String newPassword) { utilisateurDao.setPassword(pseudo, newPassword); }

    public Utilisateur getUser(Integer userid){
        if(userid == null){
            throw new IllegalArgumentException("Utilisateur  id est nul");
        }
        return utilisateurDao.getUtilisateur(userid);
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

        return utilisateurDao.addUtilisateur(utilisateur);

    }

    public void deleteUser(Integer userId) throws UtilisateurNotFoundException {
        if (utilisateurDao.getUtilisateur(userId) != null) {
            utilisateurDao.deleteUtilisateur(userId);
        }else{
            throw new UtilisateurNotFoundException();
        }
    }

    public void updateUser(Integer id) throws UtilisateurNotFoundException{
        if (utilisateurDao.getUtilisateur(id) != null) {
            utilisateurDao.isPres(id);
        }else{
            throw new UtilisateurNotFoundException();
        }
    }

    public void pseudoAlreadyExist(String pseudo) throws PseudoAlreadyExistException { utilisateurDao.pseudoAlreadyExist(pseudo);}

    public  void passwordMatch(String password1, String password2) throws PasswordDontMatchException { utilisateurDao.passwordMatch(password1, password2);}
}
