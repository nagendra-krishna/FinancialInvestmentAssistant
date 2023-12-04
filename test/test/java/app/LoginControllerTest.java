package test.java.app;
import org.junit.Test;
import static org.junit.Assert.*;

import java.main.app.LoginController;

public class LoginControllerTest {

    @Test
public void testValidLogin() {
    LoginController loginController = new LoginController();
    loginController.loginUsername.setText("admin");
    loginController.loginPassword.setText("admin12345");

    loginController.login();
    
    assertTrue(loginController.loginSuccess == true);
}


    @Test
    public void testInvalidLogin() {
        // Implement test logic for an invalid login
        // Assume you have an invalid username and/or password
        LoginController loginController = new LoginController();
        loginController.loginUsername.setText("12345");
        loginController.loginPassword.setText("12345");

        loginController.login();

        // Check if an error message is displayed (maybe by checking the UI state)
        assertTrue(loginController.loginSuccess == false);
}
    

}
