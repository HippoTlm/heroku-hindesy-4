package servlets;

import entities.ConstructionArticle;
import entities.NewsArticle;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ConstructionArticleService;
import services.NewsArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@WebServlet("/admin/ModifyConstruction")
@MultipartConfig

public class AdminModifyConstructionServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF8");
        Integer idArticle = Integer.parseInt(req.getParameter("idArticle"));

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("ConstructionArticle", ConstructionArticleService.getInstance().getConstructionArticle(idArticle));


        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin-modify-Constructions", context, resp.getWriter());
    }

    private static final String IMAGE_DIRECTORY_PATH = "C:\\data\\constructionArticles";
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // RECUPERATION DES PARAMETRES

        resp.setCharacterEncoding("UTF8");
        Integer idArticle = parseInt(req.getParameter("idArticle"));
        String titleFR = req.getParameter("titleFR");
        String titleEN = req.getParameter("titleEN");
        String dateString = req.getParameter("date");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateLocal = LocalDate.parse(dateString, dateFormat);
        String contentFR = req.getParameter("contentFR");
        String contentEN = req.getParameter("contentEN");






        Part picture1 = req.getPart("inputPicture1");
        Path picturePath1 = null;
        if(picture1 != null) {
            String filename1 = UUID.randomUUID().toString().substring(0,8) + "-" + picture1.getSubmittedFileName();
            picturePath1 = Paths.get(IMAGE_DIRECTORY_PATH, filename1);
            try {
                Files.copy(picture1.getInputStream(), picturePath1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //SUPPRESSION DE L'ANCIENNE VIDEO

        ConstructionArticle newConstructionArticle = new ConstructionArticle(null, dateLocal,  titleFR, titleEN, contentFR, contentEN, picturePath1.toString());

        try {


            ConstructionArticleService.getInstance().modifyArticle(idArticle, picturePath1.toString(),  titleFR,contentFR, titleEN,  contentEN, dateLocal);

            // REDIRECTION VERS admin-construction evolution
            resp.sendRedirect("/admin/construction-evolution");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("ConstructionArticleError", e.getMessage());
            req.getSession().setAttribute("ConstructionArticleSource", newConstructionArticle);

            resp.sendRedirect("/admin/ModifyConstruction");
        }





    }
}
