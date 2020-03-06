package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.PersonNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author carol
 */
@Provider
public class ExceptionsMapper implements ExceptionMapper<Throwable> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public ExceptionsMapper() {
    }

    @Override
    public Response toResponse(Throwable ex) {
        Logger.getLogger(ExceptionMapper.class.getName())
                .log(Level.SEVERE, null, ex);
        ExceptionDTO err = new ExceptionDTO(404, ex.getMessage());
        return Response
                .status(404)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
