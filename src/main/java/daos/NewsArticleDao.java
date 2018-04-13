package daos;

import entities.NewsArticle;
import entities.NewsArticle;
import entities.NewsArticle;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the news articles
 *
 * @see NewsArticle
 * @see services.NewsArticleService
 */
public class NewsArticleDao {

    /**
     * Returns a specific news article function of its ID with its French and English content
     *
     * @param id the article identifier
     * @return the instance of NewsArticle requested
     */
    public NewsArticle getNewsArticle(Integer id) {
        String s = "SELECT * FROM news_article AS na " +
                "INNER JOIN fr_article AS fa ON na.idFR = fa.idFR_ARTICLE " +
                "INNER JOIN en_article AS ea ON na.idEN = ea.idEN_ARTICLE " +
                "WHERE na.id = ?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(s)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new NewsArticle(
                            resultSet.getInt("na.id"),
                            resultSet.getDate("na.date").toLocalDate(),
                            resultSet.getString("na.photo1"),
                            resultSet.getString("na.photo2"),
                            resultSet.getString("na.photo3"),
                            resultSet.getString("fa.titleFR"),
                            resultSet.getString("ea.titleEN"),
                            resultSet.getString("fa.contentFR"),
                            resultSet.getString("ea.contentEN")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the list of all news articles in the DB with both its French and English content
     *
     * @return newsArticles the said list
     */
    public List<NewsArticle> listNewsArticles() {
        List<NewsArticle> newsArticles = new ArrayList<>();
        String s = "SELECT * FROM news_article AS na " +
                "INNER JOIN fr_article AS fa ON na.idFR = fa.idFR_ARTICLE " +
                "INNER JOIN en_article AS ea ON na.idEN = ea.idEN_ARTICLE " +
                "ORDER BY date DESC";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    newsArticles.add(
                            new NewsArticle(
                                    resultSet.getInt("na.id"),
                                    resultSet.getDate("na.date").toLocalDate(),
                                    resultSet.getString("na.photo1"),
                                    resultSet.getString("na.photo2"),
                                    resultSet.getString("na.photo3"),
                                    resultSet.getString("fa.titleFR"),
                                    resultSet.getString("ea.titleEN"),
                                    resultSet.getString("fa.contentFR"),
                                    resultSet.getString("ea.contentEN")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsArticles;
    }

    /**
     * Modify the content of an article about the News in the DB
     *
     * @param id        the article identifier
     * @param photo1    the path of its first picture
     * @param photo2    the path of its second picture
     * @param photo3    the path of its third picture
     * @param titleFR   its french title
     * @param contentFR its french content
     * @param titleEN   its english title
     * @param contentEN its  english content
     */
    public void modifyArticle(Integer id, LocalDate date, String photo1, String photo2, String photo3,
                              String titleFR, String contentFR, String titleEN, String contentEN) {
        String request1 = "UPDATE news_article SET date = ?, photo1 = ?, photo2 = ?, photo3 = ? WHERE id = ? ";
        String request2 = "UPDATE fr_article SET titleFR = ?, contentFR = ? " +
                "WHERE idFR_ARTICLE = (SELECT idFR FROM News_article WHERE id= ?)";
        String request3 = "UPDATE en_article SET titleEN = ?, contentEN = ? " +
                "WHERE idEN_ARTICLE = (SELECT idEN FROM news_article WHERE id= ?) ";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setDate(1, Date.valueOf(date));
                statement.setString(2, photo1);
                statement.setString(3, photo2);
                statement.setString(4, photo3);
                statement.setInt(5, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request2)) {

                statement.setString(1, titleFR);
                statement.setString(2, contentFR);
                statement.setInt(3, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request3)) {

                statement.setString(1, titleEN);
                statement.setString(2, contentEN);
                statement.setInt(3, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new article about the News in the DB
     *
     * @param photo1    the picture path of the first article photo
     * @param photo2    the picture path of the second article photo
     * @param photo3    the picture path of the third article photo
     * @param titleFR   the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN   the English title of the article
     * @param contentEN the English content of the article
     */
    public void addArticle(LocalDate date, String photo1, String photo2, String photo3,
                           String titleFR, String contentFR, String titleEN, String contentEN) {

        //Insertion of the French content of the article in the table FR_ARTICLE
        String request1 = "INSERT INTO fr_article(idFR_ARTICLE, titleFR, contentFR) VALUES (((SELECT MAX(idFR_ARTICLE)FROM (SELECT * FROM fr_article) AS somethingFr )+1),?, ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setString(1, titleFR);
                statement.setString(2, contentFR);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Insertion of the english content of the article in the table EN_ARTICLE
        String request2 = "INSERT INTO en_article(idEN_ARTICLE, titleEN, contentEN) VALUES (((SELECT MAX(idEN_ARTICLE)FROM (SELECT * FROM en_article) AS somethingEn)+1),?, ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request2)) {
                statement.setString(1, titleEN);
                statement.setString(2, contentEN);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Insertion of a new NewsArticle in the DB thanks to its picture in the table News_ARTICLE
        String request3 = "INSERT INTO news_article(date, photo1, photo2, photo3, idFR, idEN) " +
                "VALUES (?, ?, ?, ?, (SELECT MAX(idFR_ARTICLE) FROM fr_article), " +
                "(SELECT MAX(idEN_ARTICLE) FROM en_article))";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request3)) {
                statement.setDate(1, Date.valueOf(date));
                statement.setString(2, photo1);
                statement.setString(3, photo2);
                statement.setString(4, photo3);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an article about the news in the DB
     * @param id the identifier of the article
     */
    public void deleteArticle(Integer id) {
        String request1 = "DELETE FROM fr_article WHERE idFR_ARTICLE = (SELECT idFR FROM news_article WHERE id=?)";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request1)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String request2 = "DELETE FROM en_article WHERE idEN_ARTICLE = (SELECT idEN FROM news_article WHERE id=?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request2)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String request3 = "DELETE FROM news_article WHERE id = ?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request3)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the path of the first picture of the article
     * @param id the article identifier
     * @return the path
     */
    public Path getPicturePath1(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT photo1 FROM news_article WHERE id= ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("photo1");
                    if (picturePath != null) {
                        return Paths.get(picturePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Returns the path of the second picture of the article
     * @param id the article identifier
     * @return the path
     */
    public Path getPicturePath2(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT photo2 FROM news_article WHERE id= ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("photo2");
                    if (picturePath != null) {
                        return Paths.get(picturePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Returns the path of the third picture of the article
     * @param id the article identifier
     * @return the path
     */
    public Path getPicturePath3(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT photo3 FROM news_article WHERE id= ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("photo3");
                    if (picturePath != null) {
                        return Paths.get(picturePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public String getTinyPosition() {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT ville FROM position WHERE id=1")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String position = resultSet.getString("ville");
                    if (position != null) {
                        return position;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public void updatePosition(String position) {

System.out.println("dao : "+position);
        String request1 = " UPDATE position\n" +
                "        SET ville = ?\n" +
                "        WHERE id=1";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request1)) {
            statement.setString(1, position);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}