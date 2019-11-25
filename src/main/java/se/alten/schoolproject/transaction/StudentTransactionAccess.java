package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {

    List listAllStudents();

    void addStudent(Student student) throws TransactionExceptions.DuplicateEmailException;

    void removeStudent(String email) throws TransactionExceptions.EmailNotFoundException;

    void updateStudent(Student student) throws TransactionExceptions.EmailNotFoundException;

    void updateFirstName(Student student) throws TransactionExceptions.LastNameAndEmailNotFoundException;

    List findStudentsByLastName(String lastName);

}