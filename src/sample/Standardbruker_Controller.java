package sample;

import Brukere.Register;
import Brukere.Standardbruker;
import filbehandling.FiledataJOBJ;
import filbehandling.FiledataTxt;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import komponenter.Komponenter;
import sun.plugin.javascript.navig.Anchor;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private ScrollPane pane;

    @FXML
    private DialogPane infoDialog;

    @FXML
    private AnchorPane info;

    private Standardbruker bruker;

    private Komponenter komponenter = new Komponenter();

    private int kompNr;
    private Register brukere;
    private FiledataTxt lagreTxt;
    private Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

    private void save() {
        lagreTxt = new FiledataTxt();
        try {
            lagreTxt.save(brukere.toStringTxt(), path);
        } catch (IOException e) {
            //txtError.setText(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }
    }

    public void initBruker(Standardbruker bruker, Register brukere, Komponenter komponenter) {
        this.bruker = bruker;
        this.brukere = brukere;
        this.komponenter = komponenter;
    }

    public void loadKomponenter() {
        FiledataJOBJ filedata = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            filedata.load(komponenter, path);
        } catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }

        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Viskomponenter_Superbruker.fxml"));
        Viskomponenter_Superbruker_Controller controller = loader.getController();
        controller.loadKomponenter();*/
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {

        try {
            Parent Standardbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
            Scene LoggInn = new Scene(Standardbruker);
            Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_3.setScene(LoggInn);
            Scene_3.setHeight(480);
            Scene_3.setWidth(440);
            Scene_3.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visVarer(String type) {
        AnchorPane APane = new AnchorPane();
        int y = 50;
        ScrollBar sb = new ScrollBar();
        sb.setLayoutX(50);
        pane.setContent(APane);


        for (int i = 0; i < komponenter.getMainArray().size(); i++) { // lag en komponent array senere
            //ImageView img = new ImageView();

            if (komponenter.getMainArray().get(i).getType().equals(type)) {

                Label labelNavn = new Label(komponenter.getMainArray().get(i).getNavn());
                labelNavn.setLayoutY(y);
                labelNavn.setLayoutX(10);
                Label labelPris = new Label(komponenter.getMainArray().get(i).getPris() + " Kr");
                labelPris.setLayoutY(y);
                labelPris.setLayoutX(110);
                Button btnVelg = new Button("Velg");
                btnVelg.setLayoutY(y + 30);
                btnVelg.setLayoutX(10);
                Button btnVisMer = new Button("Vis mer");
                btnVisMer.setLayoutY(y + 30);
                btnVisMer.setLayoutX(85);

                y += 100;

                btnVelg.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < komponenter.getMainArray().size(); j++) {
                            if (komponenter.getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                bruker.leggTilHandlekurv(komponenter.getMainArray().get(j));
                                save();
                            }
                        }
                    }
                });

                btnVisMer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < komponenter.getMainArray().size(); j++) {
                            if (komponenter.getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                String spesifikasjonerHeader = komponenter.getMainArray().get(j).getNavn() + "\nPris "
                                        + komponenter.getMainArray().get(j).getPris();
                                String spesifikasjonerText = "";
                                for(String s : komponenter.getMainArray().get(j).getSpecs()){
                                    spesifikasjonerText += s + "\n";
                                }

                                Label labelInfoHeader = new Label(spesifikasjonerHeader);
                                Label labelInfoText = new Label(spesifikasjonerText);

                                APane.getChildren().clear();
                                APane.getChildren().add(labelInfoHeader);
                                labelInfoText.setLayoutY(110);
                                labelInfoText.setLayoutX(10);
                                labelInfoHeader.setStyle("-fx-font-size: 20");
                                labelInfoHeader.setLayoutY(10);
                                labelInfoHeader.setLayoutX(10);
                                APane.getChildren().add(labelInfoText);

                                Button btnHide = new Button("Skjul spesifikasjoner");

                                btnHide.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        visVarer(type);
                                    }
                                });
                                btnHide.setLayoutY(70);
                                btnHide.setLayoutX(25);
                                APane.getChildren().add(btnHide);



                            }
                        }
                    }
                });


                APane.getChildren().add(labelNavn);
                APane.getChildren().add(labelPris);
                APane.getChildren().add(btnVelg);
                APane.getChildren().add(btnVisMer);
            }
        }
    }

    private void updateVarer() {
        AnchorPane APane = new AnchorPane();
        pane.setContent(APane);
        int y = 10;
        if (bruker != null || komponenter != null) {
            for (int i = 0; i < bruker.getHandlekurv().getMainArray().size(); i++) {
                Label labelNavn = new Label(komponenter.getMainArray().get(i).getNavn());
                labelNavn.setLayoutY(y);
                labelNavn.setLayoutX(10);
                Label labelPris = new Label(komponenter.getMainArray().get(i).getPris() + " Kr");
                labelPris.setLayoutY(y);
                labelPris.setLayoutX(110);
                Button btnFjern = new Button("Fjern");
                btnFjern.setLayoutY(y + 30);
                btnFjern.setLayoutX(10);
                Button btnVisMer = new Button("Vis mer");
                btnVisMer.setLayoutY(y + 30);
                btnVisMer.setLayoutX(85);

                y += 80;

                btnFjern.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < bruker.getHandlekurv().getMainArray().size(); j++) {
                            if (bruker.getHandlekurv().getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                bruker.getHandlekurv().getMainArray().remove(j);
                            }
                        }
                        updateVarer();
                        save();
                    }
                });
                btnVisMer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < komponenter.getMainArray().size(); j++) {
                            if (komponenter.getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                String spesifikasjonerHeader = komponenter.getMainArray().get(j).getNavn() + "\nPris "
                                        + komponenter.getMainArray().get(j).getPris();
                                String spesifikasjonerText = "";
                                for(String s : komponenter.getMainArray().get(j).getSpecs()){
                                    spesifikasjonerText += s + "\n";
                                }

                                Label labelInfoHeader = new Label(spesifikasjonerHeader);
                                Label labelInfoText = new Label(spesifikasjonerText);

                                APane.getChildren().clear();
                                APane.getChildren().add(labelInfoHeader);
                                labelInfoText.setLayoutY(110);
                                labelInfoText.setLayoutX(10);
                                labelInfoHeader.setStyle("-fx-font-size: 20");
                                labelInfoHeader.setLayoutY(10);
                                labelInfoHeader.setLayoutX(10);
                                APane.getChildren().add(labelInfoText);

                                Button btnHide = new Button("Skjul spesifikasjoner");

                                btnHide.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        updateVarer();
                                    }
                                });
                                btnHide.setLayoutY(70);
                                btnHide.setLayoutX(25);
                                APane.getChildren().add(btnHide);



                            }
                        }
                    }
                });

                APane.getChildren().add(labelNavn);
                APane.getChildren().add(btnFjern);
                APane.getChildren().add(labelPris);
                APane.getChildren().add(btnVisMer);
            }

            Label labelTotalPris = new Label("Totale pris er " + bruker.getSum() + " Kr");
            labelTotalPris.setLayoutY(y-10);
            labelTotalPris.setStyle("-fx-padding: 10");
            APane.getChildren().add(labelTotalPris);
        } else if (bruker == null) {
            showMessageDialog(null, "Klarte ikke å laste inn brukeren");
        }
    }

    @FXML
    void On_Click_BtnKurv(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);
        //AnchorPane APane = new AnchorPane();
        //pane.setContent(APane);
        int y = 50;
        if (bruker != null) {
            updateVarer();/*
            for (int i = 0; i < bruker.getHandelskurv().getMainArray().size(); i++) {
                Label label = new Label(bruker.getHandelskurv().getMainArray().get(i).getNavn());
                label.setLayoutY(y);
                Button btn = new Button("Fjern");
                btn.setLayoutY(y + 25);
                y += 50;

                kompNr = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < bruker.getHandelskurv().getMainArray().size(); j++) {
                            if (bruker.getHandelskurv().getMainArray().get(j).getNavn().equals(label.getText())) {
                                bruker.getHandelskurv().getMainArray().remove(j);
                            }
                        }
                        updateVarer();
                        save();
                    }
                });
                APane.getChildren().add(label);
                APane.getChildren().add(btn);
            }*/
        } else if (bruker == null) {
            showMessageDialog(null, "Klarte ikke å laste inn brukeren");
        }
    }

    @FXML
    void On_Click_Btn_Grafikkort(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Skjermkort");
    }

    @FXML
    void On_Click_Btn_Harddisk(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Harddisk");
    }

    @FXML
    void On_Click_Btn_Minnebrikke(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Minne");
    }

    @FXML
    void On_Click_Btn_Mus(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Mus");
    }

    @FXML
    void On_Click_Btn_Prosessor(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Prosessor");
    }

    @FXML
    void On_Click_Btn_Skjerm(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Skjerm");
    }

    @FXML
    void On_Click_Btn_Tastatur(ActionEvent event) {
        Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene_3.setWidth(818);

        visVarer("Tastatur");
    }
}
