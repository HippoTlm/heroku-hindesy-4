package servlets;

import entities.NewsArticle;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.NewsArticleService;
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

import static java.lang.Integer.parseInt;

@WebServlet("/admin/article")

@MultipartConfig

public class AdminArticlesServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<NewsArticle> newsArticleList = NewsArticleService.getInstance().listNewsArticles();
        context.setVariable("newsArticleList", newsArticleList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin-article", context, resp.getWriter());
    }


    private static final String IMAGE_DIRECTORY_PATH = "C:\\data\\newsArticles";
@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // RECUPERATION DES PARAMETRES


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

    Part picture2 = req.getPart("inputPicture2");
    Path picturePath2 = null;
    if(picture2 != null) {
        String filename2 = UUID.randomUUID().toString().substring(0,8) + "-" + picture2.getSubmittedFileName();
        picturePath2 = Paths.get(IMAGE_DIRECTORY_PATH, filename2);

        try {
            Files.copy(picture2.getInputStream(), picturePath2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Part picture3 = req.getPart("inputPicture3");
    Path picturePath3 = null;
    if(picture3 != null) {
        String filename3 = UUID.randomUUID().toString().substring(0,8) + "-" + picture3.getSubmittedFileName();
        picturePath3 = Paths.get(IMAGE_DIRECTORY_PATH, filename3);

        try {
            Files.copy(picture3.getInputStream(), picturePath3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        //SUPPRESSION DE L'ANCIENNE VIDEO

        NewsArticle newNewsArticle = new NewsArticle(null, dateLocal, picturePath1.toString(), picturePath2.toString(), picturePath3.toString(), titleFR, titleEN, contentFR, contentEN);

        try {



            NewsArticleService.getInstance().addArticle(dateLocal, picturePath1.toString(), picturePath2.toString(), picturePath3.toString(), titleFR, contentFR, titleEN, contentEN);

            // REDIRECTION VERS admin-article
            resp.sendRedirect("article");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("NewsArticleError", e.getMessage());
            req.getSession().setAttribute("NewArticleSource", newNewsArticle);

            resp.sendRedirect("modifier");
        }





    }
}
