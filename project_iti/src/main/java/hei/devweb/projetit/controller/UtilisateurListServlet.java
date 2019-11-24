package hei.devweb.projetit.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/userlist")
public class UtilisateurListServlet extends GenericServlet {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Utilisateur> utilisateurs = UserService.getInstance().listUtilisateur();
        String utilisateursJson = MAPPER.writeValueAsString(utilisateurs);
        resp.getWriter().print(utilisateursJson);
    }
}
