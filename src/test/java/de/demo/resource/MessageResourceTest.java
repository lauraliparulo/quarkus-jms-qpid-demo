package de.demo.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class MessageResourceTest {

    @Test
    public void testGetMessageEndpoint() {
        given()
          .when().get("/messages/last")
          .then()
             .statusCode(200)
             .body(notNullValue());
    }
    
    
    @Test
    public void testSendMessageEndpoint() {
        given()
          .when().post("/messages/send","this message")
          .then()
             .statusCode(200);
    }
    

}