package se.alten.schoolproject.exceptions;

public class MissingPersonValueException extends Exception {

    private static final long serialVersionUID = -140110677808863339L;

    public MissingPersonValueException() {
        super("At least one of the required values are missing.");
    }

}
