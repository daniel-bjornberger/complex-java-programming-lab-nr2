package se.alten.schoolproject.transaction;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@Default
public class StudentTransaction extends PersonTransaction implements StudentTransactionAccess {

    public StudentTransaction() {
        super("Student");
    }
}


// Flyttat koden till nya klassen PersonTransaction.