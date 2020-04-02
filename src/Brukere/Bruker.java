package Brukere;

public class Bruker {
    private String brukernavn;
    private String passord;
    private String email;
    private String tlf;

    public String getBrukernavn(){return brukernavn;}
    public String getPassord(){return passord;}
    public String getEmail(){return email;}
    public String getTlf(){return tlf;}

    public void setBrukernavn(String brukernavn){this.brukernavn = brukernavn;}
    public void setPassord(String passord){this.passord = passord;}
    public void setEmail(String email){this.email = email;}
    public void setTlf(String tlf){this.tlf = tlf;}

    public String toStringFormat(){
        return brukernavn + ";" + passord + ";" + email + ";" + tlf + ";";
    }
}
