package sample;


import Brukere.Register;
import Brukere.Standardbruker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import komponenter.Komponenter;

import java.io.IOException;

public class MellomSide_Standardbruker_Controller {

    private Komponenter komponenter;

    private Register brukere;
    private Standardbruker bruker;


    @FXML
    private Label lblError;

    @FXML
    void click_btn_Tilbake(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoggInn.fxml"));
        Parent Avbryt_Standarbruker;
        boolean value_1 = true;
        try {
            Avbryt_Standarbruker = loader.load();
        }catch(IOException e){
            lblError.setText("Klarte ikke å gå tilbake til LoggInn siden");
            Avbryt_Standarbruker = null;
            value_1=false;
        }
        if(value_1){

            Scene LoggInn = new Scene(Avbryt_Standarbruker);
            Stage Scene_1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_1.setScene(LoggInn);
            Scene_1.setHeight(480);
            Scene_1.setWidth(440);
            Scene_1.show();

        }



    }

    @FXML
    void click_btn_bygg_egen_pc(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Standardbruker_FerdigByggetPc.fxml"));
        Parent Standardbruker_mellomside;

        boolean value_2 = true;
        try {
            Standardbruker_mellomside = loader.load();
        }catch(IOException e){
            lblError.setText("Klarte ikke å bytte til Ferdig-bygd-PC siden");
            Standardbruker_mellomside = null;
            value_2=false;
        }
        if(value_2){

            Standardbruker_FerdigByggetPc_Controller controller = loader.getController();
            controller.initBruker(bruker, brukere, komponenter);
            Scene Standardbruker_ByggDinEgenPc = new Scene(Standardbruker_mellomside);
            Stage Scene_1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_1.setScene(Standardbruker_ByggDinEgenPc);
            Scene_1.setHeight(448);
            Scene_1.setWidth(618);
            Scene_1.show();

        }

    }

    @FXML
    void click_btn_individuellekomponenter(ActionEvent event) {

    }

    public void setInfo(Register r, Komponenter k, Standardbruker b){
        komponenter = k;
        brukere = r;
        bruker = b;
    }

}
