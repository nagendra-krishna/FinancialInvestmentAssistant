package tem14.Timilehin;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.app.DashboardController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class DisplayBalanceTest {

    private DashboardController dashboardController;

    @Start
    private void start(Stage stage) {
        new JFXPanel(); // Initializes JavaFX
        dashboardController = new DashboardController();
        dashboardController.setAddAmt(new TextField()); // Set a TextField for testing
        stage.show();
    }

    @Test
    void testDisplayBalance(FxRobot robot) throws TimeoutException {
        // Execute the JavaFX-related code on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Assuming you have a method to set the balance, replace it with the actual method
            // For example, you might have a method like setInitialBalance(double balance) in DashboardController
            dashboardController.setInitialBalance(1000.0);

            // Call the displayBalance method
            dashboardController.displayBalance();

            // Verify that the avlAmtToWithdraw TextField is updated with the correct balance
            assertEquals("1000.00", dashboardController.getAvlAmtToWithdraw().getText());
        });

        // Wait for the JavaFX Application Thread to finish
        FxToolkit.showStage();
    }
}
