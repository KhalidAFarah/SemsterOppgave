package sample;

import Brukere.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import filbehandling.FiledataTxt;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoggInn_Controller implements Initializable {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    @FXML
    private Button btnLogginn;

    @FXML
    private Button btnRegistrer;

    @FXML
    private Button btnAvslutt;

    private Register brukere = new Register();

    public void setRegister(Register reg) {
        brukere = reg;
    }

    private void Succeded(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);
    }

    private void Failed(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);

        showMessageDialog(null, "Klarte ikke laste inn lagert data");
    }

    private void load() {
        FiledataTxt lese = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

        lese.setPathTxt(path);
        lese.setRegister(brukere);

        txtBrukernavn.setDisable(true);
        txtPassord.setDisable(true);
        btnLogginn.setDisable(true);
        btnRegistrer.setDisable(true);

        lese.setOnSucceeded(this::Succeded);
        lese.setOnFailed(this::Failed);

        Thread tr = new Thread(lese);
        //tr.setDaemon(true);
        tr.start();

        /*try {
            tr.sleep(5000);
        } catch (InterruptedException e) {
            showMessageDialog(null, "Klarte ikke å stoppen tråden");
        }
        }*/
    }

    @FXML
    void onClick_btn_LoggInn(ActionEvent event) {
        //load();
        boolean login_sucessfull = false;

        System.out.println(brukere.toStringTxt());

        for (int i = 0; i < brukere.getArray().size(); i++) {
            if (brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText())
                    && brukere.getArray().get(i).getPassord().equals(txtPassord.getText())) {
                login_sucessfull = true;

                System.out.println(brukere.getArray().get(i).isAdmin());
                if (brukere.getArray().get(i).isAdmin()) {



                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Mellom_side_Superbruker.fxml"));
                        Parent Logg_inn = loader.load();

                        Mellom_side_SuperbrukerController Controller = loader.getController();

                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(360);
                        Scene_5.setWidth(580);
                        Scene_5.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Standardbruker.fxml"));
                        Parent Logg_inn = loader.load();

                        //paserer inn data i standardBruker_Controller
                        Standardbruker_Controller controller = loader.getController();
                        controller.initBruker((Standardbruker) brukere.getArray().get(i), brukere);

                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(480);
                        Scene_5.setWidth(620);

                        Scene_5.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (!login_sucessfull) {
            showMessageDialog(null, "Ugyldig brukernavn eller passord");
        }
    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Registrering.fxml"));
            Parent Logg_inn = loader.load();

            Registrering_Controller controller = loader.getController();
            controller.initRegister(brukere);
            Scene Register_ny_bruker = new Scene(Logg_inn);
            Stage Scene_2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_2.setScene(Register_ny_bruker);
            Scene_2.setHeight(480);
            Scene_2.setWidth(600);
            Scene_2.show();
        } catch (IOException e) {
            e.printStackTrace(); //trace the exception..
        }


    }

    //Får å gå ut fra applikasjonen
    @FXML
    void onClick_btn_Avslutt(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
    }
}
