package nka15.krishna.BlackBoxTests.SpecificationBased;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;

public class InvestmentManagementTest extends Application {

    @Test
    public void testFailedLoginAttempt() {
        clickOn("#usernameField").write("wrongUser");
        clickOn("#passwordField").write("wrongPassword");
        clickOn("#loginButton");
        assertTrue(exists("#errorMessage"));
    }
}

