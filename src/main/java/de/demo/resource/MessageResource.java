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

/**
 * A simple resource showing the last price.
 */
@Path("/messages")
public class MessageResource {

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
    @Consumes(MediaType.TEXT_PLAIN)
    public void sendMessage(String message) {
         producer.sendMessageBody(message);
    }
     
}
