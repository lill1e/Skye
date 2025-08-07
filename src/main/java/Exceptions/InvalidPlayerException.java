package Exceptions;

import java.util.UUID;

public class InvalidPlayerException extends RuntimeException {
    public InvalidPlayerException(UUID id) {
        super("Invalid Player: " + id.toString());
    }
}
