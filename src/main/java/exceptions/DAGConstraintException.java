package exceptions;

public class DAGConstraintException extends RuntimeException{
    @Override
    public String getMessage() {
        return "A cycle has formed";
    }
}
