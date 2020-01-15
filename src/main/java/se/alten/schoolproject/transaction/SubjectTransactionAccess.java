package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
    List listAllSubjects();
    Boolean addSubject(Subject subject);
    int removeSubject(String subjectTitle);
    List<Subject> getSubjectByName(List<String> subject);
    Subject findSingelSubject(String title);
}
