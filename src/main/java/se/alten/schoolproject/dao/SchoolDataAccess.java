package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
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
    public List<?> listAllStudents() {
        StudentModel studentModel;
        Student tempStudent;
        List<StudentModel> studentModelList = new ArrayList<>();
        List<?> studentList = studentTransactionAccess.listAllStudents();

        for (Object student: studentList) {
            List<String> tempSubjects = new ArrayList<>();
            tempStudent = (Student) student;
            studentModel = new StudentModel(tempStudent);
            tempStudent.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));
            studentModel.setSubjects(tempSubjects);
            studentModelList.add(studentModel);
        }
        return studentModelList;
    }


    @Override
    public List<?> listAllTeachers() {
        TeacherModel teacherModel;
        Teacher tempTeacher;
        List<TeacherModel> teacherModelList = new ArrayList<>();
        List<?> teacherList = teacherTransactionAccess.listAllTeachers();

        for (Object teacher: teacherList) {
            List<String> tempSubjects = new ArrayList<>();
            tempTeacher = (Teacher) teacher;
            teacherModel = new TeacherModel(tempTeacher);
            tempTeacher.getSubjects().forEach(subject -> tempSubjects.add(subject.getTitle()));
            teacherModel.setSubjects(tempSubjects);
            teacherModelList.add(teacherModel);
        }
        return teacherModelList;
    }


    @Override
    public StudentModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException {
        StudentModel studentModel = new StudentModel(studentJsonString);
        studentTransactionAccess.addStudent(new Student(studentModel));
        return studentModel;
    }


    @Override
    public TeacherModel addTeacher(String teacherJsonString) throws MissingPersonValueException, DuplicateEmailException {
        TeacherModel teacherModel = new TeacherModel(teacherJsonString);
        teacherTransactionAccess.addTeacher(new Teacher(teacherModel));
        return teacherModel;
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
    public StudentModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {
        StudentModel studentModel = generateStudentModel(firstName, lastName, email);
        studentTransactionAccess.updateStudent(new Student(studentModel));
        return studentModel;
    }


    @Override
    public TeacherModel updateTeacher(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {
        TeacherModel teacherModel = generateTeacherModel(firstName, lastName, email);
        teacherTransactionAccess.updateTeacher(new Teacher(teacherModel));
        return teacherModel;
    }


    private StudentModel generateStudentModel(String firstName, String lastName, String email) throws MissingPersonValueException {
        JsonObject studentJson = new JsonObject();
        studentJson.addProperty("firstName", firstName);
        studentJson.addProperty("lastName", lastName);
        studentJson.addProperty("email", email);
        return new StudentModel(studentJson.toString());
    }


    private TeacherModel generateTeacherModel(String firstName, String lastName, String email) throws MissingPersonValueException {
        JsonObject teacherJson = new JsonObject();
        teacherJson.addProperty("firstName", firstName);
        teacherJson.addProperty("lastName", lastName);
        teacherJson.addProperty("email", email);
        return new TeacherModel(teacherJson.toString());
    }


    @Override
    public StudentModel updateStudentFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {
        StudentModel studentModel = new StudentModel(studentJsonString);
        studentTransactionAccess.updateStudentFirstName(new Student(studentModel));
        return studentModel;
    }


    @Override
    public TeacherModel updateTeacherFirstName(String teacherJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {
        TeacherModel teacherModel = new TeacherModel(teacherJsonString);
        teacherTransactionAccess.updateTeacherFirstName(new Teacher(teacherModel));
        return teacherModel;
    }


    @Override
    public List<?> findStudentsByLastName(String lastName) {
        List<?> studentList = studentTransactionAccess.findStudentsByLastName(lastName);
        List<StudentModel> studentModelList = new ArrayList<>();
        for (Object student: studentList) {
            studentModelList.add(new StudentModel((Student) student));
        }
        return studentModelList;
    }


    @Override
    public List<?> findTeachersByLastName(String lastName) {
        List<?> teacherList = teacherTransactionAccess.findTeachersByLastName(lastName);
        List<TeacherModel> teacherModelList = new ArrayList<>();
        for (Object teacher: teacherList) {
            teacherModelList.add(new TeacherModel((Teacher) teacher));
        }
        return teacherModelList;
    }


    public StudentModel findStudentByEmail(String email) throws EmailNotFoundException {
        return new StudentModel(studentTransactionAccess.findStudentByEmail(email));
    }


    public TeacherModel findTeacherByEmail(String email) throws EmailNotFoundException {
        return new TeacherModel(teacherTransactionAccess.findTeacherByEmail(email));
    }


    //StudentModel findStudentByEmail(String email) throws EmailNotFoundException;
    //TeacherModel findTeacherByEmail(String email) throws EmailNotFoundException;


    @Override
    public List<?> listAllSubjects() {
        List<?> subjectList = subjectTransactionAccess.listAllSubjects();
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
    public SubjectModel addStudentToSubject(String studentEmail, String title) throws EmailNotFoundException, TitleNotFoundException, PersonAlreadyRegisteredToSubjectException {
        Student student = studentTransactionAccess.findStudentByEmail(studentEmail);
        return new SubjectModel(subjectTransactionAccess.addStudentToSubject(title, student));
    }


    @Override
    public SubjectModel addTeacherToSubject(String teacherEmail, String title) throws EmailNotFoundException, TitleNotFoundException, PersonAlreadyRegisteredToSubjectException {
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

