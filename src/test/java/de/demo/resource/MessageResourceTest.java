package de.demo.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
public class MessageResourceTest {

//    @Test
    public void testGetMessageEndpoint() {
//    	  Response response =    given()
//          .when().get("/messages/last").andReturn().statusCode(Status.)
//          .then().extract().response();
////             .statusCode(204)
////             .body(notNullValue());
//        
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertNotNull(response.getBody().asString());
    }
    
    
//    @Test
    public void testSendMessageEndpoint() {
//        given()
//          .when().post("/messages/send")
//          .then()
//             .statusCode(200);
    }
    

}