package sample;

import Brukere.Register;
import Brukere.Standardbruker;
import filbehandling.FiledataJOBJ;
import filbehandling.FiledataTxt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import komponenter.Komponent;
import komponenter.Komponenter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Standardbruker_FerdigByggetPc_Controller {

    @FXML
    private ScrollPane pane;

    @FXML
    private Label labelError;

    private Standardbruker bruker;

    private Komponenter komponenter = new Komponenter();

    private Register brukere;
    private FiledataTxt lagreTxt;
    private final Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

    private void save() {
        lagreTxt = new FiledataTxt();
        try {
            lagreTxt.save(brukere.toStringTxt(), path);
        } catch (IOException e) {
            labelError.setText(e.getMessage());
        }
    }

    public void initBruker(Standardbruker bruker, Register brukere, Komponenter komponenter) {
        this.bruker = bruker;
        this.brukere = brukere;
        this.komponenter = komponenter;

        visVarer("alle varer");
    }

    public void loadKomponenter() {
        FiledataJOBJ filedata = new FiledataJOBJ();
        Path path = Paths.get("src/filbehandling/LagredeKomponenter.JOBJ");

        try {
            filedata.load(komponenter, path);
        } catch (Exception e) {
            labelError.setText(e.getMessage());
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

    private void visVarer(String type) {
        AnchorPane APane = new AnchorPane();
        int y = 50;
        ScrollBar sb = new ScrollBar();
        sb.setLayoutX(50);
        pane.setContent(APane);
        Label labelViser = new Label("Viser " + type.toLowerCase());
        labelViser.setLayoutY(10);
        labelViser.setLayoutX(200);
        labelViser.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
        APane.getChildren().add(labelViser);


        if (komponenter != null || bruker != null) {

            for (int i = 0; i < komponenter.getMainArray().size(); i++) {

                if (komponenter.getMainArray().get(i).getType().equals(type) || type.equals("alle varer")) {

                    Label labelNavn = new Label(komponenter.getMainArray().get(i).getNavn());
                    labelNavn.setLayoutY(y);
                    labelNavn.setLayoutX(10);

                    labelNavn.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
                    Label labelPris = new Label(komponenter.getMainArray().get(i).getPris() + " Kr");
                    labelPris.setLayoutY(y);
                    labelPris.setLayoutX(650);
                    labelPris.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
                    Button btnVelg = new Button("Velg");
                    btnVelg.setLayoutY(y + 30);
                    btnVelg.setLayoutX(10);


                    Button btnVisMer = new Button("Vis spesifikasjoner");
                    btnVisMer.setLayoutY(y + 30);

                    btnVisMer.setLayoutX(85);

                    y += 100;

                    btnVelg.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for (int j = 0; j < komponenter.getMainArray().size(); j++) {
                                if (komponenter.getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                    bruker.leggTilHandlekurv(komponenter.getMainArray().get(j));
                                    save();
                                    labelError.setText("En vare har blitt lagt inn i handlekurven");
                                }
                            }
                        }
                    });

                    btnVisMer.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for (int j = 0; j < komponenter.getMainArray().size(); j++) {
                                if (komponenter.getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                    String spesifikasjonerHeader = komponenter.getMainArray().get(j).getNavn() + "\nPris "
                                            + komponenter.getMainArray().get(j).getPris();
                                    String spesifikasjonerText = "";
                                    for (int i = 0; i < komponenter.getMainArray().get(j).getSpecs().size(); i += 2) {
                                        spesifikasjonerText += komponenter.getMainArray().get(j).getSpecs().get(i)
                                                + ": " + komponenter.getMainArray().get(j).getSpecs().get(i + 1) + "\n";
                                    }

                                    Label labelInfoHeader = new Label(spesifikasjonerHeader);
                                    Label labelInfoText = new Label(spesifikasjonerText);

                                    APane.getChildren().clear();
                                    APane.getChildren().add(labelInfoHeader);
                                    labelInfoText.setLayoutY(110);
                                    labelInfoText.setLayoutX(10);
                                    labelInfoHeader.setStyle("-fx-font-size: 20");
                                    labelInfoHeader.setLayoutY(10);
                                    labelInfoHeader.setLayoutX(10);
                                    APane.getChildren().add(labelInfoText);

                                    Button btnHide = new Button("Skjul spesifikasjoner");

                                    btnHide.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            visVarer(type);
                                        }
                                    });
                                    btnHide.setLayoutY(70);
                                    btnHide.setLayoutX(25);
                                    APane.getChildren().add(btnHide);


                                }
                            }
                        }
                    });


                    APane.getChildren().add(labelNavn);
                    APane.getChildren().add(labelPris);
                    APane.getChildren().add(btnVelg);
                    APane.getChildren().add(btnVisMer);
                }
            }
        } else if (komponenter == null || bruker == null) {
            labelError.setText("Klarte ikke å laste inn komponenter eller brukeren");
        }
    }

    private void updateVarer() {
        labelError.setText("Du må velge en vare fra hver komponent type for å kunne kjøpe pc-en");
        AnchorPane APane = new AnchorPane();
        pane.setContent(APane);
        int y = 10;
        if (bruker != null || komponenter != null) {
            Label labelTotalPris = new Label("Totalprisen er " + bruker.getSum() + " kr.");

            Label labelMangler = new Label();
            Button kvittering = new Button("Kjøp ferdig konfigurert pc");
            kvittering.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (bruker.getHandlekurv().getMainArray().size() == 8) {

                        APane.getChildren().clear();
                        Label labelHeader = new Label("Du har kjøpt følgende varer:");

                        Label labelText = new Label();
                        Label labelPris = new Label();
                        Label labelTot = new Label();


                        String s = "";
                        String p = "";
                        int y = 10;
                        for (Komponent k : bruker.getHandlekurv().getMainArray()) {
                            s += k.getNavn() + "\n\n";
                            p += k.getPris() + "\n\n";
                            y += 80;
                        }
                        bruker.setSum();
                        labelTot.setText("Totalpris: " + bruker.getSum());
                        y += 60;
                        labelText.setText(s);
                        labelPris.setText(p);


                        Button avbryt = new Button("Avbryt");
                        avbryt.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                APane.getChildren().clear();
                                updateVarer();
                            }
                        });
                        Button skrivUt = new Button("Skriv ut kvittering");
                        skrivUt.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                DirectoryChooser fc = new DirectoryChooser();
                                bruker.setAntallKjøp(bruker.getAntallKjøp() + 1);

                                File f = fc.showDialog(null);
                                if (f != null) {
                                    Path path = Paths.get(f.getAbsolutePath() + "\\Kvittering(" + bruker.getAntallKjøp() + ").csv");

                                    String s = f.getAbsolutePath();


                                    FiledataTxt save = new FiledataTxt();
                                    bruker.setSum();
                                    String brukerInfo = bruker.getBrukernavn() + ";" + bruker.getTlf() + ";" + bruker.getEmail() + "\n";
                                    String komponenter = bruker.getHandlekurv().toStringTxt() + "\nTotalsum;" + bruker.getSum();
                                    bruker.getHandlekurv().getMainArray().clear();
                                    labelHeader.setText("Kjøpet er fullført");
                                    labelText.setText("");
                                    labelPris.setText("");
                                    labelTotalPris.setText("");
                                    labelTot.setText("");


                                    try {
                                        save.save(brukerInfo + komponenter, path);
                                    } catch (IOException e) {
                                        labelError.setText(e.getMessage());
                                    }
                                    save();
                                    skrivUt.setVisible(false);
                                    avbryt.setVisible(false);
                                    Button nyttKjøp = new Button("Foreta et nytt kjøp");
                                    nyttKjøp.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            visVarer("alle varer");
                                        }
                                    });
                                    APane.getChildren().add(nyttKjøp);
                                    nyttKjøp.setLayoutY(250);
                                    nyttKjøp.setLayoutX(100);
                                }


                            }
                        });

                        APane.getChildren().add(labelHeader);
                        APane.getChildren().add(labelText);
                        APane.getChildren().add(labelTot);
                        APane.getChildren().add(labelPris);
                        APane.getChildren().add(skrivUt);
                        APane.getChildren().add(avbryt);

                        labelHeader.setLayoutY(10);
                        labelHeader.setLayoutX(10);
                        labelHeader.setStyle("-fx-font-size: 20;  -fx-font-family: Verdana");

                        labelText.setLayoutY(80);
                        labelText.setLayoutX(10);
                        labelText.setStyle("-fx-font-size: 16; -fx-font-family: Verdana");

                        labelTot.setLayoutY(420);
                        labelTot.setLayoutX(250);
                        labelTot.setStyle("-fx-font-size: 16; -fx-font-family: Verdana;-fx-font-weight: bold;");

                        labelPris.setLayoutY(68);
                        labelPris.setLayoutX(500);
                        labelPris.setStyle("-fx-font-size: 16; -fx-text-alignment: right;  -fx-font-family: Verdana;");

                        labelHeader.setPrefWidth(pane.getPrefWidth());
                        labelText.setPrefWidth(pane.getPrefWidth());

                        skrivUt.setLayoutY(y - 200);
                        skrivUt.setLayoutX(100);
                        avbryt.setLayoutY(y - 200);
                        avbryt.setLayoutX(10);
                    } else {
                        labelError.setText("Du har ennå ikke valgt en type av hver komponent og " + "\n" + " kan derfor ikke fullføre kjøpet!");

                    }
                }
            });
            for (int i = 0; i < bruker.getHandlekurv().getMainArray().size(); i++) {
                Label labelNavn = new Label(bruker.getHandlekurv().getMainArray().get(i).getNavn());
                labelNavn.setLayoutY(y);
                labelNavn.setLayoutX(10);
                Label labelPris = new Label(bruker.getHandlekurv().getMainArray().get(i).getPris() + " kr");
                labelPris.setLayoutY(y);
                labelPris.setLayoutX(650);
                labelPris.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
                labelNavn.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");

                Button btnFjern = new Button("Fjern");
                btnFjern.setLayoutY(y + 30);
                btnFjern.setLayoutX(10);
                Button btnVisMer = new Button("Vis spesifikasjoner");
                btnVisMer.setLayoutY(y + 30);
                btnVisMer.setLayoutX(80);


                y += 100;

                btnFjern.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < bruker.getHandlekurv().getMainArray().size(); j++) {
                            if (bruker.getHandlekurv().getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                bruker.getHandlekurv().remove(j);
                            }
                        }
                        bruker.setSum();
                        updateVarer();
                        save();
                        labelError.setText("En vare har blitt fjernet fra handlekurven.");
                    }
                });
                btnVisMer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int j = 0; j < bruker.getHandlekurv().getMainArray().size(); j++) {
                            if (bruker.getHandlekurv().getMainArray().get(j).getNavn().equals(labelNavn.getText())) {
                                String spesifikasjonerHeader = bruker.getHandlekurv().getMainArray().get(j).getNavn() + "\nPris "
                                        + bruker.getHandlekurv().getMainArray().get(j).getPris();
                                String spesifikasjonerText = "";
                                for (int i = 0; i < komponenter.getMainArray().get(j).getSpecs().size(); i += 2) {
                                    spesifikasjonerText += komponenter.getMainArray().get(j).getSpecs().get(i)
                                            + ": " + komponenter.getMainArray().get(j).getSpecs().get(i + 1) + "\n";
                                }


                                Label labelInfoHeader = new Label(spesifikasjonerHeader);
                                Label labelInfoText = new Label(spesifikasjonerText);

                                APane.getChildren().clear();
                                APane.getChildren().add(labelInfoHeader);
                                labelInfoText.setLayoutY(110);
                                labelInfoText.setLayoutX(10);
                                labelInfoHeader.setStyle("-fx-font-size: 20");
                                labelInfoHeader.setLayoutY(10);
                                labelInfoHeader.setLayoutX(10);
                                APane.getChildren().add(labelInfoText);

                                Button btnHide = new Button("Skjul spesifikasjoner");

                                btnHide.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        updateVarer();
                                    }
                                });
                                btnHide.setLayoutY(70);
                                btnHide.setLayoutX(25);
                                APane.getChildren().add(btnHide);


                            }
                        }
                    }
                });

                APane.getChildren().add(labelNavn);
                APane.getChildren().add(btnFjern);
                APane.getChildren().add(labelPris);
                APane.getChildren().add(btnVisMer);

                labelNavn.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
                labelPris.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");

            }
            labelTotalPris.setLayoutY(y + 10);
            labelTotalPris.setLayoutX(10);
            labelTotalPris.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
            APane.getChildren().add(labelTotalPris);

            String typer = "";
            boolean first = false;
            for (int j = 0; j < Komponenter.getTyper().length; j++) {
                boolean funnet2 = false;
                for (int i = 0; i < bruker.getHandlekurv().getMainArray().size(); i++) {
                    if (bruker.getHandlekurv().getMainArray().get(i).getType().equals(Komponenter
                            .getTyper()[j])) {
                        funnet2 = true;
                        i = bruker.getHandlekurv().getMainArray().size();
                    }
                }

                if (funnet2 == false && first == false) {
                    System.out.println("funker");
                    typer = "Du mangler " + bruker.getHandlekurv().getMainArray().size() + " ut av de 8 følgende typer komponenter:\n";
                    typer += "En " + Komponenter.getTyper()[j];
                    first = true;
                    y += 60;
                } else if (funnet2 == false && first == true) {
                    typer += ", " + Komponenter.getTyper()[j];
                }
            }


            labelMangler.setText(typer);

            labelMangler.setLayoutY(y);
            labelMangler.setLayoutX(10);
            labelMangler.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-font-family: Verdana");
            APane.getChildren().add(labelMangler);

            kvittering.setLayoutY(y + 60);
            kvittering.setLayoutX(200);
            kvittering.setStyle("-fx-padding: 10");
            APane.getChildren().add(kvittering);

        } else if (bruker == null || komponenter == null) {
            labelError.setText("Klarte ikke å laste inn brukeren eller komponenter");
        }
    }

    @FXML
    void On_Click_BtnKurv(ActionEvent event) {
        if (bruker.getHandlekurv().getMainArray().size() > 0) {
            labelError.setText("");
            updateVarer();
        } else {
            labelError.setText("Handlekurven din er tom, \nLegg til varer for å se handlekurven");
        }

    }

    @FXML
    void On_Click_Btn_Grafikkort(ActionEvent event) {
        visVarer("Skjermkort");
    }

    @FXML
    void On_Click_Btn_Harddisk(ActionEvent event) {
        visVarer("Harddisk");
    }

    @FXML
    void On_Click_Btn_Minnebrikke(ActionEvent event) {
        visVarer("Minne");
    }

    @FXML
    void On_Click_Btn_Mus(ActionEvent event) {
        visVarer("Mus");
    }

    @FXML
    void On_Click_Btn_Prosessor(ActionEvent event) {
        visVarer("Prosessor");
    }

    @FXML
    void On_Click_Btn_Skjerm(ActionEvent event) {
        visVarer("Skjerm");
    }

    @FXML
    void On_Click_Btn_Tastatur(ActionEvent event) {
        visVarer("Tastatur");
    }

    @FXML
    void On_Click_Btn_Operativsystem(ActionEvent event) {
        visVarer("Operativsystem");
    }
}
