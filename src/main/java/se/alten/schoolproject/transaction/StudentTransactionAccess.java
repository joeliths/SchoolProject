package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List listAllStudents();
    Boolean addStudent(Student studentToAdd);
    Student findStudentByEmail(String email);
    int removeStudent(String student);
    int updateStudent(String forename, String lastname, String email);
    void updateStudentPartial(Student studentToUpdate);
}
