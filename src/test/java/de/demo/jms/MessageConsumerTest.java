package de.demo.jms;

/*
* Copyright 2020 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import static de.demo.jms.QpidJmsTestSupport.RECEIVE_MESSAGE_ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
public class MessageConsumerTest {

	   @Test
	    public void testReceive() throws Exception {
	        String body = QpidJmsTestSupport.generateBody();
	      
	        try (JMSContext context = QpidJmsTestSupport.createContext()) {

	            Queue destination = context.createQueue(MessageConsumer.CONSUMER_QUEUE);
	            JMSProducer producer = context.createProducer();

	            producer.send(destination, body);
	            Response response = RestAssured.with().body(body).get(RECEIVE_MESSAGE_ENDPOINT_PATH);
	            Assertions.assertEquals(Status.OK.getStatusCode(), response.statusCode());

	            Assertions.assertEquals(body, response.getBody().asString(), "Received body did not match that sent");
	        }
         
	    }
    
}
