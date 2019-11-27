package hei.devweb.projetit.controller;


import hei.devweb.projetit.entities.Utilisateur;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/users")
public class UtilisateurController {

    public List<Utilisateur> listUser(@QueryParam("page") Integer pageNumber,
                                        @QueryParam("department") String departmentNumber,
                                        @QueryParam("sort") CitySortable sort){
        return CityService.getInstance().list(pageNumber,departmentNumber,sort);
    }

}

