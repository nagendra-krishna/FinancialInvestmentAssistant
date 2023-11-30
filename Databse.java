/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/financial_investment_db";
        String username = "your_username";
        String password = "your_password";

        return DriverManager.getConnection(url, username, password);
    }
}
//LOGIC EXPLANATION.
// this Database was created with sql server managment, but also can be created in sql workbench
//checked attached sql file
//import sql Workbench dependencies as needed
//Remember to change package name to app.model
