package hei.devweb.projetit.service;

import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.dao.EventDao;
import hei.devweb.projetit.dao.UtilisateurDao;
import hei.devweb.projetit.dao.impl.ClubDaoImpl;
import hei.devweb.projetit.dao.impl.EventDaoImpl;
import hei.devweb.projetit.dao.impl.UtilisateurDaoImpl;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.ClubNotFoundException;
import hei.devweb.projetit.exception.EventNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class EventService {

    private static class EventLibraryHolder {
        private final static EventService instance = new EventService();
    }

    public static EventService getInstance() {
        return EventLibraryHolder.instance;
    }

    private EventDao eventDao = new EventDaoImpl();
    private ClubDao clubDao = new ClubDaoImpl();
    public EventService() {
    }

    public List<Event> eventList() {
        return eventDao.listEvents();
    }

    public Event getEvent(Integer id) throws EventNotFoundException {
        if(eventDao.getEvent(id) == null){
            throw new EventNotFoundException();
        }
        return eventDao.getEvent(id);
    }

    public List<Club> clubList() {
        return clubDao.listClubs();
    }

    public Club getClub(Integer id) throws ClubNotFoundException {
        if(clubDao.getClub(id) == null){
            throw new ClubNotFoundException();
        }
        return clubDao.getClub(id);
    }

    public Event addEvent(Event event) throws EventNotFoundException {
        if(event == null){
            throw new EventNotFoundException();
        }
        return eventDao.addEvent(event);
    }

    public void deleteEvent(Integer eventID) throws EventNotFoundException{
        if(eventID==null){
            throw new EventNotFoundException();
        }
        eventDao.deleteEvent(eventID);
    }

    public Event updateEvent(Event event) throws EventNotFoundException{
        if(event ==null){
            throw new EventNotFoundException();
        }
        return eventDao.updateEvent(event);
    }
}
