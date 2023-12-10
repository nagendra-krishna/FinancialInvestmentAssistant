package nka15.krishna.WhiteBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.app.PasswordResetService;

public class PasswordResetServiceTest {

    private PasswordResetService passwordResetService;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        // Create a mock Connection
        connection = mock(Connection.class);

        // Create a mock PreparedStatement
        preparedStatement = mock(PreparedStatement.class);

        // Create a mock ResultSet
        resultSet = mock(ResultSet.class);

        // Initialize PasswordResetService with the mock Connection
        passwordResetService = new PasswordResetService(connection);
    }
    
    /*
     * . Test Case ID: FIN-16
		Test Technique: Statement Coverage
		Scenario: Verify user's security question and answer with correct data.
		Test Data: Username: "testUser", Security Answer: "Blue"
		Security Question: "What's your favorite color?" 
		Expected Result: true
		Actual Result: true
		Pass/Fail: Pass

     */

    @Test
    public void testIsUserInformationCorrect_WithCorrectData_ReturnsTrue() throws SQLException {
        // Arrange
        String username = "testUser";
        String question = "What's your favorite color?";
        String answer = "Blue";

        // Mock the behavior of the PreparedStatement and ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a matching record was found

        // Act
        boolean result = passwordResetService.isUserInformationCorrect(username, question, answer);

        // Assert
        assertTrue(result);
    }

    /*
     * . Test Case ID: FIN-17
	Test Technique: Statement Coverage
	Scenario: Verify user's security question and answer with incorrect data.
	Test Data: Username: "testUser", Security Answer: "Green"
	Security Question: "What's your favorite color?"
  	Expected Result: false
	Actual Result: false
	Pass/Fail: Pass

     */
    
    @Test
    public void testIsUserInformationCorrect_WithIncorrectData_ReturnsFalse() throws SQLException {
        // Arrange
        String username = "testUser";
        String question = "What's your favorite color?";
        String answer = "Green";

        // Mock the behavior of the PreparedStatement and ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate that no matching record was found

        // Act
        boolean result = passwordResetService.isUserInformationCorrect(username, question, answer);

        // Assert
        assertFalse(result);
    }

    /*
     * 
    Test Case ID: FIN-18
	Test Technique: Statement Coverage
	Scenario: Change the user's password with valid new password data.
	Test Data: Username: "testUser", New Password: "newPassword"
	Expected Result: true
	Actual Result: true
	Pass/Fail: Pass

     */
    
    @Test
    public void testChangePassword_WithValidData_ReturnsTrue() throws SQLException {
        // Arrange
        String username = "testUser";
        String newPassword = "newPassword";

        // Mock the behavior of the PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate that one row was affected

        // Act
        boolean result = passwordResetService.changePassword(username, newPassword);

        // Assert
        assertTrue(result);
    }

    /*
     * . Test Case ID: FIN-19
	Test Technique: Statement Coverage
	Scenario: Attempt to change the user's password with invalid new password data.
	Test Data: Username: "testUser”, New Password: "newPassword"
	Expected Result: false
	Actual Result: false
	Pass/Fail: Pass

     */
    
    @Test
    public void testChangePassword_WithInvalidData_ReturnsFalse() throws SQLException {
        // Arrange
        String username = "testUser";
        String newPassword = "newPassword";

        // Mock the behavior of the PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate that no rows were affected

        // Act
        boolean result = passwordResetService.changePassword(username, newPassword);

        // Assert
        assertFalse(result);
    }
}
