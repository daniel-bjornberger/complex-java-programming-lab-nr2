package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.MissingTitleValueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectModel implements Serializable {

    private static final long serialVersionUID = -7362520530483625608L;

    private String title;
    private List<StudentModel> students = new ArrayList<>();
    private TeacherModel teacher;


    public SubjectModel(Subject subject) {
        this.title = subject.getTitle();
        subject.getStudents().forEach(student -> this.students.add(new StudentModel(student)));
        this.teacher = new TeacherModel(subject.getTeacher());
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
