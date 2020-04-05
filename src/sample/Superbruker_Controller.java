package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Superbruker_Controller {

    @FXML
    void On_Click_BtnBestilling(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnFjernKomponenter(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnLeggTilKomponenter(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnRedigerKomponenter(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) throws IOException {
        Parent Superbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        Scene LoggInn = new Scene(Superbruker);
        Stage Scene_4 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_4.setScene(LoggInn);
        Scene_4.setHeight(420);
        Scene_4.setWidth(410);
        Scene_4.show();

    }

}
