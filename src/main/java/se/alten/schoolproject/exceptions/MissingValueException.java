package se.alten.schoolproject.exceptions;

public class MissingValueException extends Exception {

    private static final long serialVersionUID = 5144637805817824563L;

    public MissingValueException() {
        super("At least one of the required values are missing.");
    }

}
