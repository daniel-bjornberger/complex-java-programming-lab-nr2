package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private String forename;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

        switch (student.getForename()) {
            case "empty":
                studentModel.setForename("empty");
                return studentModel;
            case "duplicate":
                studentModel.setForename("duplicate");
                return studentModel;
            default:
                studentModel.setForename(student.getForename());
                studentModel.setLastname(student.getLastname());
                studentModel.setEmail(student.getEmail());
                student.getSubject().forEach(subject -> {
                    studentModel.subjects.add(subject.getTitle());
                });
                return studentModel;
        }
    }

    public List<StudentModel> toModelList(List<Student> students) {

        List<StudentModel> studentModels = new ArrayList<>();

        students.forEach(student -> {
            StudentModel sm = new StudentModel();
            sm.forename = student.getForename();
            sm.lastname = student.getLastname();
            sm.email = student.getEmail();
            student.getSubject().forEach(subject -> {
                sm.subjects.add(subject.getTitle());
            });

            studentModels.add(sm);
        });
        return studentModels;
    }
}
