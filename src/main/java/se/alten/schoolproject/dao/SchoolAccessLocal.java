package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Local;
import javax.ws.rs.core.Response;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    Response addStudent(String studentModel);

    Boolean removeStudent(String student);

    Response updateStudent(String forename, String lastname, String email);

    Response findStudentByEmail(String email);

    void updateStudentPartial(String studentModel);
    List <StudentModel> findStudentByName(String forename);

    boolean checkForEmptyVariables(Student student);
}
