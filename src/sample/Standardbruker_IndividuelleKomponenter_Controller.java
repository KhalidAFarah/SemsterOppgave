package sample;

import Brukere.Register;
import Brukere.Standardbruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import komponenter.Komponent;
import komponenter.Komponenter;
import komponenter.Spesifikasjon;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Standardbruker_IndividuelleKomponenter_Controller {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Komponent, Integer> IDKolonne;

    @FXML
    private TableColumn<Komponent, String> typeKolonne;

    @FXML
    private TableColumn<Komponent, Double> prisKolonne;

    @FXML
    private TableColumn<Komponent, String> navnKolonne;

    @FXML
    private TableColumn<Komponent, Integer> antallKolonne;

    @FXML
    private ImageView img_Techmet;

    @FXML
    private ImageView img_Background;

    @FXML
    private Label labelError;

    @FXML
    private Button btnVisKurv;

    @FXML
    private Button btnVisSpecs;

    @FXML
    private ChoiceBox<String> choice;

    @FXML
    private TextField txtSøk;

    @FXML
    private Button btnLeggTil;

    @FXML
    private Button btnTilbake;

    @FXML
    private Label labelTotaleSum;

    @FXML
    private Label labelViser;

    @FXML
    private TextField txtSubmit;

    @FXML
    private Button btnSubmit;

    private Standardbruker bruker;
    private Standardbruker bruker2 = new Standardbruker();
    private Register brukere;
    private Komponenter komponenter;
    private Komponenter komponenter2 = new Komponenter();

    private boolean showKurv = false;
    private boolean showSpecs = false;

    @FXML
    void On_Click_BtnKurv(ActionEvent event) {
        if (!showKurv){
            defualt(false);
            showKurv = true;
            btnVisKurv.setText("Tilbake");
            labelViser.setText("Viser varene i din handlekurv");
        }else{
            btnVisKurv.setText("Vis handlekurv");
            showKurv = false;
            defualt(true);
            labelViser.setText("Viser varer");
        }

    }

    @FXML
    void On_Click_BtnLeggTil(ActionEvent event) {

    }

    @FXML
    void On_Click_BtnSpecs(ActionEvent event) {
        visSpesifikasjoner();
    }

    public void visSpesifikasjoner(){
        if (!showSpecs) {
            btnSubmit.setVisible(true);
            txtSubmit.setVisible(true);
            showSpecs = true;
            btnVisSpecs.setText("Tilbake");

            txtSubmit.setText("");
            txtSubmit.setPromptText("Velg komponent. (ID)");

            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int valgtKomponent;
                    try {
                        valgtKomponent = Integer.parseInt(txtSubmit.getText());
                    } catch (Exception e) {
                        labelError.setText("Vennligst skriv inn en gyldig ID!");
                        valgtKomponent = -1;
                    }

                    if (valgtKomponent >= 0) {
                        labelViser.setText("Viser spesifikasjoner for varen");
                        tableView.getColumns().clear();

                        TableColumn<Spesifikasjon, String> spesifikasjonKolonne = new TableColumn<>();
                        spesifikasjonKolonne.setCellValueFactory(new PropertyValueFactory<>("navn2"));
                        spesifikasjonKolonne.setPrefWidth(tableView.getPrefWidth());
                        spesifikasjonKolonne.setMinWidth(tableView.getMinWidth());
                        spesifikasjonKolonne.setMaxWidth(tableView.getMaxWidth());

                        ObservableList<Spesifikasjon> spesifikasjoner = FXCollections.observableArrayList();
                        ObservableList<Spesifikasjon> spesifikasjoner2 = FXCollections.observableArrayList();
                        int teller = 0;
                        for (String s : komponenter.getMainArray().get(valgtKomponent).getSpecs()) {
                            spesifikasjoner.add(new Spesifikasjon(s, teller));
                            teller++;
                        }

                        tableView.getColumns().add(spesifikasjonKolonne);
                        tableView.setItems(spesifikasjoner);

                        txtSøk.setText("");
                        txtSøk.setPromptText("søk inn spesifikasjon");

                        choice.setDisable(true);

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                Predicate<Spesifikasjon> Navn = Spesifikasjon -> {
                                    boolean sjekk = Spesifikasjon.getNavn2().indexOf(txtSøk.getText()) != -1;
                                    return sjekk;
                                };

                                spesifikasjoner2.clear();
                                spesifikasjoner2.addAll(spesifikasjoner.stream().filter(Navn).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                tableView.setItems(spesifikasjoner2);
                            }
                        });

                    }
                }
            });
        }else{
            defualt(true);
            choice.setDisable(false);

        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MellomSide_Standardbruker.fxml"));
        Parent Standardbruker;
        boolean value = true;
        try {
            Standardbruker = loader.load();
        }catch (IOException e) {
            labelError.setText("Klart ikke å bytte side");
            Standardbruker = null;
            value = false;
        }
        if(value){
            MellomSide_Standardbruker_Controller controller = loader.getController();
            controller.setInfo(brukere, komponenter, bruker);
            Scene LoggInn = new Scene(Standardbruker);
            Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_3.setScene(LoggInn);
            Scene_3.setHeight(350);
            Scene_3.setWidth(566);
            Scene_3.show();
        }
    }

    public void start(Standardbruker bruker, Register brukere, Komponenter komponenter){
        this.bruker = bruker;
        this.brukere = brukere;
        this.komponenter = komponenter;

        //sette backgrounden til samme width og height som scenen


        ObservableList<String> choices = FXCollections.observableArrayList(FXCollections.observableArrayList(
                "Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm", "Operativsystem", "Alle"));

        choice.setItems(choices);
        //choice.setValue("Velg type");

        labelTotaleSum.setText("totale pris: " + bruker.getIndividuellevarerSum());

        defualt(true);
    }

    private void defualt(boolean view){
        choice.setDisable(false);
        tableView.setEditable(true);

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));
        antallKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("antall"));

        IntegerStringConverter intStr = new IntegerStringConverter();
        antallKolonne.setCellFactory(TextFieldTableCell.forTableColumn(intStr));

        antallKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Komponent, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Komponent, Integer> event) {
                if(event.getNewValue() > 0){
                    event.getRowValue().setAntall(event.getNewValue());
                }
            }
        });
        tableView.getColumns().clear();
        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne, antallKolonne);

        if(view) {
            tableView.setItems(komponenter.getMainArray());
        }else{
            tableView.setItems(bruker.getIndividuelleVarer().getMainArray());
        }

        choice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!choice.getValue().equals("Alle")) {
                    Predicate<Komponent> type = Komponent -> {
                        boolean sjekk = Komponent.getType().equals(choice.getValue());
                        return sjekk;
                    };

                    if (view) {
                        komponenter2.setMainArray(komponenter.getMainArray().stream().filter(type)
                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                        tableView.setItems(komponenter2.getMainArray());
                    } else {
                        bruker2.getIndividuelleVarer().setMainArray(bruker.getHandlekurv().getMainArray().stream().filter(type)
                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                        tableView.setItems(bruker.getIndividuelleVarer().getMainArray());
                    }
                }
            }
        });

        søk(view);
    }

    public void søk(boolean view){
        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Predicate<Komponent> Navn = Komponent -> {
                    if (!choice.getValue().equals("Velg type") || choice.getValue() == null) {
                        boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1
                                && choice.getValue().equals(Komponent.getType());
                        return sjekk;
                    }
                    boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                    return sjekk;
                };
                if(view) {
                    komponenter2.setMainArray(komponenter.getMainArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(komponenter2.getMainArray());
                }else{
                    bruker2.getIndividuelleVarer().setMainArray(bruker.getHandlekurv().getMainArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(bruker2.getIndividuelleVarer().getMainArray());
                }
            }
        });
    }
}
