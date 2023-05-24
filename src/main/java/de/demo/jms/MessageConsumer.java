package de.demo.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.qpid.jms.JmsContext;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * A bean consuming prices from the JMS queue.
 */
@ApplicationScoped
public class MessageConsumer implements Runnable {
	
	public final static String CONSUMER_QUEUE = "messages-queue";

    @Inject
    ConnectionFactory connectionFactory;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    private volatile String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(JmsContext.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue(CONSUMER_QUEUE));
            while (true) {
                Message message = consumer.receive();
                if (message == null) return;
                lastMessage = message.getBody(String.class);
                
//                lastMessage = consumer.receiveBody(String.class, 2000L);
            }
        } catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}