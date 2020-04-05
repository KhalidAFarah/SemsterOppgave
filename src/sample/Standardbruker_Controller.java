package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Standardbruker_Controller {

    @FXML
    private TextField txtMaskin;

    @FXML
    private TextField txtHandlekurve;

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) throws IOException {

        Parent Standardbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        Scene LoggInn = new Scene(Standardbruker);
        Stage Scene_3 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_3.setScene(LoggInn);
        Scene_3.setHeight(420);
        Scene_3.setWidth(410);
        Scene_3.show();

    }

    @FXML
    void On_Click_Btn_Grafikkort(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Harddisk(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Minnebrikke(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Mus(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Prosessor(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Skjerm(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Tastatur(ActionEvent event) {

    }

}
