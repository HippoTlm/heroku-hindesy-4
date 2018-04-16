package daos;

import entities.FormHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the helpers form
 *
 * @see entities.FormHelper
 * @see services.AdminWelcomeService
 */
public class FormHelpersDao {

    /**
     * Returns the list of all people that filled the form of the webpage "Ils nous ont aidés !"
     * @return formHelpers the said list
     */
    public List<FormHelper> listAllFormHelpers() {
        List<FormHelper> formHelpers = new ArrayList<>();
        String s = "SELECT * FROM form_helpers ORDER BY id";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    formHelpers.add(
                            new FormHelper(
                                    resultSet.getInt("id"),
                                    resultSet.getString("firstname"),
                                    resultSet.getString("lastname"),
                                    resultSet.getBoolean("shoes"),
                                    resultSet.getBoolean("helmet"),
                                    resultSet.getBoolean("gloves"),
                                    resultSet.getString("equipments"),
                                    resultSet.getString("size"),
                                    resultSet.getString("email"),
                                    resultSet.getString("phone"),
                                    resultSet.getString("message")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formHelpers;
    }

    public void addHelper(FormHelper newHelper) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO form_helpers(firstName, lastName, shoes, " +
                     "helmet, gloves, equipments, size, email, phone, message) VALUES (?, ?, ?,?,?,?,?,?,?,?)")) {
            statement.setString(1, newHelper.getFirstName());
            statement.setString(2, newHelper.getLastName());
            statement.setBoolean(3, newHelper.hasShoes());
            statement.setBoolean(4, newHelper.hasHelmet());
            statement.setBoolean(5, newHelper.hasGloves());
            statement.setString(6, newHelper.getEquipments());
            statement.setString(7, newHelper.getSize());
            statement.setString(8, newHelper.getEmail());
            statement.setString(9, newHelper.getPhone());
            statement.setString(10, newHelper.getMessage());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a form helper in the BDD function of its identifier
     * @param id the form helper identifier
     */
    public void deleteFormHelper(Integer id) {

        String request = "DELETE FROM form_helpers WHERE id = ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
