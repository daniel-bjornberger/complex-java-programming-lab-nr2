package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.TeacherModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Teacher")
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
public class Teacher implements Serializable {

    private static final long serialVersionUID = 4696957121253963185L;

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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private Set<Subject> subjects = new HashSet<>();


    public Teacher(TeacherModel teacherModel) {
        this.firstName = teacherModel.getFirstName();
        this.lastName  = teacherModel.getLastName();
        this.email     = teacherModel.getEmail();
    }

}