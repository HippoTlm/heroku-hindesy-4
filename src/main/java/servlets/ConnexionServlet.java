package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import servlets.AbstractGenericServlet;
import util.MotDePasseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/connexion")
public class ConnexionServlet extends AbstractGenericServlet {

    private Map<String, String> utilisateursAutorises;

    @Override
    public void init() throws ServletException {
        utilisateursAutorises = new HashMap<>();
        utilisateursAutorises.put("Admin", "$argon2i$v=19$m=65536,t=2,p=1$hhH2Uu3Kq2pleEa9C7h+9w$Ekpzkl3NTFv1icfbu8kD36Zdnd9MgPO5rBJqd74G0+k");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("connexion", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginForm = req.getParameter("login");
        String pwdForm = req.getParameter("password");
        if(utilisateursAutorises.containsKey(loginForm)
                && MotDePasseUtils.validerMotDePasse(pwdForm, utilisateursAutorises.get(loginForm))) {
            req.getSession().setAttribute("adminConnecte", loginForm.toUpperCase());
            resp.sendRedirect("admin/home");
        }else{
        System.out.println("le login est: "+loginForm);
        System.out.println("le pwd est: "+pwdForm);
        resp.sendRedirect("connexion");
    }}
}
