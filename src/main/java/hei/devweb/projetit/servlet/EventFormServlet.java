package hei.devweb.projetit.servlet;

import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static final Logger LOGGER = LogManager.getLogger();

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

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // GET PARAMETERS
        String title = req.getParameter("title");
        Integer club_id = Integer.valueOf(req.getParameter("club_id"));
        LocalDate event_date = LocalDate.parse(req.getParameter("event_date"));
        String bureau = req.getParameter("bureau");
        String image_link = req.getParameter("image_link");
        String resume = req.getParameter("resume");
        String details = req.getParameter("details");

        // CREATE EVENT
        Club club = EventService.getInstance().getClub(club_id);
        Event newEvent = new Event(null, title, club, event_date, bureau, image_link, resume, details);
        Event createdEvent = EventService.getInstance().addEvent(newEvent);
        LOGGER.debug("Created event: title=" + title + " | club=" + club.getName() + " | event_date=" + event_date + " | bureau=" + bureau + " | resume=" + resume + " | details=" + details + " | image_link=" + image_link);

        // REDIRECT TO EVENTS LIST
        resp.sendRedirect("home");
    }
}
