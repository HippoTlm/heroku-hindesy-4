package daos;

import entities.ConstructionArticle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the construction articles
 *
 * @see ConstructionArticle
 * @see services.ConstructionArticleService
 */
public class ConstructionArticleDao {

    /**
     * Returns a specific article about the construction function of its ID with its French and English content
     *
     * @param id the article identifier
     * @return the instance of ConstructionArticle requested
     */
    public ConstructionArticle getConstructionArticle(Integer id) {
        String s = "SELECT * FROM construction_article AS ca " +
                "INNER JOIN fr_article AS fa ON ca.idFR = fa.idFR_ARTICLE " +
                "INNER JOIN en_article AS ea ON ca.idEN = ea.idEN_ARTICLE " +
                "WHERE ca.id = ?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(s)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new ConstructionArticle(
                            resultSet.getInt("ca.id"),
                            resultSet.getDate("ca.date").toLocalDate(),
                            resultSet.getString("fa.titleFR"),
                            resultSet.getString("ea.titleEN"),
                            resultSet.getString("fa.contentFR"),
                            resultSet.getString("ea.contentEN"),
                            resultSet.getString("ca.photo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the list of all articles in about the construction in the DB, with their French and English content
     *
     * @return constructionArticles the said list
     */
    public List<ConstructionArticle> listConstructionArticles() {
        List<ConstructionArticle> constructionArticles = new ArrayList<>();
        String s = "SELECT * FROM construction_article AS ca " +
                "INNER JOIN fr_article AS fa ON ca.idFR = fa.idFR_ARTICLE " +
                "INNER JOIN en_article AS ea ON ca.idEN = ea.idEN_ARTICLE " +
                "ORDER BY date DESC";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    constructionArticles.add(
                            new ConstructionArticle(
                                    resultSet.getInt("ca.id"),
                                    resultSet.getDate("ca.date").toLocalDate(),
                                    resultSet.getString("fa.titleFR"),
                                    resultSet.getString("ea.titleEN"),
                                    resultSet.getString("fa.contentFR"),
                                    resultSet.getString("ea.contentEN"),
                                    resultSet.getString("ca.photo")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionArticles;
    }

    /**
     * Modify the content of an article about the construction in the DB
     *
     * @param id        the article identifier
     * @param photo     the path of its picture
     * @param titleFR   its French title
     * @param titleEN   its English title
     * @param contentFR its French content
     * @param contentEN its English content
     */
    public void modifyArticle(Integer id, String photo, String titleFR, String contentFR,
                              String titleEN, String contentEN, LocalDate date) {

        String request1 = "UPDATE construction_article SET date = ?, photo = ? WHERE id = ? ";
        String request2 = "UPDATE fr_article SET titleFR = ?, contentFR = ? " +
                "WHERE idFR_ARTICLE = (SELECT idFR FROM construction_article WHERE id= ?)";
        String request3 = "UPDATE en_article SET titleEN = ?, contentEN = ? " +
                "WHERE idEN_ARTICLE = (SELECT idEN FROM construction_article WHERE id= ?) ";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setDate(1, Date.valueOf(date));
                statement.setString(2, photo);
                statement.setInt(3, id);
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
     * Add a new article about the construction in the DB
     *
     * @param photo     the picture path of the article photo
     * @param titleFR   the French title of the article
     * @param contentFR the French content of the article
     * @param titleEN   the english title of the article
     * @param contentEN the english content of the article
     */
    public void addArticle(String photo, String titleFR, String contentFR, String titleEN, String contentEN, LocalDate date) {

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


        //Insertion of the English content of the article in the table EN_ARTICLE

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



        //Insertion of a new ConstructionArticle in the DB thanks to its picture in the table CONSTRUCTION_ARTICLE

        String request3 = "INSERT INTO construction_article(photo, idFR, idEN, date) " +
                "VALUES (?,(SELECT MAX(idFR_ARTICLE) FROM fr_article),(SELECT MAX(idEN_ARTICLE) " +
                "FROM en_article),?)";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request3)) {
                statement.setString(1, photo);
                statement.setDate(2, Date.valueOf(date));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an article about the construction in the DB
     *
     * @param id the identifier of the article
     */
    public void deleteArticle(Integer id) {

        String request1 = "DELETE * FROM fr_article WHERE idFR_ARTICLE = (SELECT idFR FROM construction_article WHERE id=?)";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request1)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String request2 = "DELETE FROM en_article WHERE idEN_ARTICLE = (SELECT idEN FROM construction_article WHERE id=?)";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request2)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String request3 = "DELETE FROM construction_article WHERE id = ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request3)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the photo path of an article specified by its identifier
     * @param id the article identifier
     * @return the path of the picture
     */
    public Path getPicturePath(Integer id) {

        String request = "SELECT photo FROM construction_article WHERE id= ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("photo");
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
}