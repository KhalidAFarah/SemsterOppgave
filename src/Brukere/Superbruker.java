package Brukere;

import javafx.beans.property.SimpleBooleanProperty;

public class Superbruker extends Bruker {
    private static final SimpleBooleanProperty ADMIN = new SimpleBooleanProperty(true);


    public Superbruker() {

    }

    public String getBrukernavn() {
        return super.getBrukernavn();
    }

    public String getPassord() {
        return super.getPassord();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public String getTlf() {
        return super.getTlf();
    }

    public boolean isAdmin() {
        return ADMIN.getValue();
    }

    public void setBrukernavn(String brukernavn) {
        super.setBrukernavn(brukernavn);
    }

    public void setPassord(String passord) {
        super.setPassord(passord);
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public void setTlf(String tlf) {
        super.setTlf(tlf);
    }

    public String toStringFormat() {
        return super.toStringFormat() + isAdmin() + ";";
    }

}
