package tem14.Timilehin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import junit.framework.TestCase;
import main.java.app.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddInvestmentShowlist extends TestCase {

    private DashboardController dashboardController;
    private Connection mockConnection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dashboardController = new DashboardController();
        mockConnection = new MockConnection(); // Implement a simple mock for Connection
        dashboardController.setConnection(mockConnection);
    }

    public void testAddInvestmentShowListData() {
        // Mock data for the investments
        List<Investment> mockInvestments = new ArrayList<>();
        mockInvestments.add(new Investment(1, "Stocks", 100.0, LocalDate.now()));
        mockInvestments.add(new Investment(2, "Bonds", 200.0, LocalDate.now().minusDays(1)));
        mockInvestments.add(new Investment(3, "Real Estate", 300.0, LocalDate.now().minusDays(2)));

        // Mock the ResultSet to simulate the result
        ResultSet mockResultSet = new MockResultSet();
        for (Investment investment : mockInvestments) {
            ((MockResultSet) mockResultSet).addRow(investment);
        }

        // Mock the PreparedStatement to simulate a result set
        PreparedStatement mockPreparedStatement = new MockPreparedStatement();
        ((MockPreparedStatement) mockPreparedStatement).setResultSet(mockResultSet);

        // Mock the Connection to simulate the prepared statement
        ((MockConnection) mockConnection).setPreparedStatement(mockPreparedStatement);

        // Set up TableView and columns
        TableView<Investment> tableView = new TableView<>();
        TableColumn<Investment, Integer> serialNoCol = new TableColumn<>("Serial No");
        TableColumn<Investment, String> investmentTypeColumn = new TableColumn<>("Investment Type");
        TableColumn<Investment, String> investedAmtColumn = new TableColumn<>("Invested Amount");
        TableColumn<Investment, LocalDate> investmentDateColumn = new TableColumn<>("Investment Date");
        TableColumn<Investment, Double> presentValueColumn = new TableColumn<>("Present Value");

        tableView.getColumns().addAll(serialNoCol, investmentTypeColumn, investedAmtColumn, investmentDateColumn, presentValueColumn);

        // Run the addInvestmentShowListData method
        dashboardController.setInvestmentTableView(tableView);
        dashboardController.addInvestmentShowListData();

        // Verify that the TableView is populated correctly
        ObservableList<Investment> actualData = tableView.getItems();
        assertEquals(mockInvestments, actualData);
    }

    // Add additional tests as needed

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dashboardController = null;
        mockConnection = null;
    }
}

