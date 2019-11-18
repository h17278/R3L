package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.entities.Utilisateur;

import java.sql.*;

public class UtilisateurDaoImpl implements UtilisateurDao {

    @Override
    public Utilisateur getUtilisateur(Integer id) {
        String sqlQuery = "SELECT * FROM utilisateur JOIN club ON utilisateur.club_id = club.club_id  WHERE utilisateur_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
                statement.setInt(1, id);
                try(ResultSet resultSet = statement.executeQuery()){
                    if(resultSet.next()){
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name"),
                                resultSet.getString("lien")
                        );
                        Utilisateur utilisateur = new Utilisateur(
                                resultSet.getInt("utilisateur_id"),
                                resultSet.getString("pseudo"),
                                resultSet.getString("motdepasse"),
                                resultSet.getString("mail"),
                                resultSet.getBoolean("president"),
                                club.getId()
                        );
                        return utilisateur;
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilisateur addUtilisateur(Utilisateur user) {
        String sqlQuery = "INSERT INTO utilisateur(pseudo, motdepasse, mail, president, club_id) " +
                                                 "VALUES(?, ?, ?, ?, ?)";
        try(Connection connection = DataSourceProvider.getDataSource().getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, user.getPseudo() );
                statement.setString(2, user.getMotdepasse());
                statement.setString(3, user.getMail());
                statement.setBoolean(4, user.getPresident());
                statement.setInt(5, user.getClub());

                statement.executeUpdate();

                try (ResultSet ids = statement.getGeneratedKeys()){
                    if(ids.next()){
                        int userId = ids.getInt("utilisateur_id");
                        user.setIdutilisateur(userId);
                        return user;
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Request Execution Problem");
    }


}
