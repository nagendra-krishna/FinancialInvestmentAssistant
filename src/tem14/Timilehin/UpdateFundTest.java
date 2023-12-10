package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import main.java.app.DashboardController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateFundTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Initialize the necessary tables
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS funds (id INTEGER PRIMARY KEY AUTOINCREMENT, balance REAL DEFAULT 0);");
            statement.executeUpdate("INSERT INTO funds (balance) VALUES (1000.0);"); // Assuming an initial balance of 1000
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
    public void testUpdateBalance() {
        // Assuming the initial balance is 1000
        double initialBalance = getCurrentBalance();

        // Call the updateBalance method
        try {
            dashboardController.updateBalance(500.0); // Update the balance by adding 500
        } catch (SQLException e) {
            fail("Error updating balance: " + e.getMessage());
        }

        // Check if the balance is updated correctly
        double updatedBalance = getCurrentBalance();
        assertEquals(initialBalance + 500.0, updatedBalance, 0.01); // Using delta for double comparison
    }

    private double getCurrentBalance() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT balance FROM funds;")) {
            assertTrue(resultSet.next());
            return resultSet.getDouble("balance");
        } catch (SQLException e) {
            fail("Error getting current balance: " + e.getMessage());
            return 0.0;
        }
    }
}

