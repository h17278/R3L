package hei.devweb.projetit.entities;

import java.time.LocalDate;

public class Event {

    Integer id;
    String title;
    Club club;
    LocalDate eventDate;
    String resume;
    String details;

    public Event (Integer id, String title, Club club, LocalDate eventDate, String resume, String details){
        this.id = id;
        this.title = title;
        this.club = club;
        this.eventDate = eventDate;
        this.resume = resume;
        this.details = details;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Club getClub() { return club; }

    public void setClub(Club club) { this.club = club; }

    public LocalDate getEventDate() { return eventDate; }

    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getResume() { return resume; }

    public void setResume(String resume) { this.resume = resume; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }
}
