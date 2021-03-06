package rest;

import webscraper.TagCounter;
import webscraper.TagDTO;
import webscraper.Tester;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * REST Web Service
 *
 * @author lam
 */
@Path("scrape")
public class WebScraperResource
{
    @Context
    private UriInfo context;
    
    @Path("sequental")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String getTagsSequental() {
        long startTime = System.nanoTime();
        LocalTime begin = LocalTime.now();

        List<TagCounter> dataFeched = Tester.runSequental();
        long endTime = System.nanoTime()-startTime;
        LocalTime end = LocalTime.now();
        long result = ChronoUnit.NANOS.between(begin, end);
        return TagDTO.getTagsAsJson("Sequental fetching",dataFeched, result);
    }
    @Path("parallel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String getTagsParrallel() throws ExecutionException, InterruptedException
    {
        LocalTime begin = LocalTime.now();

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Antal kerner: " + processors);

        List<TagCounter> dataFeched = Tester.runParrallel();

        LocalTime end = LocalTime.now();
        long result = ChronoUnit.NANOS.between(begin, end);
        return TagDTO.getTagsAsJson("Parallel fetching",dataFeched, result);
    }
    
    
}
