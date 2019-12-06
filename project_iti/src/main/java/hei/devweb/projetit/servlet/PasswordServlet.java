package hei.devweb.projetit.servlet;


import hei.devweb.projetit.controller.PasswordUtils;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PasswordDontMatch;
import hei.devweb.projetit.service.EventService;
import hei.devweb.projetit.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static final Logger LOGGER = LogManager.getLogger();

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
        LOGGER.info("A user is trying to update his password, pseudo=" + pseudo);
        String newmdp;
        boolean flag2 = true;
        boolean flag1 = true;
        PrintWriter out = resp.getWriter();
        List<Utilisateur> userList = UserService.getInstance().listUtilisateur();

        try{
            UserService.getInstance().passwordMatch(mdp,mdp2);
        } catch (PasswordDontMatch password){
            password.printStackTrace();
        }

        for (Utilisateur utilisateur : userList) {
            if (pseudo.equals(utilisateur.getPseudo())) {
                flag1 = false;
                if (mdp.equals(mdp2)) {
                    flag2 = false;
                    req.getSession().setAttribute("pseudo", pseudo);
                    req.getSession().setAttribute("mdp2", mdp2);

                    newmdp = PasswordUtils.genererMotDePasse(mdp2);
                    UserService.getInstance().setPassword(pseudo, newmdp);

                    LOGGER.info("The user pseudo=" + pseudo + "has updated his password.");
                    out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
                    out.println("alert('Votre mot de passe a bien été mis à jour');");
                    out.println("window.location.href = 'home';");
                    out.println("</script>");
                }
            }
        }
        if(flag1){
            LOGGER.info("The user does not exist. Aborting password update");
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('Pseudo incorrect');");
            out.println("</script>");
        }

        if(flag2){
            LOGGER.info("The passwords need to be identical. Aborting password update");
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('Vos mots de passe ne sont pas en accord');");
            out.println("window.location.href = 'password';");
            out.println("</script>");
        }
    }
}
