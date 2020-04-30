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

    @FXML
    private SubScene LeggTilKomponent_sub;

    @FXML
    private AnchorPane LeggTilKomponent_pane;

    @FXML
    private TableView tableView;

    private String KomponentType;

    private Komponenter komponenter = new Komponenter();

    private Komponenter komp = new Komponenter();

    private int IDs;

    @FXML
    private Button btnVisKomponenter;

    @FXML
    private Button btnFjern;

    @FXML
    private Button btnRediger;

    @FXML
    private Button btnTilbake;


    private boolean showLeggTil = false;
    private boolean showFjern = false;
    private boolean showRediger = false;

    private Register brukere;
    private Register brukere2 = new Register();

    public void start() {
        if (brukere != null) {

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

            tableView.getColumns().addAll(IDKolonne, brukernavnKolonne, passordKolonne, tlfKolonne, emailKolonne, adminKolonne);
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

    private void søk(TextField txtSøk, TableView tableSøk, boolean setEditAble, Label labelError) {
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
    }

    @FXML
    void On_Click_BtnFjernBruker(ActionEvent event) {
        if (!showFjern) {
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
            Button btnFjernBruker = new Button("Fjern bruker");
            Button btnFjernVare = new Button("Fjern en brukers vare");
            Button btnFjernVare2 = new Button("Fjern en brukerens vare");
            LeggTilKomponent_pane.getChildren().add(btnFjernBruker);
            LeggTilKomponent_pane.getChildren().add(btnFjernVare);
            LeggTilKomponent_pane.getChildren().add(btnFjernVare2);
            btnFjernBruker.setLayoutX(425);
            btnFjernBruker.setLayoutY(15);
            btnFjernVare.setLayoutX(425);
            btnFjernVare2.setLayoutX(425);
            btnFjernVare.setLayoutY(50);
            btnFjernVare2.setLayoutY(50);

            btnFjernVare2.setVisible(false);

            btnFjernBruker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
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

                        tableSøk.setItems(brukere2.getArray());
                        tableView.setItems(brukere.getArray());

                        saveBrukere();

                    }
                }
            });

            btnFjernVare.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String melding = showInputDialog(null, "Skriv varens ID");
                    int valgtBruker;
                    try {
                        valgtBruker = Integer.parseInt(melding);
                    } catch (Exception e) {
                        showMessageDialog(null, "Vennligst skriv inn riktig varens ID");
                        valgtBruker = -1;
                    }
                    IDs = valgtBruker;

                    if (brukere.getArray().get(valgtBruker) instanceof Standardbruker) {

                        tableSøk.getColumns().clear();

                        //senere bruke komponenter sin søk

                        TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
                        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Produkt navn");
                        TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
                        TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");

                        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
                        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
                        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
                        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));

                        tableSøk.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);

                        tableSøk.setItems(((Standardbruker) brukere.getArray().get(valgtBruker)).
                                getHandlekurv().getMainArray());
                        btnFjernVare.setVisible(false);
                        btnFjernVare2.setVisible(true);

                        btnFjernVare2.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String id = showInputDialog("Skiv inn varens ID");
                                int valgtKomponent;
                                try {
                                    valgtKomponent = Integer.parseInt(id);
                                } catch (Exception e) {
                                    showMessageDialog(null, "venligst skriv inn et gyldig tall");
                                    valgtKomponent = -1;
                                }

                                if (valgtKomponent >= 0) {
                                    ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().remove(valgtKomponent);
                                    saveBrukere();
                                }
                            }
                        });

                    } else {
                        showMessageDialog(null, "denne brukeren er ikke en kunde! \n Og" +
                                " derfor har ikke en handelskurv der varer kan fjernes");
                    }
                }
            });

            showFjern = true;
            showRediger = false;
            showLeggTil = false;
        } else if (showFjern) {
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showFjern = false;
        }
    }

    @FXML
    void On_Click_BtnVisKomponenterTilBrukeren(ActionEvent event) {
        if (!showLeggTil) {
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

            Button btnVisKomponenter = new Button("Vis komponenter");

            btnVisKomponenter.setLayoutY(15);
            btnVisKomponenter.setLayoutX(300);

            søk(txtSøk, tableSøk, false, labelError);

            btnVisKomponenter.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
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

                        tableSøk.getColumns().clear();

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
                        tableSøk.setItems(((Standardbruker) brukere.getArray().get(valgtBruker))
                                .getHandlekurv().getMainArray());
                    } else if (valgtBruker >= brukere.getArray().size()) {
                        showMessageDialog(null, "Vennligst velg en bruker som eksisterer");
                    } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                        showMessageDialog(null, "Vennligst velg en kunde");
                    } else if (valgtBruker < 0) {
                        showMessageDialog(null, "Vennlist skriv inn en gyldig ID");
                    }
                }
            });

            LeggTilKomponent_pane.getChildren().add(btnVisKomponenter);

            showLeggTil = true;
            showRediger = false;
            showFjern = false;
        } else if (showLeggTil) {
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
            showLeggTil = false;
        }
    }

    @FXML
    void On_Click_BtnRedigerBruker(ActionEvent event) {
        if (!showRediger) {
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
        } else if (showRediger) {
            LeggTilKomponent_pane.setVisible(false);
            LeggTilKomponent_sub.setVisible(false);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setHeight(420);
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
            e.printStackTrace();
        }

    }

    public void initBrukere(Register brukere, Komponenter komponenter) {
        this.brukere = brukere;
        this.komponenter = komponenter;
    }

    public void On_Click_BtnFjernKomponenter(ActionEvent actionEvent) {
    }
}
