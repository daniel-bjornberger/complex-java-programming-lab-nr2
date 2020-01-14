package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Person;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PersonTransactionAccess {

    List listAllPersons();

    void addPerson(Person person) throws DuplicateEmailException;

    void removePerson(String email) throws EmailNotFoundException;

    void updatePerson(Person person) throws EmailNotFoundException;

    void updateFirstName(Person person) throws LastNameAndEmailNotFoundException;

    List findPersonsByLastName(String lastName);

}