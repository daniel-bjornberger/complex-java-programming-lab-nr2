package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.DuplicateTitleException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.TitleNotFoundException;

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
    public void addSubject(Subject subject) throws DuplicateTitleException {

        try {
            entityManager.persist(subject);
            entityManager.flush();
        }
        catch (PersistenceException e) {
            throw new DuplicateTitleException(subject.getTitle());
        }

    }


    /*@Override
    public List<Subject> getSubjectByName(List<String> subject) {

        String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :subject";
        TypedQuery<Subject> query = entityManager.createQuery(queryStr, Subject.class);
        query.setParameter("subject", subject);

        return query.getResultList();

     }*/

    public void deleteSubject(String title) throws TitleNotFoundException {

        Query query = entityManager.createQuery("DELETE FROM Subject s WHERE s.title = :title");

        int numberOfSubjectsDeleted = query.setParameter("title", title).executeUpdate();

        if (numberOfSubjectsDeleted == 0) {
            throw new TitleNotFoundException(title);
        }

    }


    @Override
    public Subject findSubjectByTitle(String title) throws TitleNotFoundException {

        try {
            return (Subject) entityManager
                    .createQuery("SELECT s FROM Subject s WHERE s.title = :title")
                    .setParameter("title", title)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            throw new TitleNotFoundException(title);
        }

    }


    public Subject addStudentToSubject(String title, Student student) throws TitleNotFoundException {

        try {
            Subject subject = findSubjectByTitle(title);

            subject.getStudents().add(student);

            entityManager.merge(subject);

            return subject;
        }
        catch (NoResultException e) {
            throw new TitleNotFoundException(title);
        }

    }


    public Subject addTeacherToSubject(String title, Teacher teacher) throws TitleNotFoundException {

        try {
            Subject subject = findSubjectByTitle(title);

            subject.setTeacher(teacher);

            entityManager.merge(subject);

            return subject;
        }
        catch (NoResultException e) {
            throw new TitleNotFoundException(title);
        }

    }

}
