package com.jje.membercenter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Path("/conf")
@Component("configurationResource")
public class ConfigurationResource {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/log/package/{p}/{l}")
    public Response index(@PathParam("p") String p, @PathParam("l") String l) {
        Level level = Level.toLevel(l);
        Logger logger = LogManager.getLogger(p);
        logger.setLevel(level);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/log/root/{l}")
    public Response index(@PathParam("l") String l) {
        Level level = Level.toLevel(l);
        LogManager.getRootLogger().setLevel(level);
        return Response.ok().build();
    }

}
