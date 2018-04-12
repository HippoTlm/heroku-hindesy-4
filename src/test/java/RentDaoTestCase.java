import daos.DataSourceProvider;
import daos.RentDao;
import entities.Rent;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class RentDaoTestCase {

    private RentDao rentDao = new RentDao();

    @Before
    public void initDatabase() throws Exception {

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM rent");

            statement.executeUpdate("INSERT INTO rent(id, dateDebut, dateFin, email, confirmed) " +
                    "VALUES (1, '2018-09-19', '2018-09-20', 'monsieur@hello.com', '1')");
            statement.executeUpdate("INSERT INTO rent(id, dateDebut, dateFin, email, confirmed) " +
                    "VALUES (2, '2018-10-19', '2018-10-20', 'madame@hello.com', '0')");
        }
    }

    @Test
    public void shouldGetRent() {
        // WHEN
        Rent rent = rentDao.getRent(1);
        // THEN
        assertThat(rent.getId().equals(1));
        assertThat(rent.getDateDebut().equals(LocalDate.of(2018, 9,19)));
        assertThat(rent.getDateFin().equals(LocalDate.of(2018,9,20)));
        assertThat(rent.getEmail().equals("monsieur@hello.com"));
    }

    @Test
    public void shouldListRent() {
        // WHEN
        List<Rent> rents = rentDao.listRents();
        // THEN
        assertThat(rents).hasSize(2);
        assertThat(rents).extracting("id", "dateDebut", "dateFin", "email").containsOnly(
                tuple(
                        1,
                        LocalDate.of(2018,9,19),
                        LocalDate.of(2018, 9,20),
                        "monsieur@hello.com"
                ), tuple(
                        2,
                        LocalDate.of(2018,10,19),
                        LocalDate.of(2018, 10,20),
                        "madame@hello.com"
                )
        );
    }

    @Test
    public void shouldAddRent() {
        // GIVEN
        LocalDate dateDebut = LocalDate.of(2018, 11, 19);
        LocalDate dateFin = LocalDate.of(2018,11,20);
        String request = "SELECT * FROM rent WHERE id = (SELECT MAX(id) FROM rent)";
        // WHEN
        rentDao.addRent(dateDebut, dateFin, "smth@hello.com");
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getDate("dateDebut")).isEqualTo("2018-11-19");
            assertThat(resultSet.getDate("dateFin")).isEqualTo("2018-11-20");
            assertThat(resultSet.getString("email")).isEqualTo("smth@hello.com");
            assertThat(resultSet.getBoolean("confirmed")).isEqualTo(true);
            assertThat(resultSet.next()).isFalse();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldConfirmReservation() {
        // GIVEN
        String request = "SELECT confirmed FROM rent WHERE id = (SELECT MAX(id) FROM rent)";
        // WHEN
        rentDao.confirmReservation();
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(request)) {
            assertThat(resultSet.getBoolean("confirmed")).isEqualTo(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldListReservedDates() {
        // WHEN
        List<LocalDate> reservedDates = rentDao.listReservedDates();
        // THEN
        assertThat(reservedDates).hasSize(2);
        assertThat(reservedDates).extracting("year", "monthValue", "dayOfMonth").containsOnly(
                tuple(2018, 9,19),
                tuple(2018,9,20)
        );
    }
}
