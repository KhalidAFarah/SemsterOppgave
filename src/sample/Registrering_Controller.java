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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import komponenter.Prosessor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class Registrering_Controller {

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

    public static Register brukere = new Register();
    private FiledataTxt lagreTxt;
    private Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

    private void save(){
        lagreTxt = new FiledataTxt();
        try{
            lagreTxt.save(brukere.toStringTxt(),path);
        }catch (IOException e){
            txtError.setText(e.getMessage());
        }
    }



    @FXML
    void onClick_btn_Avbryt(ActionEvent event) throws IOException {
        save(); // se på denne

        Parent Registrering = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        Scene Avbryt = new Scene(Registrering);
        Stage Scene_1 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_1.setScene(Avbryt);
        Scene_1.setHeight(420);
        Scene_1.setWidth(410);
        Scene_1.show();

    }

    @FXML
    void onClick_btn_Register(ActionEvent event){
        Bruker b = new Bruker();
        b.setBrukernavn(txtBrukernavn.getText());
        b.setPassord(txtPassord.getText());
        b.setTlf(txtTelefonnummer.getText());
        b.setEmail(txtEmail.getText());

        if(chxAdmin.isSelected() && !chxStandarbruker.isSelected()){
            Superbruker A = new Superbruker(b);
            brukere.add(A);
        }else if(chxStandarbruker.isSelected() && !chxAdmin.isSelected()){
           Standardbruker A = new Standardbruker(b);
           //A.leggTilHandlekurv(new Prosessor("AMD", 200, "Prossesor", "hdd", "ssd"));
           brukere.add(A);

        }else if(chxStandarbruker.isSelected() && chxAdmin.isSelected() || chxStandarbruker.isSelected() && chxAdmin.isSelected()) {
            txtError.setText("Vennligst kryss av en av boksene");
        }
        save();

        /*
        Parent Registrering = FXMLLoader.load(getClass().getResource("MellomSide.fxml"));
        Scene MellomSide = new Scene(Registrering);
        Stage Scene_7 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_7.setScene(MellomSide);
        Scene_7.setHeight(500);
        Scene_7.setWidth(500);
        Scene_7.show();

         */


    }

}

