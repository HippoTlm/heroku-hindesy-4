package daos;

import entities.FormPartner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the partnership form
 *
 * @see entities.FormPartner
 * @see services.AdminWelcomeService
 */
public class FormPartnerDao {


    /**
     * Returns the list of all the people that filled the partner form in the page "Nos partenaires"
     * @return formPartners the said list
     */
    public List<FormPartner> listAllFormPartners() {
        List<FormPartner> formPartners = new ArrayList<>();
        String s = "SELECT * FROM form_partners ORDER BY id";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    formPartners.add(
                            new FormPartner(
                                    resultSet.getInt("id"),
                                    resultSet.getString("firstName"),
                                    resultSet.getString("lastName"),
                                    resultSet.getString("organization"),
                                    resultSet.getString("email"),
                                    resultSet.getString("phone"),
                                    resultSet.getString("message")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formPartners;
    }
    public void addPartner(FormPartner newPartner) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO form_partners(firstName, lastName, organization, email, phone, message) VALUES (?, ?, ?, ?,?,?)")) {
            statement.setString(1, newPartner.getFirstName());
            statement.setString(2, newPartner.getLastName());
            statement.setString(3, newPartner.getOrganization());
            statement.setString(4, newPartner.getEmail());
            statement.setString(5, newPartner.getPhone());
            statement.setString(6, newPartner.getMessage());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a form partner in the BDD function of its identifier
     * @param id the form partner identifier
     */
    public void deleteFormPartner(Integer id) {

        String request = "DELETE * FROM form_partner WHERE id = ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
