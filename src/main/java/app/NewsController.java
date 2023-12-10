package main.java.app;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class NewsController {

    public static void displayFinanceNews(AnchorPane newsAnchorPane) {
        // Fetch finance-related news items (you can replace this with your API call)
        List<String> financeNewsItems = getFinanceNews();

        // Clear existing items in the newsAnchorPane
        newsAnchorPane.getChildren().clear();

        // Set initial y-coordinate for positioning news items
        double yCoordinate = 20.0;

        // Add news items to the AnchorPane
        for (String newsItem : financeNewsItems) {
            // Create a Text node for each news item
            Text text = new Text(newsItem);

            // Customize the styling of the text if needed
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

            // Set the position of the Text node within the AnchorPane
            AnchorPane.setTopAnchor(text, yCoordinate);
            AnchorPane.setLeftAnchor(text, 20.0);

            // Add the text to the AnchorPane
            newsAnchorPane.getChildren().add(text);

            // Update y-coordinate for the next news item
            yCoordinate += 30.0; // Adjust the spacing between news items as needed
        }
    }

    // Mock method to fetch finance-related news items
    private static List<String> getFinanceNews() {
        return Arrays.asList(
                "Stock Market Update: The Dow Jones Industrial Average reached a new all-time high today.",
        "Cryptocurrency News: Bitcoin and Ethereum see a surge in prices amid increasing investor interest.",
        "Economic Outlook: Analysts predict positive growth for the global economy in the coming quarters.",
        "Corporate Earnings Report: Tech giants report robust earnings, beating market expectations.",
        "Federal Reserve Decision: The Federal Reserve announces interest rate adjustments to support economic recovery.",
        "Investment Trends: Renewable energy stocks gain traction as countries focus on sustainable development.",
        "Mergers and Acquisitions: Major companies explore strategic partnerships to enhance market position.",
        "Commodity Prices: Oil prices experience fluctuations due to geopolitical tensions in key producing regions.",
        "Global Trade: Trade tensions ease as nations negotiate new agreements for smoother commerce.",
        "Fintech Innovations: Financial technology companies introduce groundbreaking solutions, reshaping the industry."
        );
    }
}
