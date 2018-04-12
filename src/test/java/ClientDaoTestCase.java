import daos.ClientDao;
import daos.DataSourceProvider;
import org.junit.Before;

import java.sql.Connection;
import java.sql.Statement;

public class ClientDaoTestCase {

    private ClientDao clienDao = new ClientDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM client");
            this.insertDataSet(statement);
        }
    }

    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO client " +
                "VALUES('Bidule', 'Machin', 'machin.bidule@hello.com')");
        statement.executeUpdate("INSERT INTO client " +
                "VALUES('Testehun', 'Monsieur', 'mtest1@hello.com')");
        statement.executeUpdate("INSERT INTO client " +
                "VALUES('Testedeuh', 'Madame', 'mmetest2@hello.com')");
    }
}
