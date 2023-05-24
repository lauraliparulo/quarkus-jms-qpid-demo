package de.demo.jms;

import static de.demo.jms.QpidJmsTestSupport.RECEIVE_MESSAGE_ENDPOINT_PATH;
import static de.demo.jms.QpidJmsTestSupport.SEND_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
public class MessageProducerTest {

	
//	@BeforeEach
//	public void initialise() throws InterruptedException {
//	    Thread.sleep(8000);
//	}
	
    @Test
    @Order(1)
    public void testSend() throws Exception {
        String body = QpidJmsTestSupport.generateBody();

        io.restassured.response.Response response = RestAssured.with().body(body).post(SEND_MESSAGE_ENDPOINT_PATH);
        Assertions.assertEquals(Status.NO_CONTENT.getStatusCode(), response.statusCode());

        try (JMSContext context = QpidJmsTestSupport.createContext()) {
            Queue destination = context.createQueue(MessageProducer.PRODUCER_QUEUE);
            JMSConsumer consumer = context.createConsumer(destination);
    
            Assertions.assertEquals(body, consumer.receiveBody(String.class, 2000L), "Received body did not match that sent");
        }
    }
    
    
    /**
     * Tests that receiving works in the {@link QpidJmsReceive} application code
     * using the extension, by sending a message to the broker and using the
     * {@link QpidJmsEndpoint} to trigger the app receiving it from the broker
     * and returning the body, finally comparing the response to that we sent.
     *
     * @throws Exception
     *             if there is an unexpected problem
     */
    @Test
    @Order(2)
    public void testReceive() throws Exception {
        String body = QpidJmsTestSupport.generateBody();
      
        
      
        try (JMSContext context = QpidJmsTestSupport.createContext()) {
        	context.start();
            Queue destination = context.createQueue(MessageConsumer.CONSUMER_QUEUE);
            JMSProducer producer = context.createProducer();

            producer.send(destination, body);
            
            Response response = RestAssured.with().body(body).get(RECEIVE_MESSAGE_ENDPOINT_PATH);
            Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());
  
            Assertions.assertEquals(body, response.getBody().asString(), "Received body did not match that sent");
        }


    
        
    }
}
