package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.exception.ClubNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDaoImpl implements ClubDao {

    static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Club> listClubs() {
        LOGGER.debug("method listClubs called");
        List<Club> clubs = new ArrayList<>();
        String sqlQuery = "SELECT * FROM club ORDER BY name";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                    while (resultSet.next()) {
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name"),
                                resultSet.getString("lien")
                        );
                        LOGGER.debug("Adding to listClubs club id=" + club.getId());
                        clubs.add(club);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.debug("listClubs completed");
        return clubs;
    }


    @Override
    public Club getClub(Integer id) throws ClubNotFoundException {
        LOGGER.debug("method getClub called");
        String sqlQuery = "SELECT * FROM club WHERE club_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name"),
                                resultSet.getString("lien")
                        );
                        LOGGER.debug("Getting club id=" + club.getId() + " | name=" + club.getName());
                        return club;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
