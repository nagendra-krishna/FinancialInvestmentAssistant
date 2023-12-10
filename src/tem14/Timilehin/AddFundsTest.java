package tem14.Timilehin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.java.app.AlertMessage;
import main.java.app.DashboardController;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AddFundsTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private AlertMessage mockAlert;

    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dashboardController = new DashboardController();
        dashboardController.setAlert(mockAlert);
        dashboardController.setConnection(mockConnection);
    }

    @Test
    void testAddFundsSuccess() throws SQLException {
        // Set up the test scenario
        when(dashboardController.getAddAmt().getText()).thenReturn("100.0");

        // Call the method under test
        dashboardController.addFunds();

        // Verify that the expected interactions occurred
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).commit();
        verify(mockAlert).successMessage("Funds added successfully!");
        verify(dashboardController.getAddAmt()).clear();
    }

    @Test
    void testAddFundsEmptyAmount() {
        // Set up the test scenario
        when(dashboardController.getAddAmt().getText()).thenReturn("");

        // Call the method under test
        dashboardController.addFunds();

        // Verify that the expected interactions occurred
        verify(mockAlert).errorMessage("Please enter the amount to add.");
        verify(mockConnection, never()).setAutoCommit(false);
    }
}
