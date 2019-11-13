package hei.devweb.projetit.service;

import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.dao.EventDao;
import hei.devweb.projetit.dao.impl.ClubDaoImpl;
import hei.devweb.projetit.dao.impl.EventDaoImpl;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;

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

    private EventService() {
    }

    public List<Event> eventList() {
        return eventDao.listEvents();
    }

    public Event getEvent(Integer id) {
        return eventDao.getEvent(id);
    }

    public List<Club> clubList() {
        return clubDao.listClubs();
    }

    public Club getClub(Integer id){
        return clubDao.getClub(id);
    }

    public Event addEvent(Event event) {return eventDao.addEvent(event); }
}
