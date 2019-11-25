package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;
//import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 6544404357432832048L;

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

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    /*@JoinTable(name = "student_subject",
                joinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"))*/
    private Set<Subject> subject = new HashSet<>();


    /*@Transient
    private List<String> subjects = new ArrayList<>();*/


    public Student(StudentModel studentModel) {

        this.firstName = studentModel.getFirstname();
        this.lastName  = studentModel.getLastname();
        this.email     = studentModel.getEmail();

        for (Object subjectString: studentModel.getSubjects()) {
            this.subject.add(new Subject((String) subjectString));
        }

    }

}
