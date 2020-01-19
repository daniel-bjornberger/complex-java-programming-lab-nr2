package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess {

    @PersistenceContext(unitName = "school")
    private EntityManager entityManager;


    @Override
    public List listAllTeachers() {

        Query query = entityManager.createQuery("SELECT p from Teacher p");
        return query.getResultList();

    }


    @Override
    public void addTeacher(Teacher teacher) throws DuplicateEmailException {

        try {
            entityManager.persist(teacher);
            entityManager.flush();
        }
        catch (PersistenceException e) {
            throw new DuplicateEmailException(teacher.getEmail());
        }

    }


    @Override
    public void deleteTeacher(String email) throws EmailNotFoundException {

        Query query = entityManager.createQuery("DELETE FROM Teacher p WHERE p.email = :email");

        int numberOfTeachersDeleted = query.setParameter("email", email).executeUpdate();

        if (numberOfTeachersDeleted == 0) {
            throw new EmailNotFoundException(email);
        }

    }


    @Override
    public void updateTeacher(Teacher teacher) throws EmailNotFoundException {

        Query updateQuery = entityManager.createNativeQuery("UPDATE teacher SET firstname = :firstname, lastname = :lastname WHERE email = :email", Teacher.class);

        //System.out.println("UPDATE " + this.personType + " p SET p.firstname = :firstname, p.lastname = :lastname WHERE p.email = :email");

        int numberOfTeachersUpdated = updateQuery.setParameter("firstname", teacher.getFirstName())
                .setParameter("lastname", teacher.getLastName())
                .setParameter("email", teacher.getEmail())
                .executeUpdate();

        if (numberOfTeachersUpdated == 0) {
            throw new EmailNotFoundException(teacher.getEmail());
        }

    }


    @Override
    public void updateTeacherFirstName(Teacher teacher) throws LastNameAndEmailNotFoundException {

        Teacher teacherFound;

        try {
            teacherFound = (Teacher) entityManager
                    .createQuery("SELECT p FROM Teacher p WHERE p.lastName = :lastname AND p.email = :email")
                    .setParameter("lastname", teacher.getLastName())
                    .setParameter("email", teacher.getEmail())
                    .getSingleResult();
        }
        catch (PersistenceException e) {
            throw new LastNameAndEmailNotFoundException(teacher.getLastName(), teacher.getEmail());
        }

        Query query = entityManager
                .createQuery("UPDATE Teacher SET firstname = :teacherfirstname WHERE lastName = :lastname AND email = :email");

        query.setParameter("teacherfirstname", teacher.getFirstName())
                .setParameter("lastname", teacherFound.getLastName())
                .setParameter("email", teacherFound.getEmail())
                .executeUpdate();

    }


    @Override
    public List findTeachersByLastName(String lastName) {

        return entityManager
                .createQuery("SELECT p FROM Teacher p WHERE p.lastName = :lastname")
                .setParameter("lastname", lastName)
                .getResultList();

    }


    @Override
    public Teacher findTeacherByEmail(String email) throws EmailNotFoundException {

        try {
            return (Teacher) entityManager
                    .createQuery("SELECT p FROM Teacher p WHERE p.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            throw new EmailNotFoundException(email);
        }

    }

}
