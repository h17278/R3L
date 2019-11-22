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
import java.io.PrintWriter;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pseudo = req.getParameter("pseudo");
        String mdp = req.getParameter("mdp");
        boolean flag = true;
        PrintWriter out = resp.getWriter();
        List<Utilisateur> userList = EventService.getInstance().utilisateurList();
        for (Utilisateur utilisateur : userList) {
            if (pseudo.equals(utilisateur.getPseudo()) && mdp.equals(utilisateur.getMotdepasse())) {
                flag = false;
                req.getSession().setAttribute("pseudo", pseudo);
                System.out.println("j'ai recup" + pseudo);
                req.getSession().setAttribute("president", utilisateur.getPresident());
                req.getSession().setAttribute("club", utilisateur.getClub());

                resp.sendRedirect("home");
            }
        }

        if(flag){
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("</script>");
        }
    }
}
