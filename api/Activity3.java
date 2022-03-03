package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity3 {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    void setUp() {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://petstore.swagger.io/v2/pet").
                setContentType(ContentType.JSON).build();
        responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).
                expectStatusCode(200).expectBody("status", equalTo("alive")).build();
    }

    @Test(priority = 1)
    void addPets() {
        given().spec(requestSpec).
                body("{\"id\": 772321, \"name\": \"don\", \"status\": \"alive\"}").
                when().post().then().log().all().spec(responseSpec);
        given().spec(requestSpec).
                body("{\"id\": 772322, \"name\": \"baba\", \"status\": \"alive\"}").
                when().post().then().log().all().spec(responseSpec);
    }

    @Test(priority = 2, dataProvider = "pets")
    void getPets(int id, String name, String status) {
        Response response = given().spec(requestSpec).when().pathParam("petId", id).get("/{petId}");
        response.then().log().all().spec(responseSpec).body("name", equalTo(name)).log().all();
    }

    @Test(priority = 3, dataProvider = "pets")
    void deletePets(int id, String name, String status) {
        Response response = given().spec(requestSpec).log().params().when().pathParam("petId", id).delete("/{petId}");
        response.then().log().all();

        response.then().body("code", equalTo(200));
    }

    @DataProvider(name = "pets")
    Object[][] dataProvider() {
        Object[][] testData = new Object[][]{
                {772321, "don", "alive"},
                {772322, "baba", "alive"}
        };
        return testData;
    }
//    @AfterClass
//    void tearDown() {
//        responseSpec=new ResponseSpecBuilder().expectContentType(ContentType.JSON).
//        expectStatusCode(200).expectBody("status",equalTo("alive")).build();
//    }
}
