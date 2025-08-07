package Exceptions;

public class InvalidScoreboardException extends RuntimeException {
    public InvalidScoreboardException(String message) {
        super("InvalidScoreboard: " + message);
    }
}
