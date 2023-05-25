package de.demo.jms;

import static de.demo.jms.QpidJmsTestSupport.SEND_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
public class MessageProducerTest {

	@Inject
	MessageConsumer messageConsumer;

	@Test
	public void testSend() throws Exception {
		String body = QpidJmsTestSupport.generateBody();
		
		JsonObject jsonObject = Json.createObjectBuilder().add("message", body).build();
		io.restassured.response.Response response = RestAssured.with().body(jsonObject).post(SEND_MESSAGE_ENDPOINT_PATH);
		Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());

		Assertions.assertEquals(body, messageConsumer.getLastMessage());

	}

}
