package tem14.Timilehin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.app.DashboardController;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InvestmentInsertionTest extends Application {

    private DashboardController dashboardController;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/your/dashboard.fxml"));
        Parent root = loader.load();
        dashboardController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testInsertInvestment() {
        // Set up test data
        double initialBalance = 1000.0;
        dashboardController.setInitialBalance(initialBalance);

        // Set investment amount and type
        TextField investmentAmountField = lookup("#investmentAmount").query();
        ComboBox<String> investmentTypeDropdown = lookup("#investmentType_dropdown").query();
        Platform.runLater(() -> {
            investmentAmountField.setText("500.0");
            investmentTypeDropdown.setValue("Stocks");
        });

        // Mock the connection to avoid real database operations
        Connection mockConnection = mock(Connection.class);
        when(dashboardController.connectDB()).thenReturn(mockConnection);

        // Mock the resultSet to simulate sufficient funds
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble("balance")).thenReturn(initialBalance);

        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Run the insertInvestment method
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    dashboardController.insertInvestment();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        // Verify that the balance is updated
        assertEquals(initialBalance - 500.0, dashboardController.getCurrentBalance(), 0.001);
    }

    // Add additional tests as needed

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
}

