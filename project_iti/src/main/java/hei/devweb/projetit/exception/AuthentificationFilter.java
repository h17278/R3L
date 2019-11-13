package hei.devweb.projetit.exception;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthentificationFilter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String identifiant = (String)
                httpRequest.getSession().getAttribute("utilisateurConnecte");
        if (identifiant == null || "".equals(identifiant)) {
            System.out.println(
                    "Il faut être connecté pour accéder à cette page !");
            HttpServletResponse httpResponse =
                    (HttpServletResponse) response;
            httpResponse.sendRedirect("../home");
            return;
        }
        chain.doFilter(request, response);
    }
}
