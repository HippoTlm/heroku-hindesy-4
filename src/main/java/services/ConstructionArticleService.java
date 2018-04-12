package services;

import daos.ConstructionArticleDao;
import entities.ConstructionArticle;
import servlets.ConstructionEvolutionServlet;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * Interaction class between the DAO of the articles about the construction, and the associated servlet
 *
 * @see ConstructionArticleDao
 * @see ConstructionEvolutionServlet
 */
public class ConstructionArticleService {

    private ConstructionArticleDao constructionArticleDao = new ConstructionArticleDao();

    /**
     * Intern class that contains the static instance of ConstructionArticleService
     */
    private static class ConstructionArticleServiceHolder {
        private static ConstructionArticleService instance = new ConstructionArticleService();
    }

    /**
     * Returns the static instance of ConstructionArticleDao of ConstructionARticlesServiceHolder
     * @return ConstructionArticleServiceHolder.instance
     */
    public static ConstructionArticleService getInstance() { return ConstructionArticleServiceHolder.instance; }

    /**
     * Returns a specific article about the construction function of its ID
     * @param id the article ID
     * @return the ConstructionArticle instance requested
     */
    public ConstructionArticle getConstructionArticle(Integer id) {
        return constructionArticleDao.getConstructionArticle(id);
    }

    /**
     * Returns the list of all articles about the construction contained in the DB
     * @return constructionArticles the said list
     */
    public List<ConstructionArticle> listConstructionArticles() {
        return constructionArticleDao.listConstructionArticles(); }

    /**
     * Modify the content of an article about the construction in the DB
     *
     * @param id the article identifier
     * @param photo the path of its picture
     * @param titleFR the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN the French title of the article
     * @param contentEN the French content of the article
     */
    public void modifyArticle(Integer id, String photo, String titleFR, String contentFR,
                              String titleEN, String contentEN, LocalDate date ) {
        constructionArticleDao.modifyArticle(id, photo, titleFR, contentFR, titleEN, contentEN, date);
    }

    /**
     * Add a new article about the construction in the DB
     *
     * @param photo the picture path of the article photo
     * @param titleFR the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN the French title of the article
     * @param contentEN the French content of the article
     */
    public void addArticle(String photo, String titleFR, String contentFR, String titleEN, String contentEN, LocalDate date) {
        constructionArticleDao.addArticle(photo, titleFR, contentFR, titleEN, contentEN, date);
    }

    /**
     * Delete an article about the construction in the DB (in both CONSTRUCTION_ARTICLE and FR_ARTICLE tables)
     *
     * @param id the identifier of the article
     */
    public void deleteArticle(Integer id) {
        constructionArticleDao.deleteArticle(id);
    }


    public Path getPicturePath1(Integer id) {
        Path picturePath = constructionArticleDao.getPicturePath1(id);
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
