package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private Student student = new Student();
    private StudentModel studentModel = new StudentModel();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Override
    public List listAllStudents(){

        return studentTransactionAccess.listAllStudents();
    }

    @Override
    public Response addStudent(String newStudent) {
        Student studentToAdd = student.toEntity(newStudent);

        if (checkForEmptyVariables(studentToAdd)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
            }
         if (studentTransactionAccess.addStudent(studentToAdd)){
             StudentModel model = studentModel.toModel(studentToAdd);
             return Response.ok(model).build();
         }
         else {
             return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
        }
    }

    @Override
    public Boolean removeStudent(String studentEmail) {
       int result = studentTransactionAccess.removeStudent(studentEmail);
        System.out.println(result);
        if (result>0) return true;
        return false;
    }

    @Override
    public Response updateStudent(String forename, String lastname, String email) {
        int hits = studentTransactionAccess.updateStudent(forename, lastname, email);
        if (hits>0){
           return Response.ok(studentModel.toModel(studentTransactionAccess
                   .findStudentByEmail(email)))
                   .build();
        }

        return Response.status(422).build();

    }

    @Override
    public Response findStudentByEmail(String email) {
        Student student = studentTransactionAccess.findStudentByEmail(email);
        System.out.println(student.toString());
        StudentModel model = studentModel.toModel(student);
        return Response.ok(model).build();

    }

    @Override
    public void updateStudentPartial(String studentModel) {
        Student studentToUpdate = student.toEntity(studentModel);
        studentTransactionAccess.updateStudentPartial(studentToUpdate);
    }

    @Override
    public List<StudentModel> findStudentByName(String forename) {
        List<Student> allStudents = studentTransactionAccess.listAllStudents();
        List<StudentModel> result = new ArrayList();
        for (Student student: allStudents){
            if (student.getForename().equalsIgnoreCase(forename)) result.add(studentModel.toModel(student));
            }
        return result;
    }

    @Override
    public boolean checkForEmptyVariables(Student studentToAdd) {
        if (Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank)) {
            return true;
        }
        return false;
    }
}
