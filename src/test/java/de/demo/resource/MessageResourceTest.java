package de.demo.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.CoreMatchers.notNullValue;
import io.restassured.response.Response;

@QuarkusTest
public class MessageResourceTest {

    @Test
    public void testGetMessageEndpoint() {
    	  Response response =    given()
          .when().get("/messages/last")
          .then().extract().response();
//             .statusCode(204)
//             .body(notNullValue());
        
        Assertions.assertEquals(204, response.statusCode());
        Assertions.assertNotNull(response.getBody().asString());
    }
    
    
    @Test
    public void testSendMessageEndpoint() {
//        given()
//          .when().post("/messages/send")
//          .then()
//             .statusCode(200);
    }
    

}