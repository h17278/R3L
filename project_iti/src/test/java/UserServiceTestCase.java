import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.DataSourceProvider;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PasswordDontMatchException;
import hei.devweb.projetit.exception.PseudoAlreadyExistException;
import hei.devweb.projetit.exception.UtilisateurNotFoundException;
import hei.devweb.projetit.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Fail.fail;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestCase {

    private UtilisateurDao userDao = new UtilisateurDaoImpl();

    @Mock
    private UtilisateurDao userDaoMock;

    @InjectMocks
    private UserService userService;


    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("DELETE FROM club");

            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (1,'Saturne','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/p960x960/21640916_1657174690980107_4056707557393442670_o.jpg?_nc_cat=107&_nc_oc=AQmtFvKo2bu8Io52YWo6M506DGoIHquWAc0QOJJFRcvA4xzPlsV8xEssGCyyKuhlXdU&_nc_ht=scontent-cdg2-1.xx&oh=6de1c7ed2fb6440eabdbcc11b4a0045e&oe=5E47FE05')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (2,'Raid','http://www.jogging-plus.com/wp-content/uploads/2018/08/raid-hei.jpg')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (3,'Heir Force','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/21686919_122546058488971_8361198402224485343_o.jpg?_nc_cat=104&_nc_oc=AQlijV4NX_-GdyvppSP7SjcShi4EDHqRTwCPvmMISFuqGXx6ALsn-dcvZbodbulvUFU&_nc_ht=scontent-cdg2-1.xx&oh=84393db2015cddfab0feb9e7fbd1c1b6&oe=5E44D55B')");

            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (1,'iktro','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','iktro@gmail.com',true,3)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (2,'samos','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','samos@gmail.com',false,2)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (3,'guerissologue','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','guerissologue@gmail.com',false,1)");

            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (1,'Afterwork Saturne',1,'2019-10-12','BDA','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/42987700_2112958885401683_6930811866838990848_o.jpg?_nc_cat=105&_nc_oc=AQkGiI5Uk2ChVoYVqvzu--yf8IL3vxr4sPnJhnEVxVcOnxxNC9vzv-7EdSTvmrK3iCM&_nc_ht=scontent-cdg2-1.xx&oh=f35f4bbae9554cc5b1f42ee6bedb7638&oe=5E3FA931','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/51378834_1150566275067972_6644111332268310528_o.png?_nc_cat=105&_nc_oc=AQm-q8_r0tnAKlNCQxm7qe0qWYgsVq7dTC-n5ZBZnohZsWISIhMhzFk2jY_w5ZUlKxM&_nc_ht=scontent-cdg2-1.xx&oh=761768ff234b3fc6dd62c183a66259e4&oe=5E434D60','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','BES','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/s960x960/50550859_411825099561064_3927988599986847744_o.jpg?_nc_cat=105&_nc_oc=AQkDarwe7PF9BMoBJ0PXP_7czv5ODRmJrQDIwuIMOEiCpA3qbLAOiuBKhvq5fVp1aCM&_nc_ht=scontent-cdg2-1.xx&oh=db51266e08f774051c80e94e705941cf&oe=5E86AE0D','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.')");
        }
    }

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
        // WHEN
        List<Utilisateur> users = userDao.listUtilisateur();
        // THEN
        assertThat(users).hasSize(3);
        assertThat(users).extracting(
                Utilisateur::getIdutilisateur,
                Utilisateur::getPseudo,
                Utilisateur::getMotdepasse,
                Utilisateur::getMail,
                Utilisateur::getPresident,
                Utilisateur::getClub).containsOnly(
                tuple(1,"iktro","$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ","iktro@gmail.com",true,3),
                tuple(2,"samos","$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ","samos@gmail.com",false,2),
                tuple(3,"guerissologue","$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ","guerissologue@gmail.com",false,1)
        );
    }

    @Test
    public void shouldAddUser() throws Exception {
        //GIVEN
        Utilisateur userToCreate = new Utilisateur(null,"pseudo_test","mdp_test","mail@mail.com",true,1);
        //WHEN
        Utilisateur userCreated = userDao.addUtilisateur(userToCreate);
        // THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM utilisateur WHERE utilisateur_id = ?")) {
            stmt.setInt(1, userCreated.getIdutilisateur());
            try (ResultSet rs = stmt.executeQuery()) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("utilisateur_id")).isEqualTo(userCreated.getIdutilisateur());
                assertThat(rs.getString("pseudo")).isEqualTo("pseudo_test");
                assertThat(rs.getString("motdepasse")).isEqualTo("mdp_test");
                assertThat(rs.getString("mail")).isEqualTo("mail@mail.com");
                assertThat(rs.getBoolean("president")).isEqualTo(true);
                assertThat(rs.getInt("club_id")).isEqualTo(1);
                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shouldDeleteUser() { //TODO
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
    public void shouldUdpateUtilisateur(){ //TODO
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
        //WHEN
        userService.pseudoAlreadyExist(pseudo);
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




    @After
    public void restoreDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("DELETE FROM club");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (1,'Saturne','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/p960x960/21640916_1657174690980107_4056707557393442670_o.jpg?_nc_cat=107&_nc_oc=AQmtFvKo2bu8Io52YWo6M506DGoIHquWAc0QOJJFRcvA4xzPlsV8xEssGCyyKuhlXdU&_nc_ht=scontent-cdg2-1.xx&oh=6de1c7ed2fb6440eabdbcc11b4a0045e&oe=5E47FE05')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (2,'Raid','http://www.jogging-plus.com/wp-content/uploads/2018/08/raid-hei.jpg')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (3,'Heir Force','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/21686919_122546058488971_8361198402224485343_o.jpg?_nc_cat=104&_nc_oc=AQlijV4NX_-GdyvppSP7SjcShi4EDHqRTwCPvmMISFuqGXx6ALsn-dcvZbodbulvUFU&_nc_ht=scontent-cdg2-1.xx&oh=84393db2015cddfab0feb9e7fbd1c1b6&oe=5E44D55B')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (4,'Declic','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/p960x960/60919812_662684917509379_5031245151482150912_o.jpg?_nc_cat=101&_nc_oc=AQmGh_oV65OFGQNy44kIvpkTgjAy9lj6gT62sICyTHwAu4yhZ5AbqPRsEB-VE4m4kmo&_nc_ht=scontent-cdg2-1.xx&oh=22361b0ba3625cc93224fc488be271de&oe=5E4988A7')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (5,'Amaryllis','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/70362318_2451777885056148_5361360340762230784_n.jpg?_nc_cat=107&_nc_oc=AQlJpsXBDLOPsl8scseeUYEUr-P3fW9ecUTmjifwMI0HirQFTDBqby3KvXj-sbW--T0&_nc_ht=scontent-cdg2-1.xx&oh=09a17f815c12b9cbe17b662f17ec8744&oe=5E5928AA')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (6,'Citarun','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/p960x960/61703368_2272402502838971_5764805821674291200_o.jpg?_nc_cat=100&_nc_oc=AQlZoDI52xm9Czkc3bLr1fEO_Xm9ircss2poQ5s5_lDKC3bsSIKkUIlLyQk4HE8w-lw&_nc_ht=scontent-cdg2-1.xx&oh=2b6b05be2d2d1c5eebe70d66b7db1615&oe=5E556308')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (7,'Inntrheigers','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/p960x960/69456251_666344297109860_3988740534431645696_o.jpg?_nc_cat=103&_nc_oc=AQnaDshma_DoKi60UheRlzBZlxMsF2lzOR33maMdVreYjL601qtGriRVbMZEDJSSaG0&_nc_ht=scontent-cdg2-1.xx&oh=5af22ec93256647704664eb94ab6083d&oe=5E511345')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (8,'Athle HEI','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/70090523_1248015648692051_7132180377719799808_o.jpg?_nc_cat=104&_nc_oc=AQnEjQ2yMsLie2u7T1E_7_YrBGCJWildRbizH1OOGk2PAHRAB4tSD0c_0xvukfocsr4&_nc_ht=scontent-cdg2-1.xx&oh=dd109ec1dc65b09d213a54d0dc1f86a6&oe=5E5863C7')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (9,'Teddy Cap Solidaire','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/74685205_1360080374158531_3532478758348914688_n.jpg?_nc_cat=101&_nc_oc=AQkkvYbDjarNL58D3XEEeFEL486JLwusfUGs21jSx5Q89hZxaGnYc4i3rVGId86B2oo&_nc_ht=scontent-cdg2-1.xx&oh=77c272934ab1183547e3fb8727ea06b5&oe=5E555F10')");

            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (1,'iktro','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','iktro@gmail.com',true,3)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (2,'samos','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','samos@gmail.com',false,2)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (3,'guerissologue','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','guerissologue@gmail.com',false,1)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (4,'gautrob','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','gautrob@gmail.com',true,2)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (5,'palpal','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','palpal@gmail.com',true,8)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (6,'polo','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','polo@gmail.com',false,8)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (7,'brieuc','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','brieuc@gmail.com',false,2)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (8,'guigui','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','guigui@gmail.com',false,2)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (9,'hugo','$argon2i$v=19$m=65536,t=5,p=1$4PX9TnEJf923OAmVQClDxJRJsz9pzk8d+L6NF26ZAELraQywpXHHJ9LMIVq5XI4ZbTNt9c2LcDU+B0L7+Yj81HApGXhuHgo0yOcVsidjxuG3rHYHi7Zi+x/59kilOmHUeHPNPqpd4UdxdqIhhlYcAeGSFiO3ENgYhIqyfT16kbY$uP4orIdOlXnKj2Wrn2Sacvp2MYw8puFhtIfeA0NS/ZO4DrB0DC1u8wGteaK9zzUEvLR0OfyYT9kewEshiqSfdhYpFHBxr5iIME1OF524gJXHD5rquYHdQ1/M5W8tINh55RyK+NPBA52PCjloWGGR24QSP4aViiVHucsWsgFU7HQ','hugo@gmail.com',true,1)");

            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (1,'Afterwork Saturne',1,'2019-10-12','BDA','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/42987700_2112958885401683_6930811866838990848_o.jpg?_nc_cat=105&_nc_oc=AQkGiI5Uk2ChVoYVqvzu--yf8IL3vxr4sPnJhnEVxVcOnxxNC9vzv-7EdSTvmrK3iCM&_nc_ht=scontent-cdg2-1.xx&oh=f35f4bbae9554cc5b1f42ee6bedb7638&oe=5E3FA931','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/51378834_1150566275067972_6644111332268310528_o.png?_nc_cat=105&_nc_oc=AQm-q8_r0tnAKlNCQxm7qe0qWYgsVq7dTC-n5ZBZnohZsWISIhMhzFk2jY_w5ZUlKxM&_nc_ht=scontent-cdg2-1.xx&oh=761768ff234b3fc6dd62c183a66259e4&oe=5E434D60','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','BES','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/s960x960/50550859_411825099561064_3927988599986847744_o.jpg?_nc_cat=105&_nc_oc=AQkDarwe7PF9BMoBJ0PXP_7czv5ODRmJrQDIwuIMOEiCpA3qbLAOiuBKhvq5fVp1aCM&_nc_ht=scontent-cdg2-1.xx&oh=db51266e08f774051c80e94e705941cf&oe=5E86AE0D','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (4,'Afterwork Amaryllis',5,'2019-11-25','CAP','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/74834470_2512420778991858_3406853889813643264_n.jpg?_nc_cat=106&_nc_oc=AQndrqx7Eq3c9MxV9uUE3GLu3wVeUB2ZHeafO8Bjn3vHbAusK4m56vFj81niYEez90o&_nc_ht=scontent-cdg2-1.xx&oh=3707642886c35c916131839bb0524a9b&oe=5E402280','Afterwork à la faluche','Détails de l évènement')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (5,'Afterwork Raid',2,'2020-02-05','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-0/p526x296/50679492_673308973083636_2275619990071672832_n.jpg?_nc_cat=110&_nc_oc=AQlm236IhajJhLTAuI0QpnfmyPskKOR6KC3iPk3mMAGw4N5YpYvtwfqWflRfoRALGhc&_nc_ht=scontent-cdg2-1.xx&oh=582e80ca7ae0a00022b5e0d01687f511&oe=5E533CD3','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.','Détails de l evenement')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (6,'Citarun HEI ',6,'2020-03-21','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/61911171_2272392872839934_5444503232506232832_o.jpg?_nc_cat=104&_nc_oc=AQkLwiUr9iFwPXlz2UfamkDGMvtFVQ9O5MlVkJOlPKpgKi7BjdCsuDCHshoEd1u1bEU&_nc_ht=scontent-cdg2-1.xx&oh=3ed296bec2d18c44026f26c422af72c3&oe=5E4A8140','Course de 5, 10 ou 21km.','Viens courir le plus vite possible à la citadelle sur ta distance préférée')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (7,'Challenges BDS',7,'2019-12-10','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/76710712_1228060337379012_4900108895013306368_n.jpg?_nc_cat=100&_nc_oc=AQlv2OBs-l8XhIzpqLwuyDZBse7DxlH_zZkDEEppQNNTGNhFmE4T6bJdJ6fy9R7u1-s&_nc_ht=scontent-cdg2-1.xx&oh=bc86427c6013fdf566549a4a96747d4b&oe=5E527FD0','Multiple challenge sur pendant l année','Choisis ton challenge préféré')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (8,'Challenge bowling',7,'2019-12-25','BDS','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/74982766_1228060420712337_242547690947215360_n.jpg?_nc_cat=103&_nc_oc=AQl6vZhFmEytilX8p7Kfp_lDRSLyKvDpt6IgaKqi0NUShrVC-HoqphZxRSRF8eCyXeo&_nc_ht=scontent-cdg2-1.xx&oh=f45d7d82839b64185b7f62225d6a03fa&oe=5E863CF8','Viens jouer au bowling','Tu vas t amuser entre potes !')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`bureau`,`image_link`,`resume`,`details`) VALUES (9,'Le Movember de Teddy',9,'2019-12-25','CAP','https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/75375700_1360075290825706_6648652826392133632_o.jpg?_nc_cat=101&_nc_oc=AQmExwhOIiDBAZ7FeusTR5xXZhV5u0lvgEvFa3MZDwl2_GfdPbuyaJ4ws6kKuP2sLdw&_nc_ht=scontent-cdg2-1.xx&oh=e6c7c7e4def5ea7118cc48a5008c4e1b&oe=5E888A3A','Arbore ta plus belle moustache','C est simple non ?')");

        }
    }
}
