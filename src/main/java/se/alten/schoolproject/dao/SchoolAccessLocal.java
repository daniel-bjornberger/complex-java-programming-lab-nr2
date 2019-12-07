package se.alten.schoolproject.dao;

import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    StudentModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException;

    void removeStudent(String email) throws EmailNotFoundException;

    StudentModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException;

    StudentModel updateFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException;

    List findStudentsByLastName(String lastName);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel) throws DuplicateTitleException, MissingTitleValueException;

}
