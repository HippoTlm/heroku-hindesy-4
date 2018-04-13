package servlets;

import entities.Contributor;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ContributorService;

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
import java.util.List;
import java.util.UUID;
@MultipartConfig
@WebServlet("/admin/contributors")
public class AdminContributorsServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setCharacterEncoding("UTF8");
        List<Contributor> ContributorsList = ContributorService.getInstance().listAllContributorsFR();
        context.setVariable("ContributorsList", ContributorsList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin-contributors", context, resp.getWriter());
    }


    private static final String IMAGE_DIRECTORY_PATH = "C:\\data";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF8");

        // RECUPERATION DES PARAMETRES


        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String label = req.getParameter("label");
        Part picture = req.getPart("image");
        Path picturePath = null;
        if(picture != null) {
            String filename = UUID.randomUUID().toString().substring(0,8) + "-" + picture.getSubmittedFileName();
            picturePath= Paths.get(IMAGE_DIRECTORY_PATH, filename);

            try {
                Files.copy(picture.getInputStream(), picturePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {


    ContributorService.getInstance().addContributor(firstname, lastname,picturePath.toString(),label);


            // REDIRECTION VERS admin-article
            resp.sendRedirect("contributors");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("ContributorsError", e.getMessage());


            resp.sendRedirect("modifier");
        }





    }
}
