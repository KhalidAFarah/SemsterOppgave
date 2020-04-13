package sample;


import filbehandling.FiledataJOBJ;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import komponenter.*;
import static javax.swing.JOptionPane.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Superbruker_Controller implements Initializable {

    @FXML
    private SubScene LeggTilKomponent_sub;

    @FXML
    private AnchorPane LeggTilKomponent_pane;

    @FXML
    private TableView tableView;

    private String KomponentType;

    private Komponenter komponenter = new Komponenter();

    private Komponenter komp = new Komponenter();

    private boolean showLeggTil = false;
    private boolean showFjern = false;
    private boolean showRediger = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadKomponenter();

        TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Navn");
        TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
        TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");
        //TableColumn<Komponent, String> specsKolonne = new TableColumn<>("Specs");

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));
        //specsKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("specs"));

        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);

        tableView.setItems(komponenter.getMainArray());
    }

    public void loadKomponenter(){
        FiledataJOBJ data = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            data.load(komponenter, path);
        }catch (IOException e){
            showMessageDialog(null, "klarte ikke å laste inn data");// for nå
        }catch (Exception e){
            showMessageDialog(null, "klarte ikke å laste inn data");
        }
    }
    public void saveKomponenter(Komponenter newKomponenter){
        FiledataJOBJ data = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            data.save(newKomponenter, path);
        }catch (IOException e){
            showMessageDialog(null, "klarte ikke å laste inn data");// for nå
        }
    }

    private void søk(TextField txtSøk, TableView tableSøk, boolean setEditAble, Label labelError){
        TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Navn");
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
                    boolean sjekk =  Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                    return sjekk;
                };

                komp.setMainArray(komponenter.getMainArray().stream().filter(Navn)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                tableSøk.setItems(komp.getMainArray());
            }
        });

        if(setEditAble){//redigering
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

                    saveKomponenter(komponenter);
                }
            });
            typeKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Komponent, String> event) {
                    event.getRowValue().setType(event.getNewValue());

                    typeKolonne.getTableView().refresh();
                    saveKomponenter(komponenter);
                }
            });
            prisKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, Double>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Komponent, Double> event) {
                    event.getRowValue().setPris(event.getNewValue());

                    prisKolonne.getTableView().refresh();
                    saveKomponenter(komponenter);
                }

            });

        }
    }

    @FXML
    void On_Click_BtnFjernKomponenter(ActionEvent event) {
        if(!showFjern) {
            LeggTilKomponent_pane.setVisible(true);
            LeggTilKomponent_sub.setVisible(true);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(550);
            LeggTilKomponent_pane.getChildren().clear();

            Label labelNavn = new Label("Søk produktnavn");
            TextField txtSøk = new TextField();
            TableView tableSøk = new TableView();
            //Komponenter komp = new Komponenter();

            LeggTilKomponent_pane.getChildren().add(labelNavn);
            LeggTilKomponent_pane.getChildren().add(txtSøk);
            LeggTilKomponent_pane.getChildren().add(tableSøk);

            labelNavn.setLayoutX(15);
            labelNavn.setLayoutY(15);

            txtSøk.setLayoutX(150);
            txtSøk.setLayoutY(15);

            tableSøk.setLayoutX(15);
            tableSøk.setLayoutY(80);
            tableSøk.setPrefHeight(275);
            tableSøk.setPrefWidth(575);


            søk(txtSøk, tableSøk, false, new Label());

            //slette komponenter
            Button btnFjern = new Button("Fjern vare");
            LeggTilKomponent_pane.getChildren().add(btnFjern);
            btnFjern.setLayoutX(425);
            btnFjern.setLayoutY(15);

            btnFjern.setOnAction(new EventHandler<ActionEvent>() {
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
                        komp.getMainArray().remove(valgtKomponent);
                        saveKomponenter(komp);
                    }
                }
            });
            showFjern = true;
            showRediger = false;
            showLeggTil = false;
        }else if(showFjern){
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showFjern = false;
        }
    }

    @FXML
    void On_Click_BtnLeggTilKomponenter(ActionEvent event) {
        if(!showLeggTil) {
            LeggTilKomponent_sub.setVisible(true);
            LeggTilKomponent_pane.setVisible(true);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);

            LeggTilKomponent_pane.getChildren().clear();

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
            choice.setOnAction(new EventHandler<ActionEvent>() {
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

                    Label labelPris = new Label("Produkt pris");
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
                            saveKomponenter(komponenter);
                        }
                    });
                }
            });
            showLeggTil = true;
            showRediger = false;
            showFjern = false;
        }else if(showLeggTil){
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showLeggTil = false;
        }
    }

    @FXML
    void On_Click_BtnRedigerKomponenter(ActionEvent event) {
        if(!showRediger) {
            LeggTilKomponent_pane.setVisible(true);
            LeggTilKomponent_sub.setVisible(true);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(550);
            LeggTilKomponent_pane.getChildren().clear();

            Label labelNavn = new Label("Søk produktnavn");
            TextField txtSøk = new TextField();
            TableView tableSøk = new TableView();

            LeggTilKomponent_pane.getChildren().add(labelNavn);
            LeggTilKomponent_pane.getChildren().add(txtSøk);
            LeggTilKomponent_pane.getChildren().add(tableSøk);

            labelNavn.setLayoutX(15);
            labelNavn.setLayoutY(15);

            txtSøk.setLayoutX(175);
            txtSøk.setLayoutY(15);

            Label labelError = new Label();

            tableSøk.setLayoutX(15);
            tableSøk.setLayoutY(80);
            tableSøk.setPrefHeight(275);
            tableSøk.setPrefWidth(575);

            søk(txtSøk, tableSøk, true, labelError);
            showRediger = true;
            showLeggTil = false;
            showFjern = false;
        }else if(showRediger){
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showRediger = false;
        }
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
