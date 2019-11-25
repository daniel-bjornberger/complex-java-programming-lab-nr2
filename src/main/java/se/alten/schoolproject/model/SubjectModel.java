package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.MissingTitleValueException;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class SubjectModel {

    //private Long id;
    private String title;

    /*public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        return subjectModel;
    }*/


    public SubjectModel(Subject subject) {
        this.title = subject.getTitle();
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
