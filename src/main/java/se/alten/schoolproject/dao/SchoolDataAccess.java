package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Person;
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

        /*PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.addPerson(new Person(personModel));

        return personModel;*/

        return addPerson(studentJsonString, studentTransactionAccess);

    }


    @Override
    public PersonModel addTeacher(String teacherJsonString) throws MissingPersonValueException, DuplicateEmailException {

        /*PersonModel personModel = new PersonModel(teacherJsonString);

        teacherTransactionAccess.addPerson(new Person(personModel));

        return personModel;*/

        return addPerson(teacherJsonString, teacherTransactionAccess);

    }


    private PersonModel addPerson(String personJsonString,
                                  PersonTransactionAccess personTransactionAccess) throws MissingPersonValueException {

        PersonModel personModel = new PersonModel(personJsonString);

        personTransactionAccess.addPerson(new Person(personModel));

        return personModel;

    }


    @Override
    public void deleteStudent(String email) throws EmailNotFoundException {

        studentTransactionAccess.deletePerson(email);

    }


    @Override
    public void deleteTeacher(String email) throws EmailNotFoundException {

        teacherTransactionAccess.deletePerson(email);

    }


    @Override
    public PersonModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        /*JsonObject studentJson = new JsonObject();

        studentJson.addProperty("firstname", firstName);
        studentJson.addProperty("lastname", lastName);
        studentJson.addProperty("email", email);

        PersonModel personModel = new PersonModel(studentJson.toString());

        studentTransactionAccess.updateStudent(new Student(personModel));

        return personModel;*/

        return updatePerson(firstName, lastName, email, studentTransactionAccess);

    }


    @Override
    public PersonModel updateTeacher(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        return updatePerson(firstName, lastName, email, teacherTransactionAccess);

    }


    private PersonModel updatePerson(String firstName,
                                     String lastName, String email,
                                     PersonTransactionAccess personTransactionAccess) throws MissingPersonValueException, EmailNotFoundException {

        JsonObject personJson = new JsonObject();

        personJson.addProperty("firstname", firstName);
        personJson.addProperty("lastname", lastName);
        personJson.addProperty("email", email);

        PersonModel personModel = new PersonModel(personJson.toString());

        personTransactionAccess.updatePerson(new Person(personModel));

        return personModel;

    }

//--------------------------------------------------
    @Override
    public PersonModel updateStudentFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        /*PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.updateFirstName(new Person(personModel));

        return personModel;*/

        return updateFirstName(studentJsonString, studentTransactionAccess);

    }


    @Override
    public PersonModel updateTeacherFirstName(String teacherJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        /*PersonModel personModel = new PersonModel(teacherJsonString);

        studentTransactionAccess.updateFirstName(new Person(personModel));

        return personModel;*/

        return updateFirstName(teacherJsonString, teacherTransactionAccess);

    }


    private PersonModel updateFirstName(String personJsonString,
                                        PersonTransactionAccess personTransactionAccess) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(personJsonString);

        personTransactionAccess.updateFirstName(new Person(personModel));

        return personModel;

    }


    @Override
    public List findStudentsByLastName(String lastName) {

        /*List studentList = studentTransactionAccess.findStudentsByLastName(lastName);
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object student: studentList) {
            personModelList.add(new PersonModel((Person) student));
        }

        return personModelList;*/

        return findPersonsByLastName(lastName, studentTransactionAccess);

    }


    @Override
    public List findTeachersByLastName(String lastName) {

        /*List teacherList = teacherTransactionAccess.findTeachersByLastName(lastName);
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object student: teacherList) {
            personModelList.add(new PersonModel((Student) student));
        }

        return personModelList;*/

        return findPersonsByLastName(lastName, teacherTransactionAccess);

    }


    private List findPersonsByLastName(String lastName, PersonTransactionAccess personTransactionAccess) {

        List personList = personTransactionAccess.findPersonsByLastName(lastName);
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object person: personList) {
            personModelList.add(new PersonModel((Person) person));
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


    // Lägg till deleteSubject.

}


// Bytte StudentModel till PersonModel. Lade till listAllTeachers,
// samt (private) listAllPersons.
