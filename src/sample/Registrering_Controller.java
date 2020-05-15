package sample;

import Brukere.*;
import filbehandling.FiledataTxt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javax.swing.JOptionPane.showMessageDialog;

public class Registrering_Controller {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    @FXML
    private TextField txtTelefonnummer;

    @FXML
    private TextField txtEmail;

    @FXML
    private CheckBox chxAdmin;

    @FXML
    private CheckBox chxStandardbruker;

    @FXML
    private Label labelError;

    private Register brukere;
    private FiledataTxt lagreTxt;
    private final Path path = Paths.get("src/filbehandling/Brukerinfo.csv");

    private void save() {
        lagreTxt = new FiledataTxt();
        try {
            lagreTxt.save(brukere.toStringTxt(), path);
        } catch (IOException e) {
            labelError.setText("Klarte ikke å lagre data");
        }
    }

    public void initRegister(Register reg) {
        brukere = reg;
    }

    @FXML
    void onClick_btn_Avbryt(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/LoggInn.fxml"));
        Parent Avbryt_registrering;
        boolean value_1 = true;
        try {
            Avbryt_registrering = loader.load();
        } catch (IOException e) {
            labelError.setText("Klarte ikke å gå tilbake til LoggInn siden");
            Avbryt_registrering = null;
            value_1 = false;
        }
        if (value_1) {

            LoggInn_Controller controller = loader.getController();
            controller.setRegister(brukere);

            Scene Avbryt_Registrering = new Scene(Avbryt_registrering);
            Stage Scene_1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene_1.setScene(Avbryt_Registrering);
            Scene_1.setHeight(750);
            Scene_1.setWidth(500);
            Scene_1.centerOnScreen();
            Scene_1.show();

        }


    }

    @FXML
    void onClick_btn_Register(ActionEvent event) {
        if (brukere != null) {
            boolean eksisterer = false;
            for (int i = 0; i < brukere.getArray().size(); i++) {
                if (brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText()) &&
                        brukere.getArray().get(i).getPassord().equals(txtPassord.getText())) {
                    eksisterer = true;
                    i = brukere.getArray().size();
                    labelError.setText("Brukernavn og passordet er tatt\nvelg et annet");

                } else if (brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText()) &&
                        !(brukere.getArray().get(i).getPassord().equals(txtPassord.getText()))) {
                    labelError.setText("Brukernavn er tatt\nvelg et annet");
                    i = brukere.getArray().size();
                    eksisterer = true;
                } else if (brukere.getArray().get(i).getPassord().equals(txtPassord.getText()) &&
                        !(brukere.getArray().get(i).getBrukernavn().equals(txtBrukernavn.getText()))) {
                    labelError.setText("Passordet er tatt\nvelg et annet");
                    i = brukere.getArray().size();
                    eksisterer = true;
                }
            }
            if (!eksisterer) {
                boolean sjekk = true;
                Bruker b;
                if (chxAdmin.isSelected() && !chxStandardbruker.isSelected()) {
                    b = new Superbruker();
                    b.setBrukernavn(txtBrukernavn.getText());
                    b.setPassord(txtPassord.getText());
                    try {
                        b.setTlf(txtTelefonnummer.getText());
                    } catch (InvalidStringException e) {
                        txtTelefonnummer.setText("");
                        txtTelefonnummer.setPromptText(e.getMessage());
                        sjekk = false;
                    }
                    try {
                        b.setEmail(txtEmail.getText());
                    } catch (InvalidStringException e) {
                        txtEmail.setText("");
                        txtEmail.setPromptText(e.getMessage());
                        sjekk = false;
                    }

                    if (sjekk) {

                        brukere.add(b);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/FXML/MellomSide.fxml"));
                        Parent Registering_ny_Admin;
                        boolean lasteinn = true;
                        try {
                            Registering_ny_Admin = loader.load();
                        } catch (IOException e) {
                            labelError.setText("Klarte ikke å bytte side");
                            Registering_ny_Admin = null;
                            lasteinn = false;
                        }

                        if (lasteinn) {
                            MellomSide_Controller controller = loader.getController();
                            controller.initRegister(brukere);

                            Scene MellomSide = new Scene(Registering_ny_Admin);
                            Stage Scene_9 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene_9.setScene(MellomSide);
                            Scene_9.setHeight(750);
                            Scene_9.setWidth(500);
                            Scene_9.centerOnScreen();
                            Scene_9.show();

                            save();
                        }
                    }

                } else if (chxStandardbruker.isSelected() && !chxAdmin.isSelected()) {
                    b = new Standardbruker();
                    b.setBrukernavn(txtBrukernavn.getText());
                    b.setPassord(txtPassord.getText());
                    try {
                        b.setTlf(txtTelefonnummer.getText());
                    } catch (InvalidStringException e) {
                        txtTelefonnummer.setText("");
                        txtTelefonnummer.setPromptText(e.getMessage());
                        sjekk = false;
                    }
                    try {
                        b.setEmail(txtEmail.getText());
                    } catch (InvalidStringException e) {
                        txtEmail.setText("");
                        txtEmail.setPromptText(e.getMessage());
                        sjekk = false;
                    }

                    if (sjekk) {
                        brukere.add(b);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/FXML/MellomSide.fxml"));
                        Parent Registering_ny_Standarbruker;
                        boolean lasteinn = true;
                        try {
                            Registering_ny_Standarbruker = loader.load();
                        } catch (IOException e) {
                            labelError.setText("Klarte ikke å bytte side");
                            Registering_ny_Standarbruker = null;
                            lasteinn = false;
                        }

                        if (lasteinn) {
                            MellomSide_Controller controller = loader.getController();
                            controller.initRegister(brukere);

                            Scene MellomSide = new Scene(Registering_ny_Standarbruker);
                            Stage Scene_10 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene_10.setScene(MellomSide);
                            Scene_10.setHeight(750);
                            Scene_10.setWidth(500);
                            Scene_10.centerOnScreen();
                            Scene_10.show();
                            save();
                        }
                    }

                } else if (chxStandardbruker.isSelected() && chxAdmin.isSelected() || !chxStandardbruker.isSelected() && !chxAdmin.isSelected()) {
                    labelError.setText("Vennligst registrer brukerinformasjonen din og kryss av på en av boksene.");
                }
            }
        } else if (brukere == null) {
            labelError.setText("Registeret er ikke initialisert!");
        }
    }
}

