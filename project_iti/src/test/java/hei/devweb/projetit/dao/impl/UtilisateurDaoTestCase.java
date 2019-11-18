package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.EventDao;
import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.entities.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilisateurDaoTestCase {

    private UtilisateurDao utilisateurDao = new UtilisateurDaoImpl();


    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM utilisateur");
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM club");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (1,'Saturne')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (2,'Raid')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (3,'Heir Force')");

            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (1,'iktro','sjkhgoiheiuz','iktro@gmail.com',true,3)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (2,'samos','dfvdfh57','samos@gmail.com',false,null)");
            stmt.executeUpdate("INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (3,'guerissologue','dvdifuvbb','guerissologue@gmail.com',false,null)");

            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (1,'Afterwork',1,'2019-10-12','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.')");
        }
    }

    @Test
    public void shouldGetUtilisateur() {
        // WHEN
        Utilisateur utilisateur = utilisateurDao.getUtilisateur(1);
        // THEN
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getIdutilisateur()).isEqualTo(1);
        assertThat(utilisateur.getPseudo()).isEqualTo("iktro");
        assertThat(utilisateur.getMotdepasse()).isEqualTo("sjkhgoiheiuz");
        assertThat(utilisateur.getMail()).isEqualTo("iktro@gmail.com");
        assertThat(utilisateur.getPresident()).isEqualTo(true);
        assertThat(utilisateur.getPresident()).isEqualTo(true);
        //assertThat(utilisateur.getClub().isEqualTo(3);
    }



}
