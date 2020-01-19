package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.PersonModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student extends Person implements Serializable {

    private static final long serialVersionUID = 7311641107269987837L;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();


    public Student(PersonModel personModel) {

        super(personModel);

    }


/*    @Override
    public void setSubjects(Set<Subject> subjects) {

    }*/

}