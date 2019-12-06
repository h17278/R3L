package hei.devweb.projetit.servlet;


import hei.devweb.projetit.controller.PasswordUtils;
import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.exception.PseudoAlreadyExistException;
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

@WebServlet("/register")
public class RegisterServlet extends GenericServlet {

    static final Logger LOGGER = LogManager.getLogger();

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Integer club_id = null;
        String pseudo = req.getParameter("pseudo");
        String motdepasse = req.getParameter("motdepasse");
        String mail = req.getParameter("mail");
        Boolean president = Boolean.parseBoolean(req.getParameter("status"));
        club_id = Integer.valueOf(req.getParameter("club_id"));
        String mdpHash = PasswordUtils.genererMotDePasse(motdepasse);
        LOGGER.info("A new user is trying to register");

        List<Utilisateur> userList = UserService.getInstance().listUtilisateur();
        boolean flag = true;
        PrintWriter out = resp.getWriter();
        try {
            UserService.getInstance().pseudoAlreadyExist(pseudo);
        } catch (PseudoAlreadyExistException Pseudo){
            flag = false;
            Pseudo.printStackTrace();
        }

        if(flag) {
            //CREATE USER
            Utilisateur newUser = new Utilisateur(null, pseudo, mdpHash, mail, president, club_id);
            Utilisateur createdUser = UserService.getInstance().addUtilisateur(newUser);
            LOGGER.info("User added to database, pseudo=" + pseudo);
            // REDIRECT TO EVENTS LIST
            System.out.println(req.getSession().getAttributeNames());
            if (req.getSession().getAttribute("pseudo") != null) {
                resp.sendRedirect("user");
            } else{
                resp.sendRedirect("connection");
            }
        } else{
            LOGGER.info("This pseudo already exists. Aborting register");
            out.println("<script type=\"text/javascript\" charset=\"UTF-8\">");
            out.println("alert('Pseudo déjà utilisé');");

            out.println("window.location.href = 'register';");
            out.println("</script>");
        }
    }
}
