package hei.devweb.projetit.controller;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Event> eventList = EventService.getInstance().eventList();
        context.setVariable("eventList", eventList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("index", context, resp.getWriter());
    }
}
