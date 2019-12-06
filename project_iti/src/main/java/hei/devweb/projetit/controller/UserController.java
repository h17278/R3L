package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/users")
public class UserController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> listUtilisateurs() {
        return UserService.getInstance().listUtilisateur();
    }


    @DELETE
    @Path("/{userId}")
    public void deleteUser(
            @PathParam("userId") Integer userId) {
        UserService.getInstance().deleteUser(userId);
    }

    @PATCH
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateUser(
            @PathParam("userId") Integer userId) {
        UserService.getInstance().updateUser(userId);
    }
}
