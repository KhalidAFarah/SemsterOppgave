package komponenter;

public class Mus extends Komponent {


    public Mus(String navn, double pris, String type, String... strings){
        super(navn, pris, type, strings);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toStringTxt() {
        String ut = getNavn() + ";"  + getPris() + ";" + getType() + ";";
        for(int i = 0; i < getSpecs().size(); i++){
            ut += getSpecs().get(i) + ";";
        }
        return ut;
    }
}
