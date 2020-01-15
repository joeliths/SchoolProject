package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import java.util.List;

public interface TeacherTransactionAccess {

    List<Teacher> listAllTeachers();
    Boolean addTeacher(Teacher teacherToAdd);
    Teacher findTeacherByEmail(String email);
    int removeTeacher(String teacher);
    int updateTeacher(String forename, String lastname, String email);
}
