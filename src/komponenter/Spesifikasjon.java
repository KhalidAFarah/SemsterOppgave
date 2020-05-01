package komponenter;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Spesifikasjon {
    private SimpleStringProperty navn2;
    private SimpleIntegerProperty ID2;

    private int ID;
    private String navn;

    public Spesifikasjon(String navn, int ID) {
        navn2 = new SimpleStringProperty(navn);
        ID2 = new SimpleIntegerProperty(ID);

        navn = getNavn2();
        ID = getID2();
    }

    public void setNavn2(String navn) {
        this.navn2.setValue(navn);
    }

    public void setID2(int ID) {
        this.ID2.setValue(ID);
    }

    public String getNavn2() {
        return navn2.getValue();
    }

    public Integer getID2() {
        return ID2.getValue();
    }
}
