import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.DataSourceProvider;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PasswordDontMatchException;
import hei.devweb.projetit.exception.PseudoAlreadyExistException;
import hei.devweb.projetit.exception.UtilisateurNotFoundException;
import hei.devweb.projetit.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestCase {

    private UtilisateurDao userDao = new UtilisateurDaoImpl();

    @Mock
    private UtilisateurDao userDaoMock;

    @InjectMocks
    private UserService userService;


    @Test
    public void shouldGetUser() {
        // WHEN
        Utilisateur utilisateur = userDao.getUtilisateur(1);
        // THEN
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getIdutilisateur()).isEqualTo(1);
        assertThat(utilisateur.getPseudo()).isEqualTo("iktro");
        assertThat(utilisateur.getMotdepasse()).isEqualTo("$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ");
        assertThat(utilisateur.getMail()).isEqualTo("iktro@gmail.com");
        assertThat(utilisateur.getPresident()).isEqualTo(true);
        assertThat(utilisateur.getClub()).isEqualTo(3);
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

    @Test(expected = UtilisateurNotFoundException.class)
    public void shouldDeleteUser()throws UtilisateurNotFoundException{
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

    @Test(expected = UtilisateurNotFoundException.class)
    public void shouldUdpateUtilisateur() throws UtilisateurNotFoundException{
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
        fail("PasswordDontMatchException");
        //THEN
        fail("PseudoAlreadyExistException");
    }

    @Test
    public void shouldPasswordMatch(){
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
    public void shouldSetPassword(){
        //GIVEN
        String pseudo = "Gautier";
        String newPassword = "viveLeRaid";
        //WHEN
        userService.setPassword(pseudo,newPassword);
        //THEN
        Mockito.verify(userDaoMock,Mockito.times(1)).setPassword(pseudo,newPassword);
    }

}
