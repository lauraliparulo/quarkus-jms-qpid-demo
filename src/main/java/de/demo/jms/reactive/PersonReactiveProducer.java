package de.demo.jms.reactive;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.amqp.OutgoingAmqpMetadata;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonReactiveProducer {

	public final static String PRODUCER_QUEUE = "persons-amqp-queue";
	
	
	@Outgoing(PRODUCER_QUEUE)
	public Multi<Message<Person>> produceAStreamOfMessagesOfPersons() {
	  return Multi.createFrom().items(
	      Message.of(new Person("Luke"))
	          .addMetadata(OutgoingAmqpMetadata.builder().withDurable(false).build()),
	      Message.of(new Person("Leia"))
	          .addMetadata(OutgoingAmqpMetadata.builder().withDurable(false).build()),
	      Message.of(new Person("Obiwan"))
	          .addMetadata(OutgoingAmqpMetadata.builder().withDurable(false).build()),
	      Message.of(new Person("Palpatine"))
	          .addMetadata(OutgoingAmqpMetadata.builder().withDurable(false).build())
	  );
	}
	
}
