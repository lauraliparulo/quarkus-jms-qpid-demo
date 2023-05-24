package de.demo.jms.reactive;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.amqp.IncomingAmqpMetadata;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonReactiveConsumer {

	public final static String CONSUMER_QUEUE = "persons-from-amqp";
	
	@Incoming(CONSUMER_QUEUE)
	CompletionStage<Void> consume(Message<Person> person) {
	    Optional<IncomingAmqpMetadata> metadata = person
	            .getMetadata(IncomingAmqpMetadata.class);
	    metadata.ifPresent(meta -> {
	        String address = meta.getAddress();
	        String subject = meta.getSubject();
	        });

	    return person.ack();
	}
	
  
}
