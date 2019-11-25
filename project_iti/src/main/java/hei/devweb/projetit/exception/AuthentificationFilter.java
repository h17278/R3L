package hei.devweb.projetit.exception;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/mettreURLPrive/*")
public class AuthentificationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String identifiant = (String)
                httpRequest.getSession().getAttribute("pseudo");
        if (identifiant == null || "".equals(identifiant)) {
            System.out.println("Il faut être connecté pour accéder à cette page !");
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.sendRedirect("home");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
