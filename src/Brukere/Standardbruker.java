package Brukere;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import komponenter.*;

public class Standardbruker extends Bruker {
    private final SimpleDoubleProperty sum;
    private Komponenter handlekurv;
    private static final SimpleBooleanProperty ADMIN = new SimpleBooleanProperty(false);

    public Standardbruker() {
        sum = new SimpleDoubleProperty(0);
        handlekurv = new Komponenter();
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

    public Komponenter getHandlekurv() {
        return handlekurv;
    }

    public String getTlf() {
        return super.getTlf();
    }

    public boolean isAdmin() {
        return ADMIN.getValue();
    }

    public double getSum(){
        return sum.getValue();
    }

    public void setBrukernavn(String brukernavn) {
        super.setBrukernavn(brukernavn);
    }

    public void setPassord(String passord) {
        super.setPassord(passord);
    }

    public void setEmail(String email) throws InvalidStringException {
        super.setEmail(email);
    }

    public void setHandlekurv(Komponenter handlekurv) {
        this.handlekurv = handlekurv;
    }

    public void setTlf(String tlf) throws InvalidStringException {
        super.setTlf(tlf);
    }


    public void setSum() {
        this.sum.setValue(0);
        double sum2 = 0;
        for (int i = 0; i < handlekurv.getMainArray().size(); i++) {
            sum2 += handlekurv.getMainArray().get(i).getPris();
        }
        this.sum.setValue(sum2);
    }

    public <T extends Komponent> void leggTilHandlekurv(T elem) {
        boolean sjekk = true;
        //tester om det samme komponent type eksisterer dersom den finnes fjernes den gamle og legger til den nye
        for (int i = 0; i < handlekurv.getMainArray().size(); i++) {
            if (handlekurv.getMainArray().get(i).getType().equals(elem.getType())) {
                handlekurv.remove(i);
                handlekurv.add(elem);
                sjekk = false;

                setSum();//ny sum av all varer
            }
        }
        if (sjekk) {
            handlekurv.add(elem);

            setSum();//ny sum av alle varer
        }
    }

    public String toStringFormat() {
        return super.toStringFormat() + isAdmin() + ";" + sum.getValue() + ";" + handlekurv.getMainArray().size() + ";" + handlekurv.toStringTxt();
    }
}
