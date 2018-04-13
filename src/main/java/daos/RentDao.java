package daos;

import entities.Rent;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the interaction with the website DB about the rents
 *
 * @see entities.Rent
 * @see services.RentService
 */
public class RentDao {

    /**
     * Returns a specific rent function of its identifier
     *
     * @param id the rent identifier
     * @return the instance of Rent requested
     */
    public Rent getRent(Integer id) {
        String s = "SELECT * FROM rent WHERE id=?";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(s)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new Rent(
                            resultSet.getInt("id"),
                            resultSet.getDate("dateDebut").toLocalDate(),
                            resultSet.getDate("dateFin").toLocalDate(),
                            resultSet.getString("email"),
                            resultSet.getBoolean("confirmed")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the list of all future rentings in the DB
     * @return rentings the said list
     */
    public List<Rent> listAllComingRentings() {
        List<Rent> rentings = new ArrayList<>();
        String s = "SELECT * FROM rent ORDER BY rent.id";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(s)) {
                while (resultSet.next()) {
                    if (resultSet.getDate("dateDebut").toLocalDate().isAfter(LocalDate.now())) {
                        rentings.add(
                                new Rent(
                                        resultSet.getInt("id"),
                                        resultSet.getDate("dateDebut").toLocalDate(),
                                        resultSet.getDate("dateFin").toLocalDate(),
                                        resultSet.getString("email"),
                                        resultSet.getBoolean("confirmed")
                                )
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentings;
    }

    /**
     * Add a new rent in the DB
     *
     * @param ldDateDebut the date of the beginning of the rent, in LocalDate format
     * @param ldDateFin the date of the end of the rent, in LocalDate format
     * @param email the email address of the client of the rent
     */
    public void addRent(LocalDate ldDateDebut, LocalDate ldDateFin, String email) {

        // First, we convert the two java.time.LocalDate to java.sql.Date
        Date dateDebut = Date.valueOf(ldDateDebut);
        Date dateFin = Date.valueOf(ldDateFin);

        // Then, we send the fixed rent to the DB
        String request = "INSERT INTO rent(dateDebut, dateFin, email, confirmed) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                statement.setDate(1, dateDebut);
                statement.setDate(2, dateFin);
                statement.setString(3, email);
                statement.setBoolean(4, true);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Confirms the last reservation stored in the database
     */
    public void confirmReservation() {
        String request = "UPDATE rent SET confirmed = 1 WHERE id = (SELECT MAX(id) FROM rent)";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of all reserved dates
     * @return reservedDates the said list
     */
    public List<LocalDate> listReservedDates() {
        List<LocalDate> reservedDates = new ArrayList<>();

        String request = "SELECT dateDebut, dateFin FROM rent WHERE confirmed=true ORDER BY dateDebut";
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(request)) {
                while (resultSet.next()) {
                    LocalDate dateDebut = resultSet.getDate("dateDebut").toLocalDate();
                    LocalDate dateFin = resultSet.getDate("dateFin").toLocalDate();
                    LocalDate ld = dateDebut;
                    while (!ld.equals(dateFin)) {
                        reservedDates.add(ld);
                        ld = ld.plusDays(1);
                    }
                    // This one for dateFin
                    reservedDates.add(ld);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservedDates;
    }

    /**
     * Delete a renting in the BDD function of its identifier
     * @param id the renting identifier
     */
    public void deleteRent(Integer id) {

        String request = "DELETE * FROM rent WHERE id = ?";

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
