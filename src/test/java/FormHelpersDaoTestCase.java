import daos.DataSourceProvider;
import daos.FormHelpersDao;
import entities.FormHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class FormHelpersDaoTestCase {

    private FormHelpersDao dao = new FormHelpersDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM form_helpers");

            statement.executeUpdate("INSERT INTO form_helpers(id, firstName, lastName, shoes, helmet, gloves, " +
                    "equipments, size, email, phone, message) VALUES (1, 'Gabi', 'Prousty', true, true, true, 'Non', " +
                    "'45', 'gabi@hello.com', '0123456789', 'Bonjour')");
            statement.executeUpdate("INSERT INTO form_helpers(id, firstName, lastName, shoes, helmet, gloves, " +
                    "equipments, size, email, phone, message) VALUES (2, 'Hippo', 'Toule', false, false, false, " +
                    "'Oui', '45', 'hippo@hello.com', '0987654321', 'Salut')");
        }
    }

    @Test
    public void shouldListAllFormHelpers() {
        // WHEN
        List<FormHelper> list = dao.listAllFormHelpers();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "firstName", "lastName", "shoes", "helmet", "gloves", "equipments", "size",
                "email", "phone", "message").containsOnly(
                        tuple(1, "Gabi", "Prousty", true, true, true, "Non", "45",
                                "gabi@hello.com", "0123456789", "Bonjour"),
                        tuple(2, "Hippo", "Toule", false, false, false, "Oui", "45",
                                "hippo@hello.com", "0987654321", "Salut")
                );
    }

    @Test
    public void shouldDeleteFormHelper() {
        // GIVEN
        String request = "SELECT * FROM form_helpers";
        // WHEN
        dao.deleteFormHelper(1);
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("id")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
