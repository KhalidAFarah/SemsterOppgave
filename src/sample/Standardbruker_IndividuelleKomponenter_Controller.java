package sample;

import Brukere.InvalidDataGivenException;
import Brukere.InvalidNumberException;
import Brukere.Register;
import Brukere.Standardbruker;
import filbehandling.FiledataTxt;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import jdk.internal.org.objectweb.asm.Handle;
import komponenter.Komponent;
import komponenter.Komponenter;
import komponenter.Spesifikasjon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Button btnKvittering;

    @FXML
    private Button btnVisKurv;

    @FXML
    private Button btnVisSpecs;

    @FXML
    private ComboBox choice;

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

    @FXML
    private Button btnKjøp;

    private Standardbruker bruker;
    private Standardbruker bruker2 = new Standardbruker();
    private Register brukere;
    private Komponenter komponenter;
    private Komponenter komponenter2 = new Komponenter();

    private FiledataTxt lagreTxt;

    private boolean showKurv = false;
    private boolean showSpecs = false;
    private boolean showLeggTil = false;
    private boolean showFullført = false;

    private void save() {
        lagreTxt = new FiledataTxt();
        Path path = Paths.get("src/filbehandling/StandardbrukerSinIndividuelleHandlekurv.csv");
        try {
            lagreTxt.save(brukere.toStringTxtMedAntall(), path);
        } catch (IOException e) {
            labelError.setText("Klarte ikke å lagre data");
        }
    }

    @FXML
    void On_Click_BtnKurv(ActionEvent event) {
        choice.setValue("Alle");
        if (!showKurv) {
            if (bruker.getIndividuelleVarer().getMainArray().size() == 0) {
                labelError.setText("Din handlekurv er tom, legg til varer");
            }
            defualt(false);

            Komponenter k = new Komponenter();
            for (int i = 0; i < bruker.getIndividuelleVarer().getMainArray().size(); i++) {
                k.add(bruker.getIndividuelleVarer().getMainArray().get(i));
            }
            bruker.getIndividuelleVarer().setMainArray(k.getMainArray());


            showKurv = true;
            showSpecs = false;
            showLeggTil = false;
            btnVisKurv.setText("Tilbake");
            btnLeggTil.setText("Fjern komponent");
            btnVisSpecs.setText("Vis spesifikasjoner");
            labelViser.setText("Viser varene i din handlekurv");
            tableView.setItems(bruker.getIndividuelleVarer().getMainArray());

            btnSubmit.setVisible(false);
            txtSubmit.setVisible(false);
            btnKjøp.setVisible(true);
            choice.setValue("Alle");
            choice.setDisable(true);
            txtSøk.setVisible(true);
            søk(false);

            txtSubmit.setText("");
        } else {
            btnVisKurv.setText("Vis handlekurv");
            showKurv = false;
            defualt(true);
            btnLeggTil.setText("Legg til komponent");
            labelViser.setText("Viser varer");
        }

    }

    @FXML
    void On_Click_BtnLeggTil(ActionEvent event) {
        btnSubmit.setVisible(true);
        txtSubmit.setVisible(true);
        txtSubmit.setText("");
        showSpecs = false;
        showFullført = false;
        btnLeggTil.setText("Tilbake");
        btnVisSpecs.setText("Vis spesifikasjoner");

        if (!showKurv) {
            btnKjøp.setVisible(false);
            if (!showLeggTil) {
                showLeggTil = true;
                txtSubmit.setPromptText("Velg komponent. (ID)");
                labelError.setText("For å legge til varer så må du velge antall varer som du vil ha ved å dobbelklikke" +
                        " på varens antall \n kolonne og deretter klikke enter!");

                defualt(true);

                btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int valgtkomponent;

                        try {
                            valgtkomponent = Integer.parseInt(txtSubmit.getText());
                        } catch (Exception e) {
                            labelError.setText("Vennligst skriv inn en gyldig ID");
                            valgtkomponent = -1;
                        }

                        if (valgtkomponent >= 0) {
                            if (komponenter.getMainArray().get(valgtkomponent).getAntall() > 0) {
                                boolean funnet = false;
                                for (int i = 0; i < bruker.getIndividuelleVarer().getMainArray().size(); i++) {
                                    if (bruker.getIndividuelleVarer().getMainArray().get(i).getNavn().
                                            equals(komponenter.getMainArray().get(valgtkomponent).getNavn())) {
                                        funnet = true;
                                    }
                                }
                                if (!funnet) {
                                    bruker.getIndividuelleVarer().add(komponenter.getMainArray().get(valgtkomponent));
                                    labelTotaleSum.setText("Totalpris: " + bruker.getIndividuellevarerSum());
                                    labelError.setText("Varen har blitt lagt til.");
                                    save();
                                } else {
                                    labelError.setText("Kan ikke legge til samme vare flere ganger. Vennligst endre antall.");
                                }
                            } else {
                                labelError.setText("Du har ikke valgt et antall av varen eller så har du valgt feil (ID) til varen!");
                            }
                        }
                    }
                });
            } else {
                showLeggTil = false;
                btnLeggTil.setText("Legg til handlekurv");
                btnSubmit.setVisible(false);
                txtSubmit.setVisible(false);
            }
        } else {
            if (!showLeggTil) {
                showLeggTil = true;
                txtSubmit.setPromptText("Velg komponent. (ID)");
                btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int valgtkomponent;
                        try {
                            valgtkomponent = Integer.parseInt(txtSubmit.getText());
                        } catch (Exception e) {
                            labelError.setText("Vennligst skriv inn en gyldig ID");
                            valgtkomponent = -1;
                        }

                        if (valgtkomponent >= 0) {
                            bruker.getIndividuelleVarer().remove(valgtkomponent);
                            tableView.setItems(bruker.getIndividuelleVarer().getMainArray());
                            labelError.setText("En brukers komponent har blitt fjernet");
                            labelTotaleSum.setText("Totalpris: " + bruker.getIndividuellevarerSum());
                            save();
                        }
                    }
                });
            } else {
                showLeggTil = false;
                btnLeggTil.setText("Fjern en komponent");
                txtSubmit.setVisible(false);
                btnSubmit.setVisible(false);

            }
        }


    }

    @FXML
    void On_Click_BtnSpecs(ActionEvent event) {
        visSpesifikasjoner();
    }

    public void visSpesifikasjoner() {
        btnKvittering.setVisible(false);
        btnKjøp.setVisible(false);

        if (!showSpecs) {
            defualt(true);
            btnSubmit.setVisible(true);
            txtSubmit.setVisible(true);
            showSpecs = true;
            showKurv = false;
            showFullført = false;
            btnVisSpecs.setText("Tilbake");
            btnLeggTil.setText("Legg til komponent");
            btnVisKurv.setText("Vis handldekurv");


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
                        labelError.setText("En vare sin spesifikasjon har blitt visst");
                        labelViser.setText("Viser spesifikasjoner for varen");
                        tableView.getColumns().clear();

                        TableColumn<Spesifikasjon, String> spesifikasjonKolonne = new TableColumn<>("Spesifikasjon");
                        TableColumn<Spesifikasjon, String> spesifikasjonVerdiKolonne = new TableColumn<>("Verdi");
                        spesifikasjonKolonne.setCellValueFactory(new PropertyValueFactory<>("navn"));
                        spesifikasjonVerdiKolonne.setCellValueFactory(new PropertyValueFactory<>("verdi"));


                        ObservableList<Spesifikasjon> spesifikasjoner = FXCollections.observableArrayList();
                        ObservableList<Spesifikasjon> spesifikasjoner2 = FXCollections.observableArrayList();

                        for (int i = 0; i < komponenter.getMainArray().get(valgtKomponent).getSpecs().size(); i += 2) {
                            spesifikasjoner.add(new Spesifikasjon(komponenter.getMainArray().get(valgtKomponent)
                                    .getSpecs().get(i), i, komponenter.getMainArray()
                                    .get(valgtKomponent).getSpecs().get(i + 1)));
                        }

                        tableView.getColumns().addAll(spesifikasjonKolonne, spesifikasjonVerdiKolonne);
                        tableView.setItems(spesifikasjoner);

                        txtSøk.setText("");
                        txtSøk.setPromptText("Søk spesifikasjoner");

                        choice.setDisable(true);

                        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                Predicate<Spesifikasjon> Navn = Spesifikasjon -> {
                                    boolean sjekk = Spesifikasjon.getNavn().toLowerCase().indexOf(txtSøk.getText().toLowerCase()) != -1;
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
        } else {
            defualt(true);
            choice.setDisable(false);
            btnVisSpecs.setText("Vis spesifikasjoner");
            showSpecs = false;

            btnSubmit.setVisible(false);
            txtSubmit.setVisible(false);
        }
    }

    @FXML
    void On_Click_BtnTilbake(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MellomSide_Standardbruker.fxml"));
        Parent Standardbruker;
        boolean value = true;
        try {
            Standardbruker = loader.load();
        } catch (IOException e) {
            labelError.setText("Klarte ikke å bytte side");
            Standardbruker = null;
            value = false;
        }
        if (value) {
            MellomSide_Standardbruker_Controller controller = loader.getController();
            controller.setInfo(brukere, komponenter, bruker);
            Scene LoggInn = new Scene(Standardbruker);
            Stage Scene_3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_3.setScene(LoggInn);
            Scene_3.setHeight(750);
            Scene_3.setWidth(500);
            Scene_3.centerOnScreen();
            Scene_3.show();
        }
    }

    public void start(Standardbruker bruker, Register brukere, Komponenter komponenter) {
        this.bruker = bruker;
        this.brukere = brukere;
        this.komponenter = komponenter;

        labelTotaleSum.setText("Totalsum: " + bruker.getIndividuellevarerSum() + "Kr");

        ObservableList<String> choices = FXCollections.observableArrayList(FXCollections.observableArrayList(
                "Alle", "Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm", "Operativsystem"));

        choice.setItems(choices);

        labelTotaleSum.setText("Totalpris: " + bruker.getIndividuellevarerSum());

        defualt(true);
    }

    private void defualt(boolean view) {

        btnKvittering.setVisible(false);
        choice.setDisable(false);
        tableView.setEditable(true);
        btnKjøp.setVisible(false);
        txtSøk.setPromptText("Søk produktnavn");

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
                boolean sjekk = true;
                int antall;
                try {
                    antall = event.getNewValue();
                } catch (InvalidNumberException e) {
                    labelError.setText("Vennligst skriv inn gyldig antall");
                    sjekk = false;
                }
                try {
                    event.getRowValue().setAntall(event.getNewValue());
                } catch (InvalidNumberException e) {
                    labelError.setText(e.getMessage());
                    sjekk = false;
                }

                if (sjekk) {
                    save();
                    labelTotaleSum.setText("Totalpris: " + bruker.getIndividuellevarerSum());
                }

            }
        });
        tableView.getColumns().clear();
        tableView.getColumns().addAll(IDKolonne, navnKolonne, typeKolonne, prisKolonne, antallKolonne);

        if (view) {
            Komponenter k = new Komponenter();
            for (int i = 0; i < komponenter.getMainArray().size(); i++) {
                k.add(komponenter.getMainArray().get(i));
            }
            komponenter = k;
            tableView.setItems(komponenter.getMainArray());
        } else {
            tableView.setItems(bruker.getIndividuelleVarer().getMainArray());
        }

        choice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (choice.getValue().equals("Alle")) {
                    tableView.setItems(komponenter.getMainArray());
                } else {
                    Predicate<Komponent> type = Komponent -> {
                        boolean sjekk = Komponent.getNavn().toLowerCase().indexOf(txtSøk.getText().toLowerCase()) != -1;
                        return sjekk;
                    };
                    komponenter2.setMainArray(komponenter.getMainArray().stream().filter(type)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(komponenter2.getMainArray());

                }
                txtSøk.setVisible(true);
                søk(view);
            }
        });
    }

    public void søk(boolean view) {
        txtSøk.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Predicate<Komponent> Navn = Komponent -> {
                    if (!choice.getValue().equals("Alle")) {
                        boolean sjekk = Komponent.getNavn().toLowerCase().indexOf(txtSøk.getText().toLowerCase()) != -1
                                && choice.getValue().equals(Komponent.getType());
                        return sjekk;
                    }
                    boolean sjekk = Komponent.getNavn().toLowerCase().indexOf(txtSøk.getText().toLowerCase()) != -1;
                    return sjekk;
                };
                if (view) {
                    komponenter2.setMainArray(komponenter.getMainArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(komponenter2.getMainArray());
                } else {
                    bruker2.getIndividuelleVarer().setMainArray(bruker.getIndividuelleVarer().getMainArray().stream().filter(Navn)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    tableView.setItems(bruker2.getIndividuelleVarer().getMainArray());
                }
            }
        });
    }


    public void On_Click_BtnKjøp(ActionEvent event) {
        if (bruker.getIndividuelleVarer().getMainArray().size() > 0) {
            if (!showFullført) {
                showFullført = true;
                btnKvittering.setVisible(true);
                labelViser.setText("Ditt kjøp er fullført");
                btnKjøp.setText("Avbryt");
                btnKvittering.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        DirectoryChooser fc = new DirectoryChooser();
                        bruker.setAntallKjøp(bruker.getAntallKjøp() + 1);

                        File f = fc.showDialog(null);
                        if (f != null) {
                            Path path = Paths.get(f.getAbsolutePath() + "\\Kvittering(" + bruker.getAntallKjøp() + ").csv");
                            String s = f.getAbsolutePath();

                            FiledataTxt save = new FiledataTxt();
                            String brukerInfo = bruker.getBrukernavn() + ";" + bruker.getTlf() + ";" + bruker.getEmail() + "\n";
                            String komponenter = bruker.getIndividuelleVarer().toStringTxtMedAntall() + "\nTotalsum;" + bruker.getIndividuellevarerSum();
                            bruker.getIndividuelleVarer().getMainArray().clear();

                            try {
                                save.save(brukerInfo + komponenter, path);
                            } catch (IOException e) {
                                labelError.setText(e.getMessage());
                            }

                            save();
                            bruker.getIndividuelleVarer().getMainArray().clear();
                            labelError.setText("Kjøpet er fullført");
                            tableView.getItems().clear();
                        }

                    }
                });

            } else {
                showFullført = false;
                btnKvittering.setVisible(false);
                btnKjøp.setText("Fullfør kjøp");
            }
        } else {
            labelError.setText("Du må ha minst en vare for å foreta kjøpet");
        }
    }
}
