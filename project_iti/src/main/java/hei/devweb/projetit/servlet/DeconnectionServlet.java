package hei.devweb.projetit.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deconnection")
public class DeconnectionServlet extends GenericServlet {

    static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("pseudo");
        req.getSession().invalidate();
        LOGGER.info("Disconnected user " + user + ", now registered as " + req.getSession().getAttribute("pseudo"));
        resp.sendRedirect("home");
    }

}
