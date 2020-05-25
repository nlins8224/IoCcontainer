package exceptions;

public class SimpleContainerException extends RuntimeException {
    public SimpleContainerException(String message){
        super(message);
    }

    public SimpleContainerException(Throwable cause){
        super(cause);
    }
}
