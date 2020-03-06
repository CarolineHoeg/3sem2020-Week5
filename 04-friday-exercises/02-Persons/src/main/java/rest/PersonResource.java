package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import exceptions.ExceptionsMapper;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF
            = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ExceptionsMapper EXCEPTION_MAPPER 
            = new ExceptionsMapper();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String json) {
        try {
            PersonDTO p = GSON.fromJson(json, PersonDTO.class);
            PersonDTO person = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone());
            return Response.ok(GSON.toJson(person)).build();
        } catch (MissingInputException e) {
            return EXCEPTION_MAPPER.toResponse(e);
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        try {
            PersonDTO person = FACADE.deletePerson(id);
            return Response.ok(GSON.toJson(person)).build();
        } catch (PersonNotFoundException e) {
            return EXCEPTION_MAPPER.toResponse(e);
        }
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String all = GSON.toJson(FACADE.getAllPersons());
        return Response.ok(all).build();
    }
    
    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        try {
            String person = GSON.toJson(FACADE.getPerson(id));
            return Response.ok(person).build();
        } catch (PersonNotFoundException e) {
            return EXCEPTION_MAPPER.toResponse(e);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edit/{id}")
    public Response editPerson(String json) {
        PersonDTO p = GSON.fromJson(json, PersonDTO.class);
        try {
            PersonDTO person = FACADE.editPerson(p);
            return Response.ok(GSON.toJson(person)).build();
        } catch (PersonNotFoundException | MissingInputException e) {
            return EXCEPTION_MAPPER.toResponse(e);
        }
    }

}
