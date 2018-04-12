package servlets;

import entities.Contributor;
import entities.FormHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ContributorService;
import services.FormHelperService;
import servlets.AbstractGenericServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet("/helpers")
public class HelpersServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Contributor> ContributorsList = ContributorService.getInstance().listAllContributorsFR();
        context.setVariable("ContributorsList", ContributorsList);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("helpers", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // GET PARAMETERS
        String lastName = req.getParameter("lastName");
        String  firstName= req.getParameter("firstName");
        String shoesS = req.getParameter("shoes");
        String helmetS = req.getParameter("helmet");
        String glovesS = req.getParameter("gloves");
        String equipment = req.getParameter("equipment");
        String size = req.getParameter("size");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String message = req.getParameter("message");
        resp.setCharacterEncoding("UTF8");

        boolean shoes;
        if(shoesS.equals("true")){
            shoes=true;
        }else{shoes=false;};

boolean helmet;

        if(helmetS.equals("true")){
            helmet=true;
        }else{helmet=false;}

boolean gloves;

        if(glovesS.equals("true")){
            gloves=true;
        }else{gloves=false;}

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive
        int id = ThreadLocalRandom.current().nextInt(0, 65000000 + 1);

        // CREATION Helper
        FormHelper newFormHelper = new FormHelper(id,firstName, lastName, shoes, helmet, gloves, equipment, size, email, phone, message );

        try {
            FormHelperService.getInstance().addHelper(newFormHelper);

            req.getSession().setAttribute("ok",1 );
            resp.sendRedirect("helpers");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("FormHelperError", e.getMessage());
            req.getSession().setAttribute("ok",0 );

        }



    }
}
