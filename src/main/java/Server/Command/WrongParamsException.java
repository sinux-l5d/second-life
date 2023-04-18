package Server.Command;

public class WrongParamsException extends Exception {
    public WrongParamsException(String message) {
        super(message);
    }
}
