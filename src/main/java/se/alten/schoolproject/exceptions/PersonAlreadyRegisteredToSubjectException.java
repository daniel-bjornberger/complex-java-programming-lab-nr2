package se.alten.schoolproject.exceptions;

public class PersonAlreadyRegisteredToSubjectException extends Exception {

    private static final long serialVersionUID = -1401935579218399367L;

    public PersonAlreadyRegisteredToSubjectException(String message) {
        super(message);
    }

}
