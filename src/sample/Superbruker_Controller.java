package sample;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import komponenter.Harddisk;
import komponenter.Komponenter;
import komponenter.Prosessor;

import java.io.IOException;

public class Superbruker_Controller {

    @FXML
    private SubScene LeggTilKomponent_sub;

    @FXML
    private AnchorPane LeggTilKomponent_pane;

    private String KomponentType;

    @FXML
    void On_Click_BtnBestilling(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnFjernKomponenter(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnLeggTilKomponenter(ActionEvent event) {
        LeggTilKomponent_sub.setVisible(true);
        LeggTilKomponent_pane.setVisible(true);

        ChoiceBox choice = new ChoiceBox(FXCollections.observableArrayList(
                "Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm"
        ));


        /*String[] typer = {"Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm"};

        for( String type : typer){
            choice.set
        }*/
        Label label = new Label("Velg type");
        label.setLayoutY(20);
        label.setLayoutX(10);
        LeggTilKomponent_pane.getChildren().add(label);
        choice.setLayoutX(90);
        choice.setLayoutY(15);

        LeggTilKomponent_pane.getChildren().add(choice);
        choice.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                //senere i egen fil
                //produkt navn
                Label labelNavn = new Label("Produkt navn");
                labelNavn.setLayoutX(250);
                labelNavn.setLayoutY(20);
                LeggTilKomponent_pane.getChildren().add(labelNavn);

                TextField txtNavn = new TextField();
                txtNavn.setLayoutX(350);
                txtNavn.setLayoutY(15);
                LeggTilKomponent_pane.getChildren().add(txtNavn);

                //produkt pris

                Label labelPris = new Label("Produkt navn");
                labelPris.setLayoutX(250);
                labelPris.setLayoutY(70);
                LeggTilKomponent_pane.getChildren().add(labelPris);

                TextField txtPris = new TextField();
                txtPris.setLayoutX(350);
                txtPris.setLayoutY(65);
                LeggTilKomponent_pane.getChildren().add(txtPris);

                //produktets specs
                Label labelSpecs = new Label("Fyll inn specs");//for nå husk å bytt den til noe bedre senere
                labelSpecs.setLayoutX(250);
                labelSpecs.setLayoutY(120);
                LeggTilKomponent_pane.getChildren().add(labelSpecs);

                TextArea txtSpecs = new TextArea();
                txtSpecs.setLayoutX(250);
                txtSpecs.setLayoutY(150);
                txtSpecs.setMaxHeight(100);
                txtSpecs.setMaxWidth(300);
                LeggTilKomponent_pane.getChildren().add(txtSpecs);

                //knapp for å submit informasjonen og opprett det nye komponent
                Button btnAdd = new Button("Legg til komponent");
                btnAdd.setLayoutX(50);
                btnAdd.setLayoutY(150);
                LeggTilKomponent_pane.getChildren().add(btnAdd);

                btnAdd.setOnAction(new EventHandler<ActionEvent> () {

                    @Override
                    public void handle(ActionEvent event) {
                        String[] specs = txtSpecs.getText().split("\n");
                        double pris;
                        try{
                            pris = Double.parseDouble(txtPris.getText());
                        }catch(Exception e){
                            pris = 0;
                        }
                        if(choice.getValue().equals("Prosessor")){
                            Prosessor prosessor = new Prosessor(txtNavn.getText(), pris, "Prosessor", specs);
                        }

                        //etc. for de andre komponent typer
                        //deretter laste inn og lagre Komponenter
                    }
                });
            }
        });
    }

    @FXML
    void On_Click_BtnRedigerKomponenter(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) throws IOException {
        Parent Superbruker = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
        Scene LoggInn = new Scene(Superbruker);
        Stage Scene_4 = (Stage) ( (Node)event.getSource()).getScene().getWindow();
        Scene_4.setScene(LoggInn);
        Scene_4.setHeight(420);
        Scene_4.setWidth(410);
        Scene_4.show();

    }

}
