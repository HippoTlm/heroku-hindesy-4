package servlets;

import entities.NewsArticle;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.NewsArticleService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/articles")
public class ArticlesServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<NewsArticle> newsArticleList = NewsArticleService.getInstance().listNewsArticles();
        context.setVariable("newsArticleList", newsArticleList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("articles", context, resp.getWriter());
    }
}
