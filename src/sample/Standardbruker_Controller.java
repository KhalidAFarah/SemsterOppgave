package sample;

import Brukere.Bruker;
import Brukere.Standardbruker;
import filbehandling.FiledataJOBJ;
import filbehandling.FiledataTxt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import komponenter.Komponenter;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Standardbruker_Controller implements Initializable {

    @FXML
    private ImageView img_Techmet;

    @FXML
    private TextField txtMaskin;

    @FXML
    private TextField txtHandlekurve;

    @FXML
    private SubScene subScene;

    @FXML
    private AnchorPane pane;

    private Standardbruker bruker;

    private Komponenter komponenter = new Komponenter();

    private int kompNr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadKomponenter();
    }

    public void initBruker(Standardbruker bruker) {
        this.bruker = bruker;
    }

    public void loadKomponenter() {
        FiledataJOBJ filedata = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            filedata.load(komponenter, path);
        } catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {

        try {
            Parent Standardbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
            Scene LoggInn = new Scene(Standardbruker);
            Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_3.setScene(LoggInn);
            Scene_3.setHeight(420);
            Scene_3.setWidth(410);
            Scene_3.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visVarer(String type) {
        pane.getChildren().clear();
        int y = 50;
        for (int i = 0; i < komponenter.getMainArray().size(); i++) { // lag en komponent array senere
            //ImageView img = new ImageView();

            if (komponenter.getMainArray().get(i).getType().equals(type)) {

                Label label = new Label(komponenter.getMainArray().get(i).getNavn());
                label.setLayoutY(y);
                Button btn = new Button("Velg");
                btn.setLayoutY(y + 25);
                y += 50;

                kompNr = i;

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        bruker.leggTilHandlekurv(komponenter.getMainArray().get(kompNr));
                    }
                });

                pane.getChildren().add(label);
                pane.getChildren().add(btn);
            }
        }
    }

    @FXML
    void On_Click_BtnKurv(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        pane.getChildren().clear();
        int y = 50;
        for (int i = 0; i < bruker.getHandelskurv().getMainArray().size(); i++) {
            Label label = new Label(komponenter.getMainArray().get(i).getNavn());
            label.setLayoutY(y);
            Button btn = new Button("Fjern");
            btn.setLayoutY(y + 25);
            y += 50;

            kompNr = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("halle");
                    bruker.getHandelskurv().getMainArray().remove(kompNr);
                }
            });
            pane.getChildren().add(label);
            pane.getChildren().add(btn);
        }

    }

    @FXML
    void On_Click_Btn_Grafikkort(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Skjermkort");
    }

    @FXML
    void On_Click_Btn_Harddisk(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Harddisk");
    }

    @FXML
    void On_Click_Btn_Minnebrikke(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Minne");
    }

    @FXML
    void On_Click_Btn_Mus(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Mus");
    }

    @FXML
    void On_Click_Btn_Prosessor(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Prosessor");
    }

    @FXML
    void On_Click_Btn_Skjerm(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Skjerm");
    }

    @FXML
    void On_Click_Btn_Tastatur(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(900);

        visVarer("Tastatur");
    }
}
