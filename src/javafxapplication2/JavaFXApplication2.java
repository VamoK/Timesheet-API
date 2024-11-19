package javafxapplication2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApplication2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file and set it as the root of the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Timesheet.fxml"));

            Parent root = loader.load();

            // Optionally, you can access the controller if needed
            TimesheetController controller = loader.getController();
            // For example, you could call a method on the controller here if needed
            // controller.someMethod();

            // Set the scene with the loaded FXML root
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
