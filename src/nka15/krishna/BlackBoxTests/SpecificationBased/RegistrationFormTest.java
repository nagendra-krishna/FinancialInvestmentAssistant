package nka15.krishna.BlackBoxTests.SpecificationBased;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import main.java.app.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class RegistrationFormTest  extends Application {

    @Test
    public void testRegistrationFormPresence() {
        clickOn("#registerButton");
        assertTrue(exists("#registrationForm"));
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

