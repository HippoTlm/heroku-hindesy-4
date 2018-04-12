package services;

import daos.ContributorDao;
import entities.Contributor;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ContributorService {


    private ContributorDao ContributorDao = new ContributorDao();

    /**
     * Intern class that contains the static instance of ContributorService
     */
    private static class ContributorServiceHolder {
        private static ContributorService instance = new ContributorService();
    }

    /**
     * Returns the static instance of ContributorDao of ContributorServiceHolder
     *
     * @return ContributorServiceHolder.instance
     */
    public static ContributorService getInstance() {
        return ContributorService.ContributorServiceHolder.instance;
    }

    /**
     * Returns the list of all commercial Contributors of the Hindesy project
     *
     * @return Contributors the said list
     */
    public List<Contributor> listAllContributorsFR() {
        return ContributorDao.listAllContributorsFR();
    }

    public List<Contributor> listAllContributorsEN() {
        return ContributorDao.listAllContributorsEN();
    }

    /**
     * Modify the path of the picture of a commercial Contributor in the DB
     */
    public void modifyContributor(Integer id, String firstName, String lastName, String photo, String fr_label, String en_label) {
        ContributorDao.modifyContributor(id, firstName, lastName, photo, fr_label, en_label);
    }

    /**
     * Add a new commercial Contributor in the DB
     */
    public void addContributor(String firstName, String lastName, String photo, String fr_label) {
        ContributorDao.addContributor(firstName, lastName, photo, fr_label);
    }

    /**
     * Delete a commercial Contributor from the DB
     */
    public void deleteContributor(Integer id) {
        ContributorDao.deleteContributor(id);
    }


    public Path getPicturePath(Integer id) {
        Path picturePath = ContributorDao.getPicturePath(id);
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