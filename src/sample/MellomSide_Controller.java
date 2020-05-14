package sample;


import Brukere.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.IOException;

public class MellomSide_Controller {


    @FXML
    private Label lblError;

    private Register brukere;

    public void initRegister(Register brukere) {
        this.brukere = brukere;
    }

    @FXML
    void On_Click_BtnLoggInn(ActionEvent event) {
        if (brukere != null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoggInn.fxml"));
            Parent MellomSide;
            boolean value_8 = true;

            try {
                MellomSide = loader.load();
            } catch (IOException e) {
                lblError.setText("Klarte ikke å bytte side!");
                MellomSide = null;
                value_8 = false;
            }
            if (value_8) {
                LoggInn_Controller controller = loader.getController();
                controller.setRegister(brukere);

                Scene LoggInn = new Scene(MellomSide);
                Stage Scene_8 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene_8.setScene(LoggInn);
                Scene_8.setHeight(480);
                Scene_8.setWidth(440);
                Scene_8.show();
            }

        } else {
            showMessageDialog(null, "Mangel på data");
        }
    }

    @FXML
    void On_Click_BtnNyBruker(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Registrering.fxml"));
        Parent MellomSide;
        boolean value_9 = true;
        try {
            MellomSide = loader.load();
        } catch (IOException e) {
            lblError.setText("Klarte ikke å bytte side!");
            MellomSide = null;
            value_9 = false;
        }
        if (value_9) {
            Registrering_Controller controller = loader.getController();
            controller.initRegister(brukere);
            Scene Register_ny_bruker = new Scene(MellomSide);
            Stage Scene_9 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_9.setScene(Register_ny_bruker);
            Scene_9.setHeight(480);
            Scene_9.setWidth(600);
            Scene_9.show();
        }
    }
}
