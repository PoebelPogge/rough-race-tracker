package solutions.pge.exceptions;

public class RaceNotReadyException extends RuntimeException{

    public RaceNotReadyException(String reason) {
        super(reason);
    }
}
