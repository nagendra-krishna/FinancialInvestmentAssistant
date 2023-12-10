package tem14.Timilehin;

import javafx.application.Platform;
import main.java.app.AlertMessage;
import main.java.app.DashboardController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JPanel;

class TestAlertMessage extends AlertMessage {
    private String successMessage;

    @Override
    public void successMessage(String message) {
        successMessage = message;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}

class AddGoalTest {

    @Test
    public void testAddGoal() {
        // Initialize the JavaFX toolkit
        new JPanel();

        TestAlertMessage testAlert = new TestAlertMessage();
        DashboardController dashboardController = new DashboardController();
        dashboardController.setAlert(testAlert);

        // Execute the JavaFX-related code on the JavaFX Application Thread
        Platform.runLater(() -> dashboardController.AddGoal());

        // Wait for the JavaFX Application Thread to finish
        Platform.runLater(() -> {
            // Verify that the successMessage method is called with the expected message
            assertEquals("Goal has been set, we will notify you when you reached your goal.", testAlert.getSuccessMessage());
        });
    }
}
