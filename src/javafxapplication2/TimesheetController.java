package javafxapplication2;

import java.awt.event.ActionEvent;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.SecretKey;

public class TimesheetController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button sign_in_submit;
    
    @FXML
    private Text error_message;
    
    @FXML
    private Hyperlink signUpLink;
    
    @FXML
    private TextField email;
    
    @FXML
    private PasswordField password2;

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
                User userResponse = us.getUser(user, pass);
                
                if(userResponse.getUsername() == null){
                   
                       error_message.setText("Incorrect Credentials.Please check your username and password");
                       error_message.setVisible(true);
                       
                }else{
                    
                    //load new FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    //DashboardController sbController = loader.getController();
                    Parent root = loader.load();
                    // Get the current stage
                    Stage stage = (Stage) sign_in_submit.getScene().getWindow();


                    // Set the new scene
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                connection.close();
                
                
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(TimesheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void openSignUp(){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void signUp(){
        
        System.out.println("Username TextField: " + username); // Should not be null
        System.out.println("Password PasswordField: " + password);
        
        String user = email.getText();
        String pass = password2.getText();
        
        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("Username or password is empty.");
            return;
        }

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            if (connection != null) {
                
                UserManager us = new UserManager(connection);
                String response = us.addUser(user, pass);
                    
                if(response.equals("User added successfully. Please sign in")){
                    
                    //load new FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("timesheet.fxml"));
                    //DashboardController sbController = loader.getController();
                    Parent root = loader.load();
                    // Get the current stage
                    Stage stage = (Stage) sign_in_submit.getScene().getWindow();


                    // Set the new scene
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                    error_message.setText(response);
                    error_message.setVisible(true);
                }else{
                    error_message.setText(response);
                    error_message.setVisible(true);
                }
                    
                
                connection.close();
                
                
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
