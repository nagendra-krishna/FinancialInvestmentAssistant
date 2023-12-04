package java.main.app;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

public Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connect == null || connect.isClosed()) {
                connect = DriverManager.getConnection("jdbc:sqlite:useraccounts.db");
                if (!doesTableExist(connect, "users")) {
                    createUsersTable(connect);
                }
            }

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

private boolean doesTableExist(Connection connection, String tableName) throws SQLException {
    DatabaseMetaData meta = connection.getMetaData();
    try (ResultSet resultSet = meta.getTables(null, null, tableName, null)) {
        return resultSet.next();
    }
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

    public void login() {
        
        
        AlertMessage alert = new AlertMessage();
        if (loginUsername.getText().isEmpty() || loginPassword.getText().isEmpty()) {
            alert.errorMessage(" Username/Password is Missing");
        } else {
            String selectData = "SELECT * FROM users WHERE "
                    + "username = ? and password = ?"; 

            connect = connectDB();
            if(loginSelectShowPassword.isSelected()){
                loginPassword.setText(loginShowPassword.getText());
            }else{
                loginShowPassword.setText(loginPassword.getText());
            }
            
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, loginUsername.getText());
                prepare.setString(2, loginPassword.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    
                    getData.username = result.getString("username");
                    alert.successMessage("Successfully Login!");
                    loginSuccess = true;
                    Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    loginBtn.getScene().getWindow().hide();
                } else {
                    // ELSE, THEN ERROR MESSAGE WILL APPEAR
                    alert.errorMessage("Incorrect Username/Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showPassword() {

        if (loginSelectShowPassword.isSelected()) {
            loginShowPassword.setText(loginPassword.getText());
            loginShowPassword.setVisible(true);
            loginPassword.setVisible(false);
        } else {
            loginPassword.setText(loginShowPassword.getText());
            loginShowPassword.setVisible(false);
            loginPassword.setVisible(true);
        }

    }

    public void forgotPassword() {

        AlertMessage alert = new AlertMessage();

        if (forgotUsername.getText().isEmpty()
                || forgot_selectQuestion.getSelectionModel().getSelectedItem() == null
                || forgotAnswer.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {

            String checkData = "SELECT username, question, answer FROM users "
                    + "WHERE username = ? AND question = ? AND answer = ?";

            connect = connectDB();

            try {

                prepare = connect.prepareStatement(checkData);
                prepare.setString(1, forgotUsername.getText());
                prepare.setString(2, (String) forgot_selectQuestion.getSelectionModel().getSelectedItem());
                prepare.setString(3, forgotAnswer.getText());

                result = prepare.executeQuery();
                // IF CORRECT
                if (result.next()) {
                    signupForm.setVisible(false);
                    loginForm.setVisible(false);
                    forgotForm.setVisible(false);
                    changePassForm.setVisible(true);
                } else {
                    alert.errorMessage("Incorrect information");
                }

            } catch (Exception e) {
                e.printStackTrace();
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

        AlertMessage alert = new AlertMessage();

     
        if (signupEmail.getText().isEmpty() || signupUsername.getText().isEmpty()
                || signupPassword.getText().isEmpty() || signupCPassword.getText().isEmpty()
                || signup_selectQuestion.getSelectionModel().getSelectedItem() == null
                || signupAnswer.getText().isEmpty()) {
            alert.errorMessage("All fields are necessary to be filled");
        } else if (signupPassword.getText() == signupCPassword.getText()) {
            
            alert.errorMessage("Password does not match");
        } else 
            if (signupPassword.getText().length() < 8) 
        {
            alert.errorMessage("Invalid Password, at least 8 characters needed");
        } else
        {
            
            String checkUsername = "SELECT * FROM users WHERE username = '"
                    + signupUsername.getText() + "'";
            connect = connectDB();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkUsername);

                if (result.next()) {
                    alert.errorMessage(signupUsername.getText() + " is already taken");
                } else {

                    String insertData = "INSERT INTO users "
                            + "(email, username, password, question, answer, date) "
                            + "VALUES(?,?,?,?,?,?)"; 

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, signupEmail.getText());
                    prepare.setString(2, signupUsername.getText());
                    prepare.setString(3, signupPassword.getText());
                    prepare.setString(4,
                            (String) signup_selectQuestion.getSelectionModel().getSelectedItem());
                    prepare.setString(5, signupAnswer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully!");

                    registerClearFields();

                    signupForm.setVisible(false);
                    loginForm.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerClearFields() {
        signupEmail.setText("");
        signupUsername.setText("");
        signupPassword.setText("");
        signupCPassword.setText("");
        signup_selectQuestion.getSelectionModel().clearSelection();
        signupAnswer.setText("");
    }
    
  public void changePassword() {
        AlertMessage alert = new AlertMessage();

        
        if (changePassPassword.getText().isEmpty() || changePassCPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else if (!changePassPassword.getText().equals(changePassCPassword.getText())) {
           
            alert.errorMessage("Password does not match");
        } else if (changePassPassword.getText().length() < 6) {
           
            alert.errorMessage("Invalid Password, at least 6 characters needed");
        } else {
           
            String updateData = "UPDATE users SET password = ?, update_date = ? WHERE username = ?";

            try (Connection connection = connectDB();
                 PreparedStatement prepare = connection.prepareStatement(updateData)) {

                prepare.setString(1, changePassPassword.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(2, String.valueOf(sqlDate));
                prepare.setString(3, forgotUsername.getText());

                prepare.executeUpdate();
                alert.successMessage("Successfully changed Password");

               
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

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public void dropTable() {
        String tableName = "users";
        Connection connection = connectDB();
        
        try (Statement statement = connection.createStatement()) {
            String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
            statement.executeUpdate(dropTableSQL);
            System.out.println("Table '" + tableName + "' dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questions();
 
       forgotListQuestion();
    }

}

