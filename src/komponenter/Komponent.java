package komponenter;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Komponent {
    private transient SimpleIntegerProperty ID;
    private transient SimpleStringProperty navn;
    private transient SimpleDoubleProperty pris;
    private transient List<String> specs;
    private transient SimpleStringProperty type;

    public Komponent(String navn, double pris, String type, String... strings){
        setNavn(navn);
        setPris(pris);
        setType(type);

        specs = new ArrayList<>();
        for(String s: strings){
            specs.add(s);
        }
    }

    public String getNavn(){
        return navn.getValue();
    }
    public double getPris(){
        return pris.getValue();
    }
    public List<String> getSpecs(){
        return specs;
    }
    public String listSpecs(){
        String ut = "";
        for(String line : specs){
            ut += line + "\n";
        }
        return ut;
    }
    public String getType(){
        return type.getValue();
    }
    public int getID(){return ID.getValue();}

    public void setNavn(String navn){
        this.navn = new SimpleStringProperty(navn);
    }
    public void setPris(double pris){
        this.pris = new SimpleDoubleProperty(pris);
    }
    public void setSpecs(ArrayList<String> specs2){
        specs = specs2;
    }
    public void setType(String type){
        this.type = new SimpleStringProperty(type);
    }
    public void addSpec(String spec){
        specs.add(spec);
    }
    public void addSpecs(String... strings){
        for(String spec : strings){
            specs.add(spec);
        }
    }
    public void setID(int ID){this.ID = new SimpleIntegerProperty(ID);}

    public abstract String toString();
    public abstract String toStringTxt();

    /*public abstract void writeObject(ObjectOutputStream stream) throws IOException;
    public abstract <T extends Komponent> T readObject(ObjectInputStream stream) throws IOException;*/
}
