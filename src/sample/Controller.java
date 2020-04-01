package sample;

import Brukere.Bruker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    @FXML
    private TextField txtTelefonnummer;

    @FXML
    private TextField txtEmail;

    @FXML
    private CheckBox chxAdmin;

    @FXML
    private CheckBox chxStandarbruker;

    @FXML
    void onClick_btn_Avbryt(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void onClick_btn_Register(ActionEvent event) {



    }

}

