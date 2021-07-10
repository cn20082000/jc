package jc.exceptions;

public class AppControllerException extends Exception {

    public AppControllerException() {
        super("Unknown exception!");
    }

    public AppControllerException(String message) {
        super(message);
    }
    
}
