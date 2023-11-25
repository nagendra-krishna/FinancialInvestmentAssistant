package main.java.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import main.java.app.model.Portfolio;

public class PortfolioController {
    @FXML
    private Label portfolioLabel;

    @FXML
    private TableView<Portfolio> portfolioTableView;

    // You can inject services or other dependencies as needed

    @FXML
    private void initialize() {
        // Initialization code, if needed
        displayDefaultPortfolio();
    }

    @FXML
    private void handleRefreshButton() {
        // Implement logic to refresh the portfolio data
        System.out.println("Refreshing portfolio data...");
        // For example, you can update the TableView with the latest portfolio information
        displayDefaultPortfolio();
    }

    @FXML
    private void handlePortfolioSelection() {
        // Implement logic to handle selection of a portfolio in the TableView
        Portfolio selectedPortfolio = portfolioTableView.getSelectionModel().getSelectedItem();
        if (selectedPortfolio != null) {
            System.out.println("Selected Portfolio: " + selectedPortfolio.getPortfolioName());
        }
    }

    // Example method to display default portfolio data in the TableView
    private void displayDefaultPortfolio() {
        // This is a placeholder, replace it with actual logic to fetch portfolio data
        Portfolio defaultPortfolio = new Portfolio("Default Portfolio");
        defaultPortfolio.setTotalValue(100000.0);

        // Clear existing items and add the default portfolio to the TableView
        portfolioTableView.getItems().clear();
        portfolioTableView.getItems().add(defaultPortfolio);

        // Update the label to display the portfolio name
        portfolioLabel.setText("Portfolio: " + defaultPortfolio.getPortfolioName());
    }
}
