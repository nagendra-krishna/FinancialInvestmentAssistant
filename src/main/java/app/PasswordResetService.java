package main.java.app;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordResetService {

 private final Connection connection;

 public PasswordResetService(Connection connection) {
     this.connection = connection;
 }

 public boolean isUserInformationCorrect(String username, String question, String answer) {
     try {
         String checkData = "SELECT username, question, answer FROM users "
                 + "WHERE username = ? AND question = ? AND answer = ?";
         PreparedStatement prepare = connection.prepareStatement(checkData);
         prepare.setString(1, username);
         prepare.setString(2, question);
         prepare.setString(3, answer);
         ResultSet result = prepare.executeQuery();
         return result.next();
     } catch (SQLException e) {
         e.printStackTrace();
         return false;
     }
 }

 public boolean changePassword(String username, String newPassword) {
     try {
         String updateData = "UPDATE users SET password = ? WHERE username = ?";
         PreparedStatement prepare = connection.prepareStatement(updateData);
         prepare.setString(1, newPassword);
         prepare.setString(2, username);
         int rowsAffected = prepare.executeUpdate();
         return rowsAffected > 0;
     } catch (SQLException e) {
         e.printStackTrace();
         return false;
     }
 }
}
