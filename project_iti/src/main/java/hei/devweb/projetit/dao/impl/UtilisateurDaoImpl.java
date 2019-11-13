package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.entities.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                                resultSet.getString("name")
                        );
                        Utilisateur utilisateur = new Utilisateur(
                                resultSet.getInt("utilisateur_id"),
                                resultSet.getString("pseudo"),
                                resultSet.getString("motdepasse"),
                                resultSet.getString("mail"),
                                resultSet.getBoolean("president"),
                                club
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



}
