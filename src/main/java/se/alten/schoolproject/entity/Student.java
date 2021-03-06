package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Student")
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 7311641107269987837L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST)
    private Set<Subject> subjects = new HashSet<>();


    public Student(StudentModel studentModel) {
        this.firstName = studentModel.getFirstName();
        this.lastName  = studentModel.getLastName();
        this.email     = studentModel.getEmail();
    }

}