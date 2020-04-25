package se.alten.schoolproject.dao;

import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {
    List<?> listAllStudents() throws Exception;
    List<?> listAllTeachers() throws Exception;
    StudentModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException;
    TeacherModel addTeacher(String teacherJsonString) throws MissingPersonValueException, DuplicateEmailException;
    void deleteStudent(String email) throws EmailNotFoundException;
    void deleteTeacher(String email) throws EmailNotFoundException;
    StudentModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException;
    TeacherModel updateTeacher(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException;
    StudentModel updateStudentFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException;
    TeacherModel updateTeacherFirstName(String teacherJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException;
    List<?> findStudentsByLastName(String lastName);
    List<?> findTeachersByLastName(String lastName);
    List<?> listAllSubjects();
    SubjectModel addSubject(String subjectModel) throws DuplicateTitleException, MissingTitleValueException;
    void deleteSubject(String title) throws TitleNotFoundException;
    SubjectModel findSubjectByTitle(String title) throws TitleNotFoundException;
    SubjectModel addStudentToSubject(String studentEmail, String title) throws EmailNotFoundException, TitleNotFoundException;
    SubjectModel addTeacherToSubject(String teacherEmail, String title) throws EmailNotFoundException, TitleNotFoundException;
    void removeStudentFromSubject(String studentEmail, String title) throws EmailNotFoundException, TitleNotFoundException, PersonNotRegisteredToSubjectException;
    void removeTeacherFromSubject(String title) throws TitleNotFoundException, PersonNotRegisteredToSubjectException;
}
