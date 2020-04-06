package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Standardbruker.fxml"));
        primaryStage.setTitle("TechMet");
       //primaryStage.setScene(new Scene(root, 380, 450)); //details for LoggInn.FXML
        primaryStage.setScene(new Scene(root, 600, 400)); // details for MellomSide.FXML
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
