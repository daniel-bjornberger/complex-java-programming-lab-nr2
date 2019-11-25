package se.alten.schoolproject.exceptions;

public class MissingStudentValueException extends Exception {

    private static final long serialVersionUID = -140110677808863339L;

    public MissingStudentValueException() {
        super("At least one of the required values are missing.");
    }

}
