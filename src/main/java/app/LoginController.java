package main.java.app;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;
import javafx.application.Platform;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    public AnchorPane mainForm;

    @FXML
    public AnchorPane loginForm;

    @FXML
    public TextField loginUsername;

    @FXML
    public PasswordField loginPassword;

    @FXML
    public TextField loginShowPassword;

    @FXML
    public CheckBox loginSelectShowPassword;

    @FXML
    public Button loginBtn;

    @FXML
    public Button loginCreateAccount;

    @FXML
    public Hyperlink loginForgotPassword;

    @FXML
    public AnchorPane signupForm;

    @FXML
    public TextField signupEmail;

    @FXML
    public TextField signupUsername;

    @FXML
    public PasswordField signupPassword;

    @FXML
    public PasswordField signupCPassword;

    @FXML
    public Button signupLoginAccount;

    @FXML
    public ComboBox<?> signup_selectQuestion;

    @FXML
    public TextField signupAnswer;

    @FXML
    public AnchorPane forgotForm;

    @FXML
    public TextField forgotAnswer;

    @FXML
    public Button forgotBackBtn;

    @FXML
    public ComboBox<?> forgot_selectQuestion;

    @FXML
    public TextField forgotUsername;

    @FXML
    public AnchorPane changePassForm;

    @FXML
    public Button changePassBackBtn;

    @FXML
    public PasswordField changePassPassword;

    @FXML
    public PasswordField changePassCPassword;
    
     public boolean loginSuccess = false;

     private DataSource dataSource;
     private Connection connect;
     private PreparedStatement prepare;
     private ResultSet result;
     private Statement statement;
     
     private PasswordResetService passwordResetService;
     private RegistrationService registrationService;

     public LoginController() {
    	    // Initialize dataSource with default configuration for FXML-based instantiation
    	    this.dataSource = createDataSource();
    	    this.passwordResetService = new PasswordResetService(connectDB());
    	    this.registrationService = new RegistrationService(connectDB());
    	}

    	// Constructor with DataSource for testing
    	public LoginController(DataSource dataSource) {
    	    this.dataSource = dataSource;
    	    this.passwordResetService = new PasswordResetService(connectDB());
    	    this.registrationService = new RegistrationService(connectDB());
    	}

    	private static DataSource createDataSource() {
    	    SQLiteDataSource dataSource = new SQLiteDataSource();
    	    dataSource.setUrl("jdbc:sqlite:useraccounts.db");
    	    return dataSource;
    	}

     public Connection connectDB() {
         try {
             if (connect == null || connect.isClosed()) {
                 connect = dataSource.getConnection();
                 if (connect != null && !doesTableExist(connect, "users")) {
                     createUsersTable(connect);
                 }
             }
             return connect;
         } catch (SQLException e) {
             e.printStackTrace();
             return null;
         }
     }
     
     private boolean doesTableExist(Connection connection, String tableName) throws SQLException {
    	    DatabaseMetaData meta = connection.getMetaData();
    	    if (meta != null) {
    	        try (ResultSet resultSet = meta.getTables(null, null, tableName, null)) {
    	            return resultSet.next();
    	        }
    	    }
    	    return false;
    	}

private void createUsersTable(Connection connection) throws SQLException {
    String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "email TEXT NOT NULL, "
            + "username TEXT NOT NULL, "
            + "password TEXT NOT NULL, "
            + "question TEXT NOT NULL, "
            + "answer TEXT NOT NULL, "
            + "date DATE NOT NULL, "
            + "update_date DATE)";
    
    try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(createTableSQL);
    }
}

public String login(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
        return "Username/Password is Missing";
    } else {
        String selectData = "SELECT * FROM users WHERE username = ? and password = ?";

        try {
            Connection connect = connectDB();
            PreparedStatement prepare = connect.prepareStatement(selectData);
            prepare.setString(1, username);
            prepare.setString(2, password);

            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                getData.username = result.getString("username");
                return "Successfully Login!";
            } else {
                return "Incorrect Username/Password";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Login failed due to an error";
        }
    }
}

// Method to be called from UI event handler
public void login() {
    String username = loginUsername.getText();
    String password = loginSelectShowPassword.isSelected() ? loginShowPassword.getText() : loginPassword.getText();

    String message = login(username, password);
    displayMessage(message);

    if ("Successfully Login!".equals(message)) {
        loadDashboard();
    }
}

public void displayMessage(String message) {
    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    });
}
    private void loadDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            loginBtn.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPassword() {
        boolean isShowPasswordSelected = loginSelectShowPassword.isSelected();
        String passwordText = isShowPasswordSelected ? loginPassword.getText() : loginShowPassword.getText();

        loginShowPassword.setVisible(isShowPasswordSelected);
        loginPassword.setVisible(!isShowPasswordSelected);

        if (!isShowPasswordSelected) {
            loginPassword.setText(passwordText);
        } else 
        {
            loginShowPassword.setText(passwordText);
        }

    }
    
    

    
    public void forgotPassword() {
        AlertMessage alert = new AlertMessage();

        String username = forgotUsername.getText();
        String selectedQuestion = (String) forgot_selectQuestion.getSelectionModel().getSelectedItem();
        String answer = forgotAnswer.getText();

        if (username.isEmpty() || selectedQuestion == null || answer.isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            if (passwordResetService.isUserInformationCorrect(username, selectedQuestion, answer)) {
                // Correct user information, show the password reset form
                // Set the visibility of forms accordingly
                signupForm.setVisible(false);
                loginForm.setVisible(false);
                forgotForm.setVisible(false);
                changePassForm.setVisible(true);
            } else {
                alert.errorMessage("Incorrect information");
            }
        }
    }

    public void changePassword() {
        AlertMessage alert = new AlertMessage();

        String newPassword = changePassPassword.getText();

        if (newPassword.isEmpty() || changePassCPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else if (!newPassword.equals(changePassCPassword.getText())) {
            alert.errorMessage("Password does not match");
        } else if (newPassword.length() < 6) {
            alert.errorMessage("Invalid Password, at least 6 characters needed");
        } else {
            if (passwordResetService.changePassword(forgotUsername.getText(), newPassword)) {
                // Password changed successfully
                // Set the visibility of forms accordingly
                signupForm.setVisible(false);
                loginForm.setVisible(true);
                forgotForm.setVisible(false);
                changePassForm.setVisible(false);

                loginUsername.setText("");
                loginPassword.setVisible(true);
                loginPassword.setText("");
                loginShowPassword.setVisible(false);
                loginSelectShowPassword.setSelected(false);

                changePassPassword.setText("");
                changePassCPassword.setText("");

                alert.successMessage("Successfully changed Password");
            } else {
                alert.errorMessage("Failed to change password");
            }
        }
    }

    
    
    public void forgotListQuestion(){
        
        List<String> listQ = new ArrayList<>();
        
        for(String data : questionList){
            listQ.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listQ);
        forgot_selectQuestion.setItems(listData);
        
    }


    public void register() {
        String email = getEmail();
        String username = getUsername();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String question = getSelectedQuestion();
        String answer = getAnswer();

        String message = registrationService.registerUser(email, username, password, confirmPassword, question, answer);

        displayMessage(message);

        if ("Registered Successfully!".equals(message)) {
            clearFields(); 
        }
    }

    public void clearFields() {
    	signupEmail.setText("");
        signupUsername.setText("");
        signupPassword.setText("");
        signupCPassword.setText("");
        signup_selectQuestion.getSelectionModel().clearSelection();
        signupAnswer.setText("");
    }
    
    protected String getEmail() {
        return signupEmail.getText();
    }

    protected String getUsername() {
        return signupUsername.getText();
    }

    protected String getPassword() {
        return signupPassword.getText();
    }

    protected String getConfirmPassword() {
        return signupCPassword.getText();
    }

    protected String getSelectedQuestion() {
        // Assuming signup_selectQuestion is a ChoiceBox or ComboBox
        return signup_selectQuestion.getValue() != null ? signup_selectQuestion.getValue().toString() : null;
    }

    protected String getAnswer() {
        return signupAnswer.getText();
    }



    public void switchForm(ActionEvent event) {

       
        if (event.getSource() == signupLoginAccount || event.getSource() == forgotBackBtn) {
            signupForm.setVisible(false);
            loginForm.setVisible(true);
            forgotForm.setVisible(false);
            changePassForm.setVisible(false);
        } else if (event.getSource() == loginCreateAccount) {
            signupForm.setVisible(true);
            loginForm.setVisible(false);
            forgotForm.setVisible(false);
            changePassForm.setVisible(false);
        } else if (event.getSource() == loginForgotPassword) {
            signupForm.setVisible(false);
            loginForm.setVisible(false);
            forgotForm.setVisible(true);
            changePassForm.setVisible(false);
           
            forgotListQuestion();
        } else if (event.getSource() == changePassBackBtn) {
            signupForm.setVisible(false);
            loginForm.setVisible(false);
            forgotForm.setVisible(true);
            changePassForm.setVisible(false);
        }

    }

    private String[] questionList = {"What is your favorite food?", "What is your favorite color?",
        "What is the name of your pet?", "What is your most favorite sport?"};

    public void questions() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        signup_selectQuestion.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questions();
 
       forgotListQuestion();
    }

	

}

