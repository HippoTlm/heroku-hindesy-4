package servlets;

import services.ConstructionArticleService;
import services.NewsArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/DeleteConstruction")
public class AdminDeleteConstructionServlet extends AbstractGenericServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer idArticle = Integer.parseInt(req.getParameter("idArticle"));

        ConstructionArticleService.getInstance().deleteArticle(idArticle);

        resp.sendRedirect("/admin/construction-evolution");
    }
}
