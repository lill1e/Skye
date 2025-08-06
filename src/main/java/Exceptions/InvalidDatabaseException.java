package Exceptions;

public class InvalidDatabaseException extends RuntimeException {
    public InvalidDatabaseException(String message) {
        super(message);
    }
}
