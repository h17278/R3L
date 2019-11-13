package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.entities.Club;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDaoImpl implements ClubDao {
    @Override
    public List<Club> listClubs() {
        List<Club> clubs = new ArrayList<>();
        String sqlQuery = "SELECT * FROM club ORDER BY name";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                    while (resultSet.next()) {
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name")
                        );
                        clubs.add(club);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }


    @Override
    public Club getClub(Integer id) {
        String sqlQuery = "SELECT * FROM club WHERE club_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name")
                        );
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
