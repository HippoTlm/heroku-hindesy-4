package daos;

import entities.Contributor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContributorDao {



    /**
     * Returns the list of all commercial Contributors of the Hindesy project
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
     * Modify the path of the picture of a commercial Contributor in the DB
     *
     */
    public void modifyContributor(Integer id, String firstName, String lastName, String photo, String fr_label, String en_label) {
        String request = "UPDATE contributors SET firstName = ?, lastName = ?, picture = ? WHERE id = ? " +
            "UPDATE fr_contributor SET label = ? WHERE id = (SELECT idFR_CONTRIBUTORS FROM contributors WHERE id= ?) "+
            "UPDATE en_contributor SET label = ? WHERE id = (SELECT idEN_CONTRIBUTORS FROM contributors WHERE id= ?) ";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {

                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, photo);
                statement.setInt(4, id);
                statement.setString(5, fr_label);
                statement.setInt(6, id);
                statement.setString(7, en_label);
                statement.setInt(8, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new commercial Contributor in the DB
     *
     */
    public void addContributor( String firstName, String lastName, String photo, String fr_label) {
        //Insertion of the french content of the contributor in the table FR_contributor
        /*Integer newId =0;
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM fr_contributors")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer idFromTable = resultSet.getInt("id");
                        newId=idFromTable+1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

        String request2 = "INSERT INTO fr_contributors(label) " +
                "VALUES (?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request2)) {
                statement.setString(1, fr_label);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*
        //Insertion of the english content of the contributor in the table EN_contributor
        String request3 = "INSERT INTO en_contributors(label) " +
                "VALUES (?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request3)) {
                statement.setString(1, en_label);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*///
        //Insertion of a new Newscontributor in the DB thanks to its picture in the table News_contributor
        String request1 = "INSERT INTO contributors(firstName, lastName, picture, idFR_CONTRIBUTORS, idEN_CONTRIBUTORS) " +
                "VALUES (?, ?, ?, (SELECT MAX(id) FROM fr_contributors),(SELECT MAX(id) FROM en_contributors))";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request1)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, photo);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete a commercial Contributor from the DB
     *
     * @param id the name of the Contributor
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
