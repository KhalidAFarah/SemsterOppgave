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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import komponenter.*;

import static javax.swing.JOptionPane.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Viskomponenter_Superbruker_Controller {

    //@FXML
   // private AnchorPane pane;

    @FXML
    private TableView tableView;

    private Register Brukere;

    private Komponenter komponenter = new Komponenter();

    private final Komponenter komp = new Komponenter();

    private int IDs;

    private final Button btnf = new Button();

    @FXML
    private Button btnLeggTil;

    @FXML
    private Button btnFjern;

    @FXML
    private Button btnRediger;

    @FXML
    private Button btnTilbake;

    @FXML
    private Button btnVisSpecs;

    @FXML
    private Button btnVisKomponenter;

    @FXML
    private Label labelError;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField txtSubmit;

    @FXML
    private TableColumn<Komponent, Integer> IDKolonne;

    @FXML
    private TableColumn<Komponent, String> navnKolonne;

    @FXML
    private TableColumn<Komponent, String> typeKolonne;

    @FXML
    private TableColumn<Komponent, Double> prisKolonne;

    private final TableColumn<Spesifikasjon, Integer> idSpecKolonne = new TableColumn<>("ID");
    private final TableColumn<Spesifikasjon, String> specNavnKolonne = new TableColumn<>("Spesifikasjoner");
    private final TableColumn<Spesifikasjon, String> specVerdiKolonne = new TableColumn<>("Verdi");

    @FXML
    private Label labelSøk;

    @FXML
    private TextField txtSøk;

    @FXML
    private TextArea txtSpecs;

    @FXML
    private TextField txtNavn;

    @FXML
    private TextField txtPris;

    @FXML
    private ComboBox choice;

    @FXML
    private Button btnAdd;

    @FXML
    private GridPane leggTilKomponenterGrid;

    private boolean showLeggTil = false;
    private boolean showFjern = false;
    private boolean showSpecs = false;
    private boolean showRediger = false;

    private ObservableList<Spesifikasjon> spesifikasjoner = FXCollections.observableArrayList();
    private ObservableList<Spesifikasjon> spesifikasjonerSøk = FXCollections.observableArrayList();

    public void start() {




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

    @FXML
    void On_Click_BtnFjernKomponenter(ActionEvent event) {

            btnRediger.setText("Rediger komponenter");

            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("skriv inn ID");
            showRediger = false;
            showLeggTil = false;
            btnLeggTil.setText("Legg til komponenter");
            btnFjern.setText("Tilbake");

            if (!showSpecs) {


                    leggTilKomponenterGrid.setVisible(false);

                    if (tableView.isVisible()) {

                        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int valgtKomponent;
                                try {
                                    valgtKomponent = Integer.parseInt(txtSubmit.getText());
                                } catch (Exception e) {
                                    labelError.setText("Vennligst skriv inn riktig varens ID");
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
                                    labelError.setText("Et komponent har blitt fjernet!");

                                    btnSubmit.setVisible(false);
                                    txtSubmit.setVisible(false);

                                }
                            }
                        });


                    //String melding = showInputDialog(null, "Skriv varens ID");
                } else {

                    tableView.setVisible(true);
                    labelSøk.setVisible(true);
                    txtSøk.setVisible(true);

                    //btnf.setVisible(true);
                    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //String melding = showInputDialog(null, "Skriv varens ID");
                            int valgtKomponent;
                            try {
                                valgtKomponent = Integer.parseInt(txtSubmit.getText());
                            } catch (Exception e) {
                                labelError.setText("Vennligst skriv inn riktig varens ID");
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
                                labelError.setText("Et komponent har blitt fjernet!");

                                showRediger = false;
                                showLeggTil = false;

                            }
                        }
                    });
                }
            } else if (showSpecs) {
                //String spec = showInputDialog("skriv inn spesifikasjonens id");
                txtSubmit.setText("");
                txtSubmit.setPromptText("skriv inn ID");

                btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int specID;
                        if (txtSubmit.getText() != null) {
                            try {
                                specID = Integer.parseInt(txtSubmit.getText());
                            } catch (Exception e) {
                                labelError.setText("Skriv inn en gyldig id");
                                specID = -1;
                            }
                        } else {
                            labelError.setText("Skriv inn en gyldig id");
                            specID = -1;
                        }

                        if (specID >= 0) {
                            komponenter.getMainArray().get(IDs).getSpecs().remove(specID);
                            tableView.getItems().remove(specID);

                            ObservableList<Spesifikasjon> ny = FXCollections.observableArrayList();

                            for (int i = 0; i < spesifikasjoner.size(); i++) {
                                spesifikasjoner.get(i).setID(i);
                            }
                            tableView.refresh();
                            saveKomponenter();
                            labelError.setText("Et komponent sin spesifikasjon har blitt fjernet!");
                        }
                    }
                });

            }
    }

    @FXML
    void On_Click_BtnLeggTilKomponenter(ActionEvent event) {
        btnRediger.setText("Rediger komponenter");
        leggTilKomponenterGrid.setVisible(false);

        txtSubmit.setVisible(false);
        btnSubmit.setVisible(false);
        txtSubmit.setText("");

        if (!showSpecs) {
            if (!showLeggTil) {
                btnLeggTil.setText("Tilbake");
                showLeggTil = true;
                showRediger = false;
                showFjern = false;
                tableView.setVisible(false);
                txtSøk.setVisible(false);
                labelSøk.setVisible(false);
                leggTilKomponenterGrid.setVisible(true);
                txtNavn.setText("");
                txtPris.setText("");
                txtSpecs.setText("");
                ObservableList<String> typer = FXCollections.observableArrayList();
                for(String s : Komponenter.getTyper()){
                    typer.add(s);
                }
                choice.setItems(typer);

                choice.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        btnAdd.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                String[] specs = txtSpecs.getText().split("\n");
                                double pris;
                                boolean sjekk = true;
                                try {
                                    pris = Double.parseDouble(txtPris.getText());
                                } catch (Exception e) {
                                    pris = 0;
                                    sjekk = false;
                                    txtPris.setText("");
                                    txtPris.setPromptText("Vennligst skriv inn gyldige verdier!");
                                }
                                if (sjekk){
                                    if (choice.getValue().equals("Prosessor")){//spesifikke attributter går inn i if eller else if setningene
                                        Prosessor pro = new Prosessor(txtNavn.getText(), pris, "Prosessor", specs);
                                        if (komponenter.add(pro)){
                                            System.out.println("funker");
                                        } else {
                                            System.out.println("Noe er galt");
                                        }
                                    } else if (choice.getValue().equals("Skjermkort")){
                                        komponenter.add(new Skjermkort(txtNavn.getText(), pris, "Skjermkort", specs));
                                    } else if (choice.getValue().equals("Minne")){
                                        komponenter.add(new Minne(txtNavn.getText(), pris, "Minne", specs));
                                    } else if (choice.getValue().equals("Harddisk")){
                                        komponenter.add(new Harddisk(txtNavn.getText(), pris, "Harddisk", specs));
                                    } else if (choice.getValue().equals("Tastatur")){
                                        komponenter.add(new Tastatur(txtNavn.getText(), pris, "Tastatur", specs));
                                    } else if (choice.getValue().equals("Mus")){
                                        komponenter.add(new Mus(txtNavn.getText(), pris, "Mus", specs));
                                    } else if (choice.getValue().equals("Skjerm")){
                                        komponenter.add(new Skjerm(txtNavn.getText(), pris, "Skjerm", specs));
                                    } else if (choice.getValue().equals("Operativsystem")){
                                        komponenter.add(new Operativsystem(txtNavn.getText(), pris, "Operativsystem", specs));
                                    }
                                    //deretter lagre Komponenter
                                    labelError.setText("En komponent har blitt lagt til!");
                                    saveKomponenter();
                                }
                            }
                        });
                    }
                });
            } else if (showLeggTil) {
                tableView.setVisible(true);
                labelSøk.setVisible(true);
                txtSøk.setVisible(true);
                btnLeggTil.setText("Legg til komponenter");

                leggTilKomponenterGrid.setVisible(false);
                showLeggTil = false;
            }
        } else if (showSpecs) {

            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("");

            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Spesifikasjon spesifikasjon = new Spesifikasjon(txtSubmit.getText(), tableView.getItems().size());
                    tableView.getItems().add(spesifikasjon);
                    komponenter.getMainArray().get(IDs).getSpecs().add(txtSubmit.getText());
                    saveKomponenter();
                    labelError.setText("En spesifikasjon har blitt lagt til!");
                }
            });
            //String spec = showInputDialog("Skriv inn spesifikasjonen til komponenten");
        }
    }

    @FXML
    void On_Click_BtnRedigerKomponenter(ActionEvent event) {
        leggTilKomponenterGrid.setVisible(false);
        btnf.setVisible(false);
        tableView.setEditable(true);
        tableView.setVisible(true);

        txtSubmit.setVisible(false);
        btnSubmit.setVisible(false);
        txtSubmit.setText("");
        txtSubmit.setPromptText("skriv inn ID");

        if (!showSpecs) {
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
        } else if (showSpecs) {
            if (!showRediger) {
                specNavnKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
                specNavnKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Spesifikasjon, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Spesifikasjon, String> event) {
                        komponenter.getMainArray().get(IDs).getSpecs().remove(event.getRowValue().getNavn());
                        komponenter.getMainArray().get(IDs).getSpecs().add(event.getRowValue().getID(),
                                event.getNewValue());

                        for (String s : komponenter.getMainArray().get(IDs).getSpecs()) {
                            System.out.println(s);
                        }

                        event.getRowValue().setNavn(event.getNewValue());
                        event.getTableView().refresh();
                        saveKomponenter();
                    }
                });
                btnRediger.setText("Stop redigering");
                showRediger = true;
            } else if (showRediger) {
                tableView.setEditable(false);
                txtSubmit.setVisible(false);
                btnSubmit.setVisible(false);
                txtSubmit.setText("");
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
            Scene_4.setHeight(650);
            Scene_4.setWidth(580);
            Scene_4.centerOnScreen();
            Scene_4.show();
        } catch (IOException e) {
            labelError.setText("Klarte ikke å bytte side");
        }

    }

    public void setBruker(Register brukere, Komponenter komponenter) {
        this.Brukere = brukere;
        this.komponenter = komponenter;
    }

    public void On_Click_BtnVisKomponenter(ActionEvent event) {
        tableView.setEditable(false);
        tableView.setVisible(true);
        txtSøk.setVisible(true);
        labelSøk.setVisible(true);
        txtSubmit.setVisible(false);
        btnSubmit.setVisible(false);

        leggTilKomponenterGrid.setVisible(false);

        btnf.setVisible(false);

        tableView.getColumns().clear();
        showSpecs = false;
        showRediger = false;
        showFjern = false;
        showLeggTil = false;

        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);
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

        btnFjern.setText("Fjern komponenter");
        btnRediger.setText("Rediger komponenter");
        btnRediger.setStyle("-fx-background-color: #3daee4;" + "-fx-font-size:15;" + "-fx-font-family: Candara Light");

        btnLeggTil.setText("Legg til komponenter");
        txtSøk.setPromptText("Skriv inn produktnavn");
    }

    public void On_Click_BtnVisSpesifikasjoner(ActionEvent event) {
        if(!showSpecs) {
            tableView.setVisible(true);
            tableView.setEditable(false);
            txtSøk.setVisible(true);
            labelSøk.setVisible(true);

            btnRediger.setText("Rediger");

            leggTilKomponenterGrid.setVisible(false);

            btnf.setVisible(false);

            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("skriv inn ID");

            //String str = showInputDialog("Skriv inn komponentens id");
            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int ID;
                    if (txtSubmit.getText() != null || !txtSubmit.getText().isEmpty()) {

                        try {
                            ID = Integer.parseInt(txtSubmit.getText());
                        } catch (Exception e) {
                            labelError.setText("Vennlighst Skriv inn riktig verdi");
                            ID = -1;
                        }
                    } else {
                        labelError.setText("Vennlighst Skriv inn riktig verdi");
                        ID = -1;
                    }


                    if (ID >= 0 && ID < komponenter.getMainArray().size()) {
                        tableView.getColumns().clear();
                        txtSøk.setPromptText("Skriv inn spesifikasjon");

                        spesifikasjoner = FXCollections.observableArrayList();
                        IDs = ID;

                        for (int i = 0; i < komponenter.getMainArray().get(ID).getSpecs().size(); i+=2) {
                            Spesifikasjon t = new Spesifikasjon(komponenter.getMainArray().get(ID).getSpecs().get(i), i
                            , komponenter.getMainArray().get(ID).getSpecs().get(i+1));
                            spesifikasjoner.add(t);
                        }
                        idSpecKolonne.setCellValueFactory(new PropertyValueFactory<Spesifikasjon, Integer>("ID2"));
                        specNavnKolonne.setCellValueFactory(new PropertyValueFactory<Spesifikasjon, String>("navn2"));


                        tableView.getColumns().addAll(idSpecKolonne, specNavnKolonne);

                        tableView.setItems(spesifikasjoner);
                        showSpecs = true;
                        showRediger = false;
                        showFjern = false;
                        showLeggTil = false;

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                spesifikasjonerSøk = spesifikasjoner.stream().filter(s -> s.getNavn2().indexOf(txtSøk.getText()) != -1)
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                                tableView.setItems(spesifikasjonerSøk);
                            }
                        });
                        btnFjern.setText("Fjern Spesifikasjoner");
                        btnRediger.setText("Rediger Spesifikasjoner");
                        btnRediger.setStyle("-fx-background-color: #3daee4;" + "-fx-font-size:13;" + "-fx-font-family: Candara Light");
                        btnLeggTil.setText("Legg til Spesifikasjoner");

                        txtSubmit.setVisible(false);
                        btnSubmit.setVisible(false);

                        btnVisSpecs.setText("Tilbake");
                    }
                }
            });
        }else{
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            btnVisSpecs.setText("Vis en komponents\nspesifikasjoner");
            On_Click_BtnVisKomponenter(event);
            showSpecs = false;
        }//d

    }
}
