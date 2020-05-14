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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
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

    private final Komponenter komp = new Komponenter();

    private int IDs;

    private final Button btnf = new Button();

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
    private Label labelError;

    @FXML
    private TextField txtSubmit;

    @FXML
    private Button btnSubmit;


    private boolean showLeggTil = false;

    private boolean showFjern = false;

    private boolean showRediger = false;

    private boolean showKomponenter = false;

    private boolean showFjernK = false;

    private Register brukere;
    private final Register brukere2 = new Register();

    private final AnchorPane leggtilPane = new AnchorPane();

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
            //PropertyValueFactory<? extends Bruker, Boolean> sd = new PropertyValueFactory<>("ADMIN");
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


            //tableView.getColumns().addAll(IDKolonne, brukerKolonne, passordKolonne, tlfKolonne, mailKolonne, adminKolonne);
            //System.out.println(brukere.toStringTxt());
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
            showMessageDialog(null, "Brukere er null.");
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
            showMessageDialog(null, "Klarte ikke å laste inn data");// for nå
        }
    }
    @FXML
    void On_Click_BtnFjernBruker(ActionEvent event) {
        if (!showFjern) {

            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            txtSubmit.setPromptText("Skriv inn ID.");
            btnFjern.setText("Tilbake");

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
            /*String melding = showInputDialog(null, "Skriv brukerens ID");
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
            }*/

            showFjern = true;
            showRediger = false;
            showLeggTil = false;
        } else if (showFjern) {
            leggtilPane.setVisible(false);
            showFjern = false;
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            txtSubmit.setText("");
            labelError.setText("");
            btnFjern.setText("Fjern bruker");
        }
    }

    @FXML
    void On_Click_BtnVisKomponenterTilBrukeren(ActionEvent event) {
        tableView.setEditable(false);
        if (!showKomponenter) {
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
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

                    if (valgtBruker >= 0 && brukere.getArray().get(valgtBruker) instanceof Standardbruker) {
                        IDs = valgtBruker;
                        System.out.println("1");
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
                        txtSubmit.setVisible(false);
                        btnSubmit.setVisible(false);
                        showKomponenter = true;

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                Predicate<Komponent> Navn = komponent -> {
                                    boolean sjekk = komponent.getNavn().indexOf(txtSøk.getText()) != -1;
                                    return sjekk;
                                };
                                brukere2.setArray(brukere.getArray());
                                ((Standardbruker) brukere2.getArray().get(IDs)).getHandlekurv().setMainArray
                                        (((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().getMainArray()
                                                .stream().filter(Navn).collect(Collectors
                                                        .toCollection(FXCollections::observableArrayList)));
                                tableView.setItems(((Standardbruker) brukere2.
                                        getArray().get(IDs)).getHandlekurv().getMainArray());
                            }
                        });

                    } else if (valgtBruker >= brukere.getArray().size()) {
                        System.out.println("2");
                        labelError.setText("Vennligst velg en bruker som eksisterer");
                    } else if (!(brukere.getArray().get(valgtBruker) instanceof Standardbruker)) {
                        labelError.setText("Vennligst velg en kunde");
                        System.out.println("3");
                    } else if (valgtBruker < 0) {
                        System.out.println("4");
                        labelError.setText("Vennlist skriv inn en gyldig ID");
                    }


                    showRediger = false;
                    showFjern = false;
                    btnVisKomponenter.setText(" Tilbake ");


                }
            });

            showKomponenter = true;
            showRediger = false;
            showFjern = false;
            btnVisKomponenter.setText(" Tilbake ");
        } else if (showKomponenter) {
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
            btnRediger.setText("Rediger bruker");
            btnVisKomponenter.setText("Vis en brukers\nkomponenter");
            tableView.setItems(brukere.getArray());
            txtSubmit.setVisible(false);
            btnSubmit.setVisible(false);
            txtSubmit.setText("");

            txtSøk.setText("");
            txtSøk.setPromptText("Skriv inn brukernavn.");

        }
    }

    @FXML
    void On_Click_BtnRedigerBruker(ActionEvent event) {
        if (!showRediger) {
            tableView.setEditable(true);
            btnRediger.setText("Stopp redigering");
            showRediger = true;
            showLeggTil = false;
            showFjern = false;
        } else if (showRediger) {
            tableView.setEditable(false);
            btnRediger.setText("Rediger bruker");
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
            Scene_4.setHeight(700);
            Scene_4.setWidth(420);
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

        //senere bruke komponenter sin søk

        /*String idBruker = showInputDialog("Skiv inn brukerens ID");
        int valgtBruker;
        try {
            valgtBruker = Integer.parseInt(idBruker);
        } catch (Exception e) {
            LabelError.setText("venligst skriv inn et gyldig tall");
            valgtBruker = -1;
        }*/

        if (!showFjernK) {
            txtSubmit.setVisible(true);
            btnSubmit.setVisible(true);
            txtSubmit.setText("");
            btnFjernKomponenter.setText("Tilbake");
            showFjernK = true;
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

                        if (valgtBruker >= 0 && brukere.getArray().get(valgtBruker) instanceof Standardbruker) {

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
                        showKomponenter = true;
                        showRediger = false;
                        showFjern = false;
                        btnVisKomponenter.setText(" Tilbake ");

                    }
                });
            } else {
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

                        if (valgtKomponent >= 0 && brukere.getArray().get(IDs) instanceof Standardbruker) {

                            ((Standardbruker) brukere.getArray().get(IDs)).getHandlekurv().remove(valgtKomponent);
                            saveBrukere();
                            labelError.setText("En brukers komponent har blitt fjernet!");

                            txtSubmit.setText("");
                            txtSubmit.setPromptText("Velg komponent. (ID)");
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
            btnFjernKomponenter.setText("Fjern en brukers\nkomponenter");
            labelError.setText("");
        }
    }
}
