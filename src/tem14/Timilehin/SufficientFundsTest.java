package tem14.Timilehin;

import main.java.app.DashboardController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SufficientFundsTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Create 'funds' table for testing
        createFundsTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the connection after each test
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testAreSufficientFunds() throws SQLException {
        DashboardController dashboardController = new DashboardController();
        dashboardController.setConnection(connection);

        // Set an initial balance for testing
        dashboardController.setInitialBalance(1000.0);

        // Test with sufficient funds
        assertTrue(dashboardController.areSufficientFunds(500.0));

        // Test with insufficient funds
        assertFalse(dashboardController.areSufficientFunds(1500.0));

        // Test with an exception (simulate SQL error)
        dashboardController.setConnection(null); // Set connection to null to force SQLException
        assertFalse(dashboardController.areSufficientFunds(100.0));
    }

    private void createFundsTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE funds (id INTEGER PRIMARY KEY AUTOINCREMENT, balance REAL DEFAULT 0);");
        }
    }
}
