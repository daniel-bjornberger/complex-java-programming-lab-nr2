package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.PersonModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Teacher implements Serializable {

    private static final long serialVersionUID = 4696957121253963185L;

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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();


    public Teacher(PersonModel personModel) {

        this.firstName = personModel.getFirstname();
        this.lastName  = personModel.getLastname();
        this.email     = personModel.getEmail();

    }

}