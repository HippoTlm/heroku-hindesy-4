package services;

import daos.FormDonationDao;
import daos.FormHelpersDao;
import daos.FormPartnerDao;
import daos.RentDao;
import entities.FormDonation;
import entities.FormHelper;
import entities.FormPartner;
import entities.Rent;
import servlets.HelpersServlet;

import java.util.List;

/**
 * Interaction class between the FormHelpersDao, FormPartnerDao, and FormDonationDao, and the administrator welcome page
 *
 * @see daos.FormHelpersDao
 * @see daos.FormPartnerDao
 * @see daos.FormDonationDao
 * @see HelpersServlet
 */
public class AdminWelcomeService {

    private FormHelpersDao formHelpersDao = new FormHelpersDao();
    private FormPartnerDao formPartnerDao = new FormPartnerDao();
    private FormDonationDao formDonationDao = new FormDonationDao();
    private RentDao rentDao = new RentDao();

    /**
     * Intern class that contains the static instance of AdminWelcomeService
     */
    private static class AdminWelcomeServiceHolder {
        private static AdminWelcomeService instance = new AdminWelcomeService();
    }

    /**
     * Returns the static instance of AdminWelcomeService of AdminWelcomeServiceHolder
     * @return AdminWelcomeServiceHolder.instance
     */
    public static AdminWelcomeService getInstance() { return AdminWelcomeServiceHolder.instance; }

    /**
     * Returns the list of all people that filled the form of the webpage "Ils nous ont aidés !"
     * @return helpers the said list
     */
    public List<FormHelper> listAllFormHelpers() { return formHelpersDao.listAllFormHelpers(); }

    /**
     * Returns the list of all people that filled the form of the webpage "Ils nous ont aidés !"
     * @return helpers the said list
     */
    public List<FormPartner> listAllFormPartners() { return formPartnerDao.listAllFormPartners(); }

    /**
     * Returns the list of all people that filled the form of the webpage "Ils nous ont aidés !"
     * @return helpers the said list
     */
    public List<FormDonation> listAllFormDonations() { return formDonationDao.listAllFormDonations(); }

    /**
     * Returns the list of all rents in the DB
     * @return rents the said list
     */
    public List<Rent> listAllComingRentings() { return rentDao.listAllComingRentings(); }

    /**
     * Delete a form donation in the BDD function of its identifier
     * @param id the form donation identifier
     */
    public void deleteFormDonation(Integer id) { formDonationDao.deleteFormDonation(id); }

    /**
     * Delete a form helper in the BDD function of its identifier
     * @param id the form helper identifier
     */
    public void deleteFormHelper(Integer id) { formHelpersDao.deleteFormHelper(id); }

    /**
     * Delete a form partner in the BDD function of its identifier
     * @param id the form partner identifier
     */
    public void deleteFormPartner(Integer id) { formPartnerDao.deleteFormPartner(id); }

    /**
     * Delete a renting in the BDD function of its identifier
     * @param id the renting identifier
     */
    public void deleteRent(Integer id) { rentDao.deleteRent(id); }
}
