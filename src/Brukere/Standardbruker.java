package Brukere;

import komponenter.*;

public class Standardbruker extends Bruker {
    private double sum;
    private Komponenter handelskurv;
    private Bruker bruker;
    private static final boolean ADMIN = false;

    public Standardbruker(Bruker bruker){
        this.bruker = bruker;
        sum = 0;
        handelskurv = new Komponenter();

    }

    public String getBrukernavn(){return bruker.getBrukernavn();}
    public String getPassord(){return bruker.getPassord();}
    public String getEmail(){return bruker.getEmail();}
    public Komponenter getHandelskurv(){return handelskurv;}
    public boolean isAdmin(){return ADMIN;}

    public void setBrukernavn(String brukernavn){bruker.setBrukernavn(brukernavn);}
    public void setPassord(String passord){bruker.setPassord(passord);}
    public void setEmail(String email){bruker.setEmail(email);}
    public void setHandelskurv(Komponenter handelskurv){this.handelskurv = handelskurv;}

    public void setSum(){
        this.sum = 0;
        for(int i = 0; i < handelskurv.getMainArray().size(); i++){
            sum += handelskurv.getMainArray().get(i).getPris();
        }
    }

    public <T extends Komponent> void leggTilHandlekurv(T elem){
        boolean sjekk = true;
        //tester om det samme komponent type eksisterer dersom den finnes fjernes den gamle og legger til den nye
        for(int i = 0; i < handelskurv.getMainArray().size(); i++){
            if(handelskurv.getMainArray().get(i).getType().equals(elem.getType())){
                handelskurv.getMainArray().remove(i);
                handelskurv.getMainArray().add(elem);
                sjekk = false;

                setSum();//ny sum av all varer
            }
        }
        if(sjekk){
            handelskurv.getMainArray().add(elem);

            setSum();//ny sum av alle varer
        }
    }

    public String toStringTxt(){
        return bruker.toStringFormat() + sum + ";" + ADMIN + ";" + handelskurv.toStringTxt();
    }
    public String toStringCsv(){
        return bruker.toStringFormat() + sum + ";" + ADMIN + ";" + handelskurv.toStringCsv();
    }
}
