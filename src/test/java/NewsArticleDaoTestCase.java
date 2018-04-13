import daos.DataSourceProvider;
import daos.NewsArticleDao;
import entities.NewsArticle;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class NewsArticleDaoTestCase {

    private NewsArticleDao newsArticleDao = new NewsArticleDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM fr_article");
            statement.executeUpdate("DELETE FROM en_article");
            statement.executeUpdate("DELETE FROM news_article");

            statement.executeUpdate("INSERT INTO news_article(id, date, photo1, photo2, photo3, idfr, iden) " +
                    "VALUES (1, '2018-02-19', '/photo1.png', '/photo2.png', '/photo3.png', 1, 1)");
            statement.executeUpdate("INSERT INTO fr_article(idfr_article, titlefr, contentfr) " +
                    "VALUES (1, 'Titre article 1', 'Contenu article 1')");
            statement.executeUpdate("INSERT INTO en_article(iden_article, titleen, contenten) " +
                    "VALUES (1, 'Title article 1', 'Content article 1')");

            statement.executeUpdate("INSERT INTO news_article(id, date, photo1, photo2, photo3, idfr, iden) " +
                    "VALUES (2, '2018-02-20', '/photo4.png', '/photo5.png', '/photo6.png', 2, 2)");
            statement.executeUpdate("INSERT INTO fr_article(idfr_article, titlefr, contentfr) " +
                    "VALUES (2, 'Titre article 2', 'Contenu article 2')");
            statement.executeUpdate("INSERT INTO en_article(iden_article, titleen, contenten) " +
                    "VALUES (2, 'Title article 2', 'Content article 2')");
        }
    }

    @Test
    public void shouldGetNewsArticle() throws Exception {
        //WHEN
        NewsArticle newsArticle = newsArticleDao.getNewsArticle(1);
        //THEN
        assertThat(newsArticle.getId()).isEqualTo(1);
        assertThat(newsArticle.getDate()).isEqualTo(LocalDate.of(2018,2,19));
        assertThat(newsArticle.getPhoto1()).isEqualTo("/photo1.png");
        assertThat(newsArticle.getPhoto2()).isEqualTo("/photo2.png");
        assertThat(newsArticle.getPhoto3()).isEqualTo("/photo3.png");
        assertThat(newsArticle.getTitleFR()).isEqualTo("Titre article 1");
        assertThat(newsArticle.getTitleEN()).isEqualTo("Title article 1");
        assertThat(newsArticle.getContentFR()).isEqualTo("Contenu article 1");
        assertThat(newsArticle.getContentEN()).isEqualTo("Content article 1");
    }

    @Test
    public void shouldListNewsArticle() {
        // WHEN
        List<NewsArticle> list = newsArticleDao.listNewsArticles();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "date", "photo1", "photo2", "photo3",
                "titleFR", "titleEN", "contentFR", "contentEN").containsOnly(
                        tuple(1, LocalDate.of(2018,2,19),
                                "/photo1.png", "/photo2.png", "/photo3.png",
                                "Titre article 1", "Title article 1", "Contenu article 1", "Content article 1"),
                        tuple(2, LocalDate.of(2018,2,20),
                                "/photo4.png", "/photo5.png", "/photo6.png",
                                "Titre article 2", "Title article 2", "Contenu article 2", "Content article 2")
        );
    }

    @Test
    public void shouldModifyArticle() {
        // GIVEN
        String request = "SELECT * FROM news_article AS na " +
                "INNER JOIN fr_article AS fa ON na.idFR_ARTICLE = fa.idFR_ARTICLE " +
                "INNER JOIN en_article AS ea ON na.idFR_ARTICLE = ea.idEN_ARTICLE " +
                "WHERE id = 1";
        // WHEN
        newsArticleDao.modifyArticle(1, LocalDate.of(2038,4,16),
                "/photo11.png", "/photo22.png", "/photo33.png", "Nouveau titre",
                "Nouveau contenu", "New title", "New content");
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isEqualTo(1);
            assertThat(resultSet.getDate("date")).isEqualTo("2038-04-16");
            assertThat(resultSet.getString("photo1")).isEqualTo("/photo11.png");
            assertThat(resultSet.getString("photo2")).isEqualTo("/photo22.png");
            assertThat(resultSet.getString("photo3")).isEqualTo("/photo33.png");
            assertThat(resultSet.getString("titleFR")).isEqualTo("Nouveau titre");
            assertThat(resultSet.getString("contentFR")).isEqualTo("Nouveau contenu");
            assertThat(resultSet.getString("titleEN")).isEqualTo("New title");
            assertThat(resultSet.getString("contentEN")).isEqualTo("New content");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAddArticle() {
        // GIVEN
        String request = "SELECT * FROM news_article AS na " +
                "INNER JOIN fr_article AS fa ON na.idfr_article = fa.idfr_article " +
                "INNER JOIN en_article AS ea ON na.iden_article = ea.iden_article";
        // WHEN
        newsArticleDao.addArticle(LocalDate.now(), "/one.png", "/two.png", "three.png",
                "Un titre", "Du contenu", "Some title", "Some content");
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getDate("date")).isEqualTo(LocalDate.now());
            assertThat(resultSet.getString("photo1")).isEqualTo("/one.png");
            assertThat(resultSet.getString("photo2")).isEqualTo("/two.png");
            assertThat(resultSet.getString("photo3")).isEqualTo("/three.png");
            assertThat(resultSet.getString("titleFR")).isEqualTo("Un titre");
            assertThat(resultSet.getString("contentFR")).isEqualTo("Un contenu");
            assertThat(resultSet.getString("titleEN")).isEqualTo("Some title");
            assertThat(resultSet.getString("contentEN")).isEqualTo("Some content");
            assertThat(resultSet.next()).isFalse();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteArticle() {
        // GIVEN
        String request = "SELECT * FROM news_article AS na " +
                "INNER JOIN fr_article AS fa ON na.idfr_article = fa.idfr_article " +
                "INNER JOIN en_article AS ea ON na.iden_article = ea.iden_article";
        // WHEN
        newsArticleDao.deleteArticle(1);
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("na.id")).isNotEqualTo(1);
                assertThat(resultSet.getInt("fa.idfr_article")).isNotEqualTo(1);
                assertThat(resultSet.getInt("ea.iden_article")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetPicturePath1() {
        // GIVEN
        Path thePath = Paths.get("/photo1.png");
        // WHEN
        Path path = newsArticleDao.getPicturePath1(1);
        // THEN
        assertThat(path).isEqualTo(thePath);
    }

    @Test
    public void shouldGetPicturePath2() {
        // GIVEN
        Path thePath = Paths.get("/photo2.png");
        // WHEN
        Path path = newsArticleDao.getPicturePath2(1);
        // THEN
        assertThat(path).isEqualTo(thePath);
    }

    @Test
    public void shouldGetPicturePath3() {
        // GIVEN
        Path thePath = Paths.get("/photo3.png");
        // WHEN
        Path path = newsArticleDao.getPicturePath3(1);
        // THEN
        assertThat(path).isEqualTo(thePath);
    }
}
