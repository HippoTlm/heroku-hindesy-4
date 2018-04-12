package servlets;

import entities.Partner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.PartnerService;
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
import java.util.List;
import java.util.UUID;
@MultipartConfig
@WebServlet("/admin/support-us")
public class AdminSupportUsServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Partner> PartnersList = PartnerService.getInstance().listAllPartners();
        context.setVariable("PartnersList", PartnersList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin-support-us", context, resp.getWriter());
    }


    private static final String IMAGE_DIRECTORY_PATH = "C:\\data\\helpers";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // RECUPERATION DES PARAMETRES


        String name = req.getParameter("name");
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



            PartnerService.getInstance().addPartner(name, picturePath.toString());

            // REDIRECTION VERS admin-article
            resp.sendRedirect("support-us");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("NewsArticleError", e.getMessage());


            resp.sendRedirect("modifier");
        }





    }
}
