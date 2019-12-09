package hei.devweb.projetit.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@WebServlet("/user")
public class UserServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());

        templateEngine.process("user", context, resp.getWriter());
    }
}
