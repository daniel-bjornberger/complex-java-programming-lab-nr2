package se.alten.schoolproject.exceptions;

public class DuplicateTitleException extends Exception {

    private static final long serialVersionUID = -4001760736892279132L;

    public DuplicateTitleException(String title) {
        super("There is already a subject with title '" + title + "' stored in the database.");
    }

}