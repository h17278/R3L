package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Club;
import hei.devweb.projetit.entities.Utilisateur;
import hei.devweb.projetit.service.EventService;
import hei.devweb.projetit.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/clubs")
public class ClubController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Club> listClubs() {
        return EventService.getInstance().clubList();
    }


}
