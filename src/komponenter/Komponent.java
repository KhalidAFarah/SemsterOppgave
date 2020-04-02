package komponenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Komponent {
    private String navn;
    private double pris;
    private List<String> specs;
    private String type;

    public Komponent(String navn, double pris, String type, String... strings){
        this.navn = navn;
        this.pris = pris;
        this.type = type;

        specs = new ArrayList<>();
        for(String s: strings){
            specs.add(s);
        }
    }

    public String getNavn(){
        return navn;
    }
    public double getPris(){
        return pris;
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
        return type;
    }

    public void setNavn(String navn){
        this.navn = navn;
    }
    public void setPris(double pris){
        this.pris = pris;
    }
    public void setSpecs(ArrayList<String> specs2){
        specs = specs2;
    }
    public void setType(String type){
        this.type = type;
    }
    public void addSpec(String spec){
        specs.add(spec);
    }
    public void addSpecs(String... strings){
        for(String spec : strings){
            specs.add(spec);
        }
    }

    public abstract String toString();
    public abstract String toStringTxt();

    /*public abstract void writeObject(ObjectOutputStream stream) throws IOException;
    public abstract <T extends Komponent> T readObject(ObjectInputStream stream) throws IOException;*/
}
