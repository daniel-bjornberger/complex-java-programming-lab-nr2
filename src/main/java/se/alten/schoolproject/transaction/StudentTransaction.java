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
        Query query = entityManager.createQuery("SELECT p from Student p");
        return query.getResultList();
    }


    @Override
    public void addStudent(Student student) throws DuplicateEmailException {
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
        Query query = entityManager.createQuery("DELETE FROM Student p WHERE p.email = :email");
        int numberOfStudentsDeleted = query.setParameter("email", email).executeUpdate();
        if (numberOfStudentsDeleted == 0) {
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
    public void updateStudentFirstName(Student student) throws LastNameAndEmailNotFoundException {
        Student studentFound;
        try {
            studentFound = (Student) entityManager
                    .createQuery("SELECT p FROM Student p WHERE p.lastName = :lastname AND p.email = :email")
                    .setParameter("lastname", student.getLastName())
                    .setParameter("email", student.getEmail())
                    .getSingleResult();
        }
        catch (PersistenceException e) {
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
    public List<?> findStudentsByLastName(String lastName) {
        return entityManager
                .createQuery("SELECT p FROM Student p WHERE p.lastName = :lastname")
                .setParameter("lastname", lastName)
                .getResultList();
    }


    @Override
    public Student findStudentByEmail(String email) throws EmailNotFoundException {
        try {
            return (Student) entityManager
                    .createQuery("SELECT p FROM Student p WHERE p.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            throw new EmailNotFoundException(email);
        }
    }

}
