package Brukere;

public class Bruker {
    private String brukernavn;
    private String passord;
    private String email;

    public String getBrukernavn(){return brukernavn;}
    public String getPassord(){return passord;}
    public String getEmail(){return email;}

    public void setBrukernavn(String brukernavn){this.brukernavn = brukernavn;}
    public void setPassord(String passord){this.passord = passord;}
    public void setEmail(String email){this.email = email;}

    public String toStringFormat(){
        return brukernavn + ";" + passord + ";" + email + ";";
    }
}
