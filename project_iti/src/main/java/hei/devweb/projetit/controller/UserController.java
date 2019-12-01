package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/users")
public class UserController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> listUtilisateurs() {
        return UserService.getInstance().listUtilisateur();
    }

/*
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Utilisateur getUtilisateur(
            @PathParam("id") Integer userId){
        return UserService.getInstance().getUser(userId);
    }
*/
}
