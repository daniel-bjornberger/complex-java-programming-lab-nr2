package se.alten.schoolproject.exceptions;

public class MissingTitleValueException extends Exception {

    private static final long serialVersionUID = -302678221500313791L;

    public MissingTitleValueException() {
        super("The subject title is missing.");
    }

}
