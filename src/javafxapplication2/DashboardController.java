/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Vamokuhle
 */
public class DashboardController implements Initializable {

    @FXML
    private BorderPane bp;
    
    @FXML
    private AnchorPane ap;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private void loadPage(String page){
        
        Parent root = null;
        
        try {
            
            
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bp.setCenter(root);
        
    }
    @FXML
    private void createTimesheet(MouseEvent event){
        
        bp.setCenter(ap);
    }
    
    @FXML
    private void viewTimesheet(MouseEvent event){
         loadPage("view_timesheet");
    }
}
