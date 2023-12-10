package nka15.krishna.WhiteBoxTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.app.TransactionData;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.sql.Time;

public class TransactionDataTest {

	/*
	 * Test Case ID: FIN-25
	Test Technique: Statement Coverage
	Scenario: Verify that the getters of the TransactionData class return correct values.

	 */
    @Test
    public void testGetters() {
        // Create a TransactionData object with sample data
        TransactionData transactionData = new TransactionData(1, "Credit", "TX123", 100.0, new Date(), new Time(System.currentTimeMillis()));

        // Test the getters
        assertEquals(1, transactionData.getSerialNo());
        assertEquals("Credit", transactionData.getTransactionType());
        assertEquals("TX123", transactionData.getTransactionId());
        assertEquals(100.0, transactionData.getAmount());
        assertNotNull(transactionData.getDate());
        assertNotNull(transactionData.getTime());
    }

    /*Test Case ID: FIN-26
    Test Technique: Statement Coverage
    Scenario: Verify that the property methods of the TransactionData class return correct values.
     */
    
    @Test
    public void testProperties() {
        // Create a TransactionData object with sample data
        TransactionData transactionData = new TransactionData(2, "Debit", "TX456", 200.0, new Date(), new Time(System.currentTimeMillis()));

        // Test the properties
        assertEquals(2, transactionData.serialNoProperty().get());
        assertEquals("Debit", transactionData.transactionTypeProperty().get());
        assertEquals("TX456", transactionData.transactionIdProperty().get());
        assertEquals(200.0, transactionData.amountProperty().get());
        assertNotNull(transactionData.dateProperty().get());
        assertNotNull(transactionData.timeProperty().get());
    }
}

