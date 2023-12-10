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

class TableExistenceTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the connection after each test
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testDoesTableExist() throws SQLException {
        DashboardController dashboardController = new DashboardController();
        assertFalse(dashboardController.doesTableExist(connection, "nonexistent_table"));

        // Create a table for testing
        createTestTable();

        assertTrue(dashboardController.doesTableExist(connection, "test_table"));

        // Drop the table
        dropTestTable();

        assertFalse(dashboardController.doesTableExist(connection, "test_table"));
    }

    private void createTestTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE test_table (id INTEGER PRIMARY KEY, name TEXT);");
        }
    }

    private void dropTestTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS test_table;");
        }
    }
}
