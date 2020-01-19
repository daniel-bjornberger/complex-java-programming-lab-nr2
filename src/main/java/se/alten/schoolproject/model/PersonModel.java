package se.alten.schoolproject.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import se.alten.schoolproject.entity.Person;
import se.alten.schoolproject.entity.Subject;
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
    //private Set<String> subjects = new HashSet<>();
    private List<String> subjects = new ArrayList<>();


    public PersonModel(Person person) {

        this.firstname = person.getFirstName();
        this.lastname  = person.getLastName();
        this.email     = person.getEmail();

        for (Object subject: person.getSubjects()) {
            this.subjects.add(((Subject) subject).getTitle());
        }

        //this.subjects  = person.getSubjects();

    }


    public PersonModel(String personJsonString) throws JsonSyntaxException, MissingPersonValueException {

        PersonModel temp = new Gson().fromJson(personJsonString, PersonModel.class);

        if (empty(temp.getFirstname()) || empty(temp.getLastname()) || empty(temp.getEmail())) {
            throw new MissingPersonValueException();
        }

        this.firstname = temp.getFirstname();
        this.lastname  = temp.getLastname();
        this.email     = temp.getEmail();
        //this.subjects  = temp.getSubjects();

    }


    private static boolean empty(String string) {

        return string == null || string.isBlank();

    }


    /*public List<PersonModel> toModelList(List<Student> students) {

        List<PersonModel> studentModels = new ArrayList<>();

        students.forEach(student -> {
            PersonModel sm = new PersonModel();
            sm.firstname = student.getFirstName();
            sm.lastname = student.getLastName();
            sm.email = student.getEmail();
            student.getSubject().forEach(subject -> {
                sm.subjects.add(subject.getTitle());
            });

            studentModels.add(sm);

        });

        return studentModels;

    }*/

}


// Bytt namn, från StudentModel till PersonModel