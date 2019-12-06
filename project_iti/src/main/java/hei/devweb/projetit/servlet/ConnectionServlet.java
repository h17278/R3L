package hei.devweb.projetit.servlet;


import hei.devweb.projetit.controller.PasswordUtils;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.EventService;
import hei.devweb.projetit.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());

        List<Utilisateur> userList = UserService.getInstance().listUtilisateur();
        context.setVariable("userList", userList);

        templateEngine.process("connection", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String pseudo = req.getParameter("pseudo");
        String mdp = req.getParameter("mdp");
        boolean flag = true;
        PrintWriter out = resp.getWriter();
        List<Utilisateur> userList = UserService.getInstance().listUtilisateur();

        req.getSession().invalidate();

        for (Utilisateur utilisateur : userList) {
            if (pseudo.equals(utilisateur.getPseudo()) && PasswordUtils.validerMotDePasse(mdp,utilisateur.getMotdepasse())) {
                flag = false;
                req.getSession().setAttribute("pseudo", pseudo);
                System.out.println("j'ai recup" + pseudo);
                req.getSession().setAttribute("president", utilisateur.getPresident());
                req.getSession().setAttribute("club", utilisateur.getClub());
                resp.sendRedirect("home");
            }
        }

        if(flag){
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('Pseudo ou mot de passe incorrect');");
            out.println("window.location.href = 'connection';");
            out.println("</script>");
        }
    }

}
