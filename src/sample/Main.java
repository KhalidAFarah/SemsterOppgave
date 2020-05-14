package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //BorderPane root = new BorderPane();

            Parent root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
            primaryStage.setTitle("TechMet");
            Scene scene = new Scene(root, 420, 700);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            //primaryStage.setScene(new Scene(root, 420, 450)); //details for LoggInn.FXML
            //primaryStage.setScene(new Scene(root, 602.4, 400)); //test for andre fxml filer
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
