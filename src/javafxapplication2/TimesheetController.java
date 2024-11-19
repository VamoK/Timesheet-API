package javafxapplication2;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TimesheetController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button sign_in_submit;

    // Database connection details
    private final String DB_URL = "jdbc:sqlserver://DESKTOP-UG7EEGR;databaseName=Users;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "12534679";

    @FXML
    private void checkDB() {
        System.out.println("Username TextField: " + username); // Should not be null
        System.out.println("Password PasswordField: " + password);

        String user = username.getText();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("Username or password is empty.");
            return;
        }

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (connection != null) {
                
                UserManager us = new UserManager(connection);
                us.addUser(user, pass);
                connection.close();
                System.out.println("Connection established successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }
}
