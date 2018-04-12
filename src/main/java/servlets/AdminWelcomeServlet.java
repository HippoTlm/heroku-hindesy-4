package servlets;

import entities.FormHelper;
import entities.FormPartner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.AdminWelcomeService;
import services.NewsArticleService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/home")
public class AdminWelcomeServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String newPosition = NewsArticleService.getInstance().getTinyPosition();
        context.setVariable("TinyPosition", newPosition);




        context.setVariable("formHelpersList", AdminWelcomeService.getInstance().listAllFormHelpers());
        context.setVariable("formDonationList", AdminWelcomeService.getInstance().listAllFormDonations());
        context.setVariable("formPartnersList", AdminWelcomeService.getInstance().listAllFormPartners());

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("admin", context, resp.getWriter());
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // RECUPERATION DES PARAMETRES


        String Position = req.getParameter("position");

        System.out.println("servlet : "+Position);

 try {



            NewsArticleService.getInstance().updatePosition(Position);

            // REDIRECTION VERS admin-article
            resp.sendRedirect("home");

        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("NewsArticleError", e.getMessage());


            resp.sendRedirect("home");
        }





    }
}
