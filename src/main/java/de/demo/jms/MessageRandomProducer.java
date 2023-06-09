package de.demo.jms;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

/**
 * A bean producing random prices every 5 seconds and sending them to the prices
 * JMS queue.
 */
@ApplicationScoped
public class MessageRandomProducer implements Runnable {

    static final String QUEUE_NAME = "test-qpid-jms-send";

	public final static String PRODUCED_MESSAGE = "produced_Message_";

	@Inject
	Logger log;

	@Inject
	ConnectionFactory connectionFactory;

	private final Random random = new Random();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	void onStart(@Observes StartupEvent ev) {
		scheduler.scheduleWithFixedDelay(this, 0L, 5L, TimeUnit.SECONDS);
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
	}

	@Override
	public void run() {
		try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
			Queue destination = context.createQueue(QUEUE_NAME);
			JMSProducer producer = context.createProducer();

			producer.send(destination, PRODUCED_MESSAGE + Integer.toString(random.nextInt(100)));
		}
		
	}

}