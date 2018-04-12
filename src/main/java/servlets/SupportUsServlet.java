package servlets;

import entities.FormPartner;
import entities.NewsArticle;
import entities.Partner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.FormPartnerService;
import services.NewsArticleService;
import services.PartnerService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet("/support-us")
public class SupportUsServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Partner> PartnersList = PartnerService.getInstance().listAllPartners();
        context.setVariable("PartnersList", PartnersList);

        if(req.getSession().getAttribute("FormPartnerError") != null) {
            context.setVariable("Erreur", req.getSession().getAttribute("FormPartnerError"));
        }
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("support-us", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // GET PARAMETERS
        String lastName = req.getParameter("lastName");
        String  firstName= req.getParameter("firstName");
        String organization = req.getParameter("organization");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String message = req.getParameter("message");
        resp.setCharacterEncoding("UTF8");

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive
        int id = ThreadLocalRandom.current().nextInt(0, 65000000 + 1);

        // CREATION Partner
        FormPartner newFormPartner = new FormPartner(id,firstName, lastName, organization,email,phone,message);

        try {
            FormPartnerService.getInstance().addPartner(newFormPartner);

            req.getSession().setAttribute("ok",1 );
            resp.sendRedirect("support-us");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("FormPartnerError", e.getMessage());
            req.getSession().setAttribute("ok",0 );

        }



    }
}
