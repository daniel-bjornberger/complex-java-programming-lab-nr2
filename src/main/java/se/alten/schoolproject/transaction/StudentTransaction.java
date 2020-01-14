package se.alten.schoolproject.transaction;

public class StudentTransaction extends PersonTransaction implements StudentTransactionAccess {

    public StudentTransaction() {
        super("Student");
    }
}


// Flyttat koden till nya klassen PersonTransaction.