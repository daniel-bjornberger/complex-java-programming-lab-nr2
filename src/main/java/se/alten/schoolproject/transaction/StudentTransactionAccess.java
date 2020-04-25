package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List<?> listAllStudents();
    void addStudent(Student student) throws DuplicateEmailException;
    void deleteStudent(String email) throws EmailNotFoundException;
    void updateStudent(Student student) throws EmailNotFoundException;
    void updateStudentFirstName(Student student) throws LastNameAndEmailNotFoundException;
    List<?> findStudentsByLastName(String lastName);
    Student findStudentByEmail(String email) throws EmailNotFoundException;
}
