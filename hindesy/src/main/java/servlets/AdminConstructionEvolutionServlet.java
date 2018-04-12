package servlets;

import entities.ConstructionArticle;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ConstructionArticleService;
import services.AdminWelcomeService;
import servlets.AbstractGenericServlet;

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
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/construction-evolution")
@MultipartConfig

public class AdminConstructionEvolutionServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<ConstructionArticle> ConstructionArticleList =
                ConstructionArticleService.getInstance().listConstructionArticles();
        context.setVariable("ConstructionArticleList", ConstructionArticleList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin-construction-evolution", context, resp.getWriter());
    }
    private static final String IMAGE_DIRECTORY_PATH = "C:\\data\\constructionArticles";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // RECUPERATION DES PARAMETRES


        String titleFR = req.getParameter("titleFR");
        String titleEN = req.getParameter("titleEN");
        System.out.println(titleFR);
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


        ConstructionArticle newConstructionArticle = new ConstructionArticle(null, dateLocal,  titleFR, titleEN, contentFR, contentEN, picturePath1.toString());

        try {


ConstructionArticleService.getInstance().addArticle(picturePath1.toString(),  titleFR,contentFR, titleEN,  contentEN, dateLocal);

            // REDIRECTION VERS admin-article
            resp.sendRedirect("construction-evolution");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("ConstructionArticleError", e.getMessage());
            req.getSession().setAttribute("ConstructionArticleSource", newConstructionArticle);

            resp.sendRedirect("modifier");
        }

        /*
        //TO MODIFY AN ARTICLE
        Integer idArticle = Integer.parseInt(req.getParameter("idArticle"));
        String photo = req.getParameter("photo");
        String titleFR = req.getParameter("titleFR");
        String contentFR = req.getParameter("contentFR");
        String titleEN = req.getParameter("titleEN");
        String contentEN = req.getParameter("contentEN");
        ConstructionArticleService.getInstance().modifyArticle(idArticle, photo, titleFR, contentFR, titleEN, contentEN);


        //TO DELETE AN ARTICLE
        Integer deletedId = Integer.parseInt(req.getParameter("deletedId"));
        ConstructionArticleService.getInstance().deleteArticle(deletedId);

        */
    }
}
