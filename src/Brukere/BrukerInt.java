package Brukere;

public interface BrukerInt {
    /*String brukernavn;
    String passord;
    String email;
    String tlf;*/

    public String getBrukernavn();

    public String getPassord();

    public String getEmail();

    public String getTlf();

    public boolean isAdmin();

    public void setBrukernavn(String brukernavn);

    public void setPassord(String passord);

    public void setEmail(String email);

    public void setTlf(String tlf);


    public String toStringFormat();
}
