package se.alten.schoolproject.dao;

        import se.alten.schoolproject.exceptions.*;
        import se.alten.schoolproject.model.PersonModel;
        import se.alten.schoolproject.model.SubjectModel;

        import javax.ejb.Local;
        import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    List listAllTeachers() throws Exception;

    PersonModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException;

    void removeStudent(String email) throws EmailNotFoundException;

    PersonModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException;

    PersonModel updateFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException;

    List findStudentsByLastName(String lastName);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel) throws DuplicateTitleException, MissingTitleValueException;

}

// Bytte StudentModel till PersonModel. Lade till listAllTeachers.