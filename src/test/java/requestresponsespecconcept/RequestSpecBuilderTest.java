package requestresponsespecconcept;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecBuilderTest {

    @Test
    public void reqSpecTest() {

        RequestSpecification requestSpec = given().log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json");

        //1.
        requestSpec
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);

        //2.
        requestSpec
                .body("{\n"
                        + "    \"title\": \"foo\",\n"
                        + "    \"body\": \"bar\",\n"
                        + "    \"userId\": 1\n"
                        + "}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void getUserTest() {

        RequestSpecification requestSpecification = given().baseUri("https://gorest.co.in")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6");

        requestSpecification.when().log().all().get("/public/v2/users")
                .then().log().all().assertThat().statusCode(200);

        requestSpecification.when().log().all().get("/public/v2/users/7738343")
                .then().log().all().assertThat().statusCode(200);

    }

    @Test
    public void getUserTest_queryParam() {
        RequestSpecification requestSpecification = given().baseUri("https://gorest.co.in")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6");

        requestSpecification.queryParam("gender", "male");

        requestSpecification.when().log().all()
                .get("/public/v2/users").then().log().all().assertThat().statusCode(200);

    }
}
