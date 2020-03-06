package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone)
            throws MissingInputException {
        if (fName == null || lName == null) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        } else {
            EntityManager em = getEntityManager();
            Person p = new Person(fName, lName, phone);
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        } else {
            em.remove(p);
        }
        em.getTransaction().commit();
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException("No person with provided id found");
        }
        em.getTransaction().commit();
        return new PersonDTO(p);
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> q = em.createNamedQuery("Person.getAll", Person.class);
            return new PersonsDTO(q.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) 
            throws PersonNotFoundException, MissingInputException {
        if (p.getfName() == null || p.getlName() == null) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        } else {
            //Problem...
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            PersonDTO person = em.merge(p);
            if (person == null) {
                throw new PersonNotFoundException("No person with provided id found");
            }
            em.getTransaction().commit();
            return person;
        }
    }

}
