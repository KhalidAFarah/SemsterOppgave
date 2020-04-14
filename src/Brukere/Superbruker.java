package Brukere;

public class Superbruker extends Bruker {
    private static final boolean ADMIN = true;
    private Bruker bruker;

    public Superbruker(Bruker bruker) {
        this.bruker = bruker;
    }

    public String getBrukernavn() {
        return bruker.getBrukernavn();
    }

    public String getPassord() {
        return bruker.getPassord();
    }

    public String getEmail() {
        return bruker.getEmail();
    }

    public String getTlf() {
        return bruker.getTlf();
    }

    public boolean isAdmin() {
        return ADMIN;
    }

    public void setBrukernavn(String brukernavn) {
        bruker.setBrukernavn(brukernavn);
    }

    public void setPassord(String passord) {
        bruker.setPassord(passord);
    }

    public void setEmail(String email) {
        bruker.setEmail(email);
    }

    public void setTlf(String tlf) {
        bruker.setTlf(tlf);
    }

    public String toStringFormat() {
        return bruker.toStringFormat() + ADMIN + ";";
    }

}
