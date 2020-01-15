package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel implements Serializable {
    private static final long serialVersionUID = 1L;



    private String forename;
    private String lastname;
    private String email;
    private List<String> subjects = new ArrayList<>();


    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();
        studentModel.setForename(student.getForename());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());
        student.getSubject().forEach(subject -> {
            subjects.add(subject.getTitle());
            });
        return studentModel;
        }

}
