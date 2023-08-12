package solutions.pge.exceptions;

public class RacerNotFoundException extends RuntimeException{

    public RacerNotFoundException(String number) {
        super(String.format("Racer with number %s not found", number));
    }
}
