package se.alten.schoolproject.exceptions;

public class TitleNotFoundException extends Exception {

    private static final long serialVersionUID = 7023827368623382759L;

    public TitleNotFoundException(String email) {
        super("A subject with title '" + email + "' could not be found in the database.");
    }

}
