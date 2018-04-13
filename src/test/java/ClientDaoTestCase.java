import daos.ClientDao;
import daos.DataSourceProvider;
import entities.Client;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ClientDaoTestCase {

    private ClientDao clientDao = new ClientDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM client");

            statement.executeUpdate("INSERT INTO client(id, firstName, lastName, email) " +
                    "VALUES (1, 'Jean', 'Michel', 'jean.michel@hello.com')");
            statement.executeUpdate("INSERT INTO client(id, firstName, lastName, email) " +
                    "VALUES (2, 'Machin', 'Bidule', 'machin.bidule@hello.com')");
        }
    }

    @Test
    public void shouldGetClient() throws Exception {
        //WHEN
        Client client = clientDao.getClient(1);
        //THEN
        assertThat(client.getId()).isEqualTo(1);
        assertThat(client.getFirstName()).isEqualTo("Jean");
        assertThat(client.getLastName()).isEqualTo("Michel");
        assertThat(client.getEmail()).isEqualTo("jean.michel@hello.com");
    }

    @Test
    public void shouldAddClient() {
        // GIVEN
        String request = "SELECT * FROM client WHERE id = (SELECT MAX(id) FROM client)";
        // WHEN
        clientDao.addClient("Gabriel", "Proust", "gabriel.proust@hello.com");
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getString("firstName")).isEqualTo("Gabriel");
            assertThat(resultSet.getString("lastName")).isEqualTo("Proust");
            assertThat(resultSet.getString("email")).isEqualTo("gabriel.proust@hello.com");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldListClients() {
        // WHEN
        List<Client> clients = clientDao.listClients();
        // THEN
        assertThat(clients).hasSize(2);
        assertThat(clients).extracting("id", "firstName", "lastName", "email").containsOnly(
                tuple(1, "Jean", "Michel", "jean.michel@hello.com"),
                tuple(2, "Machin", "Bidule", "machin.bidule@hello.com")
        );
    }
}
