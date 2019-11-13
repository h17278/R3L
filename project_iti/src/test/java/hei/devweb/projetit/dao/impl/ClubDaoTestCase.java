package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.entities.Club;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ClubDaoTestCase {
    private ClubDao clubDao =new ClubDaoImpl();

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM club");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (1,'Saturne')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (2,'Raid')");
            stmt.executeUpdate("INSERT INTO `club`(`club_id`,`name`) VALUES (3,'Heir Force')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (1,'Afterwork',1,'2019-10-12','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.')");
            stmt.executeUpdate("INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.')");
        }
    }

    @Test
    public void shouldListClub() {
        // WHEN
        List<Club> clubs = clubDao.listClubs();
        // THEN
        assertThat(clubs).hasSize(3);
        assertThat(clubs).extracting("id","name").containsOnly(tuple(1,"Saturne"),tuple(2,"Raid"),tuple(3,"Heir Force"));
    }

    @Test
    public void shouldGetClub() {
        // WHEN
        Club club = clubDao.getClub(1);
        // THEN
        assertThat(club).isNotNull();
        assertThat(club.getId()).isEqualTo(1);
        assertThat(club.getName()).isEqualTo("Saturne");
    }





}

