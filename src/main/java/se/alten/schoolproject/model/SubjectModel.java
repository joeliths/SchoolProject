package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel {

    private String title;
    private List<String> students = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();


    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        subjectToAdd.getStudents().forEach(student -> {
            subjectModel.students.add(student.getEmail());
        });
        subjectToAdd.getTeachers().forEach(teacher -> {
            subjectModel.teachers.add(teacher.getForename());
        });
        return subjectModel;
    }
}
