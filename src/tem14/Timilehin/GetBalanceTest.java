package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import main.java.app.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GetBalanceTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Initialize the necessary tables
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS funds (id INTEGER PRIMARY KEY AUTOINCREMENT, balance REAL DEFAULT 0);");
            statement.executeUpdate("INSERT INTO funds (id, balance) VALUES (1, 1000.0);"); // Assuming an initial balance of 1000
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
    public void testGetCurrentBalance() {
        // Call the getCurrentBalance method
        double currentBalance = dashboardController.getCurrentBalance();

        // Check if the retrieved balance matches the expected initial balance
        assertEquals(1000.0, currentBalance, 0.01); // Using delta for double comparison
    }
}

