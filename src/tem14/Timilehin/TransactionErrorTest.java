package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import main.java.app.*;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class TransactionErrorTest {

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
    public void testHandleTransactionError() {
        // Assuming a sample SQLException during a transaction
        SQLException sampleException = new SQLException("Sample transaction error");

        // Call the handleTransactionError method
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                dashboardController.handleTransactionError(sampleException, "sampleTransaction");
            });
        });

        // Verify that the rollback operation was attempted
        assertTrue(dashboardController.isRollbackAttempted());
    }
}
