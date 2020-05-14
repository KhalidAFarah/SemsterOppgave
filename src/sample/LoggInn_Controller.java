package sample;

import Brukere.*;
import filbehandling.FiledataJOBJ;
import filbehandling.FiledataTxt;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

        lblError.setText("klaret ikke å laste inn varer");
    }

    public void loadKomponenter() {
        if (komponenter == null) {
            komponenter = new Komponenter();
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                lblError.setText("Klarte ikke å stoppe tråden!");
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

        lblError.setText("klarte ikke å laste inn lagrede brukere");
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                showMessageDialog(null, "Klarte ikke å stoppe tråden!");
            }
        }
    }

    @FXML
    void onClick_btn_LoggInn(ActionEvent event) {
        //load();
        boolean login_successful = false;

        System.out.println(brukere.toStringTxt());

        for (int i = 0; i < brukere.getArray().size(); i++) {
            boolean verdi = true;
            if (brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText())
                    && brukere.getArray().get(i).getPassord().equals(txtPassord.getText())) {
                login_successful = true;

                System.out.println(brukere.getArray().get(i).isAdmin());
                if (brukere.getArray().get(i).isAdmin()) {


                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/FXML/Mellom_side_Superbruker.fxml"));
                    Parent Logg_inn;
                    try {
                        Logg_inn = loader.load();
                    } catch (IOException e) {
                        lblError.setText("Klarer ikke å bytte side");
                        Logg_inn = null;
                        verdi = false;
                    }
                    if (verdi) {

                        Mellom_side_SuperbrukerController controller = loader.getController();
                        controller.initBrukere(brukere, komponenter);

                        Scene Standardbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standardbruker);
                        Scene_5.setHeight(700);
                        Scene_5.setWidth(420);
                        Scene_5.centerOnScreen();
                        Scene_5.show();
                    }
                } else {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/FXML/MellomSide_Standardbruker.fxml"));
                    Parent Logg_inn;
                    try {
                        Logg_inn = loader.load();
                    } catch (IOException e) {
                        lblError.setText("Klarer ikke å bytte side!");
                        Logg_inn = null;
                        verdi = false;
                    }

                    if (verdi) {
                        //passerer inn data i standardBruker_Controller
                        MellomSide_Standardbruker_Controller controller = loader.getController();
                        controller.setInfo(brukere, komponenter, (Standardbruker) brukere.getArray().get(i));

                        Scene Standarbruker = new Scene(Logg_inn);
                        Stage Scene_5 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene_5.setScene(Standarbruker);
                        Scene_5.setHeight(700);
                        Scene_5.setWidth(420);
                        Scene_5.centerOnScreen();
                        Scene_5.show();

                    }
                }

            }
        }
        if (!login_successful) {
            showMessageDialog(null, "Ugyldig brukernavn eller passord");
        }
    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Registrering.fxml"));
        Parent Logg_inn;
        boolean value_7 = true;
        try {
            Logg_inn = loader.load();
        } catch (IOException e) {
            lblError.setText("Klarte ikke å bytte side!");
            Logg_inn = null;
            value_7 = false;

        }
        if (value_7) {
            Registrering_Controller controller = loader.getController();
            controller.initRegister(brukere);
            Scene Register_ny_bruker = new Scene(Logg_inn);
            Stage Scene_2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_2.setScene(Register_ny_bruker);
            Scene_2.setHeight(600);
            Scene_2.setWidth(600);
            Scene_2.centerOnScreen();
            Scene_2.show();
        }

    }


    //For å gå ut fra applikasjonen
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
