package javafxapplication2;

import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            
            // Generate a salt
            byte[] salt = generateSalt();

            // Hash the password with the salt
            String hashedPassword = hashPassword(pass, salt);
            
            if (connection != null) {
                
                UserManager us = new UserManager(connection);
                us.addUser(user, hashedPassword);
                connection.close();
                System.out.println("Connection established successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(TimesheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Generate a random salt
    private static byte[] generateSalt() throws Exception {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Hash the password with SHA-256
    private static String hashPassword(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt); // Add salt to the hashing
        byte[] hashedBytes = md.digest(password.getBytes());
        return bytesToHex(hashedBytes);
    }

    // Convert bytes to a hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }
}
