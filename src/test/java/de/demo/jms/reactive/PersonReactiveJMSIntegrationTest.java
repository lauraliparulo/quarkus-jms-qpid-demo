package de.demo.jms.reactive;

import static de.demo.jms.QpidJmsTestSupport.RECEIVE_PERSONS_ENDPOINT_PATH;
import static de.demo.jms.QpidJmsTestSupport.SEND_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
public class PersonReactiveJMSIntegrationTest {

	@Inject
	PersonReactiveProducer personProducer;
	
	@Inject
	PersonReactiveConsumer personConsumer;

    @Test
    public void testReceivePersons() throws Exception {

         personProducer.produceAStreamOfMessagesOfPersons();
       
        Response response = RestAssured.with().get(RECEIVE_PERSONS_ENDPOINT_PATH);
        Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());

        Log.info(response.getBody().asPrettyString());
        Assertions.assertNotNull(response.getBody());
        
    }
    
    @Test
    public void testSendPersons() throws Exception {
    	RestAssured.with().body("Laura").post(SEND_MESSAGE_ENDPOINT_PATH);
    	
        Response response = RestAssured.with().get(RECEIVE_PERSONS_ENDPOINT_PATH);
        Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());
       
        Log.info(response.getBody());
        Assertions.assertNotNull(response.getBody());
    	
    	
    }
}
