package services;

import daos.NewsArticleDao;
import entities.NewsArticle;
import servlets.ArticlesServlet;


import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * Interaction class between the DAO of the articles about the News, and the associated servlet
 *
 * @see NewsArticleDao
 * @see ArticlesServlet
 */
public class NewsArticleService {

    private NewsArticleDao newsArticleDao = new NewsArticleDao();

    public String getTinyPosition() {
        return newsArticleDao.getTinyPosition();
    }

    /**
     * Intern class that contains the static instance of NewsArticleService
     */
    private static class NewsArticleServiceHolder {
        private static NewsArticleService instance = new NewsArticleService();
    }

    /**
     * Returns the static instance of NewsArticleDao of NewsARticlesServiceHolder
     * @return NewsArticleServiceHolder.instance
     */
    public static NewsArticleService getInstance() { return NewsArticleServiceHolder.instance; }

    /**
     * Returns a specific article in French about the News function of its ID
     * @param id the article ID
     * @return the NewsArticle instance requested
     */
    public NewsArticle getNewsArticle(Integer id) { return newsArticleDao.getNewsArticle(id); }

    /**
     * Returns the list of all articles in French about the News contained in the DB
     * @return NewsArticles the said list
     */
    public List<NewsArticle> listNewsArticles() { return newsArticleDao.listNewsArticles(); }

    /**
     * Modify the content of a news article in the DB
     *
     * @param id the article identifier
     * @param photo1 the path of its first picture
     * @param photo2 the path of its second picture
     * @param photo3 the path of its third picture
     * @param titleFR the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN the French title of the article
     * @param contentEN the French content of the article
     */
    public void modifyArticle(Integer id, LocalDate date, String photo1, String photo2, String photo3,
                              String titleFR, String contentFR, String titleEN, String contentEN) {
        newsArticleDao.modifyArticle(id, date, photo1, photo2, photo3, titleFR, contentFR, titleEN, contentEN);
    }

    /**
     * Add a new article about the News in the DB
     *
     * @param photo1 the picture path of the first article photo
     * @param photo2 the picture path of the second article photo
     * @param photo3 the picture path of the third article photo
     * @param titleFR the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN the French title of the article
     * @param contentEN the French content of the article
     */
    public void addArticle(LocalDate date, String photo1, String photo2, String photo3,
                           String titleFR, String contentFR, String titleEN, String contentEN) {
        newsArticleDao.addArticle(date, photo1, photo2, photo3, titleFR, contentFR, titleEN, contentEN);
    }

    /**
     * Delete an article about the News in the DB (in both News_ARTICLE and FR_ARTICLE tables)
     *
     * @param id the identifier of the article
     */
    public void deleteArticle(Integer id) {
        newsArticleDao.deleteArticle(id);
    }



    public Path getPicturePath1(Integer id) {
        Path picturePath = newsArticleDao.getPicturePath1(id);
        if (picturePath == null) {
            try {
                picturePath = Paths.get(this.getClass().getClassLoader().getResource("Authorphoto.jpg").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        return picturePath;
    }
    public Path getPicturePath2(Integer id) {
        Path picturePath = newsArticleDao.getPicturePath2(id);
        if (picturePath == null) {
            try {
                picturePath = Paths.get(this.getClass().getClassLoader().getResource("Authorphoto.jpg").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        return picturePath;
    }
    public Path getPicturePath3(Integer id) {
        Path picturePath = newsArticleDao.getPicturePath3(id);
        if (picturePath == null) {
            try {
                picturePath = Paths.get(this.getClass().getClassLoader().getResource("Authorphoto.jpg").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        return picturePath;
    }

    public void updatePosition(String posi) {

        System.out.println("service : "+posi);
        newsArticleDao.updatePosition(posi);
    }
}
