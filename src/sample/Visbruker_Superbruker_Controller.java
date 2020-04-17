package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Visbruker_Superbruker_Controller {

    @FXML
    private TableView<?> tableView;

    @FXML
    private SubScene LeggTilKomponent_sub;

    @FXML
    private AnchorPane LeggTilKomponent_pane;

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
    void On_Click_BtnTilbake(ActionEvent event) {
        try {
            Parent Superbruker = FXMLLoader.load(getClass().getResource("Mellom_side_Superbruker.fxml"));
            Scene Mellom_side = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(Mellom_side);
            Scene_4.setHeight(360);
            Scene_4.setWidth(580);
            Scene_4.show();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
