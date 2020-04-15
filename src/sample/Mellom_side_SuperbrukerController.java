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

    }

    @FXML
    void On_Click_BtnVisKomponenter(ActionEvent event) {

        try {
            Parent Logg_inn = FXMLLoader.load(getClass().getResource("Superbruker.fxml"));
            Scene Mellom_side_SuperbrukerController = new Scene(Logg_inn);
            Stage Scene_10 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_10.setScene(Mellom_side_SuperbrukerController);
            Scene_10.setHeight(420);
            Scene_10.setWidth(610.4);
            Scene_10.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
