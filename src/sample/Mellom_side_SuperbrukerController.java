package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mellom_side_SuperbrukerController {

    @FXML
    void On_Click_BtnVisBrukere(ActionEvent event) {

        try {
            Parent Mellom_side_Superbruker = FXMLLoader.load(getClass().getResource("Visbruker_Superbruker.fxml"));
            Scene  VisBruker_Super = new Scene(Mellom_side_Superbruker);
            Stage Scene_12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_12.setScene(VisBruker_Super);
            Scene_12.setHeight(420);
            Scene_12.setWidth(625);
            Scene_12.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    void On_Click_BtnVisKomponenter(ActionEvent event) {

        try {
            Parent Mellom_side_Superbruker = FXMLLoader.load(getClass().getResource("Viskomponenter_Superbruker.fxml"));
            Scene VisKomponenter_Super = new Scene(Mellom_side_Superbruker);
            Stage Scene_13 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_13.setScene(VisKomponenter_Super);
            Scene_13.setHeight(420);
            Scene_13.setWidth(625);
            Scene_13.show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}
