package se.alten.schoolproject.transaction;

public class TeacherTransaction extends PersonTransaction implements TeacherTransactionAccess {

    public TeacherTransaction() {
        super("Teacher");
    }
}
