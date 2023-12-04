/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.app.controller;

// InvestmentController.java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.app.model.InvestmentDAO;
import main.java.app.model.Investment;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public class InvestmentController {
    private InvestmentDAO investmentDAO;
    private ObservableList<Investment> investmentsList;

    public InvestmentController() {
        this.investmentDAO = new InvestmentDAO();
        this.investmentsList = FXCollections.observableArrayList();
        loadInvestments();
    }

    private void loadInvestments() {
        List<Investment> investments = investmentDAO.getAllInvestments();
        investmentsList.setAll(investments);
    }

    public ObservableList<Investment> getInvestmentsList() {
        return investmentsList;
    }

    public void addInvestment(Investment investment) {
        investmentDAO.addInvestment(investment);
        investmentsList.add(investment);
    }

    public void removeInvestment(Investment investment) {
        investmentDAO.removeInvestment(investment.getId());
        investmentsList.remove(investment);
    }

    public void updateInvestment(Investment updatedInvestment) {
        investmentDAO.updateInvestment(updatedInvestment);

        // Update the corresponding item in the investmentsList
        for (Investment existingInvestment : investmentsList) {
            if (existingInvestment.getId() == updatedInvestment.getId()) {
                existingInvestment.setName(updatedInvestment.getName());
                existingInvestment.setAmount(updatedInvestment.getAmount());
                existingInvestment.setSector(updatedInvestment.getSector());
                // You can update more fields as needed
                break;
            }
        }
    }
    
     public void viewInvestmentDetails(Investment investment) {
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Investment Details");
        alert.setHeaderText(null);
        alert.setContentText("Investment Details:\n" + investment.toString());
        alert.showAndWait();
    }
     
     
    public void checkSectorExposure() {
 
        System.out.println("Checking Sector Exposure...");
    }

    public PieChart createPortfolioChart() {
   
        PieChart pieChart = new PieChart();
        investmentsList.forEach(investment ->
                pieChart.getData().add(new PieChart.Data(investment.getName(), investment.getAmount())));
        pieChart.setTitle("Portfolio Allocation");
        return pieChart;
    }
    
//    public void viewInvestmentDetails() {
//        Investment selectedInvestment = tableView.getSelectionModel().getSelectedItem();
//        if (selectedInvestment != null) {
//            viewInvestmentDetails(selectedInvestment);
//        }
//    }
    ///This method is to be used incase of a table
    
    public void showPortfolioChart() {
        PieChart portfolioChart = createPortfolioChart();
        
        displayChartDialog(portfolioChart);
    }

    private void displayChartDialog(PieChart chart) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(chart);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    } //Change it from a dailog to reflect with front page design e.g ListBox

}
