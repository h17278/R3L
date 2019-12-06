package hei.devweb.projetit.servlet;

import hei.devweb.projetit.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteEvent")
public class DeleteEventServlet extends GenericServlet {

    static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETER
        Integer event_id = Integer.valueOf(req.getParameter("DeleteEvent"));
        //DELETE EVENT
        EventService.getInstance().deleteEvent(event_id);
        LOGGER.info("Deleted event id=" + event_id);
        // REDIRECT TO EVENTS LIST
        resp.sendRedirect("home");
    }
}
