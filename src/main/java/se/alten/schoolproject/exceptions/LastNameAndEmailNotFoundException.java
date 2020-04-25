package se.alten.schoolproject.exceptions;

public class LastNameAndEmailNotFoundException extends Exception {

    private static final long serialVersionUID = 880751866168979875L;

    public LastNameAndEmailNotFoundException(String lastName, String email) {
        super("A person with last name '" + lastName +
                "' and email '" + email + "' could not be found in the database.");
    }

}
