package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Adminfiltre implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String identifiant = (String) httpRequest.getSession().getAttribute("adminConnecte");
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if(identifiant == null || "".equals(identifiant)) {

            httpResponse.sendRedirect("../connexion");
            return;

        }else

        chain.doFilter(request, response);

    }



    @Override
    public void destroy() {

    }
}
