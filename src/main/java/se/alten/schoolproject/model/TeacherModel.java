package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TeacherModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String forename;
    private String lastname;
    private String email;
    private List<String> subjects = new ArrayList<>();


    public TeacherModel toModel (Teacher teacher){
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setForename(teacher.getForename());
        teacherModel.setLastname(teacher.getLastname());
        teacherModel.setEmail(teacher.getEmail());
        teacher.getSubject().forEach(subject -> {
            teacherModel.subjects.add(subject.getTitle());
        });
        return teacherModel;
    }
}
