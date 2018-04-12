package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.ClientService;
import services.RentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rent")
public class RentServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        // Sending the information about the current day to the context
        LocalDate today = LocalDate.now();
        int currentDay = today.getDayOfMonth();
        int currentMonth = today.getMonthValue();
        Month currentMonthName = today.getMonth();
        int currentYear = today.getYear();
        context.setVariable("currentDay", currentDay);
        context.setVariable("currentMonth", currentMonth);
        context.setVariable("currentMonthName", currentMonthName);
        context.setVariable("currentYear", currentYear);
        if (currentYear % 4 == 0) {
            context.setVariable("leap", true);
        } else {
            context.setVariable("leap", false);
        }

        List<LocalDate> allReservedDates = RentService.getInstance().listReservedDates();

        // Sending the reserved dates of the current month to the context
        List<Integer> reservedDates = new ArrayList<>();
        if (!allReservedDates.isEmpty()) {
            for (int i = 0; i < allReservedDates.size(); i++) {
                if (allReservedDates.get(i).getMonthValue() == currentMonth) {
                    reservedDates.add(allReservedDates.get(i).getDayOfMonth());
                }
            }
        }
        context.setVariable("reservedDates", reservedDates);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("rent", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Getting the parameters from the rent form
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String strDateDebut = req.getParameter("dateDebut");
        String strDateFin = req.getParameter("dateFin");

        // Conversion of some parameters so to fit in the DB
        LocalDate dateDebut = LocalDate.parse(strDateDebut);
        LocalDate dateFin = LocalDate.parse(strDateFin);

        /*
         * Checking if the rent is possible: it is if
         * - the day of the beginning is not before the current day
         * - the day of the beginning is not part of the reserved dates (which would mean that part or all of the
         *   dates would have already been reserved)
         */
        List<LocalDate> reservedDates = RentService.getInstance().listReservedDates();
        if (dateDebut.isAfter(LocalDate.now()) && !reservedDates.contains(dateDebut)) {
            // Sending the information relative to the rent to the DB
            RentService.getInstance().addRent(dateDebut, dateFin, email);
            ClientService.getInstance().addClient(firstName, lastName, email);
        } else {
            System.out.println("Error: location not possible");
        }

        resp.sendRedirect("payment");
    }
}
