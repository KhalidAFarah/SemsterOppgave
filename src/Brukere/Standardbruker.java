package Brukere;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import komponenter.*;

public class Standardbruker extends Bruker {
    private SimpleDoubleProperty sum;
    private Komponenter handelskurv;
    private static final SimpleBooleanProperty ADMIN = new SimpleBooleanProperty(false);

    public Standardbruker() {
        sum = new SimpleDoubleProperty(0);
        handelskurv = new Komponenter();
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

    public Komponenter getHandelskurv() {
        return handelskurv;
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

    public void setHandelskurv(Komponenter handelskurv) {
        this.handelskurv = handelskurv;
    }

    public void setTlf(String tlf) {
        super.setTlf(tlf);
    }


    public void setSum() {
        this.sum.setValue(0);
        double sum2 = 0;
        for (int i = 0; i < handelskurv.getMainArray().size(); i++) {
            sum2 += handelskurv.getMainArray().get(i).getPris();
        }
        this.sum.setValue(sum2);
    }

    public <T extends Komponent> void leggTilHandlekurv(T elem) {
        boolean sjekk = true;
        //tester om det samme komponent type eksisterer dersom den finnes fjernes den gamle og legger til den nye
        for (int i = 0; i < handelskurv.getMainArray().size(); i++) {
            if (handelskurv.getMainArray().get(i).getType().equals(elem.getType())) {
                handelskurv.remove(i);
                handelskurv.add(elem);
                sjekk = false;

                setSum();//ny sum av all varer
            }
        }
        if (sjekk) {
            handelskurv.add(elem);

            setSum();//ny sum av alle varer
        }
    }

    public String toStringFormat() {
        return super.toStringFormat() + isAdmin() + ";" + sum.getValue() + ";" + handelskurv.getMainArray().size() + ";" + handelskurv.toStringTxt();
    }
}
