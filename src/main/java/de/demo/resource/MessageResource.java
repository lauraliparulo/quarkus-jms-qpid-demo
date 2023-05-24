package de.demo.resource;

import org.eclipse.microprofile.reactive.messaging.Message;

import de.demo.jms.reactive.Person;
import de.demo.jms.reactive.PersonReactiveConsumer;
import de.demo.jms.reactive.PersonReactiveProducer;
import io.smallrye.mutiny.Multi;
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
@Path("/messages")
public class MessageResource {

    @Inject
    PersonReactiveConsumer consumer;
    
    @Inject
    PersonReactiveProducer producer;
     

    @GET
    @Path("last")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
    	return "TODO";
//        return consumer.consume();
    }
     
    
    @POST
    @Path("send")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendStreamOfPersons() {
    	 Multi<Message<Person>> mp =  producer.produceAStreamOfMessagesOfPersons();
         return Response.status(201).build();
    }
     
}
