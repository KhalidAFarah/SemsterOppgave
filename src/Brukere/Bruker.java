package Brukere;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Bruker {
    private final SimpleIntegerProperty ID;
    private final SimpleStringProperty brukernavn;
    private final SimpleStringProperty passord;
    private final SimpleStringProperty email;
    private final SimpleStringProperty tlf;

    public Bruker() {
        ID = new SimpleIntegerProperty();
        brukernavn = new SimpleStringProperty();
        passord = new SimpleStringProperty();
        email = new SimpleStringProperty();
        tlf = new SimpleStringProperty();
    }

    public String getBrukernavn() {
        return brukernavn.getValue();
    }

    public String getPassord() {
        return passord.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getTlf() {
        return tlf.getValue();
    }

    public boolean isAdmin() {
        return false;
    }

    public int getID() {
        return ID.getValue();
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn.setValue(brukernavn);
    }

    public void setPassord(String passord) {
        this.passord.setValue(passord);
    }

    public void setEmail(String email) throws InvalidStringException {
        if (Validering.Email(email)) {
            this.email.setValue(email);
        } else {
            throw new InvalidStringException("Ugyldig epost");
        }

    }


    public void setTlf(String tlf) throws InvalidStringException {
        if (Validering.tlf(tlf)) {
            this.tlf.setValue(tlf);
        } else {
            throw new InvalidStringException("ugyldig telefonnummer");
        }

    }

    public void setID(int ID) {
        this.ID.setValue(ID);
    }


    public String toStringFormat() {
        return getBrukernavn() + ";" + getPassord() + ";" + getEmail() + ";" + getTlf() + ";";
    }
}
