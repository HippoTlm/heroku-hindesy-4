package daos;

import entities.Contributor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContributorDao {

    /**
     * Returns the list of all contributors of the Hindesy project with their French label
     * @return Contributors the said list
     */
    public List<Contributor> listAllContributorsFR() {
        List<Contributor> Contributors = new ArrayList<>();
        String s = "SELECT * FROM contributors " +

                "INNER JOIN fr_contributors ON contributors.idFR_CONTRIBUTORS = fr_contributors.id" +
                " ORDER BY contributors.id";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    Contributors.add(
                            new Contributor(
                                    resultSet.getInt("contributors.id"),
                                    resultSet.getString("contributors.firstName"),
                                    resultSet.getString("contributors.lastName"),
                                    resultSet.getString("contributors.picture"),
                                    resultSet.getString("fr_contributors.label")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Contributors;
    }

    /**
     * Returns the list of all contributors of the Hindesy project with their English label
     * @return Contributors the said list
     */
    public List<Contributor> listAllContributorsEN() {
        List<Contributor> Contributors = new ArrayList<>();
        String s = "SELECT * FROM contributors " +

                "INNER JOIN en_contributors ON contributors.idEN_CONTRIBUTORS = en_contributors.id" +
                " ORDER BY contributors.id";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    Contributors.add(
                            new Contributor(
                                    resultSet.getInt("contributors.id"),
                                    resultSet.getString("contributors.firstName"),
                                    resultSet.getString("contributors.lastName"),
                                    resultSet.getString("contributors.picture"),
                                    resultSet.getString("en_contributors.label")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Contributors;
    }

    /**
     * Modify a contributor in the database
     * @param id his identifier
     * @param firstName his first name
     * @param lastName his last name
     * @param photo his picture
     * @param fr_label his French label
     * @param en_label his English label
     */
    public void modifyContributor(Integer id, String firstName, String lastName, String photo,
                                  String fr_label, String en_label) {
        String request1 = "UPDATE contributors SET firstName = ?, lastName = ?, picture = ? WHERE id = ? ";
        String request2 = "UPDATE fr_contributors SET label = ? WHERE id = (SELECT idFR_CONTRIBUTORS FROM contributors WHERE id = ?)";
        String request3 = "UPDATE en_contributors SET label = ? WHERE id = (SELECT idEN_CONTRIBUTORS FROM contributors WHERE id = ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, photo);
                statement.setInt(4, id);
                statement.executeUpdate();
            } try (PreparedStatement statement = connection.prepareStatement(request2)) {
                statement.setString(1, fr_label);
                statement.setInt(2, id);
                statement.executeUpdate();
            } try (PreparedStatement statement = connection.prepareStatement(request3)) {
                statement.setString(1, en_label);
                statement.setInt(2, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a contributor in the data base.
     * @param firstName his first name
     * @param lastName his last name
     * @param photo his picture
     * @param fr_label his French label
     * @param en_label his English label
     */
    public void addContributor(String firstName, String lastName, String photo, String fr_label, String en_label) {

        String request1 = "INSERT INTO contributors(firstName, lastName, picture) VALUES (?, ?, ?)";
        String request2 = "INSERT INTO fr_contributors(label) VALUES (?)";
        String request3 = "INSERT INTO en_contributors(label) VALUES (?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, photo);
                statement.executeUpdate();
            } try (PreparedStatement statement = connection.prepareStatement(request2)) {
                statement.setString(1, fr_label);
                statement.executeUpdate();
            } try (PreparedStatement statement = connection.prepareStatement(request3)) {
                statement.setString(1, en_label);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a contributor from the DB
     * @param id the contributor identifier
     */
    public void deleteContributor(Integer id) {

        String request1 = "DELETE FROM fr_contributors WHERE id = (SELECT idFR_CONTRIBUTORS FROM contributors WHERE id = ?)";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request1)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String request2 = "  DELETE FROM contributors WHERE id = ?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request2)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Path getPicturePath(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT picture FROM contributors WHERE id= ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("picture");
                    if (picturePath != null) {
                        return Paths.get(picturePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
