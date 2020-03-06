//package facades;
//
//import dtos.PersonDTO;
//import dtos.PersonsDTO;
//import entities.Person;
//import exceptions.MissingInputException;
//import exceptions.PersonNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import utils.EMF_Creator;
//
///**
// *
// * @author carol
// */
//public class PersonFacadeTest {
//
//    private static EntityManagerFactory emf;
//    private static PersonFacade facade;
//    private Person p1;
//    private Person p2;
//
//    @BeforeAll
//    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,
//                EMF_Creator.Strategy.DROP_AND_CREATE);
//        facade = PersonFacade.getFacade(emf);
//    }
//
//    @AfterAll
//    public static void tearDownClass() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    @BeforeEach
//    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//        p1 = new Person("John", "Doe", "45871289");
//        p2 = new Person("Jane", "Doe", "45703239");
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
//            em.persist(p1);
//            em.persist(p2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    /**
//     * Test of addPerson method, of class PersonFacade.
//     *
//     * @throws exceptions.MissingInputException
//     */
//    @Test
//    public void testAddPerson() throws MissingInputException {
//        String fName = "Joseph";
//        String lName = "Doe";
//        String phone = "32190367";
//        PersonDTO exp = new PersonDTO(fName, lName, phone);
//        PersonDTO result = facade.addPerson(fName, lName, phone);
//        assertEquals(exp, result);
//    }
//
//    /**
//     * Test of addPerson method missing input, of class PersonFacade.
//     *
//     */
//    @Test
//    public void testAddPerson_missingInput() {
//        String fName = null;
//        String lName = "Doe";
//        String phone = "29836538";
//        Exception exception = assertThrows(MissingInputException.class, () -> {
//            facade.addPerson(fName, lName, phone);
//        });
//
//        String expectedMessage = "First Name and/or Last Name is missing";
//        String actualMessage = exception.getMessage();
//        
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    /**
//     * Test of deletePerson method, of class PersonFacade.
//     *
//     * @throws exceptions.PersonNotFoundException
//     */
//    @Test
//    public void testDeletePerson() throws PersonNotFoundException {
//        int id = p1.getId();
//        PersonDTO exp = new PersonDTO(p1);
//        PersonDTO result = facade.deletePerson(id);
//        assertEquals(exp, result);
//    }
//    
//    /**
//     * Test of deletePerson method with wrong id, of class PersonFacade.
//     *
//     */
//    @Test
//    public void testDeletePerson_wrongId() {
//        int id = Integer.MAX_VALUE;
//        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
//            facade.deletePerson(id);
//        });
//
//        String expectedMessage = "Could not delete, provided id does not exist";
//        String actualMessage = exception.getMessage();
//        
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    /**
//     * Test of getPerson method, of class PersonFacade.
//     *
//     * @throws exceptions.PersonNotFoundException
//     */
//    @Test
//    public void testGetPerson() throws PersonNotFoundException {
//        int id = p2.getId();
//        PersonDTO exp = new PersonDTO(p2);
//        PersonDTO result = facade.getPerson(id);
//        assertEquals(exp, result);
//    }
//    
//    /**
//     * Test of getPerson method with wrong id, of class PersonFacade.
//     *
//     */
//    @Test
//    public void testGetPerson_wrongId() {
//        int id = Integer.MAX_VALUE;
//        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
//            facade.getPerson(id);
//        });
//
//        String expectedMessage = "No person with provided id found";
//        String actualMessage = exception.getMessage();
//        
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    /**
//     * Test of getAllPersons method, of class PersonFacade.
//     */
//    @Test
//    public void testGetAllPersons() {
//        List<Person> persons = new ArrayList<>();
//        persons.add(p1);
//        persons.add(p2);
//        PersonsDTO exp = new PersonsDTO(persons);
//        PersonsDTO result = facade.getAllPersons();
//        assertEquals(exp, result);
//    }
//
//    /**
//     * Test of editPerson method, of class PersonFacade.
//     *
//     * @throws exceptions.PersonNotFoundException
//     * @throws exceptions.MissingInputException
//     */
//    @Test
//    public void testEditPerson() throws PersonNotFoundException, MissingInputException {
//        p2.setFirstName("Jenna");
//        PersonDTO p = new PersonDTO(p2);
//        PersonDTO exp = new PersonDTO(p2);
//        PersonDTO result = facade.editPerson(p);
//        assertEquals(exp, result);
//    }
//    
//    /**
//     * Test of editPerson method wrong id, of class PersonFacade.
//     *
//     */
//    @Test
//    public void testEditPerson_wrongId() {
//        p2.setId(Integer.MAX_VALUE);
//        PersonDTO p = new PersonDTO(p2);
//        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
//            facade.editPerson(p);
//        });
//
//        String expectedMessage = "No person with provided id found";
//        String actualMessage = exception.getMessage();
//        
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    /**
//     * Test of editPerson method, of class PersonFacade.
//     *
//     */
//    @Test
//    public void testEditPerson_missingInput() {
//        Person p = new Person();
//        PersonDTO dto = new PersonDTO(p);
//        Exception exception = assertThrows(MissingInputException.class, () -> {
//            facade.editPerson(dto);
//        });
//
//        String expectedMessage = "First Name and/or Last Name is missing";
//        String actualMessage = exception.getMessage();
//        
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
