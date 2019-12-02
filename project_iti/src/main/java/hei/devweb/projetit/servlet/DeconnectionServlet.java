package hei.devweb.projetit.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deconnection")
public class DeconnectionServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getSession().getAttribute("pseudo"));
        req.getSession().invalidate();
        System.out.println(req.getSession().getAttribute("pseudo"));
        resp.sendRedirect("home");
    }

}
