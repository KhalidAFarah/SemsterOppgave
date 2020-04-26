package sample;

import Brukere.Register;
import filbehandling.FiledataJOBJ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import komponenter.*;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Viskomponenter_Superbruker_Controller {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView tableView;

    private Register Brukere;

    private Komponenter komponenter = new Komponenter();

    private Komponenter komp = new Komponenter();

    private int IDs;

    @FXML
    private Button btnLeggTil;

    @FXML
    private Button btnFjern;

    @FXML
    private Button btnRediger;

    @FXML
    private Button btnTilbake;

    @FXML
    private Label labelError;

    @FXML
    private TableColumn<Komponent, Integer> IDKolonne;

    @FXML
    private TableColumn<Komponent, String> navnKolonne;

    @FXML
    private TableColumn<Komponent, String> typeKolonne;

    @FXML
    private TableColumn<Komponent, Double> prisKolonne;

    private TableColumn<Spesifikasjon, Integer> idSpecKolonne = new TableColumn<>("ID");
    private TableColumn<Spesifikasjon, String> specNavnKolonne = new TableColumn<>("Spesifikasjoner");

    @FXML
    private Label labelSøk;

    @FXML
    private TextField txtSøk;


    private boolean showLeggTil = false;
    private boolean showFjern = false;
    private boolean showSpecs = false;
    private boolean showRediger = false;
    private AnchorPane leggtilPane = new AnchorPane();

    public void start() {

        pane.getChildren().add(leggtilPane);
        leggtilPane.setVisible(false);
        /*tableView = new TableView();
        tableView.setLayoutY(86);
        tableView.setPrefWidth(518);
        tableView.setPrefHeight(286);
        pane.getChildren().add(tableView);

        Label labelSøk = new Label("søk");
        TextField txtSøk = new TextField();

        pane.getChildren().add(labelSøk);
        pane.getChildren().add(txtSøk);

        txtSøk.setLayoutX(200);
        txtSøk.setLayoutY(25);

        labelSøk.setLayoutY(30);
        labelSøk.setLayoutX(100);

        søk(txtSøk, tableView, false, labelError);


        /*TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Produkt navn");
        TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
        TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");
        //TableColumn<Komponent, String> specsKolonne = new TableColumn<>("Specs");

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));
        //specsKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("specs"));

        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);*/

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));

        tableView.setItems(komponenter.getMainArray());

        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Predicate<Komponent> Navn = Komponent -> {
                    boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                    return sjekk;
                };

                komp.setMainArray(komponenter.getMainArray().stream().filter(Navn)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                tableView.setItems(komp.getMainArray());
            }
        });
    }



    public void saveKomponenter() {
        FiledataJOBJ data = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            data.save(komponenter, path);
        } catch (IOException e) {
            showMessageDialog(null, "klarte ikke å laste inn data");// for nå
        }
    }

    private void søk(TextField txtSøk, TableView tableSøk, boolean setEditAble, Label labelError) {
        TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Produkt navn");
        TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
        TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");
        //TableColumn<Komponent, String> specsKolonne = new TableColumn<>("Specs");

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));
        //specsKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("specs"));

        tableSøk.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);
        komp.setMainArray(komponenter.getMainArray());
        tableSøk.setItems(komp.getMainArray());

        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Predicate<Komponent> Navn = Komponent -> {
                    boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                    return sjekk;
                };

                komp.setMainArray(komponenter.getMainArray().stream().filter(Navn)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                tableSøk.setItems(komp.getMainArray());
            }
        });

        if (setEditAble) {//redigering
            tableSøk.setEditable(setEditAble);
            DoubleStringConverter doubleString = new DoubleStringConverter();

            navnKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            typeKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            prisKolonne.setCellFactory(TextFieldTableCell.forTableColumn(doubleString));

            navnKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Komponent, String> event) {
                    event.getRowValue().setNavn(event.getNewValue());

                    navnKolonne.getTableView().refresh();

                    saveKomponenter();
                }
            });
            typeKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Komponent, String> event) {
                    event.getRowValue().setType(event.getNewValue());

                    typeKolonne.getTableView().refresh();
                    saveKomponenter();
                }
            });
            prisKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, Double>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Komponent, Double> event) {
                    event.getRowValue().setPris(event.getNewValue());

                    prisKolonne.getTableView().refresh();
                    saveKomponenter();
                }

            });

        }
    }

    @FXML
    void On_Click_BtnFjernKomponenter(ActionEvent event) {
       // if (!showFjern) {
            //slette komponenter
        if(!showSpecs) {
            Button btnf = new Button("Fjern vare");
            pane.getChildren().add(btnf);
            leggtilPane.setVisible(false);
            leggtilPane.getChildren().clear();

            labelSøk.setLayoutX(157.0);
            txtSøk.setLayoutX(217.0);

            if (tableView.isVisible()) {

                btnf.setVisible(false);

                String melding = showInputDialog(null, "Skriv varens ID");
                int valgtKomponent;
                try {
                    valgtKomponent = Integer.parseInt(melding);
                } catch (Exception e) {
                    showMessageDialog(null, "Vennligst skriv inn riktig varens ID");
                    valgtKomponent = -1;
                }
                if (valgtKomponent != -1) {

                        /*for(int i = 0; i < Brukere.getArray().size(); i++) {
                            if (Brukere.getArray().get(i) instanceof Standardbruker) {
                                for (int j = 0; j < ((Standardbruker) Brukere.getArray().get(i))
                                        .getHandelskurv().getMainArray().size(); j++) {

                                    //lagre brukere og flytt den til standard bruker controller der brukeren for info om det
                                    if (((Standardbruker) Brukere.getArray().get(i)).getHandelskurv()
                                            .getMainArray().get(j).getNavn().equals(komponenter.getMainArray().get(valgtKomponent).getNavn())){
                                        ((Standardbruker) Brukere.getArray().get(i)).getHandelskurv().remove(j);
                                        System.out.println("Brukeren" + ((Standardbruker) Brukere.getArray().get(i)).getBrukernavn() + " " +
                                                komponenter.getMainArray().get(valgtKomponent).getNavn());
                                    }
                                }
                            }
                        }*/


                    komponenter.remove(valgtKomponent);
                    komp.setMainArray(komponenter.getMainArray());

                    //tableSøk.setItems(komp.getMainArray());
                    tableView.setItems(komponenter.getMainArray());

                    saveKomponenter();
                    showRediger = false;
                    showLeggTil = false;
                }
            } else {
                tableView.setVisible(true);
                labelSøk.setVisible(true);
                txtSøk.setVisible(true);

                btnf.setVisible(true);
                btnf.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String melding = showInputDialog(null, "Skriv varens ID");
                        int valgtKomponent;
                        try {
                            valgtKomponent = Integer.parseInt(melding);
                        } catch (Exception e) {
                            showMessageDialog(null, "Vennligst skriv inn riktig varens ID");
                            valgtKomponent = -1;
                        }
                        if (valgtKomponent != -1) {

                        /*for(int i = 0; i < Brukere.getArray().size(); i++) {
                            if (Brukere.getArray().get(i) instanceof Standardbruker) {
                                for (int j = 0; j < ((Standardbruker) Brukere.getArray().get(i))
                                        .getHandelskurv().getMainArray().size(); j++) {

                                    //lagre brukere og flytt den til standard bruker controller der brukeren for info om det
                                    if (((Standardbruker) Brukere.getArray().get(i)).getHandelskurv()
                                            .getMainArray().get(j).getNavn().equals(komponenter.getMainArray().get(valgtKomponent).getNavn())){
                                        ((Standardbruker) Brukere.getArray().get(i)).getHandelskurv().remove(j);
                                        System.out.println("Brukeren" + ((Standardbruker) Brukere.getArray().get(i)).getBrukernavn() + " " +
                                                komponenter.getMainArray().get(valgtKomponent).getNavn());
                                    }
                                }
                            }
                        }*/


                            komponenter.remove(valgtKomponent);
                            komp.setMainArray(komponenter.getMainArray());

                            //tableSøk.setItems(komp.getMainArray());
                            tableView.setItems(komponenter.getMainArray());

                            saveKomponenter();
                            showRediger = false;
                            showLeggTil = false;
                        }
                    }
                });

                btnf.setLayoutX(350);
                labelSøk.setLayoutX(100);
                txtSøk.setLayoutX(150);
                btnf.setLayoutY(30);

            }
        }else if(showSpecs){
            String spec = showInputDialog("skriv inn spesifikasjonens id");
            int specID;
            try{
                specID = Integer.parseInt(spec);
            }catch (Exception e){
                labelError.setText("Skriv inn en gyldig id");
                specID = -1;
            }

            if(specID >= 0){
                komponenter.getMainArray().get(IDs).getSpecs().remove(specID);
                tableView.getItems().remove(specID);
                saveKomponenter();
            }
        }


            //showFjern = true;
        //} else if (showFjern) {

       //     showFjern = false;
        //}
    }

    @FXML
    void On_Click_BtnLeggTilKomponenter(ActionEvent event) {
        ArrayList<Node> nodes = new ArrayList<>();
        labelSøk.setLayoutX(157.0);
        txtSøk.setLayoutX(217.0);
        leggtilPane.getChildren().clear();
        leggtilPane.setVisible(true);
        if (!showLeggTil) {

            tableView.setVisible(false);
            txtSøk.setVisible(false);
            labelSøk.setVisible(false);



            ChoiceBox choice = new ChoiceBox(FXCollections.observableArrayList(
                    "Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm"
            ));


        /*String[] typer = {"Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm"};

        for( String type : typer){
            choice.set
        }*/
            Label label = new Label("Velg komponent type");
            label.setLayoutY(20);
            label.setLayoutX(10);
            leggtilPane.getChildren().add(label);
            choice.setLayoutX(160);
            choice.setLayoutY(15);
            nodes.add(choice);
            leggtilPane.getChildren().add(choice);
            choice.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //senere i egen fil
                    //produkt navn
                    Label labelNavn = new Label("Produkt navn");
                    labelNavn.setLayoutX(10);
                    labelNavn.setLayoutY(75);
                    leggtilPane.getChildren().add(labelNavn);
                    nodes.add(labelNavn);

                    TextField txtNavn = new TextField();
                    txtNavn.setLayoutX(200);
                    txtNavn.setLayoutY(75);
                    leggtilPane.getChildren().add(txtNavn);
                    nodes.add(txtNavn);

                    //produkt pris

                    Label labelPris = new Label("Produkt pris");
                    labelPris.setLayoutX(10);
                    labelPris.setLayoutY(150);
                    leggtilPane.getChildren().add(labelPris);
                    nodes.add(labelPris);

                    TextField txtPris = new TextField();
                    txtPris.setLayoutX(200);
                    txtPris.setLayoutY(150);
                    leggtilPane.getChildren().add(txtPris);
                    nodes.add(txtPris);

                    //produktets specs
                    Label labelSpecs = new Label("Fyll inn spesifikasjoner");//for nå husk å bytt den til noe bedre senere
                    labelSpecs.setLayoutX(10);
                    labelSpecs.setLayoutY(220);
                    leggtilPane.getChildren().add(labelSpecs);
                    nodes.add(labelSpecs);

                    TextArea txtSpecs = new TextArea();
                    txtSpecs.setLayoutX(10);
                    txtSpecs.setLayoutY(250);
                    txtSpecs.setMaxHeight(100);
                    txtSpecs.setMaxWidth(450);
                    leggtilPane.getChildren().add(txtSpecs);
                    nodes.add(txtSpecs);

                    //knapp for å submit informasjonen og opprett det nye komponent
                    Button btnAdd = new Button("Legg til komponent");
                    btnAdd.setLayoutX(290);
                    btnAdd.setLayoutY(15);
                    leggtilPane.getChildren().add(btnAdd);
                    nodes.add(btnAdd);

                    //spesifikke attributter for typer komponenter legges til her

                    btnAdd.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            String[] specs = txtSpecs.getText().split("\n");
                            double pris;
                            try {
                                pris = Double.parseDouble(txtPris.getText());
                            } catch (Exception e) {
                                pris = 0;
                            }
                            if (choice.getValue().equals("Prosessor")) {//spesifikke attributter går inn i if eller else if setningene
                                Prosessor pro = new Prosessor(txtNavn.getText(), pris, "Prosessor", specs);
                                if (komponenter.add(pro)) {
                                    System.out.println("funker");
                                } else {
                                    System.out.println("Noe er galt");
                                }
                            } else if (choice.getValue().equals("Skjermkort")) {
                                komponenter.add(new Skjermkort(txtNavn.getText(), pris, "Skjermkort", specs));
                            } else if (choice.getValue().equals("Minne")) {
                                komponenter.add(new Minne(txtNavn.getText(), pris, "Minne", specs));
                            } else if (choice.getValue().equals("Harddisk")) {
                                komponenter.add(new Harddisk(txtNavn.getText(), pris, "Harddisk", specs));
                            } else if (choice.getValue().equals("Tastatur")) {
                                komponenter.add(new Tastatur(txtNavn.getText(), pris, "Tastatur", specs));
                            } else if (choice.getValue().equals("Mus")) {
                                komponenter.add(new Mus(txtNavn.getText(), pris, "Mus", specs));
                            } else if (choice.getValue().equals("Skjerm")) {
                                komponenter.add(new Skjerm(txtNavn.getText(), pris, "Skjerm", specs));
                            }
                            //deretter lagre Komponenter
                            saveKomponenter();
                        }
                    });
                }
            });
            showLeggTil = true;
            showRediger = false;
            showFjern = false;
        } else if (showLeggTil) {
            tableView.setVisible(true);
            labelSøk.setVisible(true);
            txtSøk.setVisible(true);


            leggtilPane.getChildren().clear();
            showLeggTil = false;
        }
    }

    @FXML
    void On_Click_BtnRedigerKomponenter(ActionEvent event) {
        labelSøk.setLayoutX(157.0);
        txtSøk.setLayoutX(217.0);
        leggtilPane.setVisible(false);
        leggtilPane.getChildren().clear();
        tableView.setEditable(true);
        if(!showSpecs) {
            if (!showRediger) {
                DoubleStringConverter doubleString = new DoubleStringConverter();

                navnKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
                typeKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
                prisKolonne.setCellFactory(TextFieldTableCell.forTableColumn(doubleString));

                navnKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Komponent, String> event) {
                        event.getRowValue().setNavn(event.getNewValue());

                        navnKolonne.getTableView().refresh();

                        saveKomponenter();
                    }
                });
                typeKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Komponent, String> event) {
                        event.getRowValue().setType(event.getNewValue());

                        typeKolonne.getTableView().refresh();
                        saveKomponenter();
                    }
                });
                prisKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Komponent, Double> event) {
                        event.getRowValue().setPris(event.getNewValue());

                        prisKolonne.getTableView().refresh();
                        saveKomponenter();
                    }

                });

                btnRediger.setText("Stop redigering");
                showRediger = true;
                showLeggTil = false;
                showFjern = false;
            } else if (showRediger) {
                btnRediger.setText("Rediger komponenter");
                tableView.setEditable(false);
                showRediger = false;
            }
        }else if(showSpecs){
            if(!showRediger) {
                System.out.println("#");
                specNavnKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
                specNavnKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Spesifikasjon, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Spesifikasjon, String> event) {
                        komponenter.getMainArray().get(IDs).getSpecs().remove(event.getRowValue().getID2());
                        komponenter.getMainArray().get(IDs).getSpecs().add(event.getRowValue().getID2(),
                                event.getNewValue());

                        event.getRowValue().setNavn2(event.getNewValue());
                        event.getTableView().refresh();
                        saveKomponenter();
                    }
                });
                btnRediger.setText("Stop redigering");
                showRediger = true;
            }else if(showRediger){
                tableView.setEditable(false);
                btnRediger.setText("Rediger Spesifikasjoner");
            }
        }
    }


    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Mellom_side_Superbruker.fxml"));
            Parent Superbruker = loader.load();

            Mellom_side_SuperbrukerController controller = loader.getController();
            controller.initBrukere(Brukere, komponenter);

            Scene Mellom_side = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(Mellom_side);
            Scene_4.setHeight(360);
            Scene_4.setWidth(580);
            Scene_4.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setBruker(Register brukere, Komponenter komponenter){
        this.Brukere = brukere;
        this.komponenter = komponenter;
    }

    public void On_Click_BtnFjernSpesifikasjoner(ActionEvent event) {
    }

    public void On_Click_BtnVisSpesifikasjoner(ActionEvent event) {
        tableView.setVisible(true);
        txtSøk.setVisible(true);
        labelSøk.setVisible(true);

        leggtilPane.setVisible(false);
        leggtilPane.getChildren().clear();

        labelSøk.setLayoutX(157.0);
        txtSøk.setLayoutX(217.0);

        String str = showInputDialog("Skriv inn komponentens id");
        int ID;
        try{
            ID = Integer.parseInt(str);
        }catch (Exception e){
            ID = -1;
        }

        if(ID >= 0){
            tableView.getColumns().clear();
            txtSøk.setPromptText("Skriv inn spesifikasjon");

            ObservableList<Spesifikasjon> spesifikasjoner = FXCollections.observableArrayList();
            IDs = ID;

            for(int i = 0; i < komponenter.getMainArray().get(ID).getSpecs().size(); i++){
                Spesifikasjon t = new Spesifikasjon(komponenter.getMainArray().get(ID).getSpecs().get(i), i);
                System.out.println(t.getID2());
                spesifikasjoner.add(t);
            }
            idSpecKolonne.setCellValueFactory(new PropertyValueFactory<Spesifikasjon, Integer>("ID2"));
            specNavnKolonne.setCellValueFactory(new PropertyValueFactory<Spesifikasjon, String>("navn2"));


            tableView.getColumns().addAll(idSpecKolonne, specNavnKolonne);

            tableView.setItems(spesifikasjoner);
            showSpecs = true;

        }




    }
}
