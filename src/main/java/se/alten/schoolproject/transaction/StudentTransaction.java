package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess {

    @PersistenceContext(unitName = "school")
    private EntityManager entityManager;


    @Override
    public List<?> listAllStudents() {
        Query query = entityManager.createQuery("SELECT s from Student s");
        return query.getResultList();
    }


    @Override
    public void addStudent(Student student) throws DuplicateEmailException {
        long numberOfTeachersWithSameEmail = entityManager
                .createQuery("SELECT COUNT(t) FROM Teacher t WHERE t.email = :email", Long.class)
                .setParameter("email", student.getEmail())
                .getSingleResult();
        if (numberOfTeachersWithSameEmail > 0) {
            throw new DuplicateEmailException(student.getEmail());
        }
        try {
            entityManager.persist(student);
            entityManager.flush();
        }
        catch (PersistenceException e) {
            throw new DuplicateEmailException(student.getEmail());
        }
    }


    @Override
    public void deleteStudent(String email) throws EmailNotFoundException {
        Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.email = :email");
        int numberOfStudentsDeleted = query.setParameter("email", email).executeUpdate();
        if (numberOfStudentsDeleted == 0) {
            throw new EmailNotFoundException(email);
        }
    }


    @Override
    public void updateStudent(Student student) throws EmailNotFoundException {
        int numberOfStudentsUpdated = entityManager
                .createQuery("UPDATE Student SET firstName = :first_name, lastName = :last_name WHERE email = :email")
                .setParameter("first_name", student.getFirstName())
                .setParameter("last_name", student.getLastName())
                .setParameter("email", student.getEmail())
                .executeUpdate();
        if (numberOfStudentsUpdated == 0) {
            throw new EmailNotFoundException(student.getEmail());
        }
    }


    @Override
    public void updateStudentFirstName(Student student) throws LastNameAndEmailNotFoundException {
        Student studentFound;
        try {
            studentFound = (Student) entityManager
                    .createQuery("SELECT s FROM Student s WHERE s.lastName = :last_name AND s.email = :email")
                    .setParameter("last_name", student.getLastName())
                    .setParameter("email", student.getEmail())
                    .getSingleResult();
        }
        catch (PersistenceException e) {
            throw new LastNameAndEmailNotFoundException(student.getLastName(), student.getEmail());
        }
        Query query = entityManager
                .createQuery("UPDATE Student SET firstName = :first_name WHERE lastName = :last_name AND email = :email");
        query.setParameter("first_name", student.getFirstName())
                .setParameter("last_name", studentFound.getLastName())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }


    @Override
    public List<?> findStudentsByLastName(String lastName) {
        return entityManager
                .createQuery("SELECT s FROM Student s WHERE s.lastName = :lastname")
                .setParameter("lastname", lastName)
                .getResultList();
    }


    @Override
    public Student findStudentByEmail(String email) throws EmailNotFoundException {
        try {
            return (Student) entityManager
                    .createQuery("SELECT s FROM Student s WHERE s.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            throw new EmailNotFoundException(email);
        }
    }

}
