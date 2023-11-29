import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, 1060, 600));
            primaryStage.show();
            }
        catch (IOException e)
        {
             e.printStackTrace();
        }
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}
