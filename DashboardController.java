import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    
   @FXML
    private TextField addAmt;

    @FXML
    private Button addFunds_btn;

    @FXML
    private Button addInvestment_btn;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> authorizedByColumn;

    @FXML
    private TextField avlAmtToWithdraw;

    @FXML
    private LineChart<?, ?> barchart_pane;

    @FXML
    private TableColumn<?, ?> dateColumn;
 
    @FXML
    private ProgressIndicator goalProgessCircle;

    @FXML
    private ProgressBar goalProgressBar;

    @FXML
    private TextField goalTargetAmt;

    @FXML
    private AnchorPane goal_pane;

    @FXML
    private AnchorPane homePage;

    @FXML
    private Button home_btn;

    @FXML
    private TableColumn<?, ?> investedAmtColumn;

    @FXML
    private TextField investmentAmount;

    @FXML
    private TableColumn<?, ?> investmentDateColumn;

    @FXML
    private TableColumn<?, ?> investmentTypeColumn;

    @FXML
    private ComboBox<String> investmentType_dropdown;

    @FXML
    private TextField minAmountWallet;

    @FXML
    private AnchorPane newsPage;

    @FXML
    private Button news_btn;

    @FXML
    private AnchorPane piechart_pane;

    @FXML
    private TableColumn<?, ?> presentValueColumn;

    @FXML
    private TextField profitLoss;

    @FXML
    private Button removeInvestment_btn;

    @FXML
    private AnchorPane resourcesPage;

    @FXML
    private Button resources_btn;

    @FXML
    private TableColumn<?, ?> serialNoColumn;

    @FXML
    private Button signout_btn;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private TextField totalInvestments;

    @FXML
    private TextField totalPortfolioValue;

    @FXML
    private TableColumn<?, ?> tranactionIdColumn;

    @FXML
    private TableColumn<?, ?> transactionNameColumn;

    @FXML
    private TableColumn<?, ?> transactionTypeColumn;

    @FXML
    private AnchorPane transactionsPage;

    @FXML
    private TableView<?> transactionsTable;

    @FXML
    private Button transactions_btn;

    @FXML
    private Button updateGoal_btn;

    @FXML
    private AnchorPane walletPage;

    @FXML
    private Button wallet_btn;

    @FXML
    private Label welcomeName;

    @FXML
    private TextField withdrawAmt;

    @FXML
    private Button withdrawFunds_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;
    private double x = 0;
    private double y = 0;


    public void displayUsername(){
        welcomeName.setText(getData.username);
    }
    // THATS IT FOR THESE VIDEOS, THANKS FOR WATCHING!! SUBSCRIBE AND TURN ON NOTIFICATION 
//    TO NOTIF YOU FOR MORE UPCOMING VIDEOS THANKS FOR THE SUPPORT! : )
    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }
    
    public void switchForm(ActionEvent event) {
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

            //homeDisplayTotalEnrolledStudents();
            //homeDisplayMaleEnrolled();
          

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

            //TO BECOME UPDATED ONCE YOU CLICK THE ADD STUDENTS BUTTON ON NAV
          
            //addStudentsStatusList();
            //addStudentsCourseList();
            //addStudentsSearch();

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

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
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

        //HIDE YOUR DASHBOARD FORM
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        defaultNav();
        investmentType_dropdown.getItems().addAll("Stocks", "Bonds", "Real-Estate", "Cash", "Mutual-Funds", "Fixed-Deposit", "Commodities", "Metals");
        

    }

}