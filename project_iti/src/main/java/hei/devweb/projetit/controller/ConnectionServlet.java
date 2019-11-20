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

@WebServlet("/connection")
public class ConnectionServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());

        List<Utilisateur> userList = EventService.getInstance().utilisateurList();
        context.setVariable("userList", userList);

        templateEngine.process("connection", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pseudo = req.getParameter("pseudo");
        String mdp = req.getParameter("mdp");

        List<Utilisateur> userList = EventService.getInstance().utilisateurList();
        for (int i = 0; i < userList.size(); i++){
            if (pseudo == userList.get(i).getPseudo() && mdp == userList.get(i).getMotdepasse()) {

                req.getSession().setAttribute("pseudo",pseudo);
                System.out.println("j'ai recup" + pseudo);
                req.getSession().setAttribute("president",userList.get(i).getPresident());
                req.getSession().setAttribute("club",userList.get(i).getClub());

                resp.sendRedirect("home");
            }
        }

    }
}
