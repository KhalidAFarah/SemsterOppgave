package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Superbruker.fxml"));
        primaryStage.setTitle("Inn_logging");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
/*
    public void Registrering(Stage Scene_2) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Registrering.fxml"));
        Scene_2.setTitle("Registrer ny bruker");
        Scene_2.setScene(new Scene(root, 450, 450));
    }

 */


    public static void main(String[] args) {
        launch(args);
    }
}
