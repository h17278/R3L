package hei.devweb.projetit.exception;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/AdminFilter")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String pseudo = (String) httpRequest.getSession().getAttribute("pseudo");

        Boolean president = (Boolean) httpRequest.getSession().getAttribute("president");

        PrintWriter out = resp.getWriter();

        System.out.println("Voici : " + pseudo + ", il est " + president );

        if(pseudo == null){
            System.out.println("Il faut être connecté pour accéder à cette page !");
            HttpServletResponse httpResponse = (HttpServletResponse) resp;

            System.out.println("passe par le if president");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Il faut être connecté et président pour accéder à cette page !');");
            out.println("window.location.href = 'connection';");
            out.println("</script>");

            return;
        }

        if (!president && president != null)  {
                System.out.println("Il faut être connecté et président pour accéder à cette page !");
                HttpServletResponse httpResponse = (HttpServletResponse) resp;

                System.out.println("passe par le if president");
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Il faut président pour accéder à cette page !');");
                out.println("window.location.href = 'home';");
                out.println("</script>");

                return;
        }


        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {    }
}
