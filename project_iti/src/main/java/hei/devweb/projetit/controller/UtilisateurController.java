package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.entities.UtilisateurSortable;
import hei.devweb.projetit.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UtilisateurController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> listUsers(){
        return UserService.getInstance().listUtilisateur();
    }

}

