package rest;

import com.google.gson.Gson;
import dtos.ChuckDTO;
import dtos.CombinedDTO;
import dtos.DadDTO;
import utils.HttpUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

/**
 * REST Web Service
 *
 * @author lam
 */
@Path("jokes")
public class JokeResource {

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public String getJokes() throws IOException, ExecutionException, InterruptedException
    {
        LocalTime start = LocalTime.now();
        String result = gson.toJson(HttpUtils.fetchDataSequential());
        LocalTime end = LocalTime.now();
        System.out.println("Sequential: " + ChronoUnit.MILLIS.between(start, end));

        start = LocalTime.now();
        result = gson.toJson(HttpUtils.fetchDataParallel());
        end = LocalTime.now();
        System.out.println("Parallel: " + ChronoUnit.MILLIS.between(start, end));

        return result;
    }

   
}
