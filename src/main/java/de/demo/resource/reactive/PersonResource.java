package de.demo.resource.reactive;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.resteasy.reactive.RestStreamElementType;

import de.demo.jms.reactive.Person;
import io.smallrye.common.annotation.NonBlocking;
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
@Path("/persons")
public class PersonResource {

	@Inject
	@Channel("persons-amqp-queue")
	Multi<Message<Person>> persons;

	@Inject
	@Channel("persons-amqp-queue")
	Emitter<Message<Person>> personEmitter;

	@GET
	@Path("/last")
	@RestStreamElementType(MediaType.TEXT_PLAIN)
	public Multi<Message<Person>> stream() {
		return persons;
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public void addPerson(String name) {
		CompletionStage<Void> ack = personEmitter.send(Message.of(new Person(name)));
	}

}
