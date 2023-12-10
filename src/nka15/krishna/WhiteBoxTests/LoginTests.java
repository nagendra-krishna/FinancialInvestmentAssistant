package nka15.krishna.WhiteBoxTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import main.java.app.LoginController;
import main.java.app.RegistrationService;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTests {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private LoginController loginController;
    
    @Mock
    private RegistrationService registrationService;

    
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Instantiate your controller with the mocked DataSource
        loginController = new LoginController(dataSource);
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeUpdate(anyString())).thenReturn(1); 
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        loginController = new LoginController(dataSource);
    }
    
    /*
     * Test Case ID: FIN-10
    Test Technique: Statement Coverage
    Scenario: Successful login with valid user credentials.
    Test Data: Username: "krishna" Password: "krishna123"
    Expected Result: "Successfully Login!"
    Actual Result: Successfully Login!
    Pass/Fail: Pass
    */


    @Test
    void testLoginSuccess() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("username")).thenReturn("test_user");

        String result = loginController.login("krishna", "krishna123");
        assertEquals("Successfully Login!", result);
    }
    
    
    /*
     *  Test Case ID: FIN-11
     
    Test Technique: Statement Coverage
    Scenario: Failed login attempts with incorrect user credentials.
    Test Data: Username: "wrongUser" Password: "wrongPass"
    Expected Result: "Incorrect Username/Password"
    Actual Result:  Incorrect Username/Password
    Pass/Fail: Pass
    */

    @Test
    void testLoginFailureWithIncorrectCredentials() throws Exception {
        when(resultSet.next()).thenReturn(false);

        String result = loginController.login("wrongUser", "wrongPass");
        assertEquals("Incorrect Username/Password", result);
    }

    /*
    Test Case ID: FIN-12
    Test Technique: Statement Coverage
    Scenario: Failed login attempt with empty user credentials.
    Test Data: Username: "" , Password: ""
    Expected Result: "Username/Password is Missing"
    Actual Result: Username/Password is Missing
    Pass/Fail: Pass
    */

    @Test
    void testLoginFailureWithEmptyCredentials() {
        String result = loginController.login("", "");
        assertEquals("Username/Password is Missing", result);
    }

    /*
     * Test Case ID: FIN-13
	Test Technique: Statement Coverage
	Scenario: Failed login attempt due to SQL Exception.
	Test Data: Username: "krishna", Password: "null"
	Expected Result: "Login failed due to an error"
	Actual Result: Login failed due to an error, and SQL exception can be seen in terminal.
	Pass/Fail: Pass

     */
    
    @Test
    void testLoginFailureWithSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException());

        String result = loginController.login("krishna", "null");
        assertEquals("Login failed due to an error", result);
    }
 

}

