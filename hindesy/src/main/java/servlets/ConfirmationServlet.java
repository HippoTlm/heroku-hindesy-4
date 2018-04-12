package servlets;

import daos.RentDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirmation")
public class ConfirmationServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        // Confirmation of the reservation in the database
        RentDao rentDao = new RentDao();
        rentDao.confirmReservation();

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("confirmation", context, resp.getWriter());
    }
}
