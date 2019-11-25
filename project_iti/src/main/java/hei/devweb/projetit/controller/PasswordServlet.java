package hei.devweb.projetit.controller;


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

@WebServlet("/password")
public class PasswordServlet extends GenericServlet{


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("password", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo = req.getParameter("pseudo");
        String mdp2 = req.getParameter("mdp2");
        String mdp = req.getParameter("mdp");
        boolean flag = true;
        PrintWriter out = resp.getWriter();
        List<Utilisateur> userList = EventService.getInstance().utilisateurList();
        for (Utilisateur utilisateur : userList) {
            if (pseudo.equals(utilisateur.getPseudo()) && mdp.equals(utilisateur.getMotdepasse())) {
                flag = false;
                req.getSession().setAttribute("pseudo", pseudo);
                req.getSession().setAttribute("mdp2", mdp2);

                EventService.getInstance().getUtilisateur(pseudo);
                EventService.getInstance().

                out.println("<script type=\"text/javascript\">");
                out.println("alert('Votre mot de passe a bien été mis à jour');");
                out.println("</script>");
            }
        }

        if(flag){
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("</script>");
        }
    }
}
