package sample;


import Brukere.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Mellom_side_SuperbrukerController {

    private Register brukere;

    @FXML
    void On_Click_BtnVisBrukere(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Visbruker_Superbruker.fxml"));
            Parent Mellom_side_superbruker = loader.load();

            Visbruker_Superbruker_Controller controller = loader.getController();
            controller.initBrukere(brukere);
            controller.start();

            Scene VisBruker_Super = new Scene(Mellom_side_superbruker);
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

    @FXML
    void On_Click_BtnReturnerTilStart(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoggInn.fxml"));
            Parent Superbruker = loader.load();

            LoggInn_Controller controller = loader.getController();
            controller.setRegister(brukere);
            Scene LoggInn = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(LoggInn);
            Scene_4.setHeight(480);
            Scene_4.setWidth(440);
            Scene_4.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void initBrukere(Register brukere){
        this.brukere= brukere;
    }

}