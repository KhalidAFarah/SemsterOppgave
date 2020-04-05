package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MellomSide_Controller {

    @FXML
    void On_Click_BtnLoggInn(ActionEvent event) throws IOException {

        Parent MellomSide = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        Scene LoggInn = new Scene(MellomSide);
        Stage Scene_8 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_8.setScene(LoggInn);
        Scene_8.setHeight(420);
        Scene_8.setWidth(410);
        Scene_8.show();
    }

    @FXML
    void On_Click_BtnNyBruker(ActionEvent event) throws IOException {

        Parent MellomSide = FXMLLoader.load(getClass().getResource("Registrering.fxml"));
        Scene Register_ny_bruker = new Scene(MellomSide);
        Stage Scene_9 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_9.setScene(Register_ny_bruker);
        Scene_9.setHeight(450);
        Scene_9.setWidth(600);
        Scene_9.show();

    }

}
