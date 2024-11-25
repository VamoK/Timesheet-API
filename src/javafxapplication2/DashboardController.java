/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication2;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Vamokuhle
 */
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private LocalTime startTime;
    
    private Timeline timeline;
    
    @FXML
    private TextArea projectName;
    
    @FXML
    private TextArea projectDec;
    
    @FXML
    private Text display;
    
    @FXML
    private LocalTime startCount(ActionEvent event){
        
        startTime = LocalTime.now();
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateElapsedTime()));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        return startTime;
    }
    
    private void updateElapsedTime() {
        LocalTime now = LocalTime.now();
        long seconds = java.time.Duration.between(startTime, now).getSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        display.setText(String.format("Elapsed Time: %02d:%02d:%02d", hours, minutes, seconds));
        display.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
