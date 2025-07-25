package requestresponsespecconcept;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestResponseSpecificationTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setUp() {
        //define request spec:
        requestSpecification = given().log().all().baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json");

        //define response spec:
        responseSpecification = expect().statusCode(anyOf(equalTo(200), equalTo(201)))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Server", "cloudflare")
                .time(lessThan(2419L)).log().all();
    }

    @Test
    public void checkGetTest() {
        requestSpecification.given().log().all()
                .when().log().all()
                .get("/posts/1").then().log().all().spec(responseSpecification)
                .body("userId", equalTo(1))
                .body("id", equalTo(1));
    }

    @Test
    public void checkGetTest_QueryParam() {
        requestSpecification.given().queryParam("userId", 1)
                .when().log().all()
                .get("/posts/1").then().log().all().spec(responseSpecification)
                .body("userId", equalTo(1))
                .body("id", equalTo(1));
    }

    @Test
    public void checkPostTest() {
        requestSpecification.body("{\n"
                        + "    \"title\": \"foo\",\n"
                        + "    \"body\": \"bar\",\n"
                        + "    \"userId\": 1433\n"
                        + "}")
                .when().log().all()
                .post("/posts").then().log().all().spec(responseSpecification)
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"));
    }
}
