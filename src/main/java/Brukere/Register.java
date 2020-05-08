package Brukere;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Register {
    private ObservableList<Bruker> brukere;

    public Register() {
        brukere = FXCollections.observableArrayList();
    }

    public ObservableList<Bruker> getArray() {
        return brukere;
    }

    public boolean add(Bruker bruker) {
        String br = bruker.getBrukernavn();
        String ps = bruker.getPassord();
        for (int i = 0; i < brukere.size(); i++) {
            String br2 = brukere.get(i).getBrukernavn();
            String ps2 = brukere.get(i).getPassord();
            if (br2.equals(br) &&
                    ps2.equals(ps)) {
                return false;
            }
        }
        bruker.setID(brukere.size());
        brukere.add(bruker);
        return true;
    }

    public void remove(int index) {
        brukere.remove(index);
        for (int i = 0; i < brukere.size(); i++) {
            brukere.get(i).setID(i);
        }
    }

    public void setArray(ObservableList<Bruker> brukere) {
        this.brukere = brukere;
    }

    public String toStringTxt() {
        String ut = "";

        for (int i = 0; i < brukere.size(); i++) {
            ut += brukere.get(i).toStringFormat() + "\n";
        }

        return ut;
    }
}
