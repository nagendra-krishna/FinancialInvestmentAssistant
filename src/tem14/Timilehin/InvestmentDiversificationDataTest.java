package tem14.Timilehin;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import main.java.app.DashboardController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvestmentDiversificationDataTest {

    private Connection connection;
    private DashboardController dashboardController;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up an in-memory test database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Initialize the necessary tables
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS investments (transaction_type TEXT NOT NULL);");
            statement.executeUpdate("INSERT INTO investments (transaction_type) VALUES ('Stocks');");
            statement.executeUpdate("INSERT INTO investments (transaction_type) VALUES ('Bonds');");
            statement.executeUpdate("INSERT INTO investments (transaction_type) VALUES ('Stocks');");
            statement.executeUpdate("INSERT INTO investments (transaction_type) VALUES ('Real Estate');");
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
    public void testGetInvestmentDiversificationData() {
        // Call the method to get diversification data
        ObservableList<PieChart.Data> pieChartData = dashboardController.getInvestmentDiversificationData();

        // Debugging: Print the actual size
        System.out.println("Actual size of pieChartData: " + pieChartData.size());

        // Debugging: Print the content
        for (PieChart.Data data : pieChartData) {
            System.out.println("Label: " + data.getName() + ", Value: " + data.getPieValue());
        }

        // Check the size and content of the list
        assertEquals(3, pieChartData.size()); // Assuming there are three unique investment types

        // Check the content of the list (example)
        assertPieChartData(pieChartData, "Stocks (2)", 2);
        assertPieChartData(pieChartData, "Bonds (1)", 1);
        assertPieChartData(pieChartData, "Real Estate (1)", 1);
    }


    private void assertPieChartData(ObservableList<PieChart.Data> pieChartData, String expectedLabel, int expectedValue) {
        for (PieChart.Data data : pieChartData) {
            if (data.getName().equals(expectedLabel)) {
                assertEquals(expectedValue, (int) data.getPieValue());
                return;
            }
        }
    }
}
