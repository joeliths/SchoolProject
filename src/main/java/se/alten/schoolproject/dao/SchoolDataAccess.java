package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

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
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();
    private Teacher teacher = new Teacher();
    private TeacherModel teacherModel = new TeacherModel();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;

    @Override
    public Response listAllStudents(){

        List<Student> students = studentTransactionAccess.listAllStudents();
        List<StudentModel> models = new ArrayList<>();
        for (Student student:students){
            models.add(studentModel.toModel(student));
        }
        return Response.ok(models).build();
    }

    @Override
    public Response addStudent(String newStudent) {
        Student studentToAdd = student.toEntity(newStudent);
        if (checkForEmptyVariables(studentToAdd)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
            }
        System.out.println("Is about to call addstudent from DOA to transaction");
         if (studentTransactionAccess.addStudent(studentToAdd)){
             List<Subject> subjects = subjectTransactionAccess.getSubjectByName(studentToAdd.getSubjects());

             subjects.forEach(sub -> {
                 studentToAdd.getSubject().add(sub);
             });
             StudentModel model = studentModel.toModel(studentToAdd);
             return Response.ok(model).build();
         }
         else {
             return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
        }
    }

    @Override
    public Response removeStudent(String studentEmail) {
       int result = studentTransactionAccess.removeStudent(studentEmail);
        if (result>0) return Response.ok("Student: "+studentEmail+" has been deleted").build();
        return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Can't find student!\"}").build();
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
    public Response findStudentByName(String forename) {
        List<Student> allStudents = studentTransactionAccess.listAllStudents();
        List<StudentModel> models = new ArrayList();
        for (Student student: allStudents){
            if (student.getForename().equalsIgnoreCase(forename)) models.add(studentModel.toModel(student));
            }
        return Response.ok(models).build();
    }

    @Override
    public Response addStudentToSubject(String studentEmail, String subjectTitle) {
        System.out.println("Is about to call transactions with"+ studentEmail +" "+subjectTitle);
        Student student = studentTransactionAccess.findStudentByEmail(studentEmail);
        Subject subject = subjectTransactionAccess.findSingelSubject(subjectTitle);
        System.out.println("Returned safe from transaction find");
        subject.getStudents().add(student);
        SubjectModel model = subjectModel.toModel(subject);
        System.out.println(model);
        return Response.ok(model).build();
    }

    @Override
    public Response removeStudentFromSubject(String studentEmail, String subjectTitle) {
        return null;
    }

    @Override
    public Response listAllSubjects() {
        List<Subject> subjects = subjectTransactionAccess.listAllSubjects();
        List<SubjectModel> models = new ArrayList<>();
        for (Subject subject:subjects){
            models.add(subjectModel.toModel(subject));
        }
        return Response.ok(models).build();
    }

    @Override
    public Response addSubject(String newSubject) {
        Subject subjectToAdd = subject.toEntity(newSubject);
        if (subjectTransactionAccess.addSubject(subjectToAdd)){
            SubjectModel model = subjectModel.toModel(subjectToAdd);
            return Response.ok(model).build();

        }
        return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Subject already registered!\"}").build();
    }

    @Override
    public Response removeSubject(String title) {
        int hits = subjectTransactionAccess.removeSubject(title);
        if (hits>0) return Response.ok("Subject: "+title+" has been deleted").build();
        return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Can't find subject!\"}").build();
    }

    @Override
    public Response listAllTeachers() {
        List<Teacher> teachers= teacherTransactionAccess.listAllTeachers();
        List<TeacherModel> models = new ArrayList<>();
        for (Teacher teacher:teachers){
            models.add(teacherModel.toModel(teacher));
        }
        return Response.ok(models).build();
    }

    @Override
    public Response addTeacher(String newTeacher) {
        Teacher teacherToAdd = teacher.toEntity(newTeacher);
        System.out.println("is about to call checkForEmptyVariables from Dao");
        System.out.println(teacherToAdd.toString());
        if (checkForEmptyVariables(teacherToAdd)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
        }
        System.out.println("Is about to call addTeacher Transaction from Dao");
        if(teacherTransactionAccess.addTeacher(teacherToAdd)){
            System.out.println(teacherToAdd);
            TeacherModel model = teacherModel.toModel(teacherToAdd);
            return Response.ok(model).build();
        }

        return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
    }

    @Override
    public Response findTeacherByEmail(String teacherEmail) {
        Teacher teacher = teacherTransactionAccess.findTeacherByEmail(teacherEmail);
        TeacherModel model = teacherModel.toModel(teacher);
        return Response.ok(model).build();
    }

    @Override
    public Response removeTeacher(String email) {
        int hits = teacherTransactionAccess.removeTeacher(email);
        if (hits>0) return Response.ok("Teacher: "+email+" has been deleted").build();
        return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Can't find teacher!\"}").build();
    }

    @Override
    public Response addTecherToSubject(String teacherEmail, String subjectTitle) {
        System.out.println(teacherEmail);
        System.out.println(subjectTitle);
        Teacher teacher = teacherTransactionAccess.findTeacherByEmail(teacherEmail);
        Subject subject = subjectTransactionAccess.findSingelSubject(subjectTitle);
        subject.getTeachers().add(teacher);
        SubjectModel model = subjectModel.toModel(subject);
        System.out.println(model);
        return Response.ok(model).build();
    }

    @Override
    public Response removeTeacherFromSubject(String teacherEmail, String subjectTitle) {
        return null;
    }

    @Override
    public Response findSingelSubjectByname(String title) {
        Subject subjectToadd = subjectTransactionAccess.findSingelSubject(title);
        SubjectModel model = subjectModel.toModel(subjectToadd);
        return Response.ok(model).build();
    }

    @Override
    public boolean checkForEmptyVariables(Student studentToAdd) {
        if (Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkForEmptyVariables(Teacher teacherToAdd) {
        if (Stream.of(teacherToAdd.getForename(), teacherToAdd.getLastname(), teacherToAdd.getEmail()).anyMatch(String::isBlank)) {
            return true;
        }
        return false;
    }

}
