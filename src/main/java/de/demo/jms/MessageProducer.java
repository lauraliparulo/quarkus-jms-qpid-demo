package de.demo.jms;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

@ApplicationScoped
public class MessageProducer {
	public final static String PRODUCER_QUEUE = "messages-queue";
	

    @Inject
    ConnectionFactory connectionFactory;


    public void sendMessageBody(String body) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            Queue destination = context.createQueue(PRODUCER_QUEUE);
            JMSProducer producer = context.createProducer();

            producer.send(destination, body);
        }
    }
}
