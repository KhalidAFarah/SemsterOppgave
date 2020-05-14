package komponenter;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Spesifikasjon {
    private final SimpleStringProperty navn;
    private final SimpleStringProperty verdi;
    private final SimpleIntegerProperty ID;

    public Spesifikasjon(String navn, int ID, String verdi) {
        this.navn = new SimpleStringProperty(navn);
        this.verdi = new SimpleStringProperty(verdi);
        this.ID = new SimpleIntegerProperty(ID);
    }

    public String getNavn(){
        return navn.getValue();
    }
    public String getVerdi(){
        return verdi.getValue();
    }

    public int getID(){
        return ID.getValue();
    }

    public void setNavn(String navn) {
        this.navn.setValue(navn);
    }

    public void setVerdi(String verdi) {
        this.verdi.setValue(verdi);
    }

    public void setID(int ID) {
        this.ID.setValue(ID);
    }
}

