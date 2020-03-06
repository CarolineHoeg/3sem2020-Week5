//package rest;
//
//import com.google.gson.Gson;
//import dtos.PersonDTO;
//import entities.Person;
//import io.restassured.RestAssured;
//import static io.restassured.RestAssured.given;
//import io.restassured.http.ContentType;
//import io.restassured.parsing.Parser;
//import java.net.URI;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriBuilder;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.http.util.HttpStatus;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.Matchers.hasSize;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import utils.EMF_Creator;
//import utils.Settings;
//
///**
// *
// * @author carol
// */
//public class PersonResourceTest {
//
//    private static final int SERVER_PORT = Integer.parseInt(Settings.getPropertyValue("test.port"));
//    private static final String SERVER_URL = Settings.getPropertyValue("test.server");
//    private static final String TEST_DB = Settings.getPropertyValue("db.connection");
//
//    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
//    private static HttpServer httpServer;
//    private static EntityManagerFactory emf;
//    private Person p1;
//    private Person p2;
//
//    static HttpServer startServer() {
//        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
//        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
//    }
//
//    @BeforeAll
//    public static void setUpClass() {
//        EMF_Creator.startREST_TestWithDB();
//        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,
//                EMF_Creator.Strategy.CREATE);
//
//        //Set System property so the project executed by the Grizly-server wil use this same database
//        System.setProperty("IS_TEST", TEST_DB);
//        httpServer = startServer();
//
//        //Setup RestAssured
//        RestAssured.baseURI = SERVER_URL;
//        RestAssured.port = SERVER_PORT;
//        RestAssured.defaultParser = Parser.JSON;
//    }
//
//    @AfterAll
//    public static void closeTestServer() {
//        EMF_Creator.endREST_TestWithDB();
//        httpServer.shutdownNow();
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
//    @Test
//    public void testServerConnection() {
//        given().when()
//                .get("/person").
//                then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("msg", is("Hello World"));
//    }
//
//    /**
//     * Test of addPerson method, of class PersonResource.
//     */
//    @Test
//    public void testAddPerson() {
//        PersonDTO p = new PersonDTO("Josephine", "Doe", "56738910");
//        String json = new Gson().toJson(p);
//        given().contentType(ContentType.JSON)
//                .body(json)
//                .post("/person/add").
//                then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("fName", is(p.getfName()))
//                .body("lName", is(p.getlName()))
//                .body("phone", is(p.getPhone()));
//    }
//    
//    /**
//     * Test of addPerson method missing input, of class PersonResource.
//     */
//    @Test
//    public void testAddPerson_missingInput() {
//        PersonDTO p = new PersonDTO("Josephine", null, "56738910");
//        String json = new Gson().toJson(p);
//        given().contentType(ContentType.JSON)
//                .body(json)
//                .post("/person/add").
//                then()
//                .assertThat()
//                .statusCode(404)
//                .body("code", is(404))
//                .body("message", is("First Name and/or Last Name is missing"));
//    }
//
//    /**
//     * Test of deletePerson method, of class PersonResource.
//     */
//    @Test
//    public void testDeletePerson() {
//        given().when()
//                .put("/person/delete/{id}", p1.getId()).
//                then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("fName", is(p1.getFirstName()))
//                .body("lName", is(p1.getLastName()))
//                .body("phone", is(p1.getPhone()));
//    }
//
//    /**
//     * Test of deletePerson method with wrong id, of class PersonResource.
//     */
//    @Test
//    public void testDeletePerson_wrongId() {
//        given().when()
//                .put("/person/delete/{id}", Integer.MAX_VALUE).
//                then()
//                .assertThat()
//                .statusCode(404)
//                .body("code", is(404))
//                .body("message", is("Could not delete, provided id does not exist"));
//    }
//
//    /**
//     * Test of getAll method, of class PersonResource.
//     */
//    @Test
//    public void testGetAll() {
//        given().when()
//                .get("/person/all").
//                then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("all", hasSize(2))
//                .body("all.fName", hasItems(p1.getFirstName(), p2.getFirstName()))
//                .body("all.lName", hasItems(p1.getLastName(), p2.getLastName()))
//                .body("all.phone", hasItems(p1.getPhone(), p2.getPhone()));
//    }
//
//    /**
//     * Test of getById method, of class PersonResource.
//     */
//    @Test
//    public void testGetById() {
//        given().when()
//                .get("/person/get/{id}", p1.getId()).
//                then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("fName", is(p1.getFirstName()))
//                .body("lName", is(p1.getLastName()))
//                .body("phone", is(p1.getPhone()));
//    }
//
//    /**
//     * Test of getById method with wrong id, of class PersonResource.
//     */
//    @Test
//    public void testGetById_wrongId() {
//        given().when()
//                .get("/person/get/{id}", Integer.MAX_VALUE).
//                then()
//                .assertThat()
//                .statusCode(404)
//                .body("code", is(404))
//                .body("message", is("No person with provided id found"));
//    }
//
//    /**
//     * Test of editPerson method, of class PersonResource.
//     */
//    @Test
//    public void testEditPerson() {
////        p1.setFirstName("Carl");
////        PersonDTO p = new PersonDTO(p1);
////        String json = new Gson().toJson(p);
////        given().contentType(ContentType.JSON)
////                .body(json)
////                .put("/person/edit/{id}", p1.getId()).
////                then()
////                .assertThat()
////                .statusCode(HttpStatus.OK_200.getStatusCode())
////                .body("fName", is("Carl"))
////                .body("lName", is(p1.getLastName()))
////                .body("phone", is(p1.getPhone()));
//    }
//    
//    /**
//     * Test of editPerson method with wrong id, of class PersonResource.
//     */
//    @Test
//    public void testEditPerson_wrongId() {
////        given().when()
////                .put("/person/edit/{id}", Integer.MAX_VALUE).
////                then()
////                .assertThat()
////                .statusCode(404)
////                .body("code", is(404))
////                .body("message", is("No person with provided id found"));
//    }
//
//}
