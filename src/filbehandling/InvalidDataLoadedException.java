package filbehandling;

public class InvalidDataLoadedException extends IllegalArgumentException {
    public InvalidDataLoadedException(String msg) {
        super(msg);
    }
}
