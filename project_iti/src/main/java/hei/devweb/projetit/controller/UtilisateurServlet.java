package hei.devweb.projetit.controller;

import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.EventService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user")
public class UtilisateurServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Utilisateur> utilisateurList = EventService.getInstance().utilisateurList();
        context.setVariable("user", utilisateurList);
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("user", context, resp.getWriter());
    }
}
