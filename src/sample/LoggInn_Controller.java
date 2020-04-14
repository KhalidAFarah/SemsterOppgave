package sample;

import Brukere.*;
import filbehandling.FiledataTxt;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @FXML
    private Button btnLogginn;

    @FXML
    private Button btnRegistrer;

    private Register brukere = Registrering_Controller.brukere;

    private void load(){
        FiledataTxt lese = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

        lese.setPathTxt(path);
        lese.setRegister(brukere);

        txtBrukernavn.setDisable(true);
        txtPassord.setDisable(true);
        btnLogginn.setDisable(true);
        btnRegistrer.setDisable(true);

        lese.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                txtBrukernavn.setDisable(false);
                txtPassord.setDisable(false);
                btnLogginn.setDisable(false);
                btnRegistrer.setDisable(false);
            }
        });
        lese.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                txtBrukernavn.setDisable(false);
                txtPassord.setDisable(false);
                btnLogginn.setDisable(false);
                btnRegistrer.setDisable(false);

                showMessageDialog(null, "Klarte ikke laste inn lagert data");
            }
        });

        Thread tr = new Thread(lese);
        tr.setDaemon(true);
        tr.start();

        try{
            tr.sleep(5000);
        }catch (InterruptedException e){
            showMessageDialog(null, "Klarte ikke å stoppen tråden");
        }



        /*try {
            lese.loadBruker(brukere, path);
        }catch (Exception e){
            //for nå
            showMessageDialog(null, "klarte ikke å laste inn data");
        }*/
    }

    @FXML
    void onClick_btn_LoggInn(ActionEvent event) {
        load();
        boolean login_sucessfull = false;
        int user = 0;
        for(int i = 0; i < brukere.getArray().size(); i++){
            if(brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText())
                && brukere.getArray().get(i).getPassord().equals(txtPassord.getText())){

                if(brukere.getArray().get(i).isAdmin()){

                    try{
                        Parent Logg_inn = FXMLLoader.load(getClass().getResource("Superbruker.fxml"));
                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(420);
                        Scene_5.setWidth(610.4);
                        Scene_5.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    try{
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Standardbruker.fxml"));
                        Parent Logg_inn = loader.load();
                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(480);
                        Scene_5.setWidth(620);

                        //paserer inn data i standardBruker_Controller
                        Standardbruker_Controller controller = loader.getController();
                        Standardbruker standardbruker = new Standardbruker(brukere.getArray().get(i));
                        controller.initBruker(standardbruker);

                        Scene_5.show();
                    }catch(IOException e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event)  {

        try{
            Parent Logg_inn = FXMLLoader.load(getClass().getResource("Registrering.fxml"));
            Scene Register_ny_bruker = new Scene(Logg_inn);
            Stage Scene_2 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
            Scene_2.setScene(Register_ny_bruker);
            Scene_2.setHeight(450);
            Scene_2.setWidth(600);
            Scene_2.show();
        }catch(IOException e){
            e.printStackTrace(); //trace the exception..
        }


    }

    //Får å gå ut fra applikasjonen
    @FXML
    void onClick_btn_Avslutt(ActionEvent event) {
        Platform.exit();
    }

}
