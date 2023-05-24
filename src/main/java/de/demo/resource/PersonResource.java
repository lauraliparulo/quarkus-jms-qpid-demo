package de.demo.resource;

import org.jboss.logging.Logger;

import de.demo.jms.MessageConsumer;
import de.demo.jms.MessageProducer;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * A simple resource showing the last price.
 */
@Path("/persons")
public class PersonResource {

    @Inject
    MessageConsumer consumer;
    
    @Inject
    MessageProducer producer;
     

    @GET
    @Path("last")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
        return consumer.getLastMessage();
    }
     
    
    @POST
    @Path("send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(String message) {
         producer.sendMessageBody(message);
         return Response.status(201).build();
    }
     
}
