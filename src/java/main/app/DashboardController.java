package java.main.app;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Time;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;

public class DashboardController implements Initializable {
    
private final NewsController newsController = new NewsController();

    @FXML
    private AnchorPane main_form;
   
    @FXML
    private TextField addAmt;
    
    @FXML
    private TextField avlAmtToWithdraw;
   
    @FXML
    private ProgressIndicator goalProgessCircle;

    @FXML
    private ProgressBar goalProgressBar;

    @FXML
    private TextField goalTargetAmt;

    @FXML
    private AnchorPane homePage;

    @FXML
    private Button home_btn;

    @FXML
    private TableColumn<Investment, String> investedAmtColumn;

    @FXML
    private TextField investmentAmount;

    @FXML
    private TableColumn<Investment, Date> investmentDateColumn;

    @FXML
    private TableView<Investment> investmentTableView;

    @FXML
    private TableColumn<Investment, String> investmentTypeColumn;
    
    @FXML
    private TableColumn<Investment, Integer> serialNoCol;

    @FXML
    private ComboBox<String> investmentType_dropdown;

    @FXML
    private AnchorPane newsPage;

    @FXML
    private AnchorPane newsAnchorPane;
    
    @FXML
    private Button news_btn;

    @FXML
    private AnchorPane portfolioPiechart_pane;
    
     @FXML
    private AnchorPane barChartPane;


    @FXML
    private TableColumn<Investment, Double> presentValueColumn;

    @FXML
    private TextField profitLoss;

    @FXML
    private AnchorPane resourcesPage;

    @FXML
    private Button resources_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private TextField totalInvestments;

    @FXML
    private TextField totalPortfolioValue;

    @FXML
    private TextField transactionIDField;

    @FXML
    private AnchorPane transactionsPage;

    @FXML
    private TableView<TransactionData> transactionsTableView;
    
     @FXML
    private TableColumn<TransactionData, String> transactionIdColumn;

    @FXML
    private TableColumn<TransactionData, String> transactionTypeColumn;
    
     @FXML
    private TableColumn<TransactionData, Integer> serialNoColumn;
     
    @FXML
    private TableColumn<TransactionData, Date> dateColumn;

    @FXML
    private TableColumn<TransactionData, Time> timeColumn;
    
    @FXML
    private TableColumn<TransactionData, Double> amountColumn;

    @FXML
    private Button transactions_btn;

    @FXML
    private AnchorPane walletPage;

    @FXML
    private Button wallet_btn;

    @FXML
    private Label welcomeName;

    @FXML
    private TextField withdrawAmt;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;
    private double x = 0;
    private double y = 0;


    public Connection connectDB() {
 try {
     Class.forName("org.sqlite.JDBC");
     connect = DriverManager.getConnection("jdbc:sqlite:FinancialInvestmentAssistant.db");

      System.out.println("Connection state: " + connect.isClosed());
     System.out.println("Auto-commit mode: " + connect.getAutoCommit());

     // Check and create 'investments' table if not exists
     if (!doesTableExist(connect, "investments")) {
         createInvestmentsTable(connect);
     }

     // Check and create 'fund_transactions' table if not exists
     if (!doesTableExist(connect, "fund_transactions")) {
         createFundTransactionsTable(connect);
     }

     if (!doesTableExist(connect, "fund")) {
         createFundsTable(connect);
     }

     return connect;
 } catch (Exception e) {
     e.printStackTrace();
 }
 return null;
}

private void createFundsTable(Connection connection) throws SQLException {
    String createTableSQL = "CREATE TABLE IF NOT EXISTS funds ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "balance REAL DEFAULT 0);";

    String insertInitialData = "INSERT INTO funds (balance) SELECT 0.0 WHERE NOT EXISTS (SELECT 1 FROM funds LIMIT 1);";

    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(createTableSQL);
        statement.executeUpdate(insertInitialData);
    }
}


    private void createFundTransactionsTable(Connection connection) throws SQLException {
 String createTableSQL = "CREATE TABLE IF NOT EXISTS fund_transactions ("
         +"serial_no INTEGER PRIMARY KEY AUTOINCREMENT"
         + "transaction_id TEXT PRIMARY KEY, "
         + "transaction_type TEXT NOT NULL, "
         + "transaction_amount TEXT NOT NULL, "
         + "transaction_date DATE NOT NULL, "
         + "transaction_time TIME NOT NULL)";

 try (Statement statement = connection.createStatement()) {
     statement.executeUpdate(createTableSQL);
 }    
}
    private void createInvestmentsTable(Connection connection) throws SQLException {
     String createTableSQL = "CREATE TABLE IF NOT EXISTS investments ("
            + "serial_no INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "transaction_type TEXT NOT NULL, "
            + "transaction_amount TEXT NOT NULL, "
            + "transaction_date DATE NOT NULL, "
            + "transaction_time TIME NOT NULL, "
            + "transaction_id TEXT NOT NULL)";
    
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(createTableSQL);
    }   
    }

    
public void addFunds() {
    AlertMessage alert = new AlertMessage();

    if (addAmt.getText().isEmpty()) {
        alert.errorMessage("Please enter the amount to add.");
        return;
    }

    double addedAmount = Double.parseDouble(addAmt.getText());

    try {
        connect = connectDB();
        connect.setAutoCommit(false);

        updateBalance(addedAmount); 
        recordTransaction(addedAmount, "add_funds");

        connect.commit();
        alert.successMessage("Funds added successfully!");
        addAmt.clear();
    } catch (SQLException e) {
        handleTransactionError(e, "add_funds");
    } finally {
        closeConnection();
    }
}

public void withdrawFunds() {
    AlertMessage alert = new AlertMessage();

    if (withdrawAmt.getText().isEmpty()) {
        alert.errorMessage("Please enter the amount to withdraw.");
        return;
    }

    try {
        connect = connectDB();
        connect.setAutoCommit(false);

        double withdrawalAmount = Double.parseDouble(withdrawAmt.getText());
        updateBalance(-withdrawalAmount); // Update balance for withdrawal
        recordTransaction(withdrawalAmount, "withdraw");

        connect.commit();
        alert.successMessage("Funds withdrawn successfully!");
        withdrawAmt.clear();
    } catch (SQLException e) {
        handleTransactionError(e, "withdraw");
    } finally {
        closeConnection();
    }
}

private void updateBalance(double amount) throws SQLException {
    try {
       
        System.out.println("Connection state before update: " + connect.isValid(1));

        String updateSQL = "UPDATE funds SET balance = balance + ? WHERE id = ?";

      
        System.out.println("Update SQL: " + updateSQL);

        try (PreparedStatement preparedStatement = connect.prepareStatement(updateSQL)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, 1);

            int rowsAffected = preparedStatement.executeUpdate();

           
            if (rowsAffected <= 0) {
                System.out.println("Failed to update balance.");
                throw new SQLException("Failed to update balance.");
            }
            System.out.println("Balance updated successfully. Rows affected: " + rowsAffected);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; 
    }
}

private double getCurrentBalance() {
    try {
        String selectSQL = "SELECT balance FROM funds WHERE id = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, 1); 

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    throw new SQLException("No balance found for the user.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return 0.0; 
    }
}

private void handleTransactionError(SQLException e, String transactionType) {
     AlertMessage alert = new AlertMessage();
    e.printStackTrace();
    try {
        if (connect != null) {
            connect.rollback();
        }
    } catch (SQLException rollbackException) {
        rollbackException.printStackTrace();
    }
    alert.errorMessage("Transaction failed. Please try again.");
}

private void closeConnection() {
    try {
        if (connect != null) {
            connect.setAutoCommit(true);
            connect.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void recordTransaction(double amount, String transactionType) {
        String insertSQL = "INSERT INTO fund_transactions (transaction_id, transaction_type, transaction_amount, transaction_date, transaction_time) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connect.prepareStatement(insertSQL)) {
                        String transactionId = UUID.randomUUID().toString();
            preparedStatement.setString(1, transactionId);

            preparedStatement.setString(2, transactionType);
            preparedStatement.setDouble(3, amount);

            
            LocalDate currentDate = LocalDate.now();
            preparedStatement.setDate(4, java.sql.Date.valueOf(currentDate));

            LocalTime currentTime = LocalTime.now();
            preparedStatement.setTime(5, java.sql.Time.valueOf(currentTime));

            preparedStatement.executeUpdate();

            System.out.println("Funds transaction recorded successfully:");
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Transaction Type: " + transactionType);
            System.out.println("Transaction Amount: " + amount);
            System.out.println("Transaction Date: " + currentDate);
            System.out.println("Transaction Time: " + currentTime);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public void AddGoal() {
        AlertMessage alert = new AlertMessage();
         
            alert.successMessage("Goal has been set, we will notify you when you reached your goal.");
           
         }

private void displayBalance() {
    double currentBalance = getCurrentBalance();
    System.out.println("Current Balance: " + currentBalance);
    String formattedBalance = String.format("%.2f", currentBalance);
    avlAmtToWithdraw.setText(formattedBalance);
}

private boolean doesTableExist(Connection connection, String tableName) throws SQLException {
    DatabaseMetaData meta = connection.getMetaData();
    try (ResultSet resultSet = meta.getTables(null, null, tableName, null)) {
        return resultSet.next();
    }
}

private boolean areSufficientFunds(double investmentAmount) {
    try (Connection connection = connectDB();
         Statement statement = connection.createStatement()) {

        String query = "SELECT balance FROM funds WHERE id = 1";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            double availableFunds = resultSet.getDouble("balance");

            // Check if there are sufficient funds for the investment
            return availableFunds >= investmentAmount;
        } else {
            System.out.println("Error: User not found in the funds table.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    
public void insertInvestment() throws SQLException {
        AlertMessage alert = new AlertMessage();
        if (investmentAmount.getText().isEmpty() || investmentType_dropdown.getValue().isEmpty()) {
            alert.errorMessage("All fields are necessary to be filled");
        } else {
            double investmentAmountValue = Double.parseDouble(investmentAmount.getText());

            if (areSufficientFunds(investmentAmountValue)) {
                connect = connectDB();

                // Update the balance with the investment amount
                updateBalance(-investmentAmountValue);

                String insertSQL = "INSERT INTO investments (transaction_type, transaction_amount, transaction_date, transaction_time, transaction_id) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connect.prepareStatement(insertSQL)) {
                    preparedStatement.setString(1, investmentType_dropdown.getValue());
                    preparedStatement.setDouble(2, investmentAmountValue);

                    LocalDate currentDate = LocalDate.now();
                    preparedStatement.setDate(3, java.sql.Date.valueOf(currentDate));

                    LocalTime currentTime = LocalTime.now();
                    preparedStatement.setTime(4, java.sql.Time.valueOf(currentTime));

                    String transactionId = UUID.randomUUID().toString();
                    preparedStatement.setString(5, transactionId);

                    preparedStatement.executeUpdate();

                    System.out.println("Investment added successfully:");
                    System.out.println("Investment Type: " + investmentType_dropdown.getValue());
                    System.out.println("Investment Amount: " + investmentAmountValue);
                    System.out.println("Investment Date: " + currentDate);
                    System.out.println("Investment Time: " + currentTime);
                    System.out.println("Transaction ID: " + transactionId);

                    alert.successMessage("Investment added Successfully!");
                    transactionIDField.setText(transactionId);
                    investmentsClearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                alert.errorMessage("Insufficient funds for the investment.");
            }
        }
    }

public void deleteInvestment() {
    AlertMessage alert = new AlertMessage();
    String selectedTransactionId = transactionIDField.getText();

    if (selectedTransactionId == null || selectedTransactionId.isEmpty()) {
        alert.errorMessage("Please enter a transaction ID to delete.");
        return;
    }

    connect = connectDB();

    // Prompt user for confirmation
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmation");
    confirmationAlert.setHeaderText("Confirm Deletion");
    confirmationAlert.setContentText("Are you sure you want to delete the investment with the selected transaction ID?");

    Optional<ButtonType> result = confirmationAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {

            String selectSQL = "SELECT transaction_amount, transaction_type FROM investments WHERE transaction_id = ?";
            try (PreparedStatement selectStatement = connect.prepareStatement(selectSQL)) {
                selectStatement.setString(1, selectedTransactionId);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double investmentAmountToDelete = resultSet.getDouble("transaction_amount");
                        String investmentType = resultSet.getString("transaction_type");

                      
                        updateBalance(investmentAmountToDelete);

                        // Delete the investment with the selected transaction ID
                        String deleteSQL = "DELETE FROM investments WHERE transaction_id = ?";
                        try (PreparedStatement deleteStatement = connect.prepareStatement(deleteSQL)) {
                            deleteStatement.setString(1, selectedTransactionId);
                            deleteStatement.executeUpdate();

                            System.out.println("Investment with Transaction ID '" + selectedTransactionId + "' deleted successfully.");
                            alert.successMessage("Investment with Transaction ID '" + selectedTransactionId + "' deleted successfully.");
                            investmentsClearFields();
                        }
                    } else {
                        alert.errorMessage("No investment found for the selected Transaction ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private ObservableList<Investment> getInvestmentsFromDatabase() 
    {
        ObservableList<Investment> investments = FXCollections.observableArrayList();

        connect = connectDB();
        String selectSQL = "SELECT * FROM investments";

        try (PreparedStatement preparedStatement = connect.prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Investment invest =new Investment(
                resultSet.getInt("serial_no"),
                resultSet.getString("transaction_type"),
                resultSet.getString("transaction_amount"),//investement_amount
                resultSet.getDate("transaction_date"));
                
                
                investments.add(invest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return investments;
    }
    
private ObservableList<Investment> investmentListD;

public void addInvestmentShowListData() {
        investmentListD = getInvestmentsFromDatabase();

        serialNoCol.setCellValueFactory(new PropertyValueFactory<>("serialNo"));
        investmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        investedAmtColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        investmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        presentValueColumn.setCellValueFactory(cellData -> {
            Investment investment = cellData.getValue();
            double presentValue = investment.calculatePresentValue();
            return new ReadOnlyObjectWrapper<>(presentValue);
        });

        investmentTableView.setItems(investmentListD);
    }

public void investmentsClearFields() 
{
    investmentAmount.setText("");
    investmentType_dropdown.getSelectionModel().clearSelection();
}

public void displayUsername()
{
    welcomeName.setText(getData.username);
}

public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }
    
public void switchForm(ActionEvent event) 
{
    if (event.getSource() == home_btn) {
            homePage.setVisible(true);
            walletPage.setVisible(false);
            transactionsPage.setVisible(false);
            resourcesPage.setVisible(false);
            newsPage.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            wallet_btn.setStyle("-fx-background-color:transparent");
            resources_btn.setStyle("-fx-background-color:transparent");
            news_btn.setStyle("-fx-background-color:transparent");
            transactions_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == wallet_btn) {
            resourcesPage.setVisible(false);
            walletPage.setVisible(true);
            homePage.setVisible(false);
            newsPage.setVisible(false);
            transactionsPage.setVisible(false);

            wallet_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            news_btn.setStyle("-fx-background-color:transparent");
            resources_btn.setStyle("-fx-background-color:transparent");
            transactions_btn.setStyle("-fx-background-color:transparent");

          
            addInvestmentShowListData();
            displayBalance();
        } else if (event.getSource() == news_btn) {
            resourcesPage.setVisible(false);
            newsPage.setVisible(true);
            homePage.setVisible(false);
            walletPage.setVisible(false);
            transactionsPage.setVisible(false);

            news_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            wallet_btn.setStyle("-fx-background-color:transparent");
            resources_btn.setStyle("-fx-background-color:transparent");
            transactions_btn.setStyle("-fx-background-color:transparent");
            updateFinanceNews();

        } else if (event.getSource() == resources_btn) {
            newsPage.setVisible(false);
            resourcesPage.setVisible(true);
            homePage.setVisible(false);
            walletPage.setVisible(false);
            transactionsPage.setVisible(false);

            resources_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            wallet_btn.setStyle("-fx-background-color:transparent");
            news_btn.setStyle("-fx-background-color:transparent");
            transactions_btn.setStyle("-fx-background-color:transparent");

        }
    else if (event.getSource() == transactions_btn) {
            resourcesPage.setVisible(false);
            transactionsPage.setVisible(true);
            homePage.setVisible(false);
            walletPage.setVisible(false);
            newsPage.setVisible(false);

            transactions_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            wallet_btn.setStyle("-fx-background-color:transparent");
            resources_btn.setStyle("-fx-background-color:transparent");
            news_btn.setStyle("-fx-background-color:transparent");
            displayCombinedDataInTableView();


        }
}

public void updateFinanceNews()
{
    NewsController.displayFinanceNews(newsAnchorPane);
}

private ObservableList<TransactionData> getCombinedDataFromDatabase() {
    ObservableList<TransactionData> combinedData = FXCollections.observableArrayList();

    connect = connectDB();
    List<TransactionData> fundsTransactionsData = fetchDataFromTable("fund_transactions");
    List<TransactionData> investmentData = fetchDataFromTable("investments");
    combinedData.addAll(fundsTransactionsData);
    combinedData.addAll(investmentData);

    return combinedData;
}

public void displayCombinedDataInTableView() {
    ObservableList<TransactionData> combinedData = getCombinedDataFromDatabase();
    serialNoColumn.setCellValueFactory(new PropertyValueFactory<>("serialNo"));
    transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
    transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

    transactionsTableView.setItems(combinedData);
}

public void displayFundsData() {
        try {
            Connection connection = connectDB();
            String selectSQL = "SELECT * FROM funds";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("ID\tBalance");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        double balance = resultSet.getDouble("balance");

                        System.out.println(id + "\t" + balance);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       
public void close()
{
    System.exit(0);
}

public void minimize()
{
    Stage stage = (Stage) main_form.getScene().getWindow();
    stage.setIconified(true);
}
    
public void logout() 
{

    try {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Message");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to logout?");

    Optional<ButtonType> option = alert.showAndWait();

    if (option.get().equals(ButtonType.OK)) {

    signout_btn.getScene().getWindow().hide();
           //LINK YOUR LOGIN FORM 
   Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
   Stage stage = new Stage();
   Scene scene = new Scene(root);

    root.setOnMousePressed((MouseEvent event) -> 
    {
        x = event.getSceneX();
        y = event.getSceneY();
    });

    root.setOnMouseDragged((MouseEvent event) -> {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

        stage.setOpacity(.8);
    });

    root.setOnMouseReleased((MouseEvent event) -> {
        stage.setOpacity(1);
    });

    stage.initStyle(StageStyle.TRANSPARENT);

    stage.setScene(scene);
    stage.show();

    } 
    else {
        return ;
    }

    } 
    catch (Exception e) {
            e.printStackTrace();
    }

}


 private List<TransactionData> fetchDataFromTable(String tableName) {
    List<TransactionData> dataList = new ArrayList<>();
    String selectSQL = "SELECT * FROM " + tableName + " ORDER BY transaction_date, transaction_time";

    try (Statement statement = connect.createStatement();
         ResultSet resultSet = statement.executeQuery(selectSQL)) {

        int serialNo = 1;

        while (resultSet.next()) {
            TransactionData transactionData = new TransactionData(
                    serialNo++,
                    resultSet.getString("transaction_type"),
                    resultSet.getString("transaction_id"),
                    resultSet.getDouble("transaction_amount"), 
                    resultSet.getDate("transaction_date"),
                    resultSet.getTime("transaction_time")
            );
            dataList.add(transactionData);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return dataList;
}
/*
public void dropTable() {
        String tableName = "investments";
        Connection connection = connectDB();
        
        try (Statement statement = connection.createStatement()) {
            // SQL query to drop the table in SQLite
            String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;

            // Execute the query to drop the table
            statement.executeUpdate(dropTableSQL);

            System.out.println("Table '" + tableName + "' dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
 
public void loadInvestmentDiversificationChart(AnchorPane anchorPane) {
        ObservableList<PieChart.Data> pieChartData = getInvestmentDiversificationData();

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Investment Diversification");

        // Set preferred size to fit inside the AnchorPane
        pieChart.setPrefSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());

        anchorPane.getChildren().add(pieChart);
    }

private ObservableList<PieChart.Data> getInvestmentDiversificationData() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    try (Connection connection = connectDB();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_type, COUNT(*) as sector_count FROM investments GROUP BY transaction_type");
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            String investmentType = resultSet.getString("transaction_type");
            int sectorCount = resultSet.getInt("sector_count");
            pieChartData.add(new PieChart.Data(investmentType + " (" + sectorCount + ")", sectorCount));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pieChartData;
}

public void displayTotalInvestmentsAndProfitLoss(TextField totalInvestmentTextField, TextField profitLossTextField, TextField presentValueTextField) {
    double totalInvestment = 0.0;
    double presentValue = 0.0;
    double averageReturnRate = 12.0; 

    try (Connection connection = connectDB();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_amount FROM investments");
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            double investmentAmount = resultSet.getDouble("transaction_amount");
            totalInvestment += investmentAmount;

            presentValue += investmentAmount + (investmentAmount * averageReturnRate / 100);
        }

        totalInvestmentTextField.setText(String.valueOf(totalInvestment));

        presentValueTextField.setText(String.valueOf(presentValue));

        double profitLoss = presentValue - totalInvestment;

        profitLossTextField.setText(String.valueOf(profitLoss));

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void loadInvestmentDiversificationBarChart(AnchorPane anchorPane) {

    CategoryAxis categoryAxis = new CategoryAxis();
    NumberAxis numberAxis = new NumberAxis();

    BarChart<String, Number> barChart = new BarChart<>(categoryAxis, numberAxis);

    categoryAxis.setLabel("Investment Sector");
    numberAxis.setLabel("Total Investment");

    barChart.setCategoryGap(10); 
    barChart.setBarGap(0); 
    // Fetch data from the investments table
    connect = connectDB();
    String selectSQL = "SELECT transaction_type, COUNT(*) as total_entries, SUM(transaction_amount) as total_amount FROM investments GROUP BY transaction_type";

    try (PreparedStatement preparedStatement = connect.prepareStatement(selectSQL)) {
        ResultSet resultSet = preparedStatement.executeQuery();

        int index = 0; 

        while (resultSet.next()) {
            String sector = resultSet.getString("transaction_type");
            double totalAmount = resultSet.getDouble("total_amount");
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(Integer.toString(index), totalAmount));
            barChart.getData().add(series);
           for (XYChart.Data<String, Number> data : series.getData()) {
        Node node = data.getNode();
            if (node instanceof Rectangle) {
            Rectangle bar = (Rectangle) node;
        bar.setWidth(30);
    }
}

            index++;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    anchorPane.getChildren().add(barChart);

    barChart.setPrefSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
}
  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectDB();
        displayUsername();
        defaultNav();
        displayTotalInvestmentsAndProfitLoss(totalInvestments,profitLoss,totalPortfolioValue );
        loadInvestmentDiversificationChart(portfolioPiechart_pane);
        loadInvestmentDiversificationBarChart(barChartPane);
        investmentType_dropdown.getItems().addAll("Stocks", "Bonds", "Real-Estate", "Cash", "Mutual-Funds", "Fixed-Deposit", "Commodities");
        

    }

}