package nka15.krishna.WhiteBoxTests;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import main.java.app.Main;

public class MainTest {

	/*
	 * Test Case ID: FIN-14
	Test Technique: Statement Coverage
	Scenario: Starting the JavaFX application main stage without any errors.
	Test Data: None required explicitly, but the JavaFX runtime must be initialized.
	Expected Result: The application's `start` method completes without throwing exceptions.
	Actual Result:  java.lang.ExceptionInInitializerError
	Pass/Fail: Error – due to the involvement of javaFX UI components.

	 */
    @Test
    public void testMain() {
        
        Main main = new Main();
        
        try {
            main.start(new Stage());
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}

