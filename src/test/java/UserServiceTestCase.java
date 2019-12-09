import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.DataSourceProvider;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PasswordDontMatchException;
import hei.devweb.projetit.exception.PseudoAlreadyExistException;
import hei.devweb.projetit.exception.UtilisateurNotFoundException;
import hei.devweb.projetit.service.UserService;
import jdk.jshell.execution.Util;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestCase {

    @Mock
    private UtilisateurDao userDaoMock;

    @InjectMocks
    private UserService userService;


    @Test
    public void shouldGetUser() {
        //GIVEN
        Utilisateur user = new Utilisateur(1,"iktro","mdp","iktro@mail",true,3);
        Mockito.when(userDaoMock.getUtilisateur(1)).thenReturn(user);
        // WHEN
        Utilisateur result = userService.getUser(1);
        // THEN
        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldGetUserThrowIllegal(){
        //GIVEN
        Integer id = null;
        //WHEN
        userService.getUser(id);
        //THEN
        fail("IllegalArgumentException");
    }

    @Test(expected = UtilisateurNotFoundException.class)
    public void shouldGetUserThrowUtilisateurNotFound(){
        //GIVEN
        Integer id = 1;
        Mockito.when(userDaoMock.getUtilisateur(id)).thenReturn(null);
        //WHEN
        userService.getUser(id);
        //THEN
        fail("UtilisateurNotFoundException");
    }

    @Test
    public void shouldListUser() {
        //GIVEN
        List<Utilisateur> users = Arrays.asList(new Utilisateur(1,"iktro","mdp","iktro@mail",true,3), new Utilisateur(2,"samos","mdp","samos@mail",false,1));
        Mockito.when(userDaoMock.listUtilisateur()).thenReturn(users);
        // WHEN
        List<Utilisateur> result = userService.listUtilisateur();
        // THEN
        Assertions.assertThat(result).containsExactlyElementsOf(users);
    }

    @Test
    public void shouldAddUser() throws Exception {
        //GIVEN
        Utilisateur userToCreate = new Utilisateur(null,"pseudo_test","mdp_test","mail@mail.com",true,1);
        Mockito.when(userDaoMock.addUtilisateur(userToCreate)).thenReturn(userToCreate);
        //WHEN
        Utilisateur userCreated = userService.addUtilisateur(userToCreate);
        // THEN
        Assertions.assertThat(userCreated).isEqualTo(userToCreate);
    }

    @Test
    public void shouldDeleteUser()throws UtilisateurNotFoundException{ //TODO
        //GIVEN
        Integer userid = 4;
        //WHEN
        userService.deleteUser(userid);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).deleteUtilisateur(userid);

    }

    @Test(expected = UtilisateurNotFoundException.class)
    public void shouldDeleteUserNotFoundException() throws UtilisateurNotFoundException{
        //GIVEN
        Integer userid = 4;
        //WHEN
        userService.deleteUser(userid);
        //THEN
        fail("UtilisateurNotFoundException");
    }

    @Test
    public void shouldUdpateUtilisateur() throws UtilisateurNotFoundException{ //TODO
        //GIVEN
        Integer userid = 5;
        //WHEN
        userService.updateUser(userid);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).isPres(userid);
    }

    @Test(expected = UtilisateurNotFoundException.class)
    public void shouldUpdateUserNotFoundException() throws UtilisateurNotFoundException{
        //GIVEN
        Integer userid = 5;
        //WHEN
        userService.updateUser(userid);
        //THEN
        fail("UtilisateurNotFoundException");
    }

    @Test
    public void shouldPseudoAlreadyExists(){ //TODO
        //GIVEN
        String pseudo = "Victor";
        //WHEN
        userService.pseudoAlreadyExist(pseudo);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).pseudoAlreadyExist(pseudo);
    }

    @Test(expected = PseudoAlreadyExistException.class)
    public void shouldPseudoAlreadyExistsException() throws PseudoAlreadyExistException {
        //GIVEN
        String pseudo = "Victor";
        Mockito.doThrow(new PseudoAlreadyExistException()).when(userDaoMock).pseudoAlreadyExist(pseudo);
        //WHEN
        userService.pseudoAlreadyExist(pseudo);
        //THEN
        fail("PseudoAlreadyExistException");
    }

    @Test
    public void shouldPasswordMatch(){ //TODO
        //GIVEN
        String pw1 = "password1";
        String pw2 = "password2";
        //WHEN
        userService.passwordMatch(pw1,pw2);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).passwordMatch(pw1,pw2);
    }

    @Test(expected = PasswordDontMatchException.class)
    public void shouldPasswordMatchException() throws PasswordDontMatchException { //TODO
        //GIVEN
        String pw1 = "password1";
        String pw2 = "password2";
        Mockito.doThrow(new PasswordDontMatchException()).when(userDaoMock).passwordMatch(pw1,pw2);
        //WHEN
        userService.passwordMatch(pw1,pw2);
        //THEN
        fail("PasswordDontMatchException");
    }


    @Test
    public void shouldSetPassword(){ //TODO
        //GIVEN
        String pseudo = "Gautier";
        String newPassword = "viveLeRaid";
        //WHEN
        userService.setPassword(pseudo,newPassword);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).setPassword(pseudo,newPassword);
    }

}
