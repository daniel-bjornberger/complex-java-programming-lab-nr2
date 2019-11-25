package se.alten.schoolproject.dao;

import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;
import se.alten.schoolproject.exceptions.MissingValueException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    StudentModel addStudent(String studentJsonString) throws MissingValueException, DuplicateEmailException;

    void removeStudent(String email) throws EmailNotFoundException;

    StudentModel updateStudent(String firstName, String lastName, String email) throws MissingValueException, EmailNotFoundException;

    StudentModel updateFirstName(String studentJsonString) throws MissingValueException, LastNameAndEmailNotFoundException;

    List findStudentsByLastName(String lastName);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel);

}
