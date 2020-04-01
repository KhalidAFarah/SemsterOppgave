package komponenter;

public class Skjermkort extends Komponent {


    public Skjermkort(String navn, double pris, String type, String... strings){
        super(navn, pris, type, strings);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toStringTxt() {
        String s = "";// + getSpecs().get(0);
        for(int i = 0; i < getSpecs().size(); i++){
            s += ":" + getSpecs().get(i);
        }
        return getNavn() + ";"  + getPris() + ";" + getType() + ";" + s + ";";
    }

    @Override
    public String toStringCsv() {
        String ut = getNavn() + ";"  + getPris() + ";" + getType() + ";";
        for(int i = 0; i < getSpecs().size(); i++){
            ut += getSpecs().get(i) + ";";
        }
        return ut;
    }
}
