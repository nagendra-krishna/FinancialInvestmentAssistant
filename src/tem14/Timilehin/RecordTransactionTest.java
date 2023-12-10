package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import main.java.app.DashboardController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

class RecordTransactionTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Instantiate the controller and set the test connection
        dashboardController = new DashboardController();
        dashboardController.setConnection(connection);

        // Create the 'fund_transactions' table for testing
        createTestTable();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the connection after each test
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testRecordTransaction() throws SQLException {
        // Assuming a sample transaction amount and type
        double amount = 100.0;
        String transactionType = "sampleTransaction";

        // Call the recordTransaction method
        assertDoesNotThrow(() -> dashboardController.recordTransaction(amount, transactionType));

        // Verify that the transaction record exists in the test table
        assertTrue(transactionRecordExists(amount, transactionType));
    }

    private void createTestTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS fund_transactions ("
                + "transaction_id TEXT PRIMARY KEY, "
                + "transaction_type TEXT NOT NULL, "
                + "transaction_amount REAL NOT NULL, "
                + "transaction_date DATE NOT NULL, "
                + "transaction_time TIME NOT NULL)";

        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }
    }

    private boolean transactionRecordExists(double amount, String transactionType) throws SQLException {
        String selectSQL = "SELECT * FROM fund_transactions WHERE transaction_type = ? AND transaction_amount = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, transactionType);
            preparedStatement.setDouble(2, amount);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
