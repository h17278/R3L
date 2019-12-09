package hei.devweb.projetit.dao.impl;

import hei.devweb.projetit.dao.EventDao;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.exception.EventNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {

    static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Event> listEvents() {
        LOGGER.debug("method listEvents called");
        List<Event> events = new ArrayList<>();
        String sqlQuery = "SELECT * FROM event   JOIN club ON event.club_id = club.club_id  ORDER BY event_date DESC";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                    while (resultSet.next()) {
                        Club club = new Club(
                                resultSet.getInt("club_id"),
                                resultSet.getString("name"),
                                resultSet.getString("lien")
                        );
                        Event event = new Event(
                                resultSet.getInt("event_id"),
                                resultSet.getString("title"),
                                club,
                                resultSet.getDate("event_date").toLocalDate(),
                                resultSet.getString("bureau"),
                                resultSet.getString("image_link"),
                                resultSet.getString("resume"),
                                resultSet.getString("details")
                        );
                        LOGGER.debug("Adding to event list event id=" + event.getId());
                        events.add(event);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.debug("listEvent completed");
        return events;
    }


    @Override
    public Event getEvent(Integer id) {
        LOGGER.debug("method getEvent called");
        String sqlQuery = "SELECT * FROM event JOIN club ON event.club_id = club.club_id  WHERE event_id=?";
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
                        Event event = new Event(
                                resultSet.getInt("event_id"),
                                resultSet.getString("title"),
                                club,
                                resultSet.getDate("event_date").toLocalDate(),
                                resultSet.getString("bureau"),
                                resultSet.getString("image_link"),
                                resultSet.getString("resume"),
                                resultSet.getString("details")
                        );
                        LOGGER.debug("Getting event: id=" + event.getId()+ " | title=" + event.getTitle() + " | club=" + event.getClub().getName() + " | event_date=" + event.getEventDate() + " | bureau=" + event.getBureau() + " | resume=" + event.getResume() + " | details=" + event.getDetails() + " | image_link=" + event.getImage_link());
                        return event;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event addEvent(Event event) {
        LOGGER.debug("method addEvent called");
        String sqlQuery = "INSERT INTO event(title, club_id, event_date, bureau, image_link, resume, details) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, event.getTitle());
                statement.setInt(2, event.getClub().getId());
                statement.setDate(3,Date.valueOf(event.getEventDate()));
                statement.setString(4, event.getBureau());
                statement.setString(5, event.getImage_link());
                statement.setString(6, event.getResume());
                statement.setString(7, event.getDetails());

                statement.executeUpdate();

                try (ResultSet ids = statement.getGeneratedKeys()) {
                    if (ids.next()) {
                        int eventId = ids.getInt("event_id");
                        event.setId(eventId);
                        LOGGER.info("Adding in data base event: id=" + event.getId()+ " | title=" + event.getTitle() + " | club=" + event.getClub().getName() + " | event_date=" + event.getEventDate() + " | bureau=" + event.getBureau() + " | resume=" + event.getResume() + " | details=" + event.getDetails() + " | image_link=" + event.getImage_link());
                        return event;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteEvent(Integer id) throws EventNotFoundException {
        LOGGER.debug("method deleteEvent called");
        String sqlQuery = "DELETE FROM event WHERE event_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1,id);
                Event event = getEvent(id);
                LOGGER.info("Deleting from data base event: id=" + event.getId() + " | title=" + event.getTitle() + " | club=" + event.getClub().getName());

                statement.executeUpdate();

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Event updateEvent(Event event) throws EventNotFoundException{
        LOGGER.debug("method updateEvent called");
        String sqlQuery = "UPDATE event SET title = ? , event_date = ?, image_link = ?, resume = ?, details = ?  WHERE event_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1,event.getTitle());
                statement.setDate(2,Date.valueOf(event.getEventDate()));
                statement.setString(3,event.getImage_link());
                statement.setString(4, event.getResume());
                statement.setString(5, event.getDetails() );
                statement.setInt(6, event.getId());
                LOGGER.info("Updating from data base event: title=" + event.getTitle() + " | club=" + event.getClub().getName() + " | event_date=" + event.getEventDate() + " | bureau=" + event.getBureau() + " | resume=" + event.getResume() + " | details=" + event.getDetails() + " | image_link=" + event.getImage_link());

                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }
}