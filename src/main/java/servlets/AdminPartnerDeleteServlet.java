package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.PartnerService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/partnerDelete")
public class AdminPartnerDeleteServlet extends AbstractGenericServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt( req.getParameter("id"));

            PartnerService.getInstance().deletePartner(id);

        resp.sendRedirect("admin/support-us");
        return;

    }
}
