package daos;

import entities.FormDonation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the donation form
 *
 * @see entities.FormDonation
 * @see services.AdminWelcomeService
 */
public class FormDonationDao {

    /**
     * Returns the list of all the people that filled the donation form
     * @return formDonations the said list
     */
    public List<FormDonation> listAllFormDonations() {
        List<FormDonation> formDonations = new ArrayList<>();
        String s = "SELECT * FROM form_donation ORDER BY id";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    formDonations.add(
                            new FormDonation(
                                    resultSet.getInt("id"),
                                    resultSet.getDouble("amount"),
                                    resultSet.getString("email"),
                                    resultSet.getString("civility"),
                                    resultSet.getString("firstName"),
                                    resultSet.getString("lastName"),
                                    resultSet.getString("address"),
                                    resultSet.getString("addressCompl"),
                                    resultSet.getString("postalCode"),
                                    resultSet.getString("city"),
                                    resultSet.getString("country"),
                                    resultSet.getString("phone"),
                                    resultSet.getDate("birthDate").toLocalDate(),
                                    resultSet.getBoolean("fiscalReceipt")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formDonations;
    }

    /**
     * Delete a form donation in the BDD function of its identifier
     * @param id the form donation identifier
     */
    public void deleteFormDonation(Integer id) {

        String request = "DELETE FROM form_donation WHERE id = ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
