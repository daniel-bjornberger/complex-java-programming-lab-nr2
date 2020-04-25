package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Subject")
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
public class Subject implements Serializable {

    private static final long serialVersionUID = 7606014062226993082L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "subject_student",
            joinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"))
    private Set<Student> students = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teach_id", referencedColumnName = "id"))
    private Teacher teacher;


    public Subject(SubjectModel subjectModel) {
        this.title = subjectModel.getTitle();
    }

}
