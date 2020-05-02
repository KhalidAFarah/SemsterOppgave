package komponenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Komponenter {//lager en main liste for alle typer komponenter

    private static final int TYPER = 7;
    private static final Komponent[] typer2 = {new Prosessor("", 0, ""),
            new Skjermkort("", 0, ""), new Minne("", 0, ""),
            new Harddisk("", 0, ""), new Tastatur("", 0, ""),
            new Mus("", 0, ""), new Skjerm("", 0, "")};


    //lag ny arraylist som sorteres etter komponent type med add<T> og get<T> som leter for typer[i-1] og teller dem
    //add metoden sorterer dem hver gang
    ObservableList<Komponent> main = FXCollections.observableArrayList();

    public static Komponent[] getTyper2() {
        return typer2;
    }

    public ObservableList<Komponent> getMainArray() {
        return main;
    }

    public void setMainArray(ObservableList<Komponent> elems) {
        main = elems;
    }


    public String toStringTxt() {
        String ut = "";
        for (int i = 0; i < main.size(); i++) {
            ut += "\n" + main.get(i).toStringTxt();
        }
        return ut;
    }

    public void sort() {
        ObservableList<Komponent> newMain = FXCollections.observableArrayList();
        for (int i = 0; i < TYPER; i++) {
            for (int j = 0; j < main.size(); j++) {
                if (typer2[i].getClass().equals(main.get(j).getClass())) {
                    main.get(j).setID(newMain.size());
                    newMain.add(main.get(j));
                }
            }
        }
        main = newMain;
    }

    public <T extends Komponent> boolean add(T elem) {
        boolean sjekk = false;
        for (int i = 0; i < typer2.length; i++) {
            if (typer2[i].getClass().equals(elem.getClass())) {
                sjekk = true;
            }
        }

        if (sjekk) {
            main.add(elem);
            sort();
        }
        return sjekk;
    }

    public void remove(int index) {
        main.remove(index);
        sort();
    }

    public <T extends Komponent> Komponent getFromMain(int index) {
        /*boolean sjekk = false;
        for(int i = 0; i < typer2.length; i++){
            if(typer2[i].getClass().equals(T.getClass())){
                sjekk = true;
            }
        }*/
        return main.get(index);
    }


    public void writeObject(ObjectOutputStream stream) throws IOException {
        for (int i = 0; i < main.size(); i++) {
            stream.writeUTF(main.get(i).getNavn());
            stream.writeDouble(main.get(i).getPris());
            stream.writeUTF(main.get(i).getType());

            String str = "";
            for (String s : main.get(i).getSpecs()) {
                str += s + ":";
            }
            stream.writeUTF(str);

            stream.writeBoolean(i != main.size() - 1);
        }
    }

    public void readObject(ObjectInputStream stream) throws Exception {
        boolean fortsett = true;
        while (fortsett) {
            String navn = stream.readUTF();
            double pris = stream.readDouble();
            String type = stream.readUTF();
            String string = stream.readUTF();
            String[] strings = string.split(":");

            if (type.equals("Prosessor")) {
                add(new Prosessor(navn, pris, type, strings));
            } else if (type.equals("Skjermkort")) {
                add(new Skjermkort(navn, pris, type, strings));
            } else if (type.equals("Minne")) {
                add(new Minne(navn, pris, type, strings));
            } else if (type.equals("Harddisk")) {
                add(new Harddisk(navn, pris, type, strings));
            } else if (type.equals("Tastatur")) {
                add(new Tastatur(navn, pris, type, strings));
            } else if (type.equals("Mus")) {
                add(new Mus(navn, pris, type, strings));
            } else if (type.equals("Skjerm")) {
                add(new Skjerm(navn, pris, type, strings));
            } else {
                throw new Exception("Klarte ikke å laste inn data komponent typen eksisterer ikke i registeret");
            }
            fortsett = stream.readBoolean();
        }
    }
}
