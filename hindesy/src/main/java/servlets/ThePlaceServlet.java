package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.NewsArticleService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/the-place")
public class ThePlaceServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String newPosition = NewsArticleService.getInstance().getTinyPosition();
        context.setVariable("TinyPosition", newPosition);



        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("the-place", context, resp.getWriter());
    }
}
