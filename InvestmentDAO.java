/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.app.model;

// InvestmentDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDAO {
    private Connection connection;

    //connect to database
    public InvestmentDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Get allInvestments(view after loging in sucessfully)
    public List<Investment> getAllInvestments() {
        List<Investment> investments = new ArrayList<>();
        String query = "SELECT * FROM investments";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Investment investment = new Investment();
                investment.setId(resultSet.getInt("id"));
                investment.setName(resultSet.getString("name"));
                investment.setAmount(resultSet.getDouble("amount"));
                investment.setSector(resultSet.getString("sector"));
                investments.add(investment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investments;
    }
    
    //addInvestments
    public void addInvestment(Investment investment) {
        String query = "INSERT INTO investments (name, amount, sector) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, investment.getName());
            statement.setDouble(2, investment.getAmount());
            statement.setString(3, investment.getSector());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                investment.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //removeInvestment
    public void removeInvestment(int investmentId) {
        String query = "DELETE FROM investments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, investmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //updateInvestment
    public void updateInvestment(Investment updatedInvestment) {
        String query = "UPDATE investments SET name = ?, amount = ?, sector = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, updatedInvestment.getName());
            statement.setDouble(2, updatedInvestment.getAmount());
            statement.setString(3, updatedInvestment.getSector());
            statement.setInt(4, updatedInvestment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

