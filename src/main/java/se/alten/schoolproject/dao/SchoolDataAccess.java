package se.alten.schoolproject.dao;

import com.google.gson.JsonObject;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    /*private Student student = new Student();
    private StudentModel studentModel = new StudentModel();
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();*/

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;


    @Override
    public List listAllStudents() {

        List studentList = studentTransactionAccess.listAllStudents();
        List<StudentModel> studentModelList = new ArrayList<>();

        for (Object student: studentList) {
            studentModelList.add(new StudentModel((Student) student));
        }

        return studentModelList;

    }


    @Override
    public StudentModel addStudent(String studentJsonString) throws MissingPersonValueException, DuplicateEmailException {

        StudentModel studentModel = new StudentModel(studentJsonString);

        studentTransactionAccess.addStudent(new Student(studentModel));

        return studentModel;

    }


    @Override
    public void removeStudent(String email) throws EmailNotFoundException {

        studentTransactionAccess.removeStudent(email);

    }


    @Override
    public StudentModel updateStudent(String firstName, String lastName, String email) throws MissingPersonValueException, EmailNotFoundException {

        JsonObject studentJson = new JsonObject();

        studentJson.addProperty("firstname", firstName);
        studentJson.addProperty("lastname", lastName);
        studentJson.addProperty("email", email);

        StudentModel studentModel = new StudentModel(studentJson.toString());

        studentTransactionAccess.updateStudent(new Student(studentModel));

        return studentModel;

    }


    @Override
    public StudentModel updateFirstName(String studentJsonString) throws MissingPersonValueException, LastNameAndEmailNotFoundException {

        StudentModel studentModel = new StudentModel(studentJsonString);

        studentTransactionAccess.updateFirstName(new Student(studentModel));

        return studentModel;

    }


    @Override
    public List findStudentsByLastName(String lastName) {

        List studentList = studentTransactionAccess.findStudentsByLastName(lastName);
        List<StudentModel> studentModelList = new ArrayList<>();

        for (Object student: studentList) {
            studentModelList.add(new StudentModel((Student) student));
        }

        return studentModelList;

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
