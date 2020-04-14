package Brukere;

import java.util.ArrayList;

public class Register {
    private ArrayList<Bruker> brukere;

    public Register() {
        brukere = new ArrayList<>();
    }

    public ArrayList<Bruker> getArray() {
        return brukere;
    }

    public boolean add(Bruker bruker) {
        brukere.add(bruker);
        for (int i = 0; i < brukere.size(); i++) {
            if (brukere.get(i).getBrukernavn().equals(bruker.getBrukernavn()) &&
                    brukere.get(i).getPassord().equals(bruker.getPassord())) {
                brukere.add(bruker);
                return true;
            }
        }
        return false;
    }

    public void setArray(ArrayList<Bruker> brukere) {
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
