package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Person;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.PersonModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.PersonTransactionAccess;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    /*private Student student = new Student();
    private PersonModel studentModel = new PersonModel();
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();*/

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;


    @Override
    public List listAllStudents() {

        return listAllPersons(studentTransactionAccess);

    }


    @Override
    public List listAllTeachers() {

        return listAllPersons(teacherTransactionAccess);

    }


    private List listAllPersons(PersonTransactionAccess personTransactionAccess) {

        List personList = personTransactionAccess.listAllPersons();
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object person: personList) {
            personModelList.add(new PersonModel((Person) person));
        }

        return personModelList;

    }


    @Override
    public PersonModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException {

        PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.addStudent(new Student(personModel));

        return personModel;

    }


    @Override
    public void removeStudent(String email) throws EmailNotFoundException {

        studentTransactionAccess.removeStudent(email);

    }


    @Override
    public PersonModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        JsonObject studentJson = new JsonObject();

        studentJson.addProperty("firstname", firstName);
        studentJson.addProperty("lastname", lastName);
        studentJson.addProperty("email", email);

        PersonModel personModel = new PersonModel(studentJson.toString());

        studentTransactionAccess.updateStudent(new Student(personModel));

        return personModel;

    }


    @Override
    public PersonModel updateFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.updateFirstName(new Student(personModel));

        return personModel;

    }


    @Override
    public List findStudentsByLastName(String lastName) {

        List studentList = studentTransactionAccess.findStudentsByLastName(lastName);
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object student: studentList) {
            personModelList.add(new PersonModel((Student) student));
        }

        return personModelList;

    }


    @Override
    public List listAllSubjects() {

        //return subjectTransactionAccess.listAllSubjects();

        List subjectList = subjectTransactionAccess.listAllSubjects();
        List<SubjectModel> subjectModelList = new ArrayList<>();

        for (Object subject: subjectList) {
            subjectModelList.add(new SubjectModel((Subject) subject));
        }

        return subjectModelList;

    }


    @Override
    public SubjectModel addSubject(String subjectJsonString) throws DuplicateTitleException, MissingTitleValueException {

        /*Subject subjectToAdd = subject.toEntity(newSubject);
        subjectTransactionAccess.addSubject(subjectToAdd);
        return subjectModel.toModel(subjectToAdd);*/

        SubjectModel subjectModel = new SubjectModel(subjectJsonString);

        subjectTransactionAccess.addSubject(new Subject(subjectModel));

        return subjectModel;

    }

}


// Bytte StudentModel till PersonModel. Lade till listAllTeachers,
// samt (private) listAllPersons.
