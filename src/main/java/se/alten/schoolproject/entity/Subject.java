package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import se.alten.schoolproject.model.SubjectModel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    private static final long serialVersionUID = 7606014062226993082L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_student",
            joinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"))
    private Set<Student> students = new HashSet<>();

    /*public Subject toEntity(String subjectModel) {
        JsonReader reader = Json.createReader(new StringReader(subjectModel));

        JsonObject jsonObject = reader.readObject();

        Subject subject = new Subject();

        if ( jsonObject.containsKey("subject")) {

            subject.setTitle(jsonObject.getString("subject"));
        } else {
            subject.setTitle("");
        }

        return subject;
    }*/

    public Subject(String titleString) {
        this.title = titleString;
    }


    public Subject(SubjectModel subjectModel) {
        this.title = subjectModel.getTitle();
    }

}
