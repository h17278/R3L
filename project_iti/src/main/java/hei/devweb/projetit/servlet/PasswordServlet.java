package hei.devweb.projetit.servlet;


import hei.devweb.projetit.controller.PasswordUtils;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.EventService;
import hei.devweb.projetit.service.UserService;
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
        String newmdp;
        boolean flag2 = true;
        boolean flag1 = true;
        PrintWriter out = resp.getWriter();
        List<Utilisateur> userList = EventService.getInstance().utilisateurList();
        for (Utilisateur utilisateur : userList) {
            if (pseudo.equals(utilisateur.getPseudo())) {
                flag1 = false;
                if (mdp.equals(mdp2)) {
                    flag2 = false;
                    req.getSession().setAttribute("pseudo", pseudo);
                    req.getSession().setAttribute("mdp2", mdp2);

                    newmdp = PasswordUtils.genererMotDePasse(mdp2);
                    UserService.getInstance().setPassword(pseudo, newmdp);

                    out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
                    out.println("alert('Votre mot de passe a bien été mis à jour');");
                    out.println("window.location.href = 'home';");
                    out.println("</script>");
                }
            }
        }
        if(flag1){
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('User incorrect');");
            out.println("</script>");
        }

        if(flag2){
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('Vos mots de passe ne sont pas en accord');");
            out.println("window.location.href = 'password';");
            out.println("</script>");
        }
    }
}
