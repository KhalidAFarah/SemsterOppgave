package sample;

import Brukere.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import filbehandling.FiledataJOBJ;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import komponenter.Komponenter;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
//Done try and catch with fxml scenes

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
    private Label lblError;

    @FXML
    private Button btnAvslutt;

    private Register brukere;

    private Komponenter komponenter;




    private void succededKomponenter(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);
    }

    private void failedKomponenter(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);

        showMessageDialog(null, "Klarte ikke å laste inn varer!");
    }

    public void loadKomponenter() {
        if (komponenter == null) {
            komponenter = new Komponenter();
            System.out.println("hal");
            FiledataJOBJ data = new FiledataJOBJ();
            Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

            data.setKomponent(komponenter);
            data.setPath(path);

            data.setOnSucceeded(this::succededKomponenter);
            data.setOnFailed(this::failedKomponenter);

            txtBrukernavn.setDisable(true);
            txtPassord.setDisable(true);
            btnLogginn.setDisable(true);
            btnRegistrer.setDisable(true);

            Thread tr = new Thread(data);
            tr.start();

            try {
                tr.sleep(1000);
            } catch (InterruptedException e) {
                showMessageDialog(null, "Klarte ikke å stoppe tråden");
            }
        }
    }

    public void setRegister(Register reg, Komponenter komponenter) {
        this.brukere = reg;
        this.komponenter = komponenter;
    }

    public void setRegister(Register reg) {
        this.brukere = reg;
    }

    private void SuccededBruker(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);
    }

    private void FailedBruker(WorkerStateEvent event) {
        txtBrukernavn.setDisable(false);
        txtPassord.setDisable(false);
        btnLogginn.setDisable(false);
        btnRegistrer.setDisable(false);
        btnAvslutt.setDisable(false);

        showMessageDialog(null, "Klarte ikke laste inn lagert data");
    }

    private void loadBruker() {
        if (brukere == null) {
            brukere = new Register();
            FiledataTxt lese = new FiledataTxt();
            Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

            lese.setPathTxt(path);
            lese.setRegister(brukere);

            txtBrukernavn.setDisable(true);
            txtPassord.setDisable(true);
            btnLogginn.setDisable(true);
            btnRegistrer.setDisable(true);

            lese.setOnSucceeded(this::SuccededBruker);
            lese.setOnFailed(this::FailedBruker);

            Thread tr = new Thread(lese);
            //tr.setDaemon(true);
            tr.start();

            try {
                tr.sleep(1000);
            } catch (InterruptedException e) {
                showMessageDialog(null, "Klarte ikke å stoppen tråden");
            }
        }
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
                if (brukere.getArray().get(i).isAdmin()){


                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Mellom_side_Superbruker.fxml"));
                    Parent Logg_inn;
                    boolean value_5 = true;

                    try {
                        Logg_inn = loader.load();
                    } catch (IOException e) {
                        lblError.setText("Klarer ikke å bytte side");
                        Logg_inn = null;
                        value_5 = false;
                    }
                    if (value_5){

                        Mellom_side_SuperbrukerController controller = loader.getController();
                        controller.initBrukere(brukere, komponenter);

                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(360);
                        Scene_5.setWidth(580);
                        Scene_5.show();
                    }
                    }

                } else {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Standardbruker.fxml"));
                        Parent Logg_inn;
                        boolean verdi = true;
                        try {
                            Logg_inn = loader.load();
                        } catch(IOException e){
                            lblError.setText("Klarer ikke å bytte side");
                            Logg_inn = null;
                            verdi= false;

                        if(verdi){
                            //paserer inn data i standardBruker_Controller
                            Standardbruker_Controller controller = loader.getController();
                            controller.initBruker((Standardbruker) brukere.getArray().get(i), brukere, komponenter);

                            Scene Standarbruker = new Scene(Logg_inn);
                            Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene_5.setScene(Standarbruker);
                            Scene_5.setHeight(448);
                            Scene_5.setWidth(618);

                            Scene_5.show();
                        }
                    }

                }
            }

        if(!login_sucessfull) {
            showMessageDialog(null, "Ugyldig brukernavn eller passord");
        }
    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Registrering.fxml"));
            Parent Logg_inn;
            boolean value_7 = true;
            try {
                Logg_inn = loader.load();
            }catch(IOException e) {
                lblError.setText("klarte ikke å bytte side");
                Logg_inn = null;
                value_7 = false;

            }if(value_7){
            Registrering_Controller controller = loader.getController();
            controller.initRegister(brukere);
            Scene Register_ny_bruker = new Scene(Logg_inn);
            Stage Scene_2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_2.setScene(Register_ny_bruker);
            Scene_2.setHeight(480);
            Scene_2.setWidth(600);
            Scene_2.show();
        }

        }




    //Får å gå ut fra applikasjonen
    @FXML
    void onClick_btn_Avslutt(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBruker();
        loadKomponenter();
    }
}
