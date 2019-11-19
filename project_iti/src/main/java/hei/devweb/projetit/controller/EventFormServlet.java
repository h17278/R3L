package hei.devweb.projetit.controller;

import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.service.EventService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/addevent")
public class EventFormServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Club> clubList = EventService.getInstance().clubList();
        context.setVariable("clubList", clubList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("newevent", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETERS
        String title = req.getParameter("title");
        Integer club_id = Integer.valueOf(req.getParameter("club_id"));
        LocalDate event_date = LocalDate.parse(req.getParameter("event_date"));
        String image_link = req.getParameter("image_link");
        String resume = req.getParameter("resume");
        String details = req.getParameter("details");

        // CREATE EVENT
        Club club = EventService.getInstance().getClub(club_id);
        Event newEvent = new Event(null, title, club, event_date, image_link, resume, details);
        Event createdEvent = EventService.getInstance().addEvent(newEvent);

        // REDIRECT TO EVENTS LIST
        resp.sendRedirect("home");
    }
}
