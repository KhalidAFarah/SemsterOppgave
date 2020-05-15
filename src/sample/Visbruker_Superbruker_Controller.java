package sample;

import Brukere.*;
import Brukere.Register;
import filbehandling.FiledataTxt;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import komponenter.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Visbruker_Superbruker_Controller {

    private Komponenter komponenter = new Komponenter();

    private int IDs;

    @FXML
    private Button btnVisKomponenter;

    @FXML
    private Button btnFjern;

    @FXML
    private Button btnRediger;

    @FXML
    private Button btnFjernKomponenter;


    @FXML
    private TableView tableView;

    @FXML
    private Label labelSøk;

    @FXML
    private TextField txtSøk;

    @FXML
    private Label labelError;

    @FXML
    private TextField txtSubmit;

    @FXML
    private Button btnSubmit;


    private boolean showFjern = false;

    private boolean showRediger = false;

    private boolean showKomponenter = false;

    private boolean showKomponenter2 = false;

    private boolean showFjernK = false;

    private boolean ShowIndividuelleKomponenter = false;

    private boolean ShowIndividuelleKomponenter2 = false;

    private Register brukere;

    private final Register brukere2 = new Register();


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


            tableView.setEditable(false);


            IDKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, Integer>("ID"));
            brukerKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("brukernavn"));
            passordKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("passord"));
            tlfKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("tlf"));
            mailKolonne.setCellValueFactory(new PropertyValueFactory<Bruker, String>("email"));
            adminKolonne.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bruker, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Bruker, Boolean> param) {
                    return new SimpleBooleanProperty(param.getValue().isAdmin());
                }
            });

            tlfKolonne.setCellFactory(TextFieldTableCell.forTableColumn());
            mailKolonne.setCellFactory(TextFieldTableCell.forTableColumn());

            tlfKolonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bruker, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Bruker, String> event) {
                    try {
                        event.getRowValue().setTlf(event.getNewValue());
                    } catch (InvalidStringException e) {
                        labelError.setText(e.getMessage());
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
                    } catch (InvalidStringException e) {
                        labelError.setText(e.getMessage());
                    }
                    saveBrukere();
                    mailKolonne.getTableView().refresh();
                }
            });

            tableView.setItems(brukere.getArray());

            txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    Predicate<Bruker> Navn = bruker -> {
                        boolean sjekk = bruker.getBrukernavn().indexOf(txtSøk.getText()) != -1;
                        return sjekk;
                    };

                    brukere2.setArray(brukere.getArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(brukere2.getArray());
                }
            });
        } else if (this.brukere == null) {
            labelError.setText("Brukere er null.");
        }
    }

    public void saveBrukere() {
        FiledataTxt data = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

        try {
            data.save(brukere.toStringTxt(), path);
        } catch (IOException e) {
            labelError.setText("Klarte ikke å laste inn data");// for nå
        }
    }

    private FiledataTxt lagreTxt;

    private void saveBruekesIndividuelleVarer() {
        lagreTxt = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/StandardbrukerSinIndividuelleHandlekurv.csv");
        try {
            lagreTxt.save(brukere.toStringTxtMedAntall(), path);
        } catch (IOException e) {
            labelError.setText("Klarte ikke å lagre data");
        }
    }

    @FXML
    void On_Click_BtnFjernBruker(ActionEvent event) {
        if (showKomponenter2) {
            fjernerFerdigByggetPcKomponenter();
            tableView.setItems(((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().getMainArray());
        } else if (!showKomponenter2 && !ShowIndividuelleKomponenter2) {
            btnVisKomponenter.setText("Vis en brukers\nferdig bygget pc\nkomponenter");
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            showKomponenter2 = false;
            ShowIndividuelleKomponenter2 = false;
            fjernerBruker();
            tableView.setItems(brukere.getArray());
        } else if (ShowIndividuelleKomponenter2) {
            fjernEnbrukersIndividuelleKomponenter();
            tableView.setItems(((Standardbruker) brukere.getArray().get(IDs)).getIndividuelleVarer().getMainArray());
        }
    }

    @FXML
    void On_Click_BtnVisKomponenterTilBrukeren(ActionEvent event) {
        tableView.setEditable(false);
        if (!showKomponenter2) {
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("Velg bruker. (ID)");
            btnVisKomponenter.setText("Tilbake");
            btnRediger.setText("Rediger brukere");
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            btnFjern.setText("Fjern en brukers \nkomponenter\nfor ferdig bygget\npc");

            btnRediger.setDisable(true);

            showRediger = false;
            showFjern = false;
            showFjernK = false;
            showKomponenter2 = true;
            ShowIndividuelleKomponenter2 = false;

            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int valgtBruker;
                    try {
                        valgtBruker = Integer.parseInt(txtSubmit.getText());
                    } catch (Exception e) {
                        labelError.setText("Vennligst skriv inn et gyldig tall.");
                        valgtBruker = -1;
                    }

                    if (valgtBruker >= 0 && brukere.getArray().get(valgtBruker) instanceof Standardbruker) {
                        IDs = valgtBruker;

                        TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
                        TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Produktnavn");
                        TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
                        TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");


                        IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
                        navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
                        typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
                        prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));

                        tableView.getColumns().clear();
                        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);
                        tableView.setItems(((Standardbruker) brukere.getArray().get(valgtBruker))
                                .getHandlekurv().getMainArray());
                        txtSubmit.setVisible(false);
                        btnSubmit.setVisible(false);
                        showKomponenter = true;

                        btnVisKomponenter.setText("Vis brukere");
                        labelError.setText("Brukerens komponenter er vist");
                        txtSøk.setText("");
                        txtSøk.setPromptText("Søk produktnavn");

                        txtSøk.setVisible(false);
                        labelSøk.setVisible(false);

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                Predicate<Komponent> Navn = Komponent -> {
                                    boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                                    return sjekk;
                                };
                                brukere2.setArray(brukere.getArray());
                                ((Standardbruker) brukere2.getArray().get(IDs)).getHandlekurv().setMainArray(((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().getMainArray().stream().filter(Navn)
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                tableView.setItems(((Standardbruker) brukere2.getArray().get(IDs)).getHandlekurv().getMainArray());
                            }
                        });

                    } else if (valgtBruker >= brukere.getArray().size()) {
                        labelError.setText("Vennligst velg en bruker som eksisterer");
                    } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                        labelError.setText("Vennligst velg en kunde");
                    } else if (valgtBruker < 0) {
                        labelError.setText("Vennlist skriv inn en gyldig ID");
                    }
                }
            });
        } else if (showKomponenter2) {
            showKomponenter2 = false;
            showKomponenter = false;
            tableView.getColumns().clear();
            tableView.getColumns().addAll(IDKolonne, brukerKolonne, passordKolonne,
                    tlfKolonne, mailKolonne, adminKolonne);
            txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    Predicate<Bruker> Navn = bruker -> {
                        boolean sjekk = bruker.getBrukernavn().indexOf(txtSøk.getText()) != -1;
                        return sjekk;
                    };

                    brukere2.setArray(brukere.getArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(brukere2.getArray());
                }
            });
            btnRediger.setText("Rediger brukere");
            btnVisKomponenter.setText("Vis en brukers\nferdig bygget pc\nkomponenter");
            btnFjern.setText("Fjern brukere");
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            tableView.setItems(brukere.getArray());
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            txtSubmit.setText("");

            txtSøk.setText("");
            txtSøk.setPromptText("Skriv inn brukernavn.");
            txtSøk.setVisible(true);
            labelSøk.setVisible(true);
            btnFjern.setDisable(false);
            btnRediger.setDisable(false);

        }
    }

    @FXML
    void On_Click_BtnRedigerBruker(ActionEvent event) {
        btnFjern.setDisable(false);
        btnRediger.setDisable(false);
        tableView.refresh();
        if (!showRediger) {
            tableView.setEditable(true);
            btnRediger.setText("Stopp redigering");
            showRediger = true;
            showFjern = false;
            showFjernK = false;
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            btnFjern.setText("Fjern brukere");
            btnVisKomponenter.setText("Vis komponenter\ntil en bruker");
            labelError.setText("Du kan kun redigere på telefon nummer og email, ved å klikke på kolonen og deretter trykke enter!");
        } else if (showRediger) {
            tableView.setEditable(false);
            btnRediger.setText("Rediger brukere");
            showRediger = false;
        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/Mellom_side_Superbruker.fxml"));
            Parent Superbruker = loader.load();

            Mellom_side_SuperbrukerController controller = loader.getController();
            controller.initBrukere(brukere, komponenter);

            Scene Mellom_side = new Scene(Superbruker);
            Stage Scene_4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_4.setScene(Mellom_side);
            Scene_4.setHeight(750);
            Scene_4.setWidth(500);
            Scene_4.centerOnScreen();
            Scene_4.show();
        } catch (IOException e) {
            labelError.setText("Klarte ikke å bytte side");
        }
    }

    public void initBrukere(Register brukere, Komponenter komponenter) {
        this.brukere = brukere;
        this.komponenter = komponenter;
    }

    public void On_Click_BtnFjernKomponenter(ActionEvent actionEvent) {
        btnVisKomponenter.setText("Vis en brukers\nferdig bygget pc\nkomponenter");
        btnRediger.setText("Rediger bruker");
        if (!ShowIndividuelleKomponenter) {
            ShowIndividuelleKomponenter = true;
            btnFjernKomponenter.setText("Tilbake");

            showFjernK = false;
            showKomponenter = false;
            showRediger = false;

            btnRediger.setDisable(true);


            btnSubmit.setVisible(true);
            txtSubmit.setVisible(true);

            txtSubmit.setText("");
            txtSubmit.setPromptText("Velg en bruker. (ID)");


            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int valgtBruker;
                    try {
                        valgtBruker = Integer.parseInt(txtSubmit.getText());
                    } catch (Exception e) {
                        labelError.setText("Vennligst skriv inn et gyldig tall.");
                        valgtBruker = -1;
                    }

                    if (valgtBruker >= 0 && brukere.getArray().get(valgtBruker) instanceof Standardbruker
                            && valgtBruker < brukere.getArray().size()) {

                        IDs = valgtBruker;
                        tableView.getColumns().clear();
                        labelError.setText("Viser brukerens individuelle vare kurv");
                        btnFjern.setText("Fjern en brukers\nindividuelle\nkomponenter");
                        ShowIndividuelleKomponenter2 = true;

                        TableColumn<Komponent, Integer> IDKolonne2 = new TableColumn<>("ID");
                        TableColumn<Komponent, String> navnKolonne2 = new TableColumn<>("Produktnavn");
                        TableColumn<Komponent, String> typeKolonne2 = new TableColumn<>("Type");
                        TableColumn<Komponent, Double> prisKolonne2 = new TableColumn<>("Pris");
                        TableColumn<Komponent, Integer> antallKolonne2 = new TableColumn<>("Antall");

                        IDKolonne2.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
                        navnKolonne2.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
                        typeKolonne2.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
                        prisKolonne2.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));
                        antallKolonne2.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("antall"));


                        tableView.getColumns().addAll(IDKolonne2, navnKolonne2, typeKolonne2, prisKolonne2, antallKolonne2);
                        tableView.setItems(((Standardbruker) brukere.getArray().get(valgtBruker))
                                .getIndividuelleVarer().getMainArray());

                        txtSubmit.setVisible(false);
                        btnSubmit.setVisible(false);

                        btnVisKomponenter.setText("Vis brukere");
                        txtSøk.setText("");
                        txtSøk.setPromptText("Søk produktnavn");

                        txtSøk.setVisible(false);
                        labelSøk.setVisible(false);

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                Predicate<Komponent> Navn = Komponent -> {
                                    boolean sjekk = Komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                                    return sjekk;
                                };
                                brukere2.setArray(brukere.getArray());
                                ((Standardbruker) brukere2.getArray().get(IDs)).getIndividuelleVarer()
                                        .setMainArray(((Standardbruker) brukere.getArray().get(IDs)).
                                                getIndividuelleVarer().getMainArray().stream().filter(Navn)
                                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                tableView.setItems(((Standardbruker) brukere2.getArray().get(IDs))
                                        .getIndividuelleVarer().getMainArray());
                            }
                        });

                    } else if (valgtBruker >= brukere.getArray().size()) {
                        labelError.setText("Vennligst velg en bruker som eksisterer");
                    } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                        labelError.setText("Vennligst velg en kunde");
                    } else if (valgtBruker < 0) {
                        labelError.setText("Vennligst skriv inn en gyldig ID");
                    }
                }
            });


        } else {
            btnFjernKomponenter.setText("Vis en brukers\n individuelle\n komponenter");
            btnFjern.setText("Fjern brukere");
            ShowIndividuelleKomponenter = false;
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(IDKolonne, brukerKolonne, passordKolonne, tlfKolonne, mailKolonne);
            tableView.setItems(brukere.getArray());
            ShowIndividuelleKomponenter2 = false;
            btnRediger.setDisable(false);
        }

    }

    public void fjernerFerdigByggetPcKomponenter() {
        showRediger = false;
        showFjern = false;

        if (!showFjernK) {
            showFjernK = true;
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            btnRediger.setText("Rediger bruker");
            btnFjern.setText("Fjern brukere");

            if (!showKomponenter) {
                txtSubmit.setPromptText("Velg bruker. (ID)");
                btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int valgtBruker;
                        try {
                            valgtBruker = Integer.parseInt(txtSubmit.getText());
                        } catch (Exception e) {
                            labelError.setText("Vennligst skriv inn et gyldig tall.");
                            valgtBruker = -1;
                        }

                        if (valgtBruker >= 0 && brukere.getArray().get(valgtBruker) instanceof Standardbruker
                                && valgtBruker < brukere.getArray().size()) {

                            btnFjern.setDisable(true);
                            btnRediger.setDisable(true);

                            TableColumn<Komponent, Integer> IDKolonne = new TableColumn<>("ID");
                            TableColumn<Komponent, String> navnKolonne = new TableColumn<>("Produktnavn");
                            TableColumn<Komponent, String> typeKolonne = new TableColumn<>("Type");
                            TableColumn<Komponent, Double> prisKolonne = new TableColumn<>("Pris");

                            IDKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Integer>("ID"));
                            navnKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("navn"));
                            typeKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, String>("type"));
                            prisKolonne.setCellValueFactory(new PropertyValueFactory<Komponent, Double>("pris"));

                            btnVisKomponenter.setText("Vis bruker");
                            showKomponenter = true;
                            tableView.getColumns().clear();
                            tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne);
                            tableView.setItems(((Standardbruker) brukere.getArray().get(valgtBruker))
                                    .getHandlekurv().getMainArray());

                            IDs = valgtBruker;
                            txtSubmit.setText("");
                            txtSubmit.setPromptText("Velg kompoenent. (ID)");
                            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    int valgtKomponent;
                                    try {
                                        valgtKomponent = Integer.parseInt(txtSubmit.getText());
                                    } catch (Exception e) {
                                        labelError.setText("Vennligst skriv inn et gyldig tall.");
                                        valgtKomponent = -1;
                                    }

                                    if (valgtKomponent >= 0) {
                                        ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv()
                                                .remove(valgtKomponent);
                                        saveBrukere();
                                        labelError.setText("En brukers komponent har blitt fjernet!");
                                        tableView.setItems(((Standardbruker) brukere.getArray().get(IDs))
                                                .getHandlekurv().getMainArray());

                                        txtSubmit.setText("");
                                        txtSubmit.setPromptText("Velg komponent. (ID)");

                                        txtSubmit.setVisible(false);
                                        btnSubmit.setVisible(false);
                                        showFjernK = false;

                                        btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");


                                    } else {
                                        labelError.setText("Vennligst velg en kunde!");
                                    }
                                }
                            });

                        } else if (valgtBruker >= brukere.getArray().size()) {
                            labelError.setText("Vennligst velg en bruker som eksisterer");
                        } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                            labelError.setText("Vennligst velg en kunde");
                        } else if (valgtBruker < 0) {
                            labelError.setText("Vennlist skriv inn en gyldig ID");
                        }
                        btnVisKomponenter.setText(" Tilbake ");

                    }
                });
            } else {
                showFjernK = true;
                txtSubmit.setText("");
                txtSubmit.setPromptText("Velg kompoenent. (ID)");
                btnRediger.setText("Rediger bruker");
                btnVisKomponenter.setText("Vis bruker");
                btnFjern.setText("Fjern brukere");
                btnFjernKomponenter.setText("Tilbake");
                btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int valgtKomponent;
                        try {
                            valgtKomponent = Integer.parseInt(txtSubmit.getText());
                        } catch (Exception e) {
                            labelError.setText("Vennligst skriv inn et gyldig tall.");
                            valgtKomponent = -1;
                        }

                        if (valgtKomponent >= 0 && brukere.getArray().get(IDs) instanceof Standardbruker
                                && ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv()
                                .getMainArray().size() > valgtKomponent) {

                            ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().remove(valgtKomponent);
                            saveBrukere();
                            labelError.setText("En brukers komponent har blitt fjernet!");

                            txtSubmit.setText("");
                            txtSubmit.setPromptText("Velg komponent. (ID)");

                            txtSubmit.setVisible(false);
                            btnSubmit.setVisible(false);
                            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
                        } else {
                            labelError.setText("Dette er en Admin, vennligst velg en kunde!");
                        }
                    }
                });
            }
        } else {
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            showFjernK = false;
            txtSubmit.setText("");
            btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
            labelError.setText("");
        }
    }

    public void fjernEnbrukersIndividuelleKomponenter() {
        if (!showFjern) {
            showFjern = true;
            txtSubmit.setText("");
            txtSubmit.setPromptText("Velg kompoenent. (ID)");
            btnRediger.setText("Rediger bruker");
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            showRediger = false;
            tableView.refresh();
            btnFjern.setText("Tilbake");

            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int valgtKomponent;
                    try {
                        valgtKomponent = Integer.parseInt(txtSubmit.getText());
                    } catch (Exception e) {
                        labelError.setText("Vennligst skriv inn et gyldig tall.");
                        valgtKomponent = -1;
                    }

                    if (valgtKomponent >= 0 && brukere.getArray().get(IDs) instanceof Standardbruker &&
                            ((Standardbruker) brukere.getArray().get(IDs)).getIndividuelleVarer()
                                    .getMainArray().size() > valgtKomponent) {

                        ((Standardbruker) brukere.getArray().get(IDs)).getIndividuelleVarer().remove(valgtKomponent);
                        saveBruekesIndividuelleVarer();
                        labelError.setText("En brukers komponent har blitt fjernet!");

                        txtSubmit.setText("");
                        txtSubmit.setPromptText("Velg komponent. (ID)");

                        txtSubmit.setVisible(false);
                        btnSubmit.setVisible(false);
                        btnFjernKomponenter.setText("Vis en brukers\nindividuelle\nkomponenter");
                    } else {
                        labelError.setText("Dette er en Admin, vennligst velg en kunde!");
                    }
                }
            });
        } else {
            showFjern = false;
            btnFjern.setText("Fjern en brukers\nindividuelle\nkomponenter");
            labelError.setText("");
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
        }

    }

    public void fjernerBruker() {
        btnFjern.setDisable(false);
        btnRediger.setDisable(false);
        showRediger = false;
        showKomponenter = false;
        showFjernK = false;
        if (!showFjern) {
            showFjern = true;
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("Skriv inn ID.");
            btnFjern.setText("Tilbake");
            btnRediger.setText("Rediger brukere");

            btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int valgtBruker;
                    try {
                        valgtBruker = Integer.parseInt(txtSubmit.getText());
                    } catch (Exception e) {
                        labelError.setText("Vennligst skriv inn bruker-ID riktig.");
                        valgtBruker = -1;
                    }
                    if (valgtBruker != -1) {

                        brukere.remove(valgtBruker);
                        brukere2.setArray(brukere.getArray());

                        tableView.setItems(brukere.getArray());
                        labelError.setText("En bruker har blitt fjernet!");
                        saveBrukere();
                    }
                }
            });

        } else if (showFjern) {
            showFjern = false;
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            txtSubmit.setText("");
            labelError.setText("");
            btnFjern.setText("Fjern brukere");
        }
    }
}
