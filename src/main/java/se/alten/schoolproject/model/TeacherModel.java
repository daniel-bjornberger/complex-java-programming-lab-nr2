package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.MissingPersonValueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherModel implements Serializable {

    private static final long serialVersionUID = -2116911621809260146L;

    private String firstName;
    private String lastName;
    private String email;
    private List<String> subjects = new ArrayList<>();

    public TeacherModel(Teacher teacher) {
        this.firstName = teacher.getFirstName();
        this.lastName  = teacher.getLastName();
        this.email     = teacher.getEmail();
        for (Object subject: teacher.getSubjects()) {
            this.subjects.add(((Subject) subject).getTitle());
        }
    }


    public TeacherModel(String teacherJsonString) throws JsonSyntaxException, MissingPersonValueException {
        TeacherModel temp = new Gson().fromJson(teacherJsonString, TeacherModel.class);
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