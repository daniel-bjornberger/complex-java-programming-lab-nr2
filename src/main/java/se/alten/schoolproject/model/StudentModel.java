package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private String firstname;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();


    public StudentModel(Student student) {

        this.setFirstname(student.getFirstName());
        this.setLastname(student.getLastName());
        this.setEmail(student.getEmail());
        this.setSubjects(student.getSubjects());

    }


    public StudentModel(String studentJsonString) throws JsonSyntaxException, ModelExceptions.MissingValueException {

        StudentModel temp = new Gson().fromJson(studentJsonString, StudentModel.class);

        if (empty(temp.getFirstname()) || empty(temp.getLastname()) || empty(temp.getEmail())) {
            throw new ModelExceptions.MissingValueException();
        }

        this.firstname = temp.getFirstname();
        this.lastname  = temp.getLastname();
        this.email     = temp.getEmail();
        this.subjects  = temp.getSubjects();

    }


    private static boolean empty(String string) {

        return string == null || string.isBlank();

    }


    public List<StudentModel> toModelList(List<Student> students) {

        List<StudentModel> studentModels = new ArrayList<>();

        students.forEach(student -> {
            StudentModel sm = new StudentModel();
            sm.firstname = student.getFirstName();
            sm.lastname = student.getLastName();
            sm.email = student.getEmail();
            student.getSubject().forEach(subject -> {
                sm.subjects.add(subject.getTitle());
            });

            studentModels.add(sm);

        });

        return studentModels;

    }

}
