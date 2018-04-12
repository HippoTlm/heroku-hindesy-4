package services;

import daos.PartnerDao;
import entities.Partner;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Interaction class between the DAO of the commercial partners of the Hindesy project, and the associated servlets
 *
 * @see PartnerDao
 * @see servlets.SupportUsServlet
 * @see servlets.AdminSupportUsServlet
 */
public class PartnerService {

    private PartnerDao partnerDao = new PartnerDao();

    /**
     * Intern class that contains the static instance of PartnerService
     */
    private static class PartnerServiceHolder {
        private static PartnerService instance = new PartnerService();
    }

    /**
     * Returns the static instance of PartnerDao of PartnerServiceHolder
     * @return PartnerServiceHolder.instance
     */
    public static PartnerService getInstance() { return PartnerService.PartnerServiceHolder.instance; }

    /**
     * Returns the list of all commercial partners of the Hindesy project
     * @return partners the said list
     */
    public List<Partner> listAllPartners() { return partnerDao.listAllPartners(); }

    /**
     * Modify the path of the picture of a commercial partner in the DB
     *
     * @param name the name of the partner
     * @param picPath the new path of the partner picture
     */
    public void modifyPartner(String name, String picPath) {
        partnerDao.modifyPartner(name, picPath);
    }

    /**
     * Add a new commercial partner in the DB
     *
     * @param name the name of the partner
     * @param picPath the path of the partner picture
     */
    public void addPartner(String name, String picPath) {
        partnerDao.addPartner(name, picPath);
    }

    /**
     * Delete a commercial partner from the DB
     *
     * @param id the name of the partner
     */
    public void deletePartner(Integer id) {
        partnerDao.deletePartner(id);
    }


    public Path getPicturePath(Integer id) {
        Path picturePath = partnerDao.getPicturePath(id);
        if (picturePath == null) {
            try {
                picturePath = Paths.get(this.getClass().getClassLoader().getResource("Authorphoto.jpg").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        return picturePath;
    }
}
