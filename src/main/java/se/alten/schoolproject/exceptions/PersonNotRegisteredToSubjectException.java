package se.alten.schoolproject.exceptions;

public class PersonNotRegisteredToSubjectException extends Exception {

    private static final long serialVersionUID = 6446801511375738442L;

    public PersonNotRegisteredToSubjectException(String message) {
        super(message);
    }

}
