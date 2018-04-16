import daos.ConstructionArticleDao;
import daos.DataSourceProvider;
import entities.ConstructionArticle;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ConstructionArticleDaoTestCase {

    private ConstructionArticleDao caDao = new ConstructionArticleDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM fr_article");
            statement.executeUpdate("DELETE FROM en_article");
            statement.executeUpdate("DELETE FROM construction_article");

            statement.executeUpdate("INSERT INTO construction_article(id, date, photo, iden, idfr) " +
                    "VALUES (1, '2018-02-19', '../../../img/logo-1.png', 1, 1)");
            statement.executeUpdate("INSERT INTO fr_article(idfr_article, titlefr, contentfr) " +
                    "VALUES (1, 'Titre article 1', 'Contenu article 1')");
            statement.executeUpdate("INSERT INTO en_article(iden_article, titleen, contenten) " +
                    "VALUES (1, 'Title article 1', 'Content article 1')");

            statement.executeUpdate("INSERT INTO construction_article(id, date, photo, iden, idfr) " +
                    "VALUES (2, '2018-02-19', '../../../img/bebe.png', 2, 2)");
            statement.executeUpdate("INSERT INTO fr_article(idfr_article, titlefr, contentfr) " +
                    "VALUES (2, 'Titre article 2', 'Contenu article 2')");
            statement.executeUpdate("INSERT INTO en_article(iden_article, titleen, contenten) " +
                    "VALUES (2, 'Title article 2', 'Content article 2')");
        }
    }

    @Test
    public void shouldGetConstructionArticle() throws Exception {
        // WHEN
        ConstructionArticle ca = caDao.getConstructionArticle(1);
        // THEN
        assertThat(ca.getId()).isEqualTo(1);
        assertThat(ca.getDate()).isEqualTo(LocalDate.of(2018, 2,19));
        assertThat(ca.getTitleFR()).isEqualTo("Titre article 1");
        assertThat(ca.getTitleEN()).isEqualTo("Title article 1");
        assertThat(ca.getContentFR()).isEqualTo("Contenu article 1");
        assertThat(ca.getContentEN()).isEqualTo("Content article 1");
        assertThat(ca.getPhoto()).isEqualTo("../../../img/logo-1.png");
    }

    @Test
    public void shouldListConstructionArticles() throws Exception {
        // WHEN
        List<ConstructionArticle> list = caDao.listConstructionArticles();
        // THEN
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list).extracting(
                "id",
                "date",
                "titleFR",
                "titleEN",
                "contentFR",
                "contentEN",
                "photo"
        ).containsOnly(
                tuple(
                        1,
                        LocalDate.of(2018, 2, 19),
                        "Titre article 1",
                        "Title article 1",
                        "Contenu article 1",
                        "Content article 1",
                        "../../../img/logo-1.png"
                ), tuple(
                        2,
                        LocalDate.of(2018, 2, 19),
                        "Titre article 2",
                        "Title article 2",
                        "Contenu article 2",
                        "Content article 2",
                        "../../../img/bebe.png"
                )

        );
    }

    @Test
    public void shouldModifyArticle() throws Exception {
        // GIVEN
        String request = "SELECT * FROM rent WHERE id = 1";
        // WHEN
        caDao.modifyArticle(
                1,
                "../../../img/bebe.png",
                "Oui",
                "Gros contenu",
                "Yes",
                "Huge content",
                LocalDate.of(2018, 2,19)
        );
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getString("photo")).isEqualTo("../../../img/bebe.png");
            assertThat(resultSet.getString("titleFR")).isEqualTo("Oui");
            assertThat(resultSet.getString("contentFR")).isEqualTo("Gros contenu");
            assertThat(resultSet.getString("titleEN")).isEqualTo("Yes");
            assertThat(resultSet.getString("contentEN")).isEqualTo("Huge content");
            assertThat(resultSet.getDate("date")).isEqualTo("2018-02-19");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAddArticle() throws Exception {
        // WHEN
        caDao.addArticle("somephoto", "Un article", "Nouveau contenu",
                "Some article", "New content",LocalDate.of(2018,02,19));
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM construction_article AS ca " +
                    "INNER JOIN fr_article AS fa ON fa.idfr_article = ea.iden_article " +
                    "INNER JOIN en_article AS ea ON ca.id = fa.idfr_article " +
                    "WHERE ca.id = (SELECT MAX(id) FROM ca)")) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt("ca.id")).isNotNull();
                assertThat(resultSet.getDate("ca.date")).isEqualTo("2018-02-19");
                assertThat(resultSet.getString("ca.photo")).isEqualTo("somephoto");
                assertThat(resultSet.getInt("fa.idfr_article")).isNotNull();
                assertThat(resultSet.getString("fa.titlefr")).isEqualTo("Un article");
                assertThat(resultSet.getString("fa.contentfr")).isEqualTo("Nouveau contenu");
                assertThat(resultSet.getInt("ea.iden_article")).isNotNull();
                assertThat(resultSet.getString("ea.titleen")).isEqualTo("Some article");
                assertThat(resultSet.getString("ea.contenten")).isEqualTo("New content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteArticle() {

        // WHEN
        caDao.deleteArticle(1);

        // GIVEN
        String request1 = "SELECT * FROM construction_article";
        String request2 = "SELECT * FROM fr_article";
        String request3 = "SELECT * FROM en_article";

        // THEN

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request1)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("id")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request2)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("id")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request3)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("id")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetPicturePath() {
        // GIVEN
        Path thePath = Paths.get("../../../img/logo-1.png");
        // WHEN
        Path path = caDao.getPicturePath(1);
        // THEN
        assertThat(path).isEqualTo(thePath);
    }
}
