package project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;


public class RestAssuredProject {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    static int Id;
    static String token = "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";
    File file;

    @BeforeClass
    void setUp() {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://api.github.com").
                setContentType(ContentType.JSON).build().header("Authorization", "token " + token);
        responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
    }

    @Test(priority = 1)
    void postMthd() {
        file = new File("src/test/java/project/req.json");
        Response response = given().spec(requestSpec).body(file).when().post("/user/keys");
        System.out.println(response.getBody().prettyPrint());
        Id = response.jsonPath().get("id");
//        resMsg=response.toString();
        System.out.println("Id is " + Id);
        response.then().spec(responseSpec).statusCode(201);
    }

    @Test(priority = 2)
    void getMthd() {

        Response response = given().spec(requestSpec).when().get("/user/keys");
        System.out.println(response.getBody().prettyPrint());
        response.then().spec(responseSpec).statusCode(200);
    }

    @Test(priority = 3)
    void deleteMthd() {

        Response response = given().spec(requestSpec).when().pathParam("keyId", Id).delete("/user/keys/{keyId}");
        response.then().log().all();
        response.then().statusCode(204);
    }
}
