package sample;

import Brukere.*;
import filbehandling.FiledataTxt;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggInn_Controller {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    private Register brukere = Registrering_Controller.brukere;

    private FiledataTxt lese = new FiledataTxt();
    Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

    private void load(){
        try {
            lese.loadBruker(brukere, path);
        }catch (Exception e){
            //for nå
            showMessageDialog(null, "klarte ikke å laste inn data");
        }
    }

    @FXML
    void onClick_btn_LoggInn(ActionEvent event) throws IOException {
        load();
        boolean login_sucessfull = false;
        int user = 0;
        for(int i = 0; i < brukere.getArray().size(); i++){
            if(brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText())
                && brukere.getArray().get(i).getPassord().equals(txtPassord.getText())){

                if(brukere.getArray().get(i).isAdmin()){
                    Parent Logg_inn = FXMLLoader.load(getClass().getResource("Superbruker.fxml"));
                    Scene Standarbruker = new Scene(Logg_inn);
                    Stage Scene_5 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
                    Scene_5.setScene(Standarbruker);
                    Scene_5.setHeight(410);
                    Scene_5.setWidth(610.4);
                    Scene_5.show();
                }else{
                    Parent Logg_inn = FXMLLoader.load(getClass().getResource("Standardbruker.fxml"));
                    Scene Standarbruker = new Scene(Logg_inn);
                    Stage Scene_5 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
                    Scene_5.setScene(Standarbruker);
                    Scene_5.setHeight(480);
                    Scene_5.setWidth(620);
                    Scene_5.show();
                }
            }
        }
    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event) throws IOException {

        Parent Logg_inn = FXMLLoader.load(getClass().getResource("Registrering.fxml"));
        Scene Register_ny_bruker = new Scene(Logg_inn);
        Stage Scene_2 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_2.setScene(Register_ny_bruker);
        Scene_2.setHeight(450);
        Scene_2.setWidth(600);
        Scene_2.show();
    }

    //Får å gå ut fra applikasjonen
    @FXML
    void onClick_btn_Avslutt(ActionEvent event) {
        Platform.exit();
    }

}
