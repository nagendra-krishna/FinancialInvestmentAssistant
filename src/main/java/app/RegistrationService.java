package main.java.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RegistrationService {
    private final Connection connection;

    public RegistrationService(Connection connection) {
        this.connection = connection;
    }

    public String registerUser(String email, String username, String password, String confirmPassword, String question, String answer) {
        AlertMessage alert = new AlertMessage();

        String validationResult = validateRegistrationFields(email, username, password, confirmPassword, question, answer);
        if (!"OK".equals(validationResult)) {
            alert.errorMessage(validationResult);
            return validationResult;
        }

        try {
            if (isUsernameTaken(username)) {
                String errorMessage = username + " is already taken";
                alert.errorMessage(errorMessage);
                return errorMessage;
            }

            boolean registrationSuccess = addUserToDatabase(email, username, password, question, answer);

            if (registrationSuccess) {
                //alert.successMessage("Registered Successfully!");
                return "Registered Successfully!";
            } else {
                alert.errorMessage("Registration failed");
                return "Registration failed";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Error during registration");
            return "Error during registration";
        }
    }

    private String validateRegistrationFields(String email, String username, String password, String confirmPassword, String question, String answer) {
        if (areFieldsEmpty(email, username, password, confirmPassword, question, answer))
            return "All fields are necessary to be filled";
        if (!doPasswordsMatch(password, confirmPassword))
            return "Password does not match";
        if (!isPasswordValid(password))
            return "Invalid Password, at least 8 characters needed";
        return "OK";
    }

    private boolean areFieldsEmpty(String email, String username, String password, String confirmPassword, String question, String answer) {
        return email.isEmpty() || username.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()
                || question == null || answer.isEmpty();
    }

    private boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private boolean isUsernameTaken(String username) throws SQLException {
        String checkUsername = "SELECT * FROM users WHERE username = ?";
        Connection connection = this.connection;
        PreparedStatement preparedStatement = connection.prepareStatement(checkUsername);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private boolean addUserToDatabase(String email, String username, String password, String question, String answer) {
        String insertData = "INSERT INTO users (email, username, password, question, answer, date) VALUES(?,?,?,?,?,?)";

        try (Connection connection = this.connection;
             PreparedStatement prepare = connection.prepareStatement(insertData)) {

            prepare.setString(1, email);
            prepare.setString(2, username);
            prepare.setString(3, password);
            prepare.setString(4, question);
            prepare.setString(5, answer);
            prepare.setDate(6, new java.sql.Date(new Date().getTime()));

            int result = prepare.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
}
