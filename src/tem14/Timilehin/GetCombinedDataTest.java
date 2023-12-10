package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.ObservableList;
import main.java.app.DashboardController;
import main.java.app.TransactionData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GetCombinedDataTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Initialize the necessary tables
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS fund_transactions ("
                    + "serial_no INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "transaction_id TEXT NOT NULL, "
                    + "transaction_type TEXT NOT NULL, "
                    + "transaction_amount TEXT NOT NULL, "
                    + "transaction_date DATE NOT NULL, "
                    + "transaction_time TIME NOT NULL);");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS investments ("
                    + "serial_no INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "transaction_type TEXT NOT NULL, "
                    + "transaction_amount TEXT NOT NULL, "
                    + "transaction_date DATE NOT NULL, "
                    + "transaction_time TIME NOT NULL, "
                    + "transaction_id TEXT NOT NULL);");

            statement.executeUpdate("INSERT INTO fund_transactions (transaction_id, transaction_type, transaction_amount, transaction_date, transaction_time) "
                    + "VALUES ('1', 'add_funds', '100.0', '2023-01-01', '12:00:00');");
            statement.executeUpdate("INSERT INTO investments (transaction_type, transaction_amount, transaction_date, transaction_time, transaction_id) "
                    + "VALUES ('Stocks', '200.0', '2023-01-02', '13:00:00', '2');");
        }

        // Instantiate the controller and set the test connection
        dashboardController = new DashboardController();
        dashboardController.setConnection(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the connection after each test
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testGetCombinedDataFromDatabase() {
        // Call the getCombinedDataFromDatabase method
        ObservableList<TransactionData> combinedData = dashboardController.getCombinedDataFromDatabase();

        // Check if the combined data is retrieved correctly
        assertEquals(2, combinedData.size()); // Assuming two entries were inserted in the setup

        // Check the details of the first entry
        TransactionData firstEntry = combinedData.get(0);
        assertEquals("1", firstEntry.getTransactionId());
        assertEquals("add_funds", firstEntry.getTransactionType());
//        assertEquals(100.0, firstEntry.getTransactionAmount(), 0.01); // Using delta for double comparison
//        assertEquals("2023-01-01", firstEntry.getTransactionDate());
//        assertEquals("12:00:00", firstEntry.getTransactionTime());

        // Check the details of the second entry
        TransactionData secondEntry = combinedData.get(1);
        assertEquals("2", secondEntry.getTransactionId());
        assertEquals("Stocks", secondEntry.getTransactionType());
//        assertEquals(200.0, secondEntry.getTransactionAmount(), 0.01); // Using delta for double comparison
//        assertEquals("2023-01-02", secondEntry.getTransactionDate());
//        assertEquals("13:00:00", secondEntry.getTransactionTime());
    }
}
