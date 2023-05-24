package de.demo.resource;

import de.demo.jms.MessageConsumer;
import de.demo.jms.MessageProducer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

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
//    @Produces(MediaType.APPLICATION_JSON)
    public String last() {
        return consumer.getLastMessage();
//        return Response.ok(message).build();
    }
     
    
    @POST
    @Path("send")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public void sendMessage(String message) {
         producer.sendMessageBody(message);
//         return Response.status(201).build();
    }
    
  
     
}
