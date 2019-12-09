package hei.devweb.projetit.dao;

import hei.devweb.projetit.entities.Club;

import java.util.List;

public interface ClubDao {

    public List<Club> listClubs();

    public Club getClub(Integer id);
}
