package sample;

import filbehandling.FiledataJOBJ;
import filbehandling.FiledataTxt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.nio.file.Path;
import java.nio.file.Paths;

public class Standardbruker_Controller {

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

    private Komponenter komponenter = new Komponenter();

    private int userNr;

    public void loadKomponenter(){
        FiledataJOBJ filedata = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter");

        try{
            filedata.load(komponenter, path);
        }catch (Exception e){
            showMessageDialog(null, e.getMessage());
        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {

        try{
            Parent Standardbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
            Scene LoggInn = new Scene(Standardbruker);
            Stage Scene_3 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
            Scene_3.setScene(LoggInn);
            Scene_3.setHeight(420);
            Scene_3.setWidth(410);
            Scene_3.show();
        }catch(IOException e){
            e.printStackTrace();
        }



    }

    @FXML
    void On_Click_Btn_Grafikkort(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Harddisk(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Minnebrikke(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Mus(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Prosessor(ActionEvent event) {

        Stage Scene_3 = (Stage) ( (Node)event.getSource()).getScene().getWindow();

        Scene_3.setWidth(900);

        Label la = new Label();
        la.setText("hallo");
        la.setLayoutY(100);
        la.setLayoutX(50);


        la.setLayoutX(36.0);
        la.setLayoutY(175.0);

        pane.getChildren().add(la);

        int y = 50;
        loadKomponenter();
        for(int i = 0; i < komponenter.getMainArray().size(); i++) { // lag en komponent array senere
            //ImageView img = new ImageView();

            if (komponenter.getMainArray().get(i).getType().equals("Prosessor")) {

                Label label = new Label(komponenter.getMainArray().get(i).getNavn());
                label.setLayoutY(y);
                Button btn = new Button("Velg");
                btn.setLayoutY(y + 25);
                y += 50;

                userNr = i;

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("denne varen er valgt" + komponenter.getMainArray().get(userNr).getNavn());
                    }
                });

                pane.getChildren().add(label);
                pane.getChildren().add(btn);
            }
        }

    }

    @FXML
    void On_Click_Btn_Skjerm(ActionEvent event) {

    }

    @FXML
    void On_Click_Btn_Tastatur(ActionEvent event) {

    }

}
