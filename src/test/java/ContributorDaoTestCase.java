import daos.ContributorDao;
import daos.DataSourceProvider;
import entities.Contributor;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ContributorDaoTestCase {

    private ContributorDao dao = new ContributorDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM contributors");
            statement.executeUpdate("DELETE FROM fr_contributors");
            statement.executeUpdate("DELETE FROM en_contributors");

            statement.executeUpdate("INSERT INTO contributors(id, firstName, lastName, picture, idfr_contributors, iden_contributors) " +
                    "VALUES (1, 'Gabi', 'Prousty', '/photo1.png', 1, 1)");
            statement.executeUpdate("INSERT INTO fr_contributors(id, label) " +
                    "VALUES (1, 'Le gentil testeur')");
            statement.executeUpdate("INSERT INTO en_contributors(id, label) " +
                    "VALUES (1, 'The nice tester')");

            statement.executeUpdate("INSERT INTO contributors(id, firstName, lastName, picture, idfr_contributors, iden_contributors) " +
                    "VALUES (2, 'Hippo', 'Toule', '/photo2.png', 2, 2)");
            statement.executeUpdate("INSERT INTO fr_contributors(id, label) " +
                    "VALUES (2, 'Le super web designer')");
            statement.executeUpdate("INSERT INTO en_contributors(id, label) " +
                    "VALUES (2, 'The awesome web designer')");
        }
    }

    @Test
    public void shouldListAllContributorsFR() {
        // WHEN
        List<Contributor> list = dao.listAllContributorsFR();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "firstName", "lastName", "photo", "label").containsOnly(
                tuple(1, "Gabi", "Prousty", "/photo1.png", "Le gentil testeur"),
                tuple(2, "Hippo", "Toule", "/photo2.png", "Le super web designer")
        );
    }

    @Test
    public void shouldListAllContributorsEN() {
        // WHEN
        List<Contributor> list = dao.listAllContributorsEN();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "firstName", "lastName", "photo", "label").containsOnly(
                tuple(1, "Gabi", "Prousty", "/photo1.png", "The nice tester"),
                tuple(2, "Hippo", "Toule", "/photo2.png", "The awesome web designer")
        );
    }

    @Test
    public void shouldModifyContributor() {
        // GIVEN
        String request = "SELECT * FROM contributors AS c " +
                "INNER JOIN en_contributors AS ec ON c.iden_contributors = ec.id " +
                "INNER JOIN fr_contributors AS fc ON c.idfr_contributors = fc.id " +
                "WHERE c.id = 1";
        // WHEN
        dao.modifyContributor(1, "Gabriel", "Proust", "/photo3.png",
                "L'excellent testeur", "The outstanding tester");
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("c.id")).isEqualTo(1);
            assertThat(resultSet.getString("c.firstName")).isEqualTo("Gabriel");
            assertThat(resultSet.getString("c.lastName")).isEqualTo("Proust");
            assertThat(resultSet.getString("c.picture")).isEqualTo("/photo3.png");
            assertThat(resultSet.getString("fc.label")).isEqualTo("L'excellent testeur");
            assertThat(resultSet.getString("ec.label")).isEqualTo("The outstanding tester");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteContributor() {
        // GIVEN
        String request = "SELECT * FROM contributors AS c " +
                "INNER JOIN fr_contributors AS fc ON c.idfr_contributors = fc.id " +
                "INNER JOIN en_contributors AS ec ON c.iden_contributors = ec.id " +
                "WHERE c.id = 2";
        // WHEN
        dao.deleteContributor(2);
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("c.id")).isNotEqualTo(2);
                assertThat(resultSet.getInt("fc.id")).isNotEqualTo(2);
                assertThat(resultSet.getInt("ec.id")).isNotEqualTo(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetPicturePath() {
        // GIVEN
        Path thePath = Paths.get("/photo1.png");
        // WHEN
        Path path = dao.getPicturePath(1);
        // THEN
        assertThat(path).isEqualTo(thePath);
    }
}
