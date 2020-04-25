package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.MissingPersonValueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentModel implements Serializable {

    private static final long serialVersionUID = -908035086342264597L;

    private String firstName;
    private String lastName;
    private String email;
    private List<String> subjects = new ArrayList<>();


    public StudentModel(Student student) {
        this.firstName = student.getFirstName();
        this.lastName  = student.getLastName();
        this.email     = student.getEmail();
        for (Object subject: student.getSubjects()) {
            this.subjects.add(((Subject) subject).getTitle());
        }
    }


    public StudentModel(String studentJsonString) throws JsonSyntaxException, MissingPersonValueException {
        StudentModel temp = new Gson().fromJson(studentJsonString, StudentModel.class);
        if (empty(temp.getFirstName()) || empty(temp.getLastName()) || empty(temp.getEmail())) {
            throw new MissingPersonValueException();
        }
        this.firstName = temp.getFirstName();
        this.lastName  = temp.getLastName();
        this.email     = temp.getEmail();
    }


    private static boolean empty(String string) {
        return string == null || string.isBlank();
    }

}