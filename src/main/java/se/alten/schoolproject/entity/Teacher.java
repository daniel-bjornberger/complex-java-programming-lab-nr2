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
public class Teacher extends Person implements Serializable {

    private static final long serialVersionUID = 4696957121253963185L;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();


    public Teacher(PersonModel personModel) {

        super(personModel);

    }


    /*@Override
    public void setSubjects(Set<Subject> subjects) {

    }*/

}