import daos.DataSourceProvider;
import daos.FormPartnerDao;
import entities.FormPartner;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class FormPartnerDaoTestCase {

    private FormPartnerDao dao = new FormPartnerDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM form_partners");

            statement.executeUpdate("INSERT INTO form_partners(id, firstName, lastName, organization, " +
                    " email, phone, message) VALUES (1, 'Gabi', 'Prousty', 'Microsoft', 'gabi@hello.com', " +
                    "'0123456789', 'Bonjour')");
            statement.executeUpdate("INSERT INTO form_partners(id, firstName, lastName, organization, " +
                    " email, phone, message) VALUES (2, 'Hippo', 'Toule', 'Apple', 'hippo@hello.com', " +
                    "'0987654321', 'Salut')");
        }
    }

    @Test
    public void shouldListAllFormPartners() {
        // WHEN
        List<FormPartner> list = dao.listAllFormPartners();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "firstName", "lastName", "organization",
                "email", "phone", "message").containsOnly(
                tuple(1, "Gabi", "Prousty", "Microsoft", "gabi@hello.com",
                        "0123456789", "Bonjour"),
                tuple(2, "Hippo", "Toule", "Apple", "hippo@hello.com",
                        "0987654321", "Salut")
        );
    }

    @Test
    public void shouldDeleteFormPartner() {
        // GIVEN
        String request = "SELECT * FROM form_partners";
        // WHEN
        dao.deleteFormPartner(1);
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
