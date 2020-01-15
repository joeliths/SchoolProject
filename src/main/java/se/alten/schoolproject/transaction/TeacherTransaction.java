package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess {

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List<Teacher> listAllTeachers() {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t");
        System.out.println("Inside transaction");
        return query.getResultList();
    }

    @Override
    public Boolean addTeacher(Teacher teacherToAdd) {
        try{
            entityManager.persist(teacherToAdd);
            entityManager.flush();
            System.out.println("IS about to go back to DOA from teacher transaction");
            return true;
        }catch (PersistenceException pe){
            System.out.println("Enter addTeacher Transaction catch");
            return false;
        }
    }

    @Override
    public Teacher findTeacherByEmail(String teacherEmail) {
        System.out.println("here!" + teacherEmail);
        String queryStr = "SELECT t FROM Teacher t WHERE t.email= :email";
        TypedQuery<Teacher> query = entityManager.createQuery(queryStr, Teacher.class);
        query.setParameter("email", teacherEmail);
        Teacher teacher = query.getSingleResult();
        System.out.println(teacher);
        return teacher;
    }

    @Override
    public int removeTeacher(String teacher) {
        Query query = entityManager.createQuery("DELETE FROM Teacher t WHERE t.email = :teacher");
        return query.setParameter("teacher", teacher)
                .executeUpdate();
    }

    @Override
    public int updateTeacher(String forename, String lastname, String email) {
        return 0;
    }
}
