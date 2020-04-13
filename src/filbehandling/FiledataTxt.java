package filbehandling;

import Brukere.*;
import javafx.concurrent.Task;
import komponenter.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.swing.JOptionPane.showMessageDialog;

public class FiledataTxt extends Task<Void> {

    private int intervaler = 0;
    private Standardbruker bruker;
    private Superbruker admin;

    private Register register;
    private Path pathTxt;

    public void save(String data, Path path) throws IOException {
        Files.write(path,data.getBytes());
    }

    public void loadKomponent(Komponenter komp, Path path) throws IOException{
        try(BufferedReader Reader = Files.newBufferedReader(path)){
            String line = "";
            while ((line = Reader.readLine()) != null) {
                String[] strings = line.split(";");
                String navn = strings[0];
                double pris;
                try{
                    pris = Double.parseDouble(strings[1]);
                }catch (Exception e){
                    pris = 0;
                    showMessageDialog(null, "klarte ikke å laste inn komponenter");
                }
                String type = strings[2];

                String[] specs = new String[strings.length-3];

                for(int i = 3; i < strings.length;i++){
                    int teller = 3 - i;
                    specs[teller] = strings[i];
                }

                if(type.equals("Prosessor")){
                    komp.add(new Prosessor(navn, pris, type, specs));
                }else if(type.equals("Skjermkort")){
                    komp.add(new Skjermkort(navn, pris, type, specs));
                }else if(type.equals("Minne")){
                    komp.add(new Minne(navn, pris, type, specs));
                }else if(type.equals("Harddisk")){
                    komp.add(new Harddisk(navn, pris, type, specs));
                }else if(type.equals("Tastatur")){
                    komp.add(new Tastatur(navn, pris, type, specs));
                }else if(type.equals("Mus")){
                    komp.add(new Mus(navn, pris, type, specs));
                }else if(type.equals("Skjerm")){
                    komp.add(new Skjerm(navn, pris, type, specs));
                }
            }
        }
    }

    public void loadBruker(Register brukere, Path path) throws Exception, InvalidDataLoadedException {
        try(BufferedReader Reader = Files.newBufferedReader(path)) {
            String line = "";
            while ((line = Reader.readLine()) != null) {
                String[] strings = line.split(";");
                if(intervaler == 0) {
                    Bruker b = new Bruker();

                    b.setBrukernavn(strings[0]);
                    b.setPassord(strings[1]);
                    b.setEmail(strings[2]);
                    b.setTlf(strings[3]);

                    boolean Admin = Boolean.parseBoolean(strings[4]);

                    if (Admin) {
                        admin = new Superbruker(b);
                        brukere.add(admin);
                    } else if (!Admin) {
                        bruker = new Standardbruker(b);
                        //String[5] er sum som ikke trengs for set før brukes
                        try {
                            intervaler = Integer.parseInt(strings[6]);
                        } catch (Exception e) {
                            intervaler = 0;
                            throw new InvalidDataLoadedException("Ugyldig data lagret");
                        }
                    }else{
                        throw new InvalidDataLoadedException("Ugyldig data lagret");
                    }
                }else if(intervaler > 0){


                    String navn = strings[0];
                    double pris;
                    try{
                        pris = Double.parseDouble(strings[1]);
                    }catch (Exception e){
                        pris = 0;
                        showMessageDialog(null, "klarte ikke å laste inn komponenter");
                    }
                    String type = strings[2];

                    String[] specs = new String[strings.length-3];

                    for(int i = 3; i < strings.length;i++){
                        int teller = i - 3;
                        specs[teller] = strings[i];
                    }

                    if(type.equals("Prosessor")){
                        bruker.leggTilHandlekurv(new Prosessor(navn, pris, type, specs));
                    }else if(type.equals("Skjermkort")){
                        bruker.leggTilHandlekurv(new Skjermkort(navn, pris, type, specs));
                    }else if(type.equals("Minne")){
                        bruker.leggTilHandlekurv(new Minne(navn, pris, type, specs));
                    }else if(type.equals("Harddisk")){
                        bruker.leggTilHandlekurv(new Harddisk(navn, pris, type, specs));
                    }else if(type.equals("Tastatur")){
                        bruker.leggTilHandlekurv(new Tastatur(navn, pris, type, specs));
                    }else if(type.equals("Mus")){
                        bruker.leggTilHandlekurv(new Mus(navn, pris, type, specs));
                    }else if(type.equals("Skjerm")){
                        bruker.leggTilHandlekurv(new Skjerm(navn, pris, type, specs));
                    }
                    intervaler--;
                    if(intervaler == 0){
                        brukere.add(bruker);
                    }
                }
            }
        }
    }

    public void setRegister(Register brukere){this.register = brukere;}
    public void setPathTxt(Path pathTxt){this.pathTxt = pathTxt;}

    @Override
    protected Void call() throws Exception {
        loadBruker(register, pathTxt);
        return null;
    }
}