package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.DuplicateTitleException;
import se.alten.schoolproject.exceptions.PersonNotRegisteredToSubjectException;
import se.alten.schoolproject.exceptions.TitleNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {

    List listAllSubjects();

    void addSubject(Subject subject) throws DuplicateTitleException;

    void deleteSubject(String title) throws TitleNotFoundException;

    Subject findSubjectByTitle(String title) throws TitleNotFoundException;

    Subject addStudentToSubject(String title, Student student) throws TitleNotFoundException;

    Subject addTeacherToSubject(String title, Teacher teacher) throws TitleNotFoundException;

    void removeStudentFromSubject(String title, Student student) throws TitleNotFoundException, PersonNotRegisteredToSubjectException;

    void removeTeacherFromSubject(String title) throws TitleNotFoundException, PersonNotRegisteredToSubjectException;

}
