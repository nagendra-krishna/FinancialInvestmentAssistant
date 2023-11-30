package main.java.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main FXML file
        	System.out.println(getClass().getResource("/app/PortfolioView.fxml"));
        
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("/app/view/PortfolioView.fxml"));
        	
        	Parent root = loader.load();
                
                // the investment controller object czn be edited if need be or cut out entirely
               // InvestmentController controller = new InvestmentController();
                     //loader.setController(controller);

            Scene scene = new Scene(root);

            primaryStage.setTitle("Financial Investment Assistant");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load PortfolioView.fxml", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
