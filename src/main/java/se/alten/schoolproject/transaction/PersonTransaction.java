package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Person;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

//import javax.ejb.Stateless;
//import javax.enterprise.inject.Default;
//import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/*@Stateless
@Default*/
public abstract class PersonTransaction implements PersonTransactionAccess {

    @PersistenceContext(unitName = "school")
    private EntityManager entityManager;

    private String personType;

    //@Inject
    public PersonTransaction(String personType) {
        this.personType = personType;
    }


    @Override
    public List listAllPersons() {

        //Query query = entityManager.createQuery("SELECT p from Student p");
        Query query = entityManager.createQuery("SELECT p from " + this.personType + " p");
        return query.getResultList();

    }


    @Override
    public void addPerson(Person person) throws DuplicateEmailException {

        try {
            entityManager.persist(person);  // Kommer detta att fungera? Dela upp i student & teacher?
            entityManager.flush();
        }
        catch (PersistenceException e) {
            throw new DuplicateEmailException(person.getEmail());
        }

    }


    @Override
    public void deletePerson(String email) throws EmailNotFoundException {

        //Query query = entityManager.createQuery("DELETE FROM Student p WHERE p.email = :email");
        Query query = entityManager.createQuery("DELETE FROM " + this.personType + " p WHERE p.email = :email");

        int numberOfPersonsDeleted = query.setParameter("email", email).executeUpdate();

        if (numberOfPersonsDeleted == 0) {
            throw new EmailNotFoundException(email);
        }

    }


    @Override
    public void updatePerson(Person person) throws EmailNotFoundException {

        //Query updateQuery = entityManager.createNativeQuery("UPDATE student SET firstname = :firstname, lastname = :lastname WHERE email = :email", Student.class);

        Query updateQuery = entityManager.createQuery("UPDATE " + this.personType + " p SET p.firstname = :firstname, p.lastname = :lastname WHERE p.email = :email");

        int numberOfPersonsUpdated = updateQuery.setParameter("firstname", person.getFirstName())
                .setParameter("lastname", person.getLastName())
                .setParameter("email", person.getEmail())
                .executeUpdate();

        if (numberOfPersonsUpdated == 0) {
            throw new EmailNotFoundException(person.getEmail());
        }

    }


    @Override
    public void updateFirstName(Person person) throws LastNameAndEmailNotFoundException {

        Person personFound;

        try {
            personFound = (Person) entityManager
                    //.createQuery("SELECT p FROM Student p WHERE p.lastName = :lastname AND p.email = :email")
                    .createQuery("SELECT p FROM " + this.personType + " p WHERE p.lastName = :lastname AND p.email = :email")
                    .setParameter("lastname", person.getLastName())
                    .setParameter("email", person.getEmail())
                    .getSingleResult();
        }
        catch (PersistenceException e) {
            throw new LastNameAndEmailNotFoundException(person.getLastName(), person.getEmail());
        }

        Query query = entityManager
                //.createQuery("UPDATE Student SET firstname = :personfirstname WHERE lastName = :lastname AND email = :email");
                .createQuery("UPDATE " + this.personType + " p SET p.firstname = :personfirstname WHERE p.lastName = :lastname AND p.email = :email");

        query.setParameter("personfirstname", person.getFirstName())
                .setParameter("lastname", personFound.getLastName())
                .setParameter("email", personFound.getEmail())
                .executeUpdate();

    }


    @Override
    public List findPersonsByLastName(String lastName) {

        return entityManager
                .createQuery("SELECT p FROM " + this.personType + " p WHERE p.lastName = :lastname")
                .setParameter("lastname", lastName)
                .getResultList();

    }

}