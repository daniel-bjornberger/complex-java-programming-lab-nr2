package se.alten.schoolproject.exceptions;

public class EmailNotFoundException extends Exception {

    private static final long serialVersionUID = -6047367558055476626L;

    public EmailNotFoundException(String email) {
        super("A person with email '" + email + "' could not be found in the database.");
    }

}