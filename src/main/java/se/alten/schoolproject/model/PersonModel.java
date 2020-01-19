package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.MissingPersonValueException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonModel {

    private String firstname;
    private String lastname;
    private String email;
    private List<String> subjects = new ArrayList<>();


    public PersonModel(Student student) {

        this.firstname = student.getFirstName();
        this.lastname  = student.getLastName();
        this.email     = student.getEmail();

        for (Object subject: student.getSubjects()) {
            this.subjects.add(((Subject) subject).getTitle());
        }

    }


    public PersonModel(Teacher teacher) {

        this.firstname = teacher.getFirstName();
        this.lastname  = teacher.getLastName();
        this.email     = teacher.getEmail();

        for (Object subject: teacher.getSubjects()) {
            this.subjects.add(((Subject) subject).getTitle());
        }

    }


    public PersonModel(String personJsonString) throws JsonSyntaxException, MissingPersonValueException {

        PersonModel temp = new Gson().fromJson(personJsonString, PersonModel.class);

        if (empty(temp.getFirstname()) || empty(temp.getLastname()) || empty(temp.getEmail())) {
            throw new MissingPersonValueException();
        }

        this.firstname = temp.getFirstname();
        this.lastname  = temp.getLastname();
        this.email     = temp.getEmail();

    }


    private static boolean empty(String string) {

        return string == null || string.isBlank();

    }

}