package hei.devweb.projetit.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user")
public class UtilisateurListServlet extends GenericServlet {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Utilisateur> utilisateurs = UserService.getInstance().listUtilisateur();
        String utilisateursJson = MAPPER.writeValueAsString(utilisateurs);
        resp.getWriter().print(utilisateursJson);

        context.setVariable("user", utilisateursJson);
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("user", context, resp.getWriter());
    }
}
