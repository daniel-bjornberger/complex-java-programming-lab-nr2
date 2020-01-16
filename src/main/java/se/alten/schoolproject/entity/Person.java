package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.PersonModel;
//import javax.json.*;
import javax.persistence.*;
//import java.io.Serializable;
import java.util.*;

@Entity
//@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class Person {

    //private static final long serialVersionUID = 6544404357432832048L;

    public enum PersonType {
        STUDENT,
        TEACHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    //@ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    /*@JoinTable(name = "student_subject",
                joinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"))*/
    //private Set<Subject> subject = new HashSet<>();


    /*@Transient
    private List<String> subjects = new ArrayList<>();*/


    public Person(PersonModel personModel) {

        this.firstName = personModel.getFirstname();
        this.lastName  = personModel.getLastname();
        this.email     = personModel.getEmail();

        /*for (Object subjectString: personModel.getSubjects()) {
            this.subject.add(new Subject((String) subjectString));
        }*/

    }

}


// Bytte StudentModel till PersonModel.
