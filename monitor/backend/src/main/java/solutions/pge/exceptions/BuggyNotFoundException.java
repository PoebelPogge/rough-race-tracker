package solutions.pge.exceptions;

public class BuggyNotFoundException extends RuntimeException{
    public BuggyNotFoundException(String tagId) {
        super(String.format("Buggy with tagId: %s not found", tagId));
    }
}
