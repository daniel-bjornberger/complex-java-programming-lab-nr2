package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.ModelExceptions;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.TransactionExceptions;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    StudentModel addStudent(String studentJsonString) throws ModelExceptions.MissingValueException, TransactionExceptions.DuplicateEmailException;

    void removeStudent(String email) throws TransactionExceptions.EmailNotFoundException;

    StudentModel updateStudent(String firstName, String lastName, String email) throws ModelExceptions.MissingValueException, TransactionExceptions.EmailNotFoundException;

    StudentModel updateFirstName(String studentJsonString) throws ModelExceptions.MissingValueException, TransactionExceptions.LastNameAndEmailNotFoundException;

    List findStudentsByLastName(String lastName);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel);

}
