package sample;

public class InvalidDataGivenException extends IllegalArgumentException {
    public InvalidDataGivenException(String msg) {
        super(msg);
    }
}
