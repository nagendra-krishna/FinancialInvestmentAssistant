package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import main.java.app.DashboardController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class CloseConnectionTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

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
    public void testCloseConnection() throws SQLException {
        // Verify auto-commit is initially true
        assertTrue(connection.getAutoCommit());

        // Call the closeConnection method
        assertDoesNotThrow(() -> dashboardController.closeConnection());

        // Verify that the connection is closed
        assertTrue(connection.isClosed());
    }
}
