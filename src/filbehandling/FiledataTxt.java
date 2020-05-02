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
    private Bruker bruker;

    private Register register;
    private Path pathTxt;

    public void save(String data, Path path) throws IOException {
        Files.write(path, data.getBytes());
    }

    public void loadKomponent(Komponenter komp, Path path) throws IOException {
        try (BufferedReader Reader = Files.newBufferedReader(path)) {
            String line = "";
            while ((line = Reader.readLine()) != null) {
                String[] strings = line.split(";");
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
                    int teller = 3 - i;
                    specs[teller] = strings[i];
                }

                if (type.equals("Prosessor")) {
                    komp.add(new Prosessor(navn, pris, type, specs));
                } else if (type.equals("Skjermkort")) {
                    komp.add(new Skjermkort(navn, pris, type, specs));
                } else if (type.equals("Minne")) {
                    komp.add(new Minne(navn, pris, type, specs));
                } else if (type.equals("Harddisk")) {
                    komp.add(new Harddisk(navn, pris, type, specs));
                } else if (type.equals("Tastatur")) {
                    komp.add(new Tastatur(navn, pris, type, specs));
                } else if (type.equals("Mus")) {
                    komp.add(new Mus(navn, pris, type, specs));
                } else if (type.equals("Skjerm")) {
                    komp.add(new Skjerm(navn, pris, type, specs));
                }
            }
        }
    }

    public Register loadBruker(Register brukere, Path path) throws Exception, InvalidDataLoadedException {
        try (BufferedReader Reader = Files.newBufferedReader(path)) {
            String line = "";
            while ((line = Reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] strings = line.split(";");
                    if (intervaler == 0) {
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
                        }
                        try {
                            bruker.setTlf(strings[3]);
                        } catch (InvalidStringException e) {
                            showMessageDialog(null, e.getMessage());
                        }


                        if (Admin) {
                            brukere.add(bruker);
                        } else if (!Admin) {
                            //String[5] er sum som ikke trengs for set før brukes
                            try {
                                intervaler = Integer.parseInt(strings[6]);
                            } catch (Exception e) {
                                intervaler = 0;
                                //throw new InvalidDataLoadedException("Ugyldig data lagret");
                            }

                            if (intervaler == 0) {
                                brukere.add(bruker);
                            }
                        } else {
                            //throw new InvalidDataLoadedException("Ugyldig data lagret");
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

    public void setRegister(Register brukere) {
        this.register = brukere;
    }

    public void setPathTxt(Path pathTxt) {
        this.pathTxt = pathTxt;
    }

    @Override
    protected Void call() throws Exception {
        register.setArray(loadBruker(register, pathTxt).getArray());
        //System.out.println(register.toStringTxt() + " hdd");
        return null;
    }
}