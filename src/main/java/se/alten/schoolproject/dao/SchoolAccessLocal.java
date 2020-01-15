package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Local;
import javax.ws.rs.core.Response;


@Local
public interface SchoolAccessLocal {

    Response listAllStudents() throws Exception;

    Response addStudent(String studentModel);

    Response removeStudent(String student);

    Response updateStudent(String forename, String lastname, String email);

    Response findStudentByEmail(String email);

    void updateStudentPartial(String studentModel);

    Response findStudentByName(String forename);

    Response addStudentToSubject(String studentEmail, String subjectTitle);

    Response removeStudentFromSubject(String studentEmail, String subjectTitle);

    Response listAllSubjects();

    Response addSubject(String subjectModel);

    Response removeSubject(String title);

    Response listAllTeachers();

    Response addTeacher(String teacherModel);

    Response findTeacherByEmail(String teacherEmail);

    Response removeTeacher(String email);

    Response addTecherToSubject(String teacherEmail, String subjectTitle);

    Response removeTeacherFromSubject(String teacherEmail, String subjectTitle);

    Response findSingelSubjectByname(String title);

    boolean checkForEmptyVariables(Student student);

    boolean checkForEmptyVariables(Teacher teacher);

}
