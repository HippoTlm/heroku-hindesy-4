package daos;

import entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the clients
 *
 * @see entities.Client
 * @see services.ClientService
 */
public class ClientDao {

    /**
     * Returns a specific customer function of his identifier
     *
     * @param id the client identifier
     * @return the instance of Client requested
     */
    public Client getClient(Integer id) {
        String s = "SELECT * FROM client WHERE id=?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(s)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add a new client in the DB
     *
     * @param firstName the client's first name
     * @param lastName the client's last name
     * @param email the email address of the client
     */
    public void addClient(String firstName, String lastName, String email) {
        String request = "INSERT INTO client(firstName, lastName, email) VALUES (?, ?, ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of all clients in the DB
     *
     * @return clients the said list
     */
    public List<Client> listClients() {
        List<Client> clients = new ArrayList<>();
        String s = "SELECT * FROM client ORDER BY client.id";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    clients.add(
                            new Client(
                                    resultSet.getInt("id"),
                                    resultSet.getString("firstName"),
                                    resultSet.getString("lastName"),
                                    resultSet.getString("email")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
