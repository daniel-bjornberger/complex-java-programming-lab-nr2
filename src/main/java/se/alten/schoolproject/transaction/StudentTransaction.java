package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

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
    public void addStudent(Student student) throws DuplicateEmailException {

        try {
            entityManager.persist(student);
            entityManager.flush();
        } catch (PersistenceException pe) {
            throw new DuplicateEmailException(student.getEmail());
        }

    }


    @Override
    public void removeStudent(String email) throws EmailNotFoundException {

        Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.email = :email");

        int numberOfStudentsRemoved = query.setParameter("email", email).executeUpdate();

        if (numberOfStudentsRemoved == 0) {
            throw new EmailNotFoundException(email);
        }

    }


    @Override
    public void updateStudent(Student student) throws EmailNotFoundException {

        Query updateQuery = entityManager.createNativeQuery("UPDATE student SET firstname = :firstname, lastname = :lastname WHERE email = :email", Student.class);

        int numberOfStudentsUpdated = updateQuery.setParameter("firstname", student.getFirstName())
                .setParameter("lastname", student.getLastName())
                .setParameter("email", student.getEmail())
                .executeUpdate();

        if (numberOfStudentsUpdated == 0) {
            throw new EmailNotFoundException(student.getEmail());
        }

    }


    @Override
    public void updateFirstName(Student student) throws LastNameAndEmailNotFoundException {

        Student studentFound;

        try {
            studentFound = (Student) entityManager
                    .createQuery("SELECT s FROM Student s WHERE s.lastName = :lastname AND s.email = :email")
                    .setParameter("lastname", student.getLastName())
                    .setParameter("email", student.getEmail())
                    .getSingleResult();
        } catch (PersistenceException pe) {
            throw new LastNameAndEmailNotFoundException(student.getLastName(), student.getEmail());
        }

        Query query = entityManager
                .createQuery("UPDATE Student SET firstname = :studentfirstname WHERE lastName = :lastname AND email = :email");

        query.setParameter("studentfirstname", student.getFirstName())
                .setParameter("lastname", studentFound.getLastName())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();

    }


    @Override
    public List findStudentsByLastName(String lastName) {

        return entityManager
                .createQuery("SELECT s FROM Student s WHERE s.lastName = :lastname")
                .setParameter("lastname", lastName)
                .getResultList();

    }

}