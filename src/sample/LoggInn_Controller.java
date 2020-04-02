package sample;

import Brukere.*;
import filbehandling.FiledataTxt;
import filbehandling.InvalidDataLoadedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import static javax.swing.JOptionPane.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggInn_Controller {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    private Register brukere = new Register();

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
    void onClick_btn_LoggInn(ActionEvent event) {
        load();
        boolean login_sucessfull = false;
        int user = 0;
        for(int i = 0; i < brukere.getArray().size(); i++){
            if(brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText())
                && brukere.getArray().get(i).getPassord().equals(txtPassord.getText())){
                login_sucessfull = true;
                user = i;
            }
        }

        if(login_sucessfull){
            //en eller annen funksjon som bytter UI og sender inn indexen til brukeren i bruker listen

        }

    }

    @FXML
    void onClick_btn_RegistrerNyBruker(ActionEvent event) {

    }

}
