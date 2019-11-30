package hei.devweb.projetit.controller;

import hei.devweb.projetit.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteEvent")
public class DeleteEventServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETER
        Integer event_id = Integer.valueOf(req.getParameter("DeleteEvent"));
        //DELETE EVENT
        EventService.getInstance().deleteEvent(event_id);
        // REDIRECT TO EVENTS LIST
        resp.sendRedirect("home");
    }
}
