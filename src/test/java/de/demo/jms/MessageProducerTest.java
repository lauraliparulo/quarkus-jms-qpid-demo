package de.demo.jms;

import static de.demo.jms.QpidJmsTestSupport.RECEIVE_MESSAGE_ENDPOINT_PATH;
import static de.demo.jms.QpidJmsTestSupport.SEND_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
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
    public void testSend() throws Exception {
        String body = QpidJmsTestSupport.generateBody();

        io.restassured.response.Response response = RestAssured.with().body(body).post(SEND_MESSAGE_ENDPOINT_PATH);
        Assertions.assertEquals(Status.NO_CONTENT.getStatusCode(), response.statusCode());

        try (JMSContext context = QpidJmsTestSupport.createContext()) {
        	context.start();
            Queue destination = context.createQueue(MessageProducer.PRODUCER_QUEUE);
            JMSConsumer consumer = context.createConsumer(destination);
            Thread.sleep(5000);
            
          response = RestAssured.with().body(body).get(RECEIVE_MESSAGE_ENDPOINT_PATH);
            Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());

            Assertions.assertEquals(body, response.getBody().asString(), "Received body did not match that sent");
            
    
        }
    }
    

}
