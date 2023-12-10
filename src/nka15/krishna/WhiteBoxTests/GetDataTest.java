package nka15.krishna.WhiteBoxTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import main.java.app.getData;

public class GetDataTest {

    /**
     * Test Case ID: FIN-001
     * Technique: White-Box Testing
     * Scenario: Verify that the username stored in 'getData' is retrieved correctly.
     * Expected Result: The 'username' field should return the value "testUser".
     * Preconditions: 'getData' class must be compiled and the 'username' field accessible.
     * Test Data: The username to be tested against is "testUser".
     * Actual Result: testUser
     * Pass/Fail: Pass
     */
    @Test
    public void testUsername() {
        // Set a username in the getData class
        getData.username = "testUser";
        
        // Retrieve the username and assert it matches the expected value
        String retrievedUsername = getData.username;
        assertEquals("testUser", retrievedUsername, "The retrieved username should match the set value of 'testUser'.");
    }
}
