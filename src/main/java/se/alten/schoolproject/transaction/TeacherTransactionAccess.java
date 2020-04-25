package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TeacherTransactionAccess {

    List<?> listAllTeachers();

    void addTeacher(Teacher teacher) throws DuplicateEmailException;

    void deleteTeacher(String email) throws EmailNotFoundException;

    void updateTeacher(Teacher teacher) throws EmailNotFoundException;

    void updateTeacherFirstName(Teacher teacher) throws LastNameAndEmailNotFoundException;

    List<?> findTeachersByLastName(String lastName);

    Teacher findTeacherByEmail(String email) throws EmailNotFoundException;

}
