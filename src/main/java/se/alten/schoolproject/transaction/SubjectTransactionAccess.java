package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exceptions.DuplicateTitleException;
import se.alten.schoolproject.exceptions.TitleNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {

    List listAllSubjects();

    void addSubject(Subject subject) throws DuplicateTitleException;

    //List<Subject> getSubjectByName(List<String> subject);

    void deleteSubject(String title) throws TitleNotFoundException;

    Subject findSubjectByTitle(String title) throws TitleNotFoundException;

}
