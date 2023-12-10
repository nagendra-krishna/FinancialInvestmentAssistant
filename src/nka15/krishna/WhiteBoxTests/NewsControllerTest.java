package nka15.krishna.WhiteBoxTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.AnchorPane;
import main.java.app.NewsController;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsControllerTest {

/*
 * Test Case ID: FIN-15
Test Technique: Statement Coverage
Scenario: Displaying finance news in an AnchorPane.
Test Data:
- Initial State: An empty AnchorPane object.
- Action: Call NewsController.displayFinanceNews with the AnchorPane.

Expected Result:
  	- The AnchorPane should have one or more children after the method call.
- Each child in the AnchorPane should have non-null and non-empty text content.
Actual Result: no empty text content and child anchorPanes are not Null
Pass/Fail: Pass

*/
    @Test
    public void testDisplayFinanceNews() {
        AnchorPane newsAnchorPane = new AnchorPane();

        // Call the method to display finance news
        NewsController.displayFinanceNews(newsAnchorPane);

        // You can add assertions to check if the news items were added to the AnchorPane
        assertTrue(newsAnchorPane.getChildren().size() > 0);

        // You can also perform more specific assertions based on your needs
        // For example, checking the text content of added Text nodes
        for (javafx.scene.Node node : newsAnchorPane.getChildren()) {
            if (node instanceof javafx.scene.text.Text) {
                javafx.scene.text.Text textNode = (javafx.scene.text.Text) node;
                assertNotNull(textNode.getText());
                assertFalse(textNode.getText().isEmpty());
            }
        }
    }
}


