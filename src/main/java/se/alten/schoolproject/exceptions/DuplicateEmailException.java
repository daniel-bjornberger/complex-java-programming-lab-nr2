package se.alten.schoolproject.exceptions;

public class DuplicateEmailException extends Exception {

    private static final long serialVersionUID = 9195389659279412624L;

    public DuplicateEmailException(String email) {
        super("There is already a student with email '" + email + "' stored in the database.");
    }

}
