package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess{

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllSubjects() {
        Query query = entityManager.createQuery("SELECT s FROM Subject s");
        return query.getResultList();
    }

    @Override
    public Boolean addSubject(Subject subject) {
        try {
            entityManager.persist(subject);
            entityManager.flush();

            return true;
        } catch ( PersistenceException pe ) {
            return false;
        }
    }

    @Override
    public int removeSubject(String subject) {
        Query query = entityManager.createQuery("DELETE FROM Subject s WHERE s.title = :subject");
        return query.setParameter("subject", subject)
                .executeUpdate();
    }
    @Override
    public List<Subject> getSubjectByName(List<String> subject) {

        String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :subject";
        TypedQuery<Subject> query = entityManager.createQuery(queryStr, Subject.class);
        query.setParameter("subject", subject);

        return query.getResultList();

    }

    @Override
    public Subject findSingelSubject(String title) {
        System.out.println("here!" + title);
        String queryStr = "SELECT sub FROM Subject sub WHERE sub.title= :title";
        TypedQuery<Subject> query = entityManager.createQuery(queryStr, Subject.class);
        query.setParameter("title", title);
        Subject subject = query.getSingleResult();
        return subject;

       /* Query query = entityManager.createQuery("SELECT sub from Subject sub WHERE sub.title= :title");
        query.setParameter("title", title);
        return (Subject) query.getSingleResult();*/
    }
}
