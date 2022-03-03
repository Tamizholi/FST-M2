package activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {
    File file;

    @BeforeClass
    void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        baseURI = "https://petstore.swagger.io/v2/pet";
        file = new File("src/test/java/activities/Act1Req.json");
    }

    @Test(priority = 1)
    void postMethod() {
        Response response = given().contentType(ContentType.JSON).baseUri(baseURI).
                body(file).when().log().all().post();
        response.then()
                .log().all();
        response.then().body("id", equalTo(172320));
        response.then().body("name", equalTo("TamCT"));
        response.then().body("status", equalTo("alive"));

    }

    @Test(priority = 2)
    void getMethod() {
        Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "172320").log().all().
                get(baseURI + "/{petId}");
        response.then()
                .log().all();
        response.then().body("id", equalTo(172320));
        response.then().body("name", equalTo("TamCT"));
        response.then().body("status", equalTo("alive"));


    }

    @Test(priority = 3)
    void deleteMethod() {
        Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "172320").log().all().
                delete(baseURI + "/{petId}");
        response.then()
                .log().all();
        response.then().assertThat().statusCode(200);
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("172320"));
    }
}
