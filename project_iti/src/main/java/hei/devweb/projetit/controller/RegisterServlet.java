package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Event;
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

@WebServlet("/register")
public class RegisterServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Club> clubList = EventService.getInstance().clubList();
        context.setVariable("clubList", clubList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("register", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer club_id = null;
        String pseudo = req.getParameter("pseudo");
        String motdepasse = req.getParameter("motdepasse");
        String mail = req.getParameter("mail");
        Boolean statut = Boolean.valueOf(req.getParameter("president"));
        club_id = Integer.valueOf(req.getParameter("club_id"));
        //CREATE USER
        Utilisateur newUser = new Utilisateur(  null, pseudo, motdepasse, mail, statut, club_id);
        Utilisateur createdUser = EventService.getInstance().addUtilisateur(newUser);

        // REDIRECT TO EVENTS LIST
        resp.sendRedirect("home");
    }
}
