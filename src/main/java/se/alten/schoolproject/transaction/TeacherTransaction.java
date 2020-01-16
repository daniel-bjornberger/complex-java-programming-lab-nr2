package se.alten.schoolproject.transaction;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@Default
public class TeacherTransaction extends PersonTransaction implements TeacherTransactionAccess {

    public TeacherTransaction() {
        super("Teacher");
    }
}
