package sample;


import Brukere.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.showMessageDialog;
import java.io.IOException;

public class MellomSide_Controller {

    private Register brukere;

    public void initRegister(Register brukere){
        this.brukere = brukere;
    }

    @FXML
    void On_Click_BtnLoggInn(ActionEvent event) {
        if(brukere != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("LoggInn.fxml"));
                Parent MellomSide = loader.load();

                LoggInn_Controller controller = loader.getController();
                controller.setRegister(brukere);

                Scene LoggInn = new Scene(MellomSide);
                Stage Scene_8 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene_8.setScene(LoggInn);
                Scene_8.setHeight(420);
                Scene_8.setWidth(410);
                Scene_8.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            showMessageDialog(null, "Mangel på data");
        }

    }

    @FXML
    void On_Click_BtnNyBruker(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Registrering.fxml"));
            Parent MellomSide = loader.load();

            Registrering_Controller controller = loader.getController();
            controller.initRegister(brukere);
            Scene Register_ny_bruker = new Scene(MellomSide);
            Stage Scene_9 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_9.setScene(Register_ny_bruker);
            Scene_9.setHeight(450);
            Scene_9.setWidth(600);
            Scene_9.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
