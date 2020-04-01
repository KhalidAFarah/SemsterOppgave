package sample;

import Brukere.Bruker;
import Brukere.Standardbruker;
import Brukere.Superbruker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    private Label txtError;


    @FXML
    void onClick_btn_Avbryt(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void onClick_btn_Register(ActionEvent event) {
        Bruker b = new Bruker();
        b.setBrukernavn(txtBrukernavn.getText());
        b.setPassord(txtPassord.getText());
       // b.setTelefonnummer(txtTelefonnummer.getText());
        b.setEmail(txtEmail.getText());

        Bruker A;
        if(chxAdmin.isSelected() && !chxStandarbruker.isSelected()){
            A = new Superbruker(b);
        }else if(chxStandarbruker.isSelected() && !chxAdmin.isSelected()){
            A = new Standardbruker(b);
        }else{
            txtError.setText("");
        }




    }

}

