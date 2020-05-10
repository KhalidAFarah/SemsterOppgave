package komponenter;

public class Operativsystem extends Komponent {

    public Operativsystem(String navn, double pris, String type, String... strings) {
        super(navn, pris, type, strings);
    }

    @Override
    public String toString() {
        return "Navn: " + super.getNavn();
    }

    @Override
    public String toStringTxt() {
        String ut = getNavn() + ";" + getPris() + ";" + getType() + ";";
        for (int i = 0; i < getSpecs().size(); i++) {
            ut += getSpecs().get(i) + ";";
        }
        return ut;
    }
}
