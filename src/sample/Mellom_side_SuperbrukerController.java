package sample;
//

import Brukere.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;
//Done try and catch with fxml scenes



public class Mellom_side_SuperbrukerController {

    private Register brukere;
    private Komponenter komponenter;

    @FXML
    private Label lblError;

    @FXML
    void On_Click_BtnVisBrukere(ActionEvent event) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Visbruker_Superbruker.fxml"));
            Parent Mellom_side_superbruker;
            boolean value_2 = true;
            try{
                Mellom_side_superbruker = loader.load();
            }catch(IOException e){
                lblError.setText("klarte ikke å bytte side");
                Mellom_side_superbruker = null;
                value_2 = false;

            }if(value_2){

                Visbruker_Superbruker_Controller controller = loader.getController();
                controller.initBrukere(brukere, komponenter);
                controller.start();

                Scene VisBruker_Super = new Scene(Mellom_side_superbruker);
                Stage Scene_12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene_12.setScene(VisBruker_Super);
                Scene_12.setHeight(630);
                Scene_12.setWidth(914);
                Scene_12.show();
            }
    }

    @FXML
    void On_Click_BtnVisKomponenter(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Viskomponenter_Superbruker.fxml"));
        Parent Mellom_side_Superbruker;
        boolean value_4 = true;
        try {
            Mellom_side_Superbruker = loader.load();
        } catch (IOException e) {
            lblError.setText("klarte ikke å bytte side");
            Mellom_side_Superbruker = null;
            value_4 = false;

        }
        if (value_4) {

        Viskomponenter_Superbruker_Controller controller = loader.getController();
        controller.setBruker(brukere, komponenter);
        controller.start();

        Scene VisKomponenter_Super = new Scene(Mellom_side_Superbruker);
        Stage Scene_13 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_13.setScene(VisKomponenter_Super);
        Scene_13.setHeight(600);
        Scene_13.setWidth(914);
        Scene_13.show();
    }
        }



    @FXML
    void On_Click_BtnReturnerTilStart(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoggInn.fxml"));
        Parent Superbruker;
        boolean value_3 = true;
        try {
            Superbruker = loader.load();
        } catch (IOException e) {
            lblError.setText("klarte ikke å bytte side");
            Superbruker = null;
            value_3 = false;

        }
        if (value_3){

            LoggInn_Controller controller = loader.getController();
            controller.setRegister(brukere, komponenter);

            Scene LoggInn = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(LoggInn);
            Scene_4.setHeight(480);
            Scene_4.setWidth(440);
            Scene_4.show();
        }
    }



    public void initBrukere(Register brukere, Komponenter komponenter) {
        this.brukere = brukere;
        this.komponenter = komponenter;
    }

}
