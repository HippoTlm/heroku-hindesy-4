package servlets;

import entities.ConstructionArticle;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ConstructionArticleService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/construction-evolution")
public class ConstructionEvolutionServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<ConstructionArticle> ConstructionArticleList =
                ConstructionArticleService.getInstance().listConstructionArticles();
        context.setVariable("ConstructionArticleList", ConstructionArticleList);


        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("construction-evolution", context, resp.getWriter());
    }
}
