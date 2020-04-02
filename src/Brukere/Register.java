package Brukere;

import java.util.ArrayList;

public class Register {
    private ArrayList<Bruker> brukere;

    public Register(){
        brukere = new ArrayList<>();
    }

    public ArrayList<Bruker> getArray(){return brukere;}
    public void add(Bruker bruker){brukere.add(bruker);}
    public void setArray(ArrayList<Bruker> brukere){this.brukere = brukere;}

    public String toStringTxt(){
        String ut = "";

        for(int i = 0; i < brukere.size(); i++){
            ut += brukere.get(i).toStringFormat();
        }

        return ut;
    }
}
