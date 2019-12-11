import hei.devweb.projetit.dao.ClubDao;
import hei.devweb.projetit.dao.EventDao;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.exception.ClubNotFoundException;
import hei.devweb.projetit.exception.EventNotFoundException;
import hei.devweb.projetit.service.EventService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceTestCase {

    @Mock
    private EventDao eventDaoMock;

    @Mock
    private ClubDao clubDaoMock;

    @InjectMocks
    private EventService eventService;

    @Test
    public void shouldEventList() {
        //GIVEN
        Club club = new Club(1, "Saturne", "lien1");
        Club club2 = new Club(2, "Raid HEI", "lien2");
        Event event1 = new Event(null, "Afterwork Raid", club, LocalDate.of(2020, Month.FEBRUARY, 12), "BDS", "url4", "Afterwork à la garderie", "details4");
        Event event2 = new Event(null, "Week-end du Raid", club2, LocalDate.of(2020, Month.APRIL, 18), "BDS", "url5", "Week end sportif", "details5");
        List<Event> events = Arrays.asList(event1, event2);
        Mockito.when(eventDaoMock.listEvents()).thenReturn(events);
        //WHEN
        List<Event> result = eventService.eventList();
        //THEN
        Assertions.assertThat(result).contains(event1, event2).hasSize(events.size());

    }

    @Test
    public void shouldGetEvent() {
        //GIVEN
        Club club = new Club(1, "Saturne", "lien1");
        Event event1 = new Event(1, "Afterwork Raid", club, LocalDate.of(2020, Month.FEBRUARY, 12), "BDS", "url4", "Afterwork à la garderie", "details4");
        Mockito.when(eventDaoMock.getEvent(1)).thenReturn(event1);
        //WHEN
        Event result = eventService.getEvent(1);
        //THEN
        Assertions.assertThat(result).isEqualTo(event1);
    }

    @Test
    public void shouldClubList() {
        //GIVEN
        Club club1 = new Club(1, "Saturne", "lien1");
        Club club2 = new Club(2, "Raid HEI", "lien2");
        List<Club> clubs = Arrays.asList(club1, club2);
        Mockito.when(clubDaoMock.listClubs()).thenReturn(clubs);
        //WHEN
        List<Club> result = eventService.clubList();
        //THEN
        Assertions.assertThat(result).contains(club1, club2).hasSize(clubs.size());
    }

    @Test
    public void shouldGetClub() {
        //GIVEN
        Club club = new Club(1, "Saturne", "lien1");
        Mockito.when(clubDaoMock.getClub(1)).thenReturn(club);
        //WHEN
        Club result = eventService.getClub(1);
        //THEN
        Assertions.assertThat(result).isEqualTo(club);
    }

    @Test
    public void shouldAddEvent() {
        //GIVEN
        Club club = new Club(1, "Saturne", "lien1");
        Event event1 = new Event(1, "Afterwork Raid", club, LocalDate.of(2020, Month.FEBRUARY, 12), "BDS", "url4", "Afterwork à la garderie", "details4");
        Mockito.when(eventDaoMock.addEvent(event1)).thenReturn(event1);
        //WHEN
        Event result = eventService.addEvent(event1);
        //THEN
        Assertions.assertThat(result).isEqualTo(event1);
    }

    @Test
    public void shouldDeleteEvent(){
        //GIVEN
        Integer event_id = 4;
        //WHEN
        eventService.deleteEvent(event_id);
        //THEN
        Mockito.verify(eventDaoMock, Mockito.times(1)).deleteEvent(event_id);
    }

    @Test(expected = EventNotFoundException.class)
    public void shouldDeleteEventThrowEventNotFoundException() throws EventNotFoundException{
        //GIVEN
        Integer event_id = null;
        //WHEN
        eventService.deleteEvent(event_id);
        //THEN
        fail("EventNotFoundException");
    }

    @Test(expected = EventNotFoundException.class)
    public void shouldUpdateEventThrowEventNotFount() throws EventNotFoundException{
        //WHEN
        eventService.updateEvent(null);
        //THEN
        fail("EventNotFoundException");
    }

    @Test(expected = EventNotFoundException.class)
    public void shouldAddEventThrowEventNotFound() throws EventNotFoundException{
        //WHEN
        eventService.addEvent(null);
        //THEN
        fail("EventNotFoundException");
    }

    @Test(expected = EventNotFoundException.class)
    public void shouldGetEventThrowEventNotFound() throws EventNotFoundException{
        //WHEN
        eventService.getEvent(null);
        //THEN
        fail("EventNotFoundException");
    }

    @Test(expected = ClubNotFoundException.class)
    public void shouldGetClubThrowEventNotFound() throws ClubNotFoundException{
        //WHEN
        eventService.getClub(null);
        //THEN
        fail("ClubNotFoundException");
    }

    @Test
    public void shouldUpdateEvent() {
        //GIVEN
        Club club = new Club(1, "Saturne", "lien1");
        Event event = new Event(null, "Afterwork Raid", club, LocalDate.of(2020, Month.FEBRUARY, 12), "BDS", "url4", "Afterwork à la garderie", "details4");
        Mockito.when(eventDaoMock.updateEvent(event)).thenReturn(event);
        //WHEN
        Event eventModif = eventService.updateEvent(event);
        //THEN
        Assertions.assertThat(eventModif).isEqualTo(event);
    }

}