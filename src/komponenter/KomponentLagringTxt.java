package komponenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.swing.JOptionPane.showMessageDialog;

public class KomponentLagringTxt {

    public void save(String data, Path path) throws IOException {
        Files.write(path,data.getBytes());
    }

    public void load(Komponenter komp, Path path) throws IOException{
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
                String[] specs = strings[3].split(":");

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
}

