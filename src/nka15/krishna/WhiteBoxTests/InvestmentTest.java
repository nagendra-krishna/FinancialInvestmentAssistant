package nka15.krishna.WhiteBoxTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.app.Investment;

public class InvestmentTest {

	/**
     * Test Case ID: FIN-02
     * Technique: Statement Coverage / White-Box Testing
     * Scenario: Calculate the present value for stocks investment.
     * Test Data: Investment type "stocks", amount "1000", start date "2023-01-01".
     * Expected Result: The present value calculated should be approximately 275636.19.
     */
	
    @Test
    public void testCalculatePresentValueStocks() {
        Investment investment = new Investment(1, "stocks", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(275636.1947986698, presentValue, 0.01);
    }

   
    /*
     * Test Case ID: FIN-03
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for bonds investment.
    Test Data: Investment type "bonds", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1046.80.
    Actual Result: 1046.8002302556679
    Pass/Fail: Pass
     */
    
    @Test
    public void testCalculatePresentValueBonds() {
        Investment investment = new Investment(2, "bonds", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1046.8002302556679, presentValue, 0.01);
    }
  
    
    /*
     * Test Case ID: FIN-04
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for real-estate investment.
    Test Data: Investment type "real-estate", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1027.846341060854.
    Actual Result: 1027.846341060854
    Pass/Fail: Pass
     */

    @Test
    public void testCalculatePresentValueRealEstate() {
        Investment investment = new Investment(3, "real-estate", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1027.8463410608545, presentValue, 0.01);
    }

    /*
     * Test Case ID: FIN-05
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for cash investment.
    Test Data: Investment type "cash", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1000.01.
    Actual Result: 1000.01
    Pass/Fail: Pass
    */
    
    @Test
    public void testCalculatePresentValueCash() {
        Investment investment = new Investment(4, "cash", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1000.0, presentValue, 0.01);
    }

    /*
     *  Test Case ID: FIN-06
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for mutual-funds investment.
    Test Data: Investment type "mutual-funds ", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1095.5832.
    Actual Result: 1095.5832
    Pass/Fail: Pass
	*/
    
    @Test
    public void testCalculatePresentValueMutualFunds() {
        Investment investment = new Investment(5, "mutual-funds", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1095.5832073797988, presentValue, 0.01);
    }

    /*
     * Test Case ID: FIN-07
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for fixed-deposit investment.
    Test Data: Investment type "fixed-deposit", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1095.5832
    Actual Result: 1095.5832
    Pass/Fail: Pass
	*/
    
    @Test
    public void testCalculatePresentValueFixedDeposit() {
        Investment investment = new Investment(6, "fixed-deposit", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1037.2839298203894, presentValue, 0.01);
    }

    /*
     * Test Case ID: FIN-08
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for commodities investment.
    Test Data: Investment type "commodities ", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 1056.39
    Actual Result: 1056.39
    Pass/Fail: Pass
    *
    */

    @Test
    public void testCalculatePresentValueCommodities() {
        Investment investment = new Investment(7, "commodities", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(1056.3958327009934, presentValue, 0.01);
    }

    /*
     * Test Case ID: FIN-09
    Test Technique: Statement Coverage 
    Scenario: Calculate the present value for unknown-type investment.
    Test Data: Investment type "unknown-type", amount "1000", start date "2023-01-01".
    Expected Result: The present value calculated should be approximately 0.0
    Actual Result: 0.0
    Pass/Fail: Pass
     */
    @Test
    public void testCalculatePresentValueUnknownType() {
        Investment investment = new Investment(8, "unknown-type", "1000", java.sql.Date.valueOf("2023-01-01"));
        double presentValue = investment.calculatePresentValue();
        assertEquals(0.0, presentValue, 0.01);
    }
}
