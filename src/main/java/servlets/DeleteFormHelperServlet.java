package servlets;

import services.AdminWelcomeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/deleteformhelper")
public class DeleteFormHelperServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));

        AdminWelcomeService.getInstance().deleteFormHelper(id);

        resp.sendRedirect("/admin/home");
    }
}
