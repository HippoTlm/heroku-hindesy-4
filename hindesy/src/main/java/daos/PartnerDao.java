package daos;

import entities.Partner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartnerDao {

    /**
     * Returns the list of all commercial partners of the Hindesy project
     * @return partners the said list
     */
    public List<Partner> listAllPartners() {
        List<Partner> partners = new ArrayList<>();
        String s = "SELECT * FROM partners ORDER BY name";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    partners.add(
                            new Partner(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("logo")
                            ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partners;
    }

    /**
     * Modify the path of the picture of a commercial partner in the DB
     *
     * @param name the name of the partner
     * @param picPath the new path of the partner picture
     */
    public void modifyPartner(String name, String picPath) {
        String request = "UPDATE partner SET picPath = ? WHERE name = ? ";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                statement.setString(1, picPath);
                statement.setString(2, name);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new commercial partner in the DB
     *
     * @param name the name of the partner
     * @param picPath the path of the partner picture
     */
    public void addPartner(String name, String picPath) {
        String request = "INSERT INTO partners (name,logo) VALUES (?, ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                statement.setString(1, name);
                statement.setString(2, picPath);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a commercial partner from the DB
     *
     * @param id the name of the partner
     */
    public void deletePartner(Integer id) {
        String request = "DELETE FROM partners WHERE id = ?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Path getPicturePath(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT logo FROM partners WHERE id= ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("logo");
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
