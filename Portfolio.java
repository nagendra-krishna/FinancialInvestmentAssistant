// Portfolio.java model

package main.java.app.model;

import java.util.List;

public class Portfolio {
    
    private String PortfolioName;
    private String symbol;
    private int quantity;
    private double costBasis;
    private int id;
    private List<Portfolio> entries;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCostBasis() {
        return costBasis;
    }

    public void setCostBasis(double costBasis) {
        this.costBasis = costBasis;
    }

    
    
    public int getId() {
        return id;
    }

    public List<Portfolio> getEntries() {
        return entries; 
    }
  public String getPortfolioName() {
        return PortfolioName;
    }

    public void setPortfolioName(String PortfolioName) {
        this.PortfolioName = PortfolioName;
    }

 
    private double TotalValue;

    public double getTotalValue() {
        return TotalValue;
    }

    public void setTotalValue(double TotalValue) {
        this.TotalValue = TotalValue;
    }
    // other methods 
    
}
//
//public class Portfolio
//{
//    
//    private String symbol;
//    private int quantity;
//    private double costBasis;
//    
//    // getters, setters
//    
//}