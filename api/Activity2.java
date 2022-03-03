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

public class Activity2 {
    File file;

    @BeforeClass
    void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        baseURI = "https://petstore.swagger.io/v2/user";
        file = new File("src/test/java/activities/Act2Req.json");

    }

    @Test(priority = 1)
    void postMethod() {
        Response response = given().contentType(ContentType.JSON).when().body(file).log().all().post(baseURI);
        response.then().log().all();
        response.then().body("message", equalTo("30102"));
    }

    @Test(priority = 2)
    void getMethod() {
        Response response = given().contentType(ContentType.JSON).when().pathParam("username", "TamCT1").
                log().all().get(baseURI + "/{username}");
        response.then().log().all();
        response.then().body("id", equalTo(30102));
    }

    @Test(priority = 3)
    void deleteMethod() {
        Response response = given().contentType(ContentType.JSON).when().pathParam("username", "TamCT1").
                log().all().delete(baseURI + "/{username}");
        response.then().log().all();
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("TamCT1"));
    }
}
