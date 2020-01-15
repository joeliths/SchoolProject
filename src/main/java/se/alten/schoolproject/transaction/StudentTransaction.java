package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess{

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllStudents() {
        Query query = entityManager.createQuery("SELECT s from Student s");
        return query.getResultList();
    }

    @Override
    public Boolean addStudent(Student studentToAdd) {
        try {
            entityManager.persist(studentToAdd);
            entityManager.flush();
            return true;
        } catch ( PersistenceException pe ) {
            return false;
        }
    }

    @Override
    public Student findStudentByEmail(String email) {
            Query query = entityManager.createQuery("SELECT s from Student s WHERE s.email= :email");
            query.setParameter("email", email);
            return (Student) query.getSingleResult();
}

    @Override
    public int removeStudent(String student) {
        Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.email = :email");
        System.out.println(student);
        return query.setParameter("email", student)
             .executeUpdate();
    }

    @Override
    public int updateStudent(String forename, String lastname, String email) {
        Query updateQuery = entityManager.createNativeQuery("UPDATE student SET forename = :forename, lastname = :lastname WHERE email = :email", Student.class);
        int hits = updateQuery.setParameter("forename", forename)
                   .setParameter("lastname", lastname)
                   .setParameter("email", email)
                   .executeUpdate();
        entityManager.flush();
        return hits;

    }

    @Override
    public void updateStudentPartial(Student student) {
        Student studentFound = (Student)entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email")
                .setParameter("email", student.getEmail()).getSingleResult();

        Query query = entityManager.createQuery("UPDATE Student SET forename = :studentForename WHERE email = :email");
        query.setParameter("studentForename", student.getForename())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }
}
