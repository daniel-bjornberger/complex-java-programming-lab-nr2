package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.MissingTitleValueException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubjectModel {

    private String title;

    private List<PersonModel> students = new ArrayList<>();

    private PersonModel teacher;


    public SubjectModel(Subject subject) {
        this.title = subject.getTitle();

        subject.getStudents().forEach(student -> this.students.add(new PersonModel(student)));

        this.teacher = new PersonModel(subject.getTeacher());
    }


    public SubjectModel(String subjectJsonString) throws JsonSyntaxException, MissingTitleValueException {

        SubjectModel temp = new Gson().fromJson(subjectJsonString, SubjectModel.class);

        if (empty(temp.getTitle())) {
            throw new MissingTitleValueException();
        }

        this.title = temp.getTitle();

    }


    private static boolean empty(String string) {

        return string == null || string.isBlank();

    }

}
