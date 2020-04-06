package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        primaryStage.setTitle("TechMet");
      primaryStage.setScene(new Scene(root, 400, 400)); //details for LoggInn.FXML
      // primaryStage.setScene(new Scene(root, 602.4, 400)); //test for andre fxml filer
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
