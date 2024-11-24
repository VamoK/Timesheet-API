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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.crypto.SecretKey;

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
            // Generate a SecretKey
            SecretKey secretKey = EncryptionUtil.generateSecretKey();

            // Convert SecretKey to Base64 for storage (optional)
            String secretKeyBase64 = EncryptionUtil.secretKeyToBase64(secretKey);

            // Convert Base64 back to SecretKey (optional)
            SecretKey restoredSecretKey = EncryptionUtil.base64ToSecretKey(secretKeyBase64);
            
            if (connection != null) {
                
                UserManager us = new UserManager(connection , restoredSecretKey);
                us.addUser(user, pass);
                connection.close();
                System.out.println("Connection established successfully!");
                
                //load new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                // Get the current stage
                Stage stage = (Stage) sign_in_submit.getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(TimesheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }
}
