import daos.DataSourceProvider;
import daos.FormDonationDao;
import entities.FormDonation;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class FormDonationDaoTestCase {

    private FormDonationDao dao = new FormDonationDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM form_donation");

            statement.executeUpdate("INSERT INTO form_donation(id, amount, email, civility, firstName, lastName, " +
                    "address, addressCompl, postalCode, city, country, phone, birthDate, fiscalReceipt) " +
                    "VALUES (1, 100, 'first@hello.com', 'Mme', 'Machin', 'Bidule', '123 Avenue Bidon', null, " +
                    "'59000', 'Lille', 'France', '0123456789', '1996-12-10', TRUE)");
            statement.executeUpdate("INSERT INTO form_donation(id, amount, email, civility, firstName, lastName, " +
                    "address, addressCompl, postalCode, city, country, phone, birthDate, fiscalReceipt) " +
                    "VALUES (2, 50, 'second@hello.com', 'M.', 'Truc', 'Muche', '456 Rue Inconnue', null, " +
                    "'75009', 'Paris', 'France', '0987654321', '1996-06-18', TRUE)");
        }
    }

    @Test
    public void shouldListAllFormDonations() {
        // WHEN
        List<FormDonation> list = dao.listAllFormDonations();
        // THEN
        assertThat(list).hasSize(2);
        assertThat(list).extracting("id", "amount", "email", "civility", "firstName", "lastName",
                "address", "addressCompl", "postalCode", "city", "country", "phone", "birthDate", "fiscalReceipt")
                .containsOnly(
                        tuple(1, 100.0, "first@hello.com", "Mme", "Machin", "Bidule", "123 Avenue Bidon", null, "59000",
                                "Lille", "France", "0123456789", LocalDate.of(1996,12,10), true),
                        tuple(2, 50.0, "second@hello.com", "M.", "Truc", "Muche", "456 Rue Inconnue", null, "75009",
                                "Paris", "France", "0987654321", LocalDate.of(1996,6,18), true)
                );
    }

    @Test
    public void shouldDeleteFormDonation() {
        // GIVEN
        String request = "SELECT * FROM form_donation";
        // WHEN
        dao.deleteFormDonation(1);
        // THEN
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                assertThat(resultSet.getInt("id")).isNotEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
