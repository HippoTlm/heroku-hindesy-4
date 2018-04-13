package services;

import daos.RentDao;
import entities.Rent;

import java.time.LocalDate;
import java.util.List;

/**
 * Interaction class between the DAO of the rents, and the associated servlets
 *
 * @see RentDao
 * @see servlets.RentServlet
 * @see servlets.AdminWelcomeServlet
 */
public class RentService {

    private RentDao rentDao = new RentDao();

    /**
     * Intern class that contains the static instance of RentService
     */
    private static class RentServiceHolder {
        private static RentService instance = new RentService();
    }

    /**
     * Returns the static instance of RentDao of RentServiceHolder
     * @return RentServiceHolder.instance
     */
    public static RentService getInstance() { return RentService.RentServiceHolder.instance; }

    /**
     * Returns a specific rent function of its identifier
     *
     * @param id the rent identifier
     * @return the instance of Rent requested
     */
    public Rent getRent(Integer id) { return rentDao.getRent(id); }

    /**
     * Returns the list of all rents in the DB
     * @return rentings the said list
     */
    public List<Rent> listAllComingRents() { return rentDao.listAllComingRentings(); }

    /**
     * Add a new rent in the DB
     *
     * @param ldDateDebut the date of the beginning of the rent, in LocalDate format
     * @param ldDateFin the date of the end of the rent, in LocalDate format
     * @param email the email address of the client
     */
    public void addRent(LocalDate ldDateDebut, LocalDate ldDateFin, String email) {
        rentDao.addRent(ldDateDebut, ldDateFin, email);
    }

    /**
     * Confirms a reservation stored in the database
     * @param id the reservation identifier
     */
    public void confirmReservation(Integer id) { rentDao.confirmReservation(); }

    /**
     * Returns the list of all reserved dates
     *
     * @return reservedDates the said list
     */
    public List<LocalDate> listReservedDates() { return rentDao.listReservedDates(); }
}
