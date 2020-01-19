package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.PersonModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;


    @Override
    public List listAllStudents() {

        PersonModel personModel;

        Student tempStudent;

        List<PersonModel> personModelList = new ArrayList<>();

        List studentList = studentTransactionAccess.listAllStudents();


        for (Object student: studentList) {

            List<String> tempSubjects = new ArrayList<>();
            tempStudent = (Student) student;

            personModel = new PersonModel(tempStudent);

            tempStudent.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));

            personModel.setSubjects(tempSubjects);
            personModelList.add(personModel);

        }

        return personModelList;

    }


    @Override
    public List listAllTeachers() {

        PersonModel personModel;

        Teacher tempTeacher;

        List<PersonModel> personModelList = new ArrayList<>();

        List teacherList = teacherTransactionAccess.listAllTeachers();


        for (Object teacher: teacherList) {

            List<String> tempSubjects = new ArrayList<>();
            tempTeacher = (Teacher) teacher;

            personModel = new PersonModel(tempTeacher);

            tempTeacher.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));

            personModel.setSubjects(tempSubjects);
            personModelList.add(personModel);

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
    public PersonModel addTeacher(String teacherJsonString) throws MissingPersonValueException, DuplicateEmailException {

        PersonModel personModel = new PersonModel(teacherJsonString);

        teacherTransactionAccess.addTeacher(new Teacher(personModel));

        return personModel;

    }


    @Override
    public void deleteStudent(String email) throws EmailNotFoundException {

        studentTransactionAccess.deleteStudent(email);

    }


    @Override
    public void deleteTeacher(String email) throws EmailNotFoundException {

        teacherTransactionAccess.deleteTeacher(email);

    }


    @Override
    public PersonModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        PersonModel personModel = generatePersonModel(firstName, lastName, email);

        studentTransactionAccess.updateStudent(new Student(personModel));

        return personModel;

    }


    @Override
    public PersonModel updateTeacher(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        PersonModel personModel = generatePersonModel(firstName, lastName, email);

        teacherTransactionAccess.updateTeacher(new Teacher(personModel));

        return personModel;

    }


    private PersonModel generatePersonModel(String firstName, String lastName, String email) throws MissingPersonValueException {

        JsonObject personJson = new JsonObject();

        personJson.addProperty("firstname", firstName);
        personJson.addProperty("lastname", lastName);
        personJson.addProperty("email", email);

        return new PersonModel(personJson.toString());

    }


    @Override
    public PersonModel updateStudentFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(studentJsonString);

        studentTransactionAccess.updateStudentFirstName(new Student(personModel));

        return personModel;

    }


    @Override
    public PersonModel updateTeacherFirstName(String teacherJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        PersonModel personModel = new PersonModel(teacherJsonString);

        teacherTransactionAccess.updateTeacherFirstName(new Teacher(personModel));

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
    public List findTeachersByLastName(String lastName) {

        List teacherList = teacherTransactionAccess.findTeachersByLastName(lastName);
        List<PersonModel> personModelList = new ArrayList<>();

        for (Object teacher: teacherList) {
            personModelList.add(new PersonModel((Teacher) teacher));
        }

        return personModelList;

    }


    @Override
    public List listAllSubjects() {

        List subjectList = subjectTransactionAccess.listAllSubjects();
        List<SubjectModel> subjectModelList = new ArrayList<>();

        for (Object subject: subjectList) {
            subjectModelList.add(new SubjectModel((Subject) subject));
        }

        return subjectModelList;

    }


    @Override
    public SubjectModel addSubject(String subjectJsonString) throws DuplicateTitleException, MissingTitleValueException {

        SubjectModel subjectModel = new SubjectModel(subjectJsonString);

        subjectTransactionAccess.addSubject(new Subject(subjectModel));

        return subjectModel;

    }


    public void deleteSubject(String title) throws TitleNotFoundException {

        subjectTransactionAccess.deleteSubject(title);

    }

    @Override
    public SubjectModel findSubjectByTitle(String title) throws TitleNotFoundException {

        return new SubjectModel(subjectTransactionAccess.findSubjectByTitle(title));

    }


    @Override
    public SubjectModel addStudentToSubject(String studentEmail, String title) throws EmailNotFoundException, TitleNotFoundException {

        Student student = studentTransactionAccess.findStudentByEmail(studentEmail);

        return new SubjectModel(subjectTransactionAccess.addStudentToSubject(title, student));

    }


    @Override
    public SubjectModel addTeacherToSubject(String teacherEmail, String title) throws EmailNotFoundException, TitleNotFoundException {

        Teacher teacher = teacherTransactionAccess.findTeacherByEmail(teacherEmail);

        return new SubjectModel(subjectTransactionAccess.addTeacherToSubject(title, teacher));

    }


    @Override
    public void removeStudentFromSubject(String studentEmail, String title) throws EmailNotFoundException, TitleNotFoundException, PersonNotRegisteredToSubjectException {

        Student student = studentTransactionAccess.findStudentByEmail(studentEmail);

        subjectTransactionAccess.removeStudentFromSubject(title, student);

    }


    @Override
    public void removeTeacherFromSubject(String title) throws TitleNotFoundException, PersonNotRegisteredToSubjectException {

        subjectTransactionAccess.removeTeacherFromSubject(title);

    }

}

