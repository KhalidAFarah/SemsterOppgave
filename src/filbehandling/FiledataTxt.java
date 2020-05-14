package filbehandling;

import Brukere.*;
import javafx.concurrent.Task;
import komponenter.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javax.swing.JOptionPane.showMessageDialog;

public class FiledataTxt extends Task<Void> {

    private int intervaler = 0;
    private Bruker bruker;

    private Register register;
    private Path pathTxt;
    private Path pathTxt2 = Paths.get("src/filbehandling/StandardbrukerSinIndividuelleHandlekurv.csv");

    public void save(String data, Path path) throws IOException {
        Files.write(path, data.getBytes());
    }

    public Register loadBruker(Register brukere, Path path) throws Exception {
        try (BufferedReader Reader = Files.newBufferedReader(path)) {
            String line = "";
            while ((line = Reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] strings = line.split(";");
                    if (intervaler == 0) {
                        boolean sjekk = true;
                        boolean Admin = Boolean.parseBoolean(strings[4]);
                        if (Admin) {
                            bruker = new Superbruker();
                        } else {
                            bruker = new Standardbruker();
                        }

                        bruker.setBrukernavn(strings[0]);
                        bruker.setPassord(strings[1]);

                        try {
                            bruker.setEmail(strings[2]);
                        } catch (InvalidStringException e) {
                            showMessageDialog(null, e.getMessage());
                            sjekk = false;
                        }
                        try {
                            bruker.setTlf(strings[3]);
                        } catch (InvalidStringException e) {
                            showMessageDialog(null, e.getMessage());
                            sjekk = false;
                        }


                        if (Admin) {
                            brukere.add(bruker);
                        } else if (!Admin) {
                            //String[5] er sum som ikke trengs for set før brukes

                            int ant;

                            try{
                                ant = Integer.parseInt(strings[6]);
                            }catch (Exception e){
                                ant = -1;
                                sjekk = false;
                            }

                            try {
                                intervaler = Integer.parseInt(strings[7]);
                            } catch (Exception e) {
                                intervaler = -1;
                                sjekk = false;
                                //throw new InvalidDataLoadedException("Ugyldig data lagret");
                            }

                            if (intervaler == 0 && sjekk) {
                                ((Standardbruker)bruker).setAntallKjøp(ant);
                                brukere.add(bruker);
                            }
                        }
                    } else if (intervaler > 0) {


                        String navn = strings[0];
                        double pris;

                        try {
                            pris = Double.parseDouble(strings[1]);
                        } catch (Exception e) {
                            pris = 0;
                            showMessageDialog(null, "klarte ikke å laste inn komponenter");
                        }
                        String type = strings[2];

                        String[] specs = new String[strings.length - 3];

                        for (int i = 3; i < strings.length; i++) {
                            int teller = i - 3;
                            specs[teller] = strings[i];
                        }

                        if (bruker instanceof Standardbruker) {
                            if (type.equals("Prosessor")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Prosessor(navn, pris, type, specs));
                            } else if (type.equals("Skjermkort")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Skjermkort(navn, pris, type, specs));
                            } else if (type.equals("Minne")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Minne(navn, pris, type, specs));
                            } else if (type.equals("Harddisk")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Harddisk(navn, pris, type, specs));
                            } else if (type.equals("Tastatur")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Tastatur(navn, pris, type, specs));
                            } else if (type.equals("Mus")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Mus(navn, pris, type, specs));
                            } else if (type.equals("Skjerm")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Skjerm(navn, pris, type, specs));
                            }else if (type.equals("Operativsystem")) {
                                ((Standardbruker) bruker).leggTilHandlekurv(new Operativsystem(navn, pris, type, specs));
                            }
                            intervaler--;
                            if (intervaler == 0) {
                                brukere.add(bruker);
                            }
                        }

                    }
                }
            }
        }
        return brukere;
    }

    public void lesIndividuelleVarer() throws IOException{
        try (BufferedReader Reader = Files.newBufferedReader(pathTxt2)) {
            String line = "";
            boolean funnet = false;
            while ((line = Reader.readLine()) != null) {
                String[] strings = line.split(";");
                if(!funnet && intervaler == 0) {
                    for (int i = 0; i < register.getArray().size(); i++) {
                        if (register.getArray().get(i).getBrukernavn().equals(strings[0]) &&
                                register.getArray().get(i).getPassord().equals(strings[1])) {
                            bruker = register.getArray().get(i);
                            funnet = true;

                            try{
                               intervaler = Integer.parseInt(strings[3]);
                            }catch (Exception e){
                                intervaler = -1;
                            }

                            int antallKjøp;
                            try{
                                antallKjøp = Integer.parseInt(strings[2]);
                            }catch (Exception e){
                                antallKjøp = -1;
                            }
                            if(((Standardbruker)register.getArray().get(i)).getAntallKjøp() < antallKjøp){
                                ((Standardbruker)register.getArray().get(i)).setAntallKjøp(antallKjøp);
                            }
                        }
                    }
                }else {
                    if (intervaler > 0) {
                        boolean sjekk = true;
                        String navn = strings[0];
                        double pris;

                        try {
                            pris = Double.parseDouble(strings[1]);
                        } catch (Exception e) {
                            pris = 0;
                            sjekk = false;

                        }

                        String type = strings[2];

                        int antall;
                        try{
                            antall = Integer.parseInt(strings[3]);
                        }catch (Exception e){
                            antall = -1;
                            sjekk = false;
                        }

                        String[] specs = new String[strings.length - 4];

                        for (int i = 4; i < strings.length; i++) {
                            int teller = i - 4;
                            specs[teller] = strings[i];
                        }
                        sjekk = specs.length % 2 == 0;

                        if (bruker instanceof Standardbruker && sjekk) {
                            if (type.equals("Prosessor")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Prosessor(navn, pris, type, specs));
                            } else if (type.equals("Skjermkort")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Skjermkort(navn, pris, type, specs));
                            } else if (type.equals("Minne")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Minne(navn, pris, type, specs));
                            } else if (type.equals("Harddisk")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Harddisk(navn, pris, type, specs));
                            } else if (type.equals("Tastatur")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Tastatur(navn, pris, type, specs));
                            } else if (type.equals("Mus")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Mus(navn, pris, type, specs));
                            } else if (type.equals("Skjerm")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Skjerm(navn, pris, type, specs));
                            } else if (type.equals("Operativsystem")) {
                                ((Standardbruker) bruker).leggTilIndividuelleHandlekurv(new Operativsystem(navn, pris, type, specs));
                            }
                            intervaler--;
                        }

                    }
                }
            }
        }
    }

    public void setRegister(Register brukere) {
        this.register = brukere;
    }

    public void setPathTxt(Path pathTxt) {
        this.pathTxt = pathTxt;
    }

    public void setPathTxt2(Path pathTxt2) {
        this.pathTxt2 = pathTxt2;
    }

    @Override
    protected Void call() throws Exception {
        if(pathTxt != null && register != null) {
            register.setArray(loadBruker(register, pathTxt).getArray());
            if(pathTxt2 != null){
                lesIndividuelleVarer();
            }
        }
        return null;
    }
}