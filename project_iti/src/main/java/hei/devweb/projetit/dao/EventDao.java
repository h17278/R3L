package hei.devweb.projetit.dao;

import hei.devweb.projetit.entities.Event;

import java.util.List;

public interface EventDao {

    public List<Event> listEvents();

    public Event getEvent(Integer id);

    public Event addEvent(Event event);
}
