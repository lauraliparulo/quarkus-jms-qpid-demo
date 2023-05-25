package de.demo.jms;

import static de.demo.jms.QpidJmsTestSupport.SEND_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
public class MessageProducerTest {
	
	@Inject
	MessageConsumer consumer;
	
    @Test
    public void testSend() throws Exception {
        String body = QpidJmsTestSupport.generateBody();
        
        try (JMSContext context = QpidJmsTestSupport.createContext()) {
            Queue destination = context.createQueue(MessageProducer.PRODUCER_QUEUE);
            JMSConsumer consumer = context.createConsumer(destination);

            io.restassured.response.Response response = RestAssured.with().body(body).post(SEND_MESSAGE_ENDPOINT_PATH);
            Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());
     
            Assertions.assertEquals(body, consumer.receiveBody(String.class, 2000L), "Received body did not match that sent");
            
        }
    }
    

}
