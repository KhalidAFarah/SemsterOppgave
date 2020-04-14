package filbehandling;

import javafx.concurrent.Task;
import komponenter.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FiledataJOBJ extends Task<Void> {

    private Path path;
    private Komponenter komponenter;

    public void save(Komponenter komp, Path path) throws IOException {
        try (OutputStream os = Files.newOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(os)) {
            komp.writeObject(out);
        }

    }

    public void load(Komponenter komp, Path path) throws IOException, Exception {// for nå siden der er ikke er forskjeller mellom komponentene
        try (InputStream is = Files.newInputStream(path);             // for fremtiden dersom der blir forskjeller lag metoder for hver
             ObjectInputStream oin = new ObjectInputStream(is)) {          // komponent type inne i komponent typens klasse
            komp.readObject(oin);
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setKomponent(Komponenter komponenter) {
        this.komponenter = komponenter;
    }

    @Override
    protected Void call() throws Exception {
        load(komponenter, path);
        return null;
    }
}
