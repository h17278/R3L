package hei.devweb.projetit.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/AdminFilter")
public class AdminFilter implements Filter {

    static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        HttpServletRequest httpRequest;
        httpRequest = (HttpServletRequest) req;
        String pseudo = (String) httpRequest.getSession().getAttribute("pseudo");

        Boolean president = (Boolean) httpRequest.getSession().getAttribute("president");

        PrintWriter out = resp.getWriter();

        LOGGER.info("The user pseudo=" + pseudo + " is trying to access the page " + httpRequest.getServletPath());

        if(pseudo == null){
            LOGGER.info("You need to be registered to access this page");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Il faut se connecter et être président pour accéder à cette page !');");
            out.println("window.location.href = 'connection';");
            out.println("</script>");

            return;
        }

        if (!president)  {
                LOGGER.info("This page requires to be registered as a president");
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Il faut être président pour accéder à cette page !');");
                out.println("window.location.href = 'home';");
                out.println("</script>");

                return;
        }

        LOGGER.info("The user meets the requirements to access to this page");
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {    }
}
