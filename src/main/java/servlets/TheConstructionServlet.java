package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/the-construction")
public class TheConstructionServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("the-construction", context, resp.getWriter());
    }
}
