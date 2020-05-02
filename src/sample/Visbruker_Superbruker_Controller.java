package sample;

import Brukere.*;
import Brukere.Register;
import filbehandling.FiledataTxt;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import komponenter.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class Visbruker_Superbruker_Controller {

    private String KomponentType;

    private Komponenter komponenter = new Komponenter();

    private Komponenter komp = new Komponenter();

    private int IDs;

    private Button btnf = new Button();

    @FXML
    private Button btnVisKomponenter;

    @FXML
    private Button btnFjern;

    @FXML
    private Button btnRediger;

    @FXML
    private Button btnTilbake;

    @FXML
    private Button btnFjernKomponenter;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView tableView;

    @FXML
    private Label labelSøk;

    @FXML
    private TextField txtSøk;

    @FXML
    private Label LabelError;


    private boolean showLeggTil = false;

    private boolean showFjern = false;

    private boolean showRediger = false;

    private boolean showKomponenter = false;

    private Register brukere;
    private Register brukere2 = new Register();

    private AnchorPane leggtilPane = new AnchorPane();

    @FXML
    private TableColumn<Bruker, Integer> IDKolonne;

    @FXML
    private TableColumn<Bruker, String> brukerKolonne;

    @FXML
    private TableColumn<Bruker, String> passordKolonne;

    @FXML
    private TableColumn<Bruker, String> tlfKolonne;

    @FXML
    private TableColumn<Bruker, String> mailKolonne;

    @FXML
    private TableColumn<Bruker, Boolean> adminKolonne;

    public void start() {
        if (brukere != null) {
            pane.getChildren().add(leggtilPane);
            pane.getChildren().add(btnf);
            btnf.setVisible(false);

            tableView.setEditable(false);

            leggtilPane.setVisible(false);

            IDKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, Integer>("ID"));
            brukerKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("brukernavn"));
            passordKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("passord"));
            tlfKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("tlf"));
            mailKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("email"));
            PropertyValueFactory<? extends Bruker, Boolean> sd = new PropertyValueFactory<>("ADMIN");
            //adminKolonne.setCellValueFactory(sd);
            adminKolonne.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bruker, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Bruker, Boolean> param) {
                    return new SimpleBooleanProperty(param.getValue().isAdmin());
                }
            });

            /*brukerKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            passordKolonne.setCellFactory(TextFieldTableCell.forTableColumn());*/
            tlfKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            mailKolonne.setCellFactory(TextFieldTableCell.forTableColumn());

            /*brukerKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setBrukernavn(event.getNewValue());
                    saveBrukere();
                    brukerKolonne.getTableView().refresh();
                }
            });
            passordKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setPassord(event.getNewValue());
                    saveBrukere();
                    passordKolonne.getTableView().refresh();
                }
            });*/
            tlfKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    try {
                        event.getRowValue().setTlf(event.getNewValue());
                    }catch (InvalidStringException e){
                        LabelError.setText(e.getMessage());
                    }
                    saveBrukere();
                    tlfKolonne.getTableView().refresh();
                }
            });
            mailKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    try {
                        event.getRowValue().setEmail(event.getNewValue());
                    }catch (InvalidStringException e){
                        LabelError.setText(e.getMessage());
                    }
                    saveBrukere();
                    mailKolonne.getTableView().refresh();
                }
            });


            //tableView.getColumns().addAll(IDKolonne, brukerKolonne, passordKolonne, tlfKolonne, mailKolonne, adminKolonne);
            //System.out.println(brukere.toStringTxt());
            tableView.setItems(brukere.getArray());
        } else if (this.brukere == null) {
            showMessageDialog(null, "brukere er null");
        }
    }

    private void succeded(WorkerStateEvent event) {
        tableView.setDisable(false);
        btnVisKomponenter.setDisable(false);
        btnFjern.setDisable(false);
        btnRediger.setDisable(false);
        btnTilbake.setDisable(false);
    }

    private void failed(WorkerStateEvent event) {
        tableView.setDisable(false);
        btnVisKomponenter.setDisable(false);
        btnFjern.setDisable(false);
        btnRediger.setDisable(false);
        btnTilbake.setDisable(false);

        showMessageDialog(null, "Klarte ikke å laste inn varer!");
    }

    /*public void loadKomponenter() {
        FiledataJOBJ data = new FiledataJOBJ();
        Path path = (Path) Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        data.setKomponent(komponenter);
        data.setPath((java.nio.file.Path) path);

        data.setOnSucceeded(this::succeded);
        data.setOnFailed(this::failed);

        tableView.setDisable(true);
        btnLeggTil.setDisable(true);
        btnFjern.setDisable(true);
        btnRediger.setDisable(true);
        btnTilbake.setDisable(true);

        Thread tr = new Thread(data);
        tr.start();

        try {
            tr.sleep(1000);
        } catch (InterruptedException e) {
            showMessageDialog(null, "Klarte ikke å stoppe tråden");
        }
    }*/

    public void saveBrukere() {
        FiledataTxt data = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

        try {
            data.save(brukere.toStringTxt(), path);
        } catch (IOException e) {
            showMessageDialog(null, "klarte ikke å laste inn data");// for nå
        }
    }

    /*private void søk(TextField txtSøk, TableView tableSøk, boolean setEditAble, Label labelError) {
        TableColumn<Bruker, Integer> IDKolonne = new TableColumn<>("ID");
        TableColumn<Bruker, String> brukernavnKolonne = new TableColumn<>("brukernavn");
        TableColumn<Bruker, String> passordKolonne = new TableColumn<>("passord");
        TableColumn<Bruker, String> tlfKolonne = new TableColumn<>("tlf");
        TableColumn<Bruker, String> emailKolonne = new TableColumn<>("email");
        TableColumn<Bruker, Boolean> adminKolonne = new TableColumn<>("Admin");

        IDKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, Integer>("ID"));
        brukernavnKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("brukernavn"));
        passordKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("passord"));
        tlfKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("tlf"));
        emailKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("email"));
        PropertyValueFactory<? extends Bruker, Boolean> sd = new PropertyValueFactory<>("ADMIN");
        //adminKolonne.setCellValueFactory(sd);
        adminKolonne.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bruker, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Bruker, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().isAdmin());
            }
        });

        tableSøk.getColumns().addAll(IDKolonne, brukernavnKolonne, passordKolonne, emailKolonne, tlfKolonne, adminKolonne);
        brukere2.setArray(brukere.getArray());
        tableSøk.setItems(brukere2.getArray());

        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Predicate<Bruker> Navn = Bruker -> {
                    boolean sjekk = Bruker.getBrukernavn().indexOf(txtSøk.getText()) != -1;
                    return sjekk;
                };

                brukere2.setArray(brukere.getArray().stream().filter(Navn)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                tableSøk.setItems(brukere2.getArray());
            }
        });

        if (setEditAble) {//redigering
            tableSøk.setEditable(setEditAble);
            BooleanStringConverter s = new BooleanStringConverter();

            brukernavnKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            passordKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            emailKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            tlfKolonne.setCellFactory(TextFieldTableCell.forTableColumn());

            brukernavnKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setBrukernavn(event.getNewValue());

                    brukernavnKolonne.getTableView().refresh();

                    saveBrukere();
                }
            });
            passordKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setPassord(event.getNewValue());

                    passordKolonne.getTableView().refresh();
                    saveBrukere();
                }
            });
            emailKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setEmail(event.getNewValue());

                    emailKolonne.getTableView().refresh();
                    saveBrukere();
                }

            });
            tlfKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    event.getRowValue().setTlf(event.getNewValue());

                    tlfKolonne.getTableView().refresh();
                    saveBrukere();
                }

            });

        }
    }*/

    @FXML
    void On_Click_BtnFjernBruker(ActionEvent event) {
        if (!showFjern) {

            String melding = showInputDialog(null, "Skriv brukerens ID");
            int valgtBruker;
            try {
                valgtBruker = Integer.parseInt(melding);
            } catch (Exception e) {
                showMessageDialog(null, "Vennligst skriv inn riktig brukers ID");
                valgtBruker = -1;
            }
            if (valgtBruker != -1) {

                brukere.remove(valgtBruker);
                brukere2.setArray(brukere.getArray());

                tableView.setItems(brukere.getArray());

                saveBrukere();
            }

            showFjern = true;
            showRediger = false;
            showLeggTil = false;
        } else if (showFjern) {
            leggtilPane.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showFjern = false;
        }
    }

    @FXML
    void On_Click_BtnVisKomponenterTilBrukeren(ActionEvent event) {
        tableView.setEditable(false);
        if (!showKomponenter) {

            String ID = showInputDialog("Vennligst skriv inn brukerens ID");
            int valgtBruker;
            try {
                valgtBruker = Integer.parseInt(ID);
            } catch (Exception e) {
                valgtBruker = -1;
            }

            if (valgtBruker >= 0 &&
                    brukere.getArray().get(valgtBruker) instanceof Standardbruker &&
                    valgtBruker < brukere.getArray().size()) {


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
                tableView.getColumns().clear();
                tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);
                tableView.setItems(((Standardbruker) brukere.getArray().get(valgtBruker))
                        .getHandlekurv().getMainArray());
            } else if (valgtBruker >= brukere.getArray().size()) {
                LabelError.setText("Vennligst velg en bruker som eksisterer");
            } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                LabelError.setText("Vennligst velg en kunde");
            } else if (valgtBruker < 0) {
                LabelError.setText("Vennlist skriv inn en gyldig ID");
            }
            showKomponenter = true;
            showRediger = false;
            showFjern = false;
            btnVisKomponenter.setText("Vis brukere");
        }else if (showKomponenter) {
            showKomponenter = false;
            tableView.getColumns().clear();
            tableView.getColumns().addAll(IDKolonne, brukerKolonne, passordKolonne,
                    tlfKolonne, mailKolonne, adminKolonne);
            btnRediger.setText("Rediger");
            btnVisKomponenter.setText("Vis en brukers komponenter");
            tableView.setItems(brukere.getArray());

        }
    }

    @FXML
    void On_Click_BtnRedigerBruker(ActionEvent event) {
        if (!showRediger) {
            tableView.setEditable(true);
            btnRediger.setText("Stop redigering");
            showRediger = true;
            showLeggTil = false;
            showFjern = false;
        } else if (showRediger) {
            tableView.setEditable(false);
            btnRediger.setText("Redigering");
            showRediger = false;
        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Mellom_side_Superbruker.fxml"));
            Parent Superbruker = loader.load();

            Mellom_side_SuperbrukerController controller = loader.getController();
            controller.initBrukere(brukere, komponenter);

            Scene Mellom_side = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(Mellom_side);
            Scene_4.setHeight(360);
            Scene_4.setWidth(580);
            Scene_4.show();
        } catch (IOException e) {
            LabelError.setText("Klarte ikke å bytte side");
        }
    }

    public void initBrukere(Register brukere, Komponenter komponenter) {
        this.brukere = brukere;
        this.komponenter = komponenter;
    }

    public void On_Click_BtnFjernKomponenter(ActionEvent actionEvent) {

        //senere bruke komponenter sin søk

        String idBruker = showInputDialog("Skiv inn brukerens ID");
        int valgtBruker;
        try {
            valgtBruker = Integer.parseInt(idBruker);
        } catch (Exception e) {
            LabelError.setText("venligst skriv inn et gyldig tall");
            valgtBruker = -1;
        }

        IDs = valgtBruker;

        String id = showInputDialog("Skiv inn varens ID");
        int valgtKomponent;
        try {
            valgtKomponent = Integer.parseInt(id);
        } catch (Exception e) {
            LabelError.setText("venligst skriv inn et gyldig tall");
            valgtKomponent = -1;
        }

        if (valgtKomponent >= 0 && brukere.getArray().get(IDs) instanceof Standardbruker) {
            ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().remove(valgtKomponent);
            saveBrukere();
        }else{
            LabelError.setText("Vennligst velg en kunde!");
        }
    }
}
