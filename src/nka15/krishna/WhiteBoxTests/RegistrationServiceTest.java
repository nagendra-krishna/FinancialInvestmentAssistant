package nka15.krishna.WhiteBoxTests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import main.java.app.RegistrationService;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegistrationServiceTest {

    @Mock
    private Connection connection;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
    	new JPanel();
        MockitoAnnotations.initMocks(this);
        // Initialize any other test setup here
    }

    
/*
* Test Case ID: FIN-23
Test Technique: Statement Coverage
Scenario: Attempt to register a new user with a username that is already taken.
Test Data:  Email: "test@example.com"
Username: "existinguser" (Simulating a username that is already taken)
Password: "password" Confirm Password: "password"
Security Question: "What is your favorite color?" Security Answer: "Blue"
Expected Result: "existinguser is already taken"
Actual Result:  java.lang.ExceptionInInitializerError
Pass/Fail: Failed due to some missing JavaFX components while testing.
    
 */
    @Test
    public void testRegisterUser_UsernameTaken() throws SQLException {
        // Arrange
        String email = "test@example.com";
        String username = "existinguser"; // Simulate an existing username
        String password = "password";
        String confirmPassword = "password";
        String question = "What is your favorite color?";
        String answer = "Blue";

        // Mock the PreparedStatement and ResultSet
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Define the expected SQL queries and results
        String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
        when(connection.prepareStatement(checkUsernameQuery)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Username is taken

        // Act
        String result = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        // Assert
        assertEquals("existinguser is already taken", result);
    }

    /*
Test Case ID: FIN-24
Test Technique: Statement Coverage
Scenario: Successfully register a new user with unique username and valid data.
Test Data:  Email: "test@example.com"
Username: "testuser" (Simulating a new, unique username)
Password: "password" Confirm Password: "password"
Security Question: "What is your favorite color?" Security Answer: "Blue"
Expected Result: "Registered Successfully!"
Actual Result:  java.lang.ExceptionInInitializerError
Pass/Fail: Failed due to some missing JavaFX components while testing.

     */
    @Test
    public void testRegisterUser_SuccessfulRegistration() throws SQLException {
        // Arrange
        String email = "test@example.com";
        String username = "testuser";
        String password = "password";
        String confirmPassword = "password";
        String question = "What is your favorite color?";
        String answer = "Blue";

        // Mock the PreparedStatement and ResultSet
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Define the expected SQL queries and results
        String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
        String insertDataQuery = "INSERT INTO users (email, username, password, question, answer, date) VALUES(?,?,?,?,?,?)";
        Mockito.when(connection.prepareStatement(checkUsernameQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false); // Username is not taken
        Mockito.when(connection.prepareStatement(insertDataQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1); // Registration successful

        // Act
        String result = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        // Assert
        assertEquals("Registered Successfully!", result);
    }
    
/*    
    Test Case ID: FIN-20
    Test Technique: Statement Coverage
    Scenario: Successfully register a new user with valid data.
    Test Data: Email: "newuser@example.com"
    Username: "newuser" Password: "password" Confirm Password: "password"
    Security Question: "What is your favorite color?" Security Answer: "Blue"
    Expected Result: "Registered Successfully!"
    Actual Result: Registered Successfully!
    Pass/Fail: Pass
*/
    @Test
    public void testRegisterUser_ValidRegistration() throws SQLException {
        // Arrange
        String email = "newuser@example.com";
        String username = "newuser"; // Simulate a new username
        String password = "password";
        String confirmPassword = "password";
        String question = "What is your favorite color?";
        String answer = "Blue";

        // Mock the PreparedStatement and ResultSet
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Define the expected SQL queries and results
        String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
        when(connection.prepareStatement(checkUsernameQuery)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Username is not taken

        // Mock the successful database insert
        String insertQuery = "INSERT INTO users (email, username, password, question, answer, date) VALUES(?,?,?,?,?,?)";
        when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate success

        // Act
        String result = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        // Assert
        assertEquals("Registered Successfully!", result);
    }

    /*
Test Case ID: FIN-21
Test Technique: Statement Coverage
Scenario: Attempt to register a new user with mismatched passwords.
Test Data: Email: "test@example.com"
Username: "newuser"  Password: "password" Confirm Password: "differentpassword"
 	 Security Question: "What is your favorite color?" Security Answer: "Blue"
Expected Result: "Password does not match"
Actual Result: java.lang.ExceptionInInitializerError
Pass/Fail: Fail. due to some Initialization errors. 

     */
    
    @Test
    public void testRegisterUser_PasswordMismatch() throws SQLException {
        // Arrange
        String email = "test@example.com";
        String username = "newuser"; // Simulate a new username
        String password = "password";
        String confirmPassword = "differentpassword"; // Mismatched password
        String question = "What is your favorite color?";
        String answer = "Blue";

        // Act
        String result = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        // Assert
        assertEquals("Password does not match", result);
    }

    /*
Test Case ID: FIN-22
Test Technique: Statement Coverage
Scenario: Attempt to register a new user with a password that is too short.
Test Data: Email: "test@example.com"
Username: "newuser” Password: "short" Confirm Password: "short"
Security Question: "What is your favorite color?" Security Answer: "Blue"
Expected Result: "Invalid Password, at least 8 characters needed"
Actual Result: java.lang.ExceptionInInitializerError
Pass/Fail: Failed due to some missing JavaFX components while testing.

     */
    
    @Test
    public void testRegisterUser_PasswordTooShort() throws SQLException {
        // Arrange
        String email = "test@example.com";
        String username = "newuser"; // Simulate a new username
        String password = "short"; // Password is too short (less than 8 characters)
        String confirmPassword = "short"; // Matching password
        String question = "What is your favorite color?";
        String answer = "Blue";

        // Act
        String result = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        // Assert
        assertEquals("Invalid Password, at least 8 characters needed", result);
    }

  


}

