package komponenter;

import Brukere.InvalidNumberException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Komponent {
    private transient SimpleIntegerProperty ID;
    private transient SimpleIntegerProperty antall;
    private transient SimpleStringProperty navn;
    private transient SimpleDoubleProperty pris;
    private transient ObservableList<String> specs;
    private transient SimpleStringProperty type;

    public Komponent(String navn, double pris, String type, String... strings) {
        setNavn(navn);
        setPris(pris);
        setType(type);
        antall = new SimpleIntegerProperty(0);

        specs = FXCollections.observableArrayList();
        for (String s : strings) {
            specs.add(s);
        }
    }

    public String getNavn() {
        return navn.getValue();
    }

    public double getPris() {
        return pris.getValue();
    }

    public ObservableList<String> getSpecs() {
        return specs;
    }

    public String listSpecs() {
        String ut = "";
        for (String line : specs) {
            ut += line + "\n";
        }
        return ut;
    }

    public String getType() {
        return type.getValue();
    }

    public int getID() {
        return ID.getValue();
    }

    public int getAntall() {
        return antall.getValue();
    }

    public void setNavn(String navn) {
        this.navn = new SimpleStringProperty(navn);
    }

    public void setPris(double pris) {
        this.pris = new SimpleDoubleProperty(pris);
    }

    public void setSpecs(ObservableList<String> specs2) {
        specs = specs2;
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public void addSpec(String spec) {
        specs.add(spec);
    }

    public void addSpecs(String... strings) {
        for (String spec : strings) {
            specs.add(spec);
        }
    }

    public void setID(int ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public void setAntall(int antall) throws InvalidNumberException {
        if(antall > 0)
            this.antall.setValue(antall);
        else{
            throw new InvalidNumberException("Vennligst velg et antall over 0");
        }
    }

    public abstract String toString();

    public abstract String toStringTxt();
    public abstract String toStringTxtMedAntall();

    /*public abstract void writeObject(ObjectOutputStream stream) throws IOException;
    public abstract <T extends Komponent> T readObject(ObjectInputStream stream) throws IOException;*/
}
