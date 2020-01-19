package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Person;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
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

        PersonModel personModel;

        Person tempPerson;

        List<PersonModel> personModelList = new ArrayList<>();

        List personList = personTransactionAccess.listAllPersons();


        for (Object person: personList) {

            List<String> tempSubjects = new ArrayList<>();
            tempPerson = (Person) person;

            personModel = new PersonModel(tempPerson);

            tempPerson.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));

            personModel.setSubjects(tempSubjects);
            personModelList.add(personModel);

        }


        /*if (personType == STUDENT) {

            personList = studentTransactionAccess.listAllPersons();

            Student tempStudent;

            for (Object person: personList) {

                List<String> tempSubjects = new ArrayList<>();
                tempStudent = (Student) person;

                personModel = new PersonModel(tempStudent);

                tempStudent.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));

                personModel.setSubjects(tempSubjects);
                personModelList.add(personModel);

            }

        }
        else {

            personList = teacherTransactionAccess.listAllPersons();

            Teacher tempTeacher;

            for (Object person: personList) {

                List<String> tempSubjects = new ArrayList<>();
                tempTeacher = (Teacher) person;

                personModel = new PersonModel(tempTeacher);

                tempTeacher.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));

                personModel.setSubjects(tempSubjects);
                personModelList.add(personModel);

            }

        }*/

        return personModelList;

    }


/*    private List generateTeacherModelList() {



    }*/


    @Override
    public PersonModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException {

        PersonModel personModel = new PersonModel(studentJsonString);

        /*studentTransactionAccess.addPerson(new Person(personModel));

        return personModel;*/

        studentTransactionAccess.addPerson(new Student(personModel));

        return personModel;

    }


    @Override
    public PersonModel addTeacher(String teacherJsonString) throws MissingPersonValueException, DuplicateEmailException {

        PersonModel personModel = new PersonModel(teacherJsonString);

        /*teacherTransactionAccess.addPerson(new Person(personModel));

        return personModel;*/

        teacherTransactionAccess.addPerson(new Teacher(personModel));

        return personModel;

    }


    /*private void addPerson(Person person,
                                  PersonTransactionAccess personTransactionAccess) throws MissingPersonValueException, DuplicateEmailException {

        //PersonModel personModel = new PersonModel(personJsonString);

*//*        if (personType == STUDENT) {
            studentTransactionAccess.addPerson(new Student(personModel));
        }
        else {
            teacherTransactionAccess.addPerson(new Teacher(personModel));
        }*//*

        personTransactionAccess.addPerson(new Person() {
        });

        //return personModel;

    }*/


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

        return personModel;

        return updatePerson(firstName, lastName, email, STUDENT);*/

        PersonModel personModel = generatePersonModel(firstName, lastName, email);

        studentTransactionAccess.updatePerson(new Student(personModel));

        return personModel;

    }


    @Override
    public PersonModel updateTeacher(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        //return updatePerson(firstName, lastName, email, TEACHER);

        PersonModel personModel = generatePersonModel(firstName, lastName, email);

        teacherTransactionAccess.updatePerson(new Teacher(personModel));

        return personModel;

    }


    private PersonModel generatePersonModel(String firstName, String lastName, String email) throws MissingPersonValueException {

        JsonObject personJson = new JsonObject();

        personJson.addProperty("firstname", firstName);
        personJson.addProperty("lastname", lastName);
        personJson.addProperty("email", email);

        return new PersonModel(personJson.toString());

    }


    /*private PersonModel updatePerson(String firstName,
                                     String lastName, String email,
                                     Person.PersonType personType) throws MissingPersonValueException, EmailNotFoundException {

        JsonObject personJson = new JsonObject();

        personJson.addProperty("firstname", firstName);
        personJson.addProperty("lastname", lastName);
        personJson.addProperty("email", email);

        PersonModel personModel = new PersonModel(personJson.toString());

        if (personType == STUDENT) {
            studentTransactionAccess.updatePerson(new Student(personModel));
        }
        else {
            teacherTransactionAccess.updatePerson(new Teacher(personModel));
        }

        return personModel;

    }*/


    @Override
    public PersonModel updateStudentFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.updateFirstName(new Student(personModel));

        return personModel;

        //return updateFirstName(studentJsonString, STUDENT);

    }


    @Override
    public PersonModel updateTeacherFirstName(String teacherJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(teacherJsonString);

        teacherTransactionAccess.updateFirstName(new Teacher(personModel));

        return personModel;

        //return updateFirstName(teacherJsonString, TEACHER);

    }


    /*private PersonModel updateFirstName(String personJsonString,
                                        Person.PersonType personType) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(personJsonString);

        if (personType == STUDENT) {
            studentTransactionAccess.updateFirstName(new Student(personModel));
        }
        else {
            teacherTransactionAccess.updateFirstName(new Teacher(personModel));
        }

        return personModel;

    }*/


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


    public void deleteSubject(String title) throws TitleNotFoundException {

        subjectTransactionAccess.deleteSubject(title);

    }

    @Override
    public SubjectModel findSubjectByTitle(String title) throws TitleNotFoundException {

        Subject subject = subjectTransactionAccess.findSubjectByTitle(title);

        return new SubjectModel(subject);

    }

}


// Bytte StudentModel till PersonModel. Lade till listAllTeachers,
// samt (private) listAllPersons.
